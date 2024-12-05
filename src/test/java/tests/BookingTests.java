package tests;

import base.TestBase;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingTests extends TestBase {

    private int bookingId;

    @Test(priority = 1)
    public void testAddBooking() {
        // جسم الطلب
        String requestBody = "{\n" +
                "  \"firstname\": \"testFirstName\",\n" +
                "  \"lastname\": \"lastName\",\n" +
                "  \"totalprice\": 10.11,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2022-01-01\",\n" +
                "    \"checkout\": \"2024-01-01\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"testAdd\"\n" +
                "}";

        // إرسال الطلب
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/booking");

        // التحقق من الحالة
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200.");

        // استخراج ID الحجز
        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking ID: " + bookingId);
    }

    @Test(priority = 2, dependsOnMethods = "testAddBooking")
    public void testGetBooking() {
        // إرسال الطلب للتحقق
        Response response = given()
                .header("Content-Type", "application/json")
                .get("/booking/" + bookingId);

        // التحقق من الحالة
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200.");

        // التحقق من البيانات
        Assert.assertEquals(response.jsonPath().getString("firstname"), "testFirstName");
        Assert.assertEquals(response.jsonPath().getString("lastname"), "lastName");
        Assert.assertEquals(response.jsonPath().getFloat("totalprice"), 10);
        Assert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), "2022-01-01");
        Assert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), "2024-01-01");
    }

    @Test(priority = 3)
    public void testNegativeCase() {
        // طلب ببيانات غير صحيحة
        Response response = given()
                .header("Content-Type", "application/json")
                .get("/booking/9999999");

        // التحقق من الحالة
        Assert.assertEquals(response.getStatusCode(), 404, "Status code should be 404 for non-existing booking.");
    }
}
