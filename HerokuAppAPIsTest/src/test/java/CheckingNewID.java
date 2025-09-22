import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckingNewID
{
    @Test
    public static void checkNewID()
    {
        CreateBooking.createBooking();

        Response requestResponse = RestAssured
                .given()
                .baseUri(CreateBooking.BaseURL + "/booking")
                .get();

        // status code assertion
        requestResponse.then().statusCode(200);

        // extract data
        JsonPath checkingPath = requestResponse.jsonPath();

        // Checking new ID is exist
        List<Integer> IDs = checkingPath.getList("bookingid");
        Assert.assertTrue(IDs.contains(CreateBooking.bookingID));
    }
}
