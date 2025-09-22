import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UpdateBookingID
{
    @Test
    public static void updateBookingData()
    {
        String updatedClientFirstName       = "Musta";
        String updateClientLastName         = "Anas";
        Integer updateTotalPrice            = 500;
        Boolean updateDepositPaid           = false;
        String updateCheckInDate            = "2026-10-21";
        String updateCheckOutDate           = "2026-11-20";

        CreateToken.createNewToken();

        Map<String, Object> requestHeader = new HashMap<>();
        requestHeader.put("Content-Type", "application/json");
        requestHeader.put("Cookie", "token=" + CreateToken.abstractedToken);

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", updateCheckInDate);
        bookingDates.put("checkout", updateCheckOutDate);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", updatedClientFirstName);
        requestBody.put("lastname", updateClientLastName);
        requestBody.put("totalprice", updateTotalPrice);
        requestBody.put("depositpaid", updateDepositPaid);
        requestBody.put("bookingdates", bookingDates);

        Response requestResponse = RestAssured
                .given()
                .baseUri(CreateBooking.BaseURL)
                .basePath("/booking/{id}")
                .pathParam("id", CreateBooking.bookingID)
                .headers(requestHeader)
                .body(requestBody)
                .put();

        // printing the response after editing the data
        System.out.println("Check new Data:");
        requestResponse.prettyPrint();
        System.out.print("\n");

        // status code assertion
        requestResponse.then().statusCode(200);

        // extract data
        JsonPath creatingPath = requestResponse.jsonPath();

        //Checking response data
        Assert.assertEquals(creatingPath.getString("firstname"), updatedClientFirstName);
        Assert.assertEquals(creatingPath.getString("lastname"), updateClientLastName);
        Assert.assertEquals(creatingPath.getInt("totalprice"), updateTotalPrice);
        Assert.assertEquals(creatingPath.getBoolean("depositpaid"), updateDepositPaid);
        Assert.assertEquals(creatingPath.getString("bookingdates.checkin"), updateCheckInDate);
        Assert.assertEquals(creatingPath.getString("bookingdates.checkout"), updateCheckOutDate);
    }
}
