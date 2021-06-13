import Api.Store;
import Dto.Status;
import Responses.StoreResponse;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StoreTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(UserTest.class);

    @Test
    public void crudOperationForStoreFunction(){
        /**
         * Note : Result of "Wight Box Testing": Order data has default initialization in a Static Block.
         * Please look at class OrderData Line 26-30
         * static {
         *         order.add(createOrder(1, 1, 100, new Date(), "placed", true));
         *         order.add(createOrder(2, 1, 50, new Date(), "approved", true));
         *         order.add(createOrder(3, 1, 50, new Date(), "delivered", true));
         *     }
         * Since data is in Static Block, before each and every /store/order call, the quantities initialize with
         * default value again. It means the order quantities will Not never sum up correctly, because project has no DB interaction.
         * It can be taken as BUG. In order to further test, success criterias and scope of the testing must clearly set.
         * I assert only the first calculation.
         * There is no minus number limitation. Sending minus quantity as an order does not make sense.
         * Possibly, refund api can be built
         *
         * */

        logger.info("Getting initial status of store inventory");
        Response response = Store.getStoreInventory(env);
        int initialQuantityOfApproved = response.jsonPath().getInt(Status.APPROVED.name);
        int initialQuantityOfPlaced = response.jsonPath().getInt(Status.PLACED.name);
        int initialQuantityOfDelivered = response.jsonPath().getInt(Status.DELIVERED.name);

        //Order Setup
        String shipDate = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        int orderQuantity = 15;
        int orderId = 10;
        int petId= 191920;
        Map<String, Object> order = new HashMap<>();
        order.put("id",orderId);
        order.put("petId",petId);
        order.put("quantity", orderQuantity);
        order.put("shipDate", shipDate);
        order.put("status", Status.APPROVED.name);
        order.put("complete", true);

        logger.info("Place an order for a pet");
        Store.storeOrder(env, order);

        logger.info("New inventory record fetched");
        response = Store.getStoreInventory(env);
        int quantityOfApproved = response.jsonPath().getInt(Status.APPROVED.name);

        Assert.assertTrue(quantityOfApproved==initialQuantityOfApproved + orderQuantity,
                "Total order quantity should be correctly calculated");
    /**
     * Note : this part can be used as long as BUG fixes/DB migration.
        logger.info("Creating another order for same pet");
        shipDate = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        orderQuantity = 50;
        order = new HashMap<>();
        order.put("id",orderId);
        order.put("petId",petId);
        order.put("quantity", orderQuantity);
        order.put("shipDate", shipDate);
        order.put("status", Status.PLACED.name);
        order.put("complete", true);

        Store.storeOrder(env, order);

        response = Store.getStoreInventory(env);
        logger.info("New inventory record fetched");
        int quantityOfPlaced = response.jsonPath().getInt(Status.PLACED.name);
        Assert.assertTrue(quantityOfPlaced==initialQuantityOfPlaced + orderQuantity,
                "Total order quantity should be correctly calculated");
        */

        logger.info("Query for orders by specific order id");
        response = Store.getOrderById(env,orderId);
        StoreResponse storeResponse = new StoreResponse(response);
        int id = (int) storeResponse.detailsOfOrderByType("id");
        int responsePetId = (int) storeResponse.detailsOfOrderByType("petId");
        int responseQuantity = (int) storeResponse.detailsOfOrderByType("quantity");
        shipDate = (String) storeResponse.detailsOfOrderByType("shipDate");
        boolean isComplete = (boolean) storeResponse.detailsOfOrderByType("complete");

        Assert.assertEquals(id, orderId, "Order ids should match");
        Assert.assertEquals(petId, responsePetId, "Pet ids should match");
        Assert.assertEquals(orderQuantity, responseQuantity, "Order quantities should match");


        logger.info("Delete order by order id");
        Store.deleteOrderById(env, orderId).then()
                .assertThat().statusCode(200);

        logger.info("Verify that order can not found anymore");
        Store.getOrderById(env,orderId).then()
                .assertThat().statusCode(404);






    }


}
