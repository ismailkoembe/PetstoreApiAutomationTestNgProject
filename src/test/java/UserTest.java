import Api.User;
import Configuration.PropManager;
import Helpers.CsvParser;
import Responses.UserResponse;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;


public class UserTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(UserTest.class);
    String testUsersListPath= PropManager.getProperties(env, "testUsersListPath");

    @Test
    public void userCreate () {
        /**
         * This methods creates user.
         * All user credentials are stored in environment relevant properties file.
         * In order to demonstrate framework ability Hamcrest matcher was used.
         * /user api response is parsed through dedicated UserResponse class
         * Basically, Java HAS-A relationship is used
         * */

        Map<String, Object> user = new HashMap<>();
        user.put("id", Integer.parseInt(PropManager.getProperties(env, "generalUserId")));
        user.put("username", PropManager.getProperties(env, "generalUserName"));
        user.put("firstName", PropManager.getProperties(env, "generalUserFirstName"));
        user.put("lastName", PropManager.getProperties(env, "generalUserLastName"));
        user.put("email", PropManager.getProperties(env, "generalUserEmail"));
        user.put("password", PropManager.getProperties(env, "generalUserPassword"));
        user.put("phone", PropManager.getProperties(env, "generalUserPhone"));
        user.put("userStatus", PropManager.getProperties(env, "generalUserStatus"));

        logger.info("This is Hamcrest Matchers example");
        User.createUser(env, user).then()
                .assertThat().body("firstName" , equalTo(PropManager.getProperties(env, "generalUserFirstName")))
                .assertThat().body("lastName" , equalTo(PropManager.getProperties(env, "generalUserLastName")))
                .assertThat().statusCode(200);

        logger.info("This is dedicated response class example");
        Response response = User.createUser(env, user);
        UserResponse userResponse = new UserResponse(response);
        Assert.assertEquals(userResponse.getUserId(), Integer.parseInt(PropManager.getProperties(env, "generalUserId")), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserStatus(), Integer.parseInt(PropManager.getProperties(env, "generalUserStatus")), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserFirstName(), PropManager.getProperties(env, "generalUserFirstName"), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserLastName(), PropManager.getProperties(env, "generalUserLastName"), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserEmail(), PropManager.getProperties(env, "generalUserEmail"), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserPassword(), PropManager.getProperties(env, "generalUserPassword"), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserPhone(), PropManager.getProperties(env, "generalUserPhone"), "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserName(), PropManager.getProperties(env, "generalUserName"), "Userid should match in DB");

    }

    @Test
    public void createUserWithList() throws Exception {
        /**
         * In order to show framework ability, user list parsed through environment dedicated Csv file
         *
        * */
        List<Map<String, String>> userList = CsvParser.csvToCollection(
                new File(testUsersListPath));
        logger.info("testUser list parsed through Csv file");
        int testUserListSize = userList.size();

        Response response = User.createUserWithList(env, userList);
        UserResponse userResponse = new UserResponse(response);
        logger.info("Users coming from Csv file is being used as request body");
        Assert.assertEquals(userResponse.getUserListSize(), testUserListSize, "All user is in the list should be in server response");
        List<Map<String, Object>> createdUserList = userResponse.getCreatedUserUserList();

    }

    @Test
    public void login(){
        /**
         * Since I have no Business requirement and success criteria for /user/login and
         * user/logout I can't evaluate actual and expected status.
         * Those apis have no restriction. Even without username and password, api still returns success message,
         * session number and 200 status code
         * That's why, the negative path is not automated
         * */

        String username = "johnDoe";
        String password = "1Qweasdzxc";
        Response response = User.userLogin(env, username, password);
        UserResponse userResponse = new UserResponse(response);
        String sessionNumber = userResponse.getSessionNumber();
        logger.info("User logged in and session number is : "+sessionNumber);
        Assert.assertNotNull(userResponse.getSessionNumber(), "Server response should contain session number");

        Assert.assertEquals(User.userLogout(env).body().asString(), "User logged out");
        logger.info("Current user logged out");
    }

    @Test
    public void crudOperationForUser(){
        /**
         * This test case covers all CRUD user operations
         * Since test clears user completely, test can run again
         * 1. Create user with given parameters
         * 2. Get user info and verify that user created successfully
         * 3. Update username and some properties of user
         * 4. Get user info for old user and verify that server does not send response
         * 5. Verify that username is updated
         * 6. Delete updated user
         * 7. Verify that user deleted
         * */

        Map<String, Object> user = new HashMap<>();
        user.put("id", 18);
        user.put("username", "ardaHan");
        user.put("firstName", "arda");
        user.put("lastName", "koembe");
        user.put("email", "ardakoembe@example.com");
        user.put("password", "qweasdzxc");
        user.put("phone", "123456");
        user.put("userStatus", 3);


        logger.info("A new user created");
        User.createUser(env, user);

        logger.info("Get user info and verify that user created successfully");

        String username = "ardaHan";
        Response response = User.getUserInfo(env, username);
        UserResponse userResponse = new UserResponse(response);
        Assert.assertEquals(userResponse.getUserId(), 18, "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserStatus(), 3, "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserFirstName(), "arda", "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserLastName(), "koembe", "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserEmail(), "ardakoembe@example.com", "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserPassword(), "qweasdzxc", "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserPhone(), "123456", "Userid should match in DB");
        Assert.assertEquals(userResponse.getUserName(), "ardaHan", "Userid should match in DB");


        logger.info("Email, username and phone fields will be updated and validated");
        String newUserName = "blackJoker";
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("id", 19);
        newUser.put("username", newUserName);
        newUser.put("firstName", "arda");
        newUser.put("lastName", "koembe");
        newUser.put("email", "arda@example.com");
        newUser.put("password", "qweasdzxc");
        newUser.put("phone", "123456");
        newUser.put("userStatus", 3);
        response= User.updateUserName(env, username,newUser);


        logger.info("Get user info for old user and verify that server does not send response");
        Assert.assertEquals(User.getUserInfo(env, username).body().asString(),"User not found", "Query with old user should fail ");

        logger.info("Verify that username is updated");
        User.getUserInfo(env, newUserName).then().assertThat().statusCode(200)
        .assertThat().body("username", equalTo(newUserName));

        logger.info("User will be deleted");;
        User.deleteUserName(env, newUserName).then().assertThat().statusCode(200);

        logger.info("Call get /user/{username} api and verify that No user found message show up");
        Assert.assertEquals(User.getUserInfo(env, username).body().asString(),"User not found", "Query with old user should fail ");


    }

}
