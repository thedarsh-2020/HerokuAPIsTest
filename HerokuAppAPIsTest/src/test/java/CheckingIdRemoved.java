import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckingIdRemoved
{
    @Test
    public void checkIdRemoved()
    {
        Delete_ID.DeleteNewID();

        Response requestResponse = RestAssured
                .given()
                .baseUri(CreateBooking.BaseURL + "/booking")
                .get();

        // status code assertion
        requestResponse.then().statusCode(200);

        // extract data
        JsonPath checkingPath = requestResponse.jsonPath();

        // Checking new ID is NOT exist
        List<Integer> IDs = checkingPath.getList("bookingid");
        Assert.assertFalse(IDs.contains(CreateBooking.bookingID));

        System.out.println("After checking ID is NOT found");
    }
}
