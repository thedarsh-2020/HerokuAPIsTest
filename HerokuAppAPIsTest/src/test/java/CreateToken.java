import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateToken
{
    public static String abstractedToken;

    @Test
    public static void createNewToken()
    {
        String userName = "admin";
        String paasWord = "password123";

        CheckingNewIdData.checkData();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userName);
        requestBody.put("password", paasWord);

        Response requestResponse = RestAssured
                .given()
                .baseUri(CreateBooking.BaseURL + "/auth")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post();

        // status code assertion
        requestResponse.then().statusCode(200);

        // extract data
        JsonPath creatingPath = requestResponse.jsonPath();

        abstractedToken = creatingPath.getString("token");
    }
}
