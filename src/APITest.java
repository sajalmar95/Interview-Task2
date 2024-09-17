import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;

public class APITest {

    @BeforeClass
    public void setup() {
        // Base URI
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
    }

    @Test
    public void testAddNewDevice() {
        // payload
        Map<String, Object> data = new HashMap<>();
        data.put("year", 2023);
        data.put("price", 7999.99);
        data.put("CPU model", "Apple ARM A7");
        data.put("Hard disk size", "1 TB");

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Apple Max Pro 1TB");
        payload.put("data", data);

        // Sending POST request to add a new device
        Response response = given()
            .header("Content-Type", "application/json")
            .body(payload)
            .post();

        // Response validation syntax
        response.then().statusCode(200);

        
        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String createdAt = response.jsonPath().getString("createdAt");
        int year = response.jsonPath().getInt("data.year");
        double price = response.jsonPath().getDouble("data.price");

        // Validating response fields
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "createdAt should not be null");
        Assert.assertEquals(name, "Apple Max Pro 1TB");
        Assert.assertEquals(year, 2023);
        Assert.assertEquals(price, 7999.99);

        // Print response for debugging
        System.out.println("Response: " + response.asPrettyString());
    }
}
