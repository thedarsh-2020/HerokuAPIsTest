import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Delete_ID
{
    @Test
    public static void DeleteNewID()
    {
        UpdateBookingID.updateBookingData();

        Response requestResponse = RestAssured
                .given()
                .baseUri(CreateBooking.BaseURL)
                .basePath("/booking/{id}")
                .pathParam("id", CreateBooking.bookingID)
                .header("Cookie", "token=" + CreateToken.abstractedToken)
                .delete();

        // status code assertion
        requestResponse.then().statusCode(201);

        System.out.println("ID Deleted");
    }
}
