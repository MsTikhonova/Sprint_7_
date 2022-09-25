import com.google.gson.Gson;
import config.Config;
import config.CreateCourierData;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {
    CreateCourierData createCourier;
    String oauthToken;

    @Before
    public void setUp(){
        createCourier = CreateCourierData.getRandomCourier();
        oauthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjdkMzE4ZWQzYjg2YTAwM2Q2N2M1ODQiLCJpYXQiOjE2NjQxMjk1ODMsImV4cCI6MTY2NDczNDM4M30.IKzjRlcG_JmPBl7WKUuI5CVkJn7sU4jiX_rKZt8TZqk";
        }

        @Test
    public void isCourierCreated(){
            Gson gson = new Gson();
            String json = gson.toJson(createCourier);
            boolean isOk = given().log().all()
                    .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                    .baseUri(Config.BASE_URL)
                    .body(json)
                    .when()
                    .post("/courier")
                    .then().log().all()
                    .assertThat()
                    .statusCode(201)
                    .extract()
                    .path("ok");
        }

    @Test
    public void isCourierWithSameLoginCanBeCreated(){
        Gson gson = new Gson();
        String json = gson.toJson(createCourier);
        boolean isOk = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                .baseUri(Config.BASE_URL)
                .body(json)
                .when()
                .post("/courier")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");

        ValidatableResponse message = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                .baseUri(Config.BASE_URL)
                .body(json)
                .when()
                .post("/courier")
                .then().log().all()
                .assertThat()
                .statusCode(409);
    }

    @Test
    public void isCourierWithoutPasswordCanBeCreated(){
        CreateCourierData courierWithoutPassword = new CreateCourierData("login", "firstName");
        Gson gson = new Gson();
        String json = gson.toJson(courierWithoutPassword);
        ValidatableResponse message = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                .baseUri(Config.BASE_URL)
                .body(json)
                .when()
                .post("/courier")
                .then().log().all()
                .assertThat()
                .statusCode(400);
    }
}
