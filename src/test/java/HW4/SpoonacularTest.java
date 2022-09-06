package HW4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;

import static io.restassured.path.json.JsonPath.with;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpoonacularTest {

    String addToShoppingListBody = readResourceAsString("addToShoppingListBody.json");
    Response response;
    File addToShoppingListBodyFile =
            new File("src/test/resources/HW4/SpoonacularTest/addToShoppingListBody.json");
    File getShoppingListExpectedFile =
            new File("src/test/resources/HW4/SpoonacularTest/getShoppingListExpected.json");
    String aisleValue = with(addToShoppingListBodyFile).get("aisle");
    Integer productId = with(getShoppingListExpectedFile).getInt("id");


    /*
    Делал данный метод по подобию метода readResourceAsString, но что-то пошло не так.
    Идея ругается, что не может найти файл, хотя файл существовал(имя файла передавалось параметром (filePath) в метод).
    Помог только хардкод имени файла :( Если есть возможность, подскажите пожалуйста, в чем проблема
    */
    void writeToFile(String responseName){
     //   String path = getClass().getSimpleName() + FileSystems.getDefault().getSeparator() + filePath;
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/test/resources/HW4/SpoonacularTest/getShoppingListExpected.json"))){
            writer.write(String.valueOf(responseName));
        } catch(IOException e){
            e.printStackTrace();
        }
    }




    private String readResourceAsString(String resourceName) {
        String path = getClass().getSimpleName() + FileSystems.getDefault().getSeparator() + resourceName;
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            assert inputStream != null;
            byte[] data = inputStream.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeAll
    static void setup(){
        RestAssured.baseURI = "https://api.spoonacular.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", "d64cb88ee7fa4dd98c3cead1d89b25b9")
                .addPathParam("username", "gbhwuser")
                .addQueryParam("hash", "74a219d083326916b34159c089033d8269f020ab")
                .build();

    }

    @Order(1)
    @Test
    void addToShoppingListTest() {
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(addToShoppingListBody)
                .expect()
                .statusCode(200)
                .body("aisle", is(aisleValue))
                .when()
                .post("mealplanner/{username}/shopping-list/items");
         writeToFile(String.valueOf(response.asPrettyString()));
    }

    @Order(2)
    @Test
    void getShoppingListTest(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("aisles.items.id[0]", contains(productId))
                .when()
                .get("mealplanner/{username}/shopping-list");
    }

    @Order(3)
    @Test
    void deleteFromShoppingList(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", productId)
                .build();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("status", is("success"))
                .when()
                .delete("mealplanner/{username}/shopping-list/items/{id}");
    }

}
