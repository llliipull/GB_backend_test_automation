package HW6;

import HW6.db.dao.ProductsMapper;
import HW6.db.model.Products;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrmTests {

    private static ProductResponse response;

    private static String createProductBodyJson = "{\n" +
            "  \"title\": \"testProduct\",\n" +
            "  \"price\": 999,\n" +
            "  \"categoryTitle\": \"Food\"\n" +
            "}";


    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://minimarket1.herokuapp.com/market";
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createProductBodyJson)
                .expect()
                .statusCode(201)
                .when()
                .post("/api/v1/products")
                .as(ProductResponse.class);
    }

    @Order(1)
    @Test
    void createProductTest() throws IOException {

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));

        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(response.getId());
            Assertions.assertEquals(response.getTitle(), product.getTitle());
        }
    }

    @Order(2)
    @Test
    void deleteProductTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParams("id", response.getId())
                .expect()
                .statusCode(200)
                .when()
                .delete("/api/v1/products/{id}");
    }

    @Order(3)
    @Test
    void deletedProductDoNotExistsTest() {
        RestAssured.given()
                .pathParams("id", response.getId())
                .expect()
                .statusCode(404)
                .when()
                .get("/api/v1/products/{id}");
    }

}
