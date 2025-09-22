import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateBooking
{
    public static Integer bookingID;
    public static String BaseURL            = "https://restful-booker.herokuapp.com";

    public static String clientFirstName    = "Anas";
    public static String clientLastName     = "Musta";
    public static Integer totalPrice        = 100;
    public static Boolean depositPaid       = true;
    public static String checkInDate        = "2025-10-21";
    public static String checkOutDate       = "2025-11-20";

    @Test
    public static void createBooking()
    {
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkInDate);
        bookingDates.put("checkout", checkOutDate);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", clientFirstName);
        requestBody.put("lastname", clientLastName);
        requestBody.put("totalprice", totalPrice);
        requestBody.put("depositpaid", depositPaid);
        requestBody.put("bookingdates", bookingDates);

        Response requestResponse = RestAssured
                .given()
                .baseUri(BaseURL + "/booking")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post();

        // printing the response
        System.out.println("Data after booking:");
        requestResponse.prettyPrint();
        System.out.print("\n");

        // status code assertion
        requestResponse.then().statusCode(200);

        // extract data
        JsonPath creatingPath = requestResponse.jsonPath();

        bookingID = creatingPath.getInt("bookingid");

        //Checking response data
        Assert.assertEquals(creatingPath.getString("booking.firstname"), clientFirstName);
        Assert.assertEquals(creatingPath.getString("booking.lastname"), clientLastName);
        Assert.assertEquals(creatingPath.getInt("booking.totalprice"), totalPrice);
        Assert.assertEquals(creatingPath.getBoolean("booking.depositpaid"), depositPaid);
        Assert.assertEquals(creatingPath.getString("booking.bookingdates.checkin"), checkInDate);
        Assert.assertEquals(creatingPath.getString("booking.bookingdates.checkout"), checkOutDate);
    }
}
