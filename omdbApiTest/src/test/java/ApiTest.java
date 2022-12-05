import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import org.apache.http.HttpStatus;

import com.sun.org.glassfish.gmbal.Description;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;

public class ApiTest {
    private RequestSpecification requestSpecification;

    @Before
    @Description("Base Url tanımlama")
    public void setUp(){
        RestAssured.baseURI="http://www.omdbapi.com";
        requestSpecification = RestAssured.given();
        requestSpecification.
                param("apikey","72ddef2f").
                param("s", "Never back down").
                param("type", "movie").
                param("y", "").
                param("r", "json").
                param("page", "1").
                param("callback", "").
                param("v", "1");
    }

    @Test
    @Description("Status'un 200(Success) gelme kontrolü")
    public void testDoRequestForStatusCode(){
        requestSpecification.
                when().get(baseURI).then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @Description("Gelen json body içerisindeki filmin gösterim yılının kontrolü")
    public void testMovieReleaseYear(){
        requestSpecification
                .when().get(baseURI).then().body("Year",equalTo("2008"));
    }

    @Test
    @Description("Gelen response içeriği tipinin kontrolü")
    public void testContentType(){
        requestSpecification
                .when().get(baseURI).then().contentType(ContentType.JSON);
    }

    @Test
    @Description("Response json'da Ratings keyinin Source ve Value'ya sahip olduğunun kontrolü")
    public void test(){
        requestSpecification
                .when().get(baseURI).then().assertThat().body("Ratings",
                        hasItems("Source","Value"));
    }

    @AfterClass
    public static void tearDown(){
        // Do something for last activities
    }

}
