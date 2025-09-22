import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckingNewIdData
{
    @Test
    public static void checkData()
    {
        CheckingNewID.checkNewID();

        Response requestResponse = RestAssured.given().baseUri(CreateBooking.BaseURL)
                .basePath("/booking/{id}")
                .pathParam("id", CreateBooking.bookingID)
                .get();

        // printing the response
        System.out.println("Check ID Data:");
        requestResponse.prettyPrint();
        System.out.print("\n");

        // status code assertion
        requestResponse.then().statusCode(200);

        // extract data
        JsonPath creatingPath = requestResponse.jsonPath();

        //Checking response data
        Assert.assertEquals(creatingPath.getString("firstname"), CreateBooking.clientFirstName);
        Assert.assertEquals(creatingPath.getString("lastname"), CreateBooking.clientLastName);
        Assert.assertEquals(creatingPath.getInt("totalprice"), CreateBooking.totalPrice);
        Assert.assertEquals(creatingPath.getBoolean("depositpaid"), CreateBooking.depositPaid);
        Assert.assertEquals(creatingPath.getString("bookingdates.checkin"), CreateBooking.checkInDate);
        Assert.assertEquals(creatingPath.getString("bookingdates.checkout"), CreateBooking.checkOutDate);
    }
}
