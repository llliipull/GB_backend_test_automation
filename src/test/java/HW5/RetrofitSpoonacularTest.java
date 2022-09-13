package HW5;

import HW5.Data.AddToShoppingListRequest;
import HW5.Data.AddToShoppingListResult;
import HW5.Data.DeleteFromShoppingListResult;
import HW5.Data.GetShoppingListResult;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RetrofitSpoonacularTest {

    /*Надеюсь что правильно понял, что нужно создать pojo,
    затем в тестах вызвать запросы через ретрофит
    и потом протестировать ответы*/

    SpoonacularService service = new SpoonacularService();

    static AddToShoppingListRequest addToShoppingListRequest = new AddToShoppingListRequest();
    AddToShoppingListResult addToShoppingListResult = service.addToShoppingListResult(addToShoppingListRequest);
    GetShoppingListResult getShoppingListResult = service.getShoppingListResult();
    DeleteFromShoppingListResult deleteFromShoppingListResult = service.deleteFromShoppingListResult();
    String addToListJson = "{\n" +
            "\t\"item\": \"1 package baking powder\",\n" +
            "\t\"aisle\": \"Baking\",\n" +
            "\t\"parse\": true\n" +
            "}";

    @BeforeAll
    static void setupBody(){
        addToShoppingListRequest.setItem("1 package baking powder");
        addToShoppingListRequest.setAisle("Baking");
        addToShoppingListRequest.setParse(true);
    }

    @Order(1)
    @Test
    void addToListTest(){

        service.addToShoppingListResult(addToShoppingListRequest);

        Assertions.assertTrue(addToShoppingListResult.getName().toLowerCase()
                .contains(addToShoppingListRequest.getAisle().toLowerCase()));
    }

    @Order(2)
    @Test
    void getShopListTest(){
        service.getShoppingListResult();
        Assertions.assertEquals(addToShoppingListResult.getId(),
                getShoppingListResult.getAisles().get(0).getItems().get(0).getId());
    }

    @Order(3)
    @Test
    void deleteFromShopListTest(){
        Assertions.assertTrue(deleteFromShoppingListResult.getStatus().equalsIgnoreCase("success"));
    }
}
