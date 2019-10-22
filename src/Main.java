import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class Main extends BaseTest {


    private static String apiSearchPath="/_hotels/api/suggestRequest";

    public static void redirectTest(String source, String location) {
        given()
        .redirects().follow(false).and().redirects().max(0)
                .when()
                .get(source)
                .then()
                .statusCode(301)
                .header("location", location);
    }

    public void redirectToRu() {
        given()
                .redirects().follow(false).and().redirects().max(0)
                .when()
                .get("https://www.onetwotrip.com")
                .then()
                .statusCode(301)
                .header("location", "https://www.onetwotrip.com/ru/");
    }

    /***
     *
     * @param query строка или подстрока поиска
     * @param searchType geo, city, airport
     * @param results список результатов поиска через запятую без пробела
     *
     */
    public static void search(String query, String searchType, String results) {
        given()
                .param("query", query)
                .param("limit", 7)
                .param("hotelsFormLocation", "ott")
                .param("lang", "ru")
                .param("locale", "ru")
                .param("currency", "RUB")
                .when()
//                .log().parameters()
                .get(apiSearchPath)
                .then()
                .body(
                        "error", equalTo(null),
                        "result.findAll { it.type=='"+searchType+"' }.name", hasItems(results.split(","))
                )
                .assertThat().body(matchesJsonSchemaInClasspath("schema.json")); // провверка на соответствие ответа схеме json
    }

    public static void accesibility() {
        given()
                .when()
                .get(apiSearchPath)
                .then()
                .statusCode(200); // здесь проверили, что метод не отвалился и возвращает ОК
    }
}


