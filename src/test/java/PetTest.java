import Api.Pet;
import Dto.PetCategories;
import Dto.Status;
import Responses.PetResponse;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class PetTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(PetTest.class);

    @Test
    public void draft_createPet(){
        Response response;
        String petStatus = Status.AVAILABLE.name;
        int petId = 1001;
        String petName= "Buddy";
        int categoryId= PetCategories.CATS.getCategoryNumber();
        String categoryName = PetCategories.CATS.getName();
        int tagId= 1;
        String tagName= "Domesticated";
        String photoUrl = "https://www.pexels.com/photo/two-yellow-labrador-retriever-puppies-1108099/";

        logger.info("New pet was added in list");
        response = Pet.addNewPet(env, petId, petName, petStatus,categoryId,categoryName,tagId,tagName, photoUrl);


        logger.info("Call pet api with petId and verify that server returns newly created pet information ");
        response = Pet.getPetById(env, petId);
        PetResponse petResponse = new PetResponse(response);
        int id = (int) petResponse.getInformationByType("id");
        String name = (String) petResponse.getInformationByType("name");
        String status = (String) petResponse.getInformationByType("status");

        Map<String, Object> categoryInformation = petResponse.getCategoryInformation();
        List<Map<String, Object>> tagsInformation = petResponse.getTagsInformation();

        Assert.assertEquals(petId, id, "Server should return correct pet id");
        Assert.assertEquals(petName, name, "Server should return correct pet name");
        Assert.assertEquals(petStatus, status, "Server should return correct pet status");
        Assert.assertEquals(categoryInformation.get("name"), categoryName, "Server should return correct category name");
        Assert.assertEquals(tagsInformation.get(0).get("name"), tagName, "Server should return correct category name");



        petStatus = Status.SOLD.name;
        petName= "Buddy";
        tagId= 2;
        tagName= "Wild";
        logger.info("Updating some information of existing pet");
        response = Pet.addNewPet(env, petId, petName, petStatus,categoryId,categoryName,tagId,tagName, photoUrl);


        logger.info("Call pet api with petId and verify that server returns UPDATED pet information ");
        response = Pet.getPetById(env, petId);
        petResponse = new PetResponse(response);
        id = (int) petResponse.getInformationByType("id");
        name = (String) petResponse.getInformationByType("name");
        status = (String) petResponse.getInformationByType("status");

        categoryInformation = petResponse.getCategoryInformation();
        tagsInformation = petResponse.getTagsInformation();

        Assert.assertEquals(petId, id, "Server should return correct pet id");
        Assert.assertEquals(petName, name, "Server should return correct pet name");
        Assert.assertEquals(petStatus, status, "Server should return correct pet status");
        Assert.assertEquals(categoryInformation.get("name"), categoryName, "Server should return correct category name");
        Assert.assertEquals(tagsInformation.get(0).get("name"), tagName, "Server should return correct category name");


        logger.info ("Deleting the pet from list");
        Pet.deletePetById(env, petId).then().assertThat().statusCode(200);

        logger.info("Call pet api with deleted pet id and verify that pet is not exist anymore");
        Pet.getPetById(env, petId).then().assertThat().statusCode(404);



    }


}
