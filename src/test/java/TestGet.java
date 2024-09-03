import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TestGet {

    @Test
    public void GetBookingIdsTest()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.when().get();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", not(0));
        response.then().log().body();

    }

    @Test
    public void GetBookinTest()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().pathParams("id", "1").when().get("/{id}");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", not(0));
        response.then().log().body();
        //revisamos los campos del json que no esten vacios
        response.then().assertThat()
                .body("firstname", Matchers.not(Matchers.isEmptyOrNullString()))
                .body("lastname", Matchers.not(Matchers.isEmptyOrNullString()))
                .body("totalprice", Matchers.notNullValue()) // Verifica que no sea nulo (totalprice es numérico)
                .body("depositpaid", Matchers.notNullValue()) // Verifica que no sea nulo (depositpaid es booleano)
                .body("bookingdates.checkin", Matchers.not(Matchers.isEmptyOrNullString()))
                .body("bookingdates.checkout", Matchers.not(Matchers.isEmptyOrNullString()));
    }
    @Test
    public void ControlIdOnGetBookings()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().pathParams("id", "abcd").when().get("/{id}");
        response.then().assertThat().statusCode(404);
        response.then().log().body();
        response.then().assertThat().body("size()", equalTo(0));
    }


}
