package base;

import io.restassured.RestAssured;

public class TestBase {
    public TestBase() {
        // إعداد قاعدة URL
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }
}
