import com.google.gson.Gson;
import config.Config;
import config.CreateCourierData;
import config.LoginData;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginTest {

    CreateCourierData createCourier;
    String oauthToken;
    @Before
    public void setUp() {
        createCourier = CreateCourierData.getRandomCourier();
        oauthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjdkMzE4ZWQzYjg2YTAwM2Q2N2M1ODQiLCJpYXQiOjE2NjQxMjk1ODMsImV4cCI6MTY2NDczNDM4M30.IKzjRlcG_JmPBl7WKUuI5CVkJn7sU4jiX_rKZt8TZqk";
    }

    @Test
    public void isLoginNotFound() {
        LoginData loginData  = new LoginData("ninja", "1234");
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        ValidatableResponse isNotFound = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                .baseUri(Config.BASE_URL)
                .body(json)
                .when()
                .post("/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void isSomeFieldNull() {
        LoginData loginData  = new LoginData("ninja");
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        ValidatableResponse isNotFound = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                .baseUri(Config.BASE_URL)
                .body(json)
                .when()
                .post("/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void isLoginOk() {
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

        LoginData loginData = new LoginData(createCourier.getLogin(), createCourier.getPassword());
        Gson loginGson = new Gson();
        String loginJson = loginGson.toJson(loginData);
        ValidatableResponse isLoginOk = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(oauthToken)
                .baseUri(Config.BASE_URL)
                .body(loginJson)
                .when()
                .post("/courier/login")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

}
