import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class TestPost {

    @Test
    public void TestPostCorrecto() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        //Booking
        Booking booking = new Booking();
        booking.setFirstname("Juan");
        booking.setLastname("Martin");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);

        //  BookingDates
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2017-06-19");
        bookingDates.setCheckout("2021-10-03");


        // Establecer bookingdates en booking
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println("payload\n"+payload);
        // solicitud POST
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/booking");


      response.then().assertThat().statusCode(200);
        //response.then().log().body();
        response.then().log().all(); // Esto registrará toda la respuesta

    }

    @Test
    public void RevisarJsonPost() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";

        //Booking
        Booking booking = new Booking();
        booking.setFirstname("");
        booking.setLastname("");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);

        //  BookingDates
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2017-06-19");
        bookingDates.setCheckout("2021-10-03");

        // Establecer bookingdates en booking
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        // solicitud POST
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post();

        response.then().log().body();


        //status code for Unprocessable Entity
        response.then().assertThat().statusCode(422);


    }



}
