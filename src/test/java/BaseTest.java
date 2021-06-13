import Configuration.PropManager;
import Dto.UserDto;
import Environments.Envs;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    /// Environments
    Envs testEnv = Envs.STAGE;
    public String env = testEnv.name();
    UserDto context;

    @BeforeMethod(enabled = false)
    public void setupUserContext(Method method) {
        /**
         * BaseTest class is essential part of the framework.
         * In order to switch environment, BaseTest class holds central point.
         * By means of Before-After (Suite, Class, Method) methods TestRail integration
         * should be provided.
         * Here is a one user creation example which can be used all user dependent test.
         * PetStore app could be consider as less capable to use additional framework components
         * */


        // Fill the user context object with data based on a typical user

        logger.info("Running tests on: " + env);
        context = new UserDto();
        logger.info("Not skipping user setup for: " + method.getName());
        Map<String, Object> user = new HashMap<>();
        user.put("id", Integer.parseInt(PropManager.getProperties(env, "generalUserId")));
        user.put("username", PropManager.getProperties(env, "generalUserName"));
        user.put("firstName", PropManager.getProperties(env, "generalUserFirstName"));
        user.put("lastName", PropManager.getProperties(env, "generalUserLastName"));
        user.put("email", PropManager.getProperties(env, "generalUseUserEmail"));
        user.put("password", PropManager.getProperties(env, "generalUserPassword"));
        user.put("phone", PropManager.getProperties(env, "generalUserPhone"));
        user.put("userStatus", PropManager.getProperties(env, "generalUserStatus"));

        Response resp = Api.User.createUser(env, user);

    }
}
