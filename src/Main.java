import io.restassured.response.ValidatableResponseOptions;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Main extends BaseTest {
    Map<String, String> cookies;


    @Test
    public void redirectToWWW() {
        ValidatableResponseOptions vro = given()
        .redirects().follow(false).and().redirects().max(0)
                .when()
                .get("https://onetwotrip.com")
                .then()
                .statusCode(301)
                .header("location", "https://www.onetwotrip.com/");
    }

    @Test
    public void redirectToRu() {
        ValidatableResponseOptions vro = given()
                .redirects().follow(false).and().redirects().max(0)
                .when()
                .get("https://www.onetwotrip.com")
                .then()
                .statusCode(301)
                .header("location", "https://www.onetwotrip.com/ru/");
    }

    @Test
    public void search() {
        given()
                .param("query", "мос")
                .param("limit", 7)
                .param("hotelsFormLocation", "ott")
                .param("lang", "ru")
                .param("locale", "ru")
                .param("currency", "RUB")
                .when()
                .log().parameters()
                .get("/_hotels/api/suggestRequest")
                .then()
//                .log().all()
                .body(
                        "error", equalTo(null),
                        // Здесь проверили, что возвращаются первые позиции из списка геолокаций, аэропортов и отелей.
                        "result.findAll { it.type=='geo' }.name", hasItems("Москва", "Мостар", "Моссел-Бей"),
                        "result.findAll { it.type=='hotel' }.name", hasItems("Москва Гост", "МОСКВА 1", "Красивые апартаменты в новом доме рядом с мостами и дворцами"),
                        "result.findAll { it.type=='airport' }.name", hasItems("Шереметьево", "Домодедово", "Внуково")

                )
                .assertThat().body(matchesJsonSchemaInClasspath("schema.json"))
                .statusCode(200); // здесь проверили, что метод не отвалился и возвращает ОК
    }
}


