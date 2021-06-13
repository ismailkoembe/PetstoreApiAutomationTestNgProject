package Helpers;

import Dto.UserDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateTestUser {
    public static List<UserDto> createTestUsersFromCsv(String path) throws IOException{

        List<UserDto> result = new ArrayList<>();
        ArrayList<Map<String, String>> testUsers = CsvParser.csvToCollection(new File(path));

        for (int i = 0; i < testUsers.size(); i++) {
            UserDto user = new UserDto();
            user.setId(Integer.parseInt(testUsers.get(i).get("userid")));
            user.setUsername(testUsers.get(i).get("username"));
            user.setFirstName(testUsers.get(i).get("firstName"));
            user.setLastName(testUsers.get(i).get("lastName"));
            user.setEmail(testUsers.get(i).get("email"));
            user.setPassword(testUsers.get(i).get("password"));
            user.setPhone(testUsers.get(i).get("phone"));
            user.setUserStatus(Integer.parseInt(testUsers.get(i).get("userStatus")));

            result.add(user);
        }
        return result;
    }
}
