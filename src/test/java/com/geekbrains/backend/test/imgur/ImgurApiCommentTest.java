package com.geekbrains.backend.test.imgur;

import org.apache.commons.lang3.time.CalendarUtils;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ImgurApiCommentTest extends ImgurApiAbstractTest{

    private static String CURRENT_IMAGE_ID;
    private static String CURRENT_COMMENT_ID;
    String userName = "e1enamail";

    @Test
    @Order(1)
    @DisplayName("Загрузка изображения, получение ID")
    void postImageTest() {
        CURRENT_IMAGE_ID = given()
                .spec(requestSpecification)
                .multiPart("image", getFileResource("cat2.jpeg"))
                .formParam("name", "One more siamese cat")
                .formParam("title", "My favorite cat!")
                .log()
                .all()
                .expect()
                .spec (responseSpecification)
                .body("data.type", is("image/jpeg"))
                .body("data.name", is("One more siamese cat"))
                .body("data.title", is("My favorite cat!"))
                .log()
                .all()
                .when()
                .post("upload")
                .body()
                .jsonPath()
                .getString("data.id");
        System.out.println(CURRENT_IMAGE_ID);
    }

    @Test
    @Order(2)
    @DisplayName("Делаем изображение публичным")
    void ShareWithCommunityTest() {
        given()
                .spec(requestSpecification)
                .formParam("title", "Funny")
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .log()
                .all()
                .when()
                .post("gallery/image/" + CURRENT_IMAGE_ID);

    }

    @Test
    @Order(3)
    @DisplayName("Добавление комментария к изображению, получение ID комментария")
    void CommentCreationTest() {

        CURRENT_COMMENT_ID = given()
                .spec(requestSpecification)
                .formParam("image_id", CURRENT_IMAGE_ID)
                .formParam("comment", "I love siamese cats!")
                .log()
                .all()
                .expect()
                .spec (responseSpecification)
                .log()
                .all()
                .when()
                .post("comment")
                .body()
                .jsonPath()
                .getString("data.id");
        System.out.println(CURRENT_COMMENT_ID);
    }

    @Test
    @Order(4)
    @DisplayName("Получение комментария к изображению")
    void getCommentTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .body("data.comment", is("I love siamese cats!"))
                .body("data.author", is("e1enamail"))
                .log()
                .all()
                .when()
                .get("comment/" + CURRENT_COMMENT_ID);
    }

    @Test
    @Order(5)
    @DisplayName("Добавление ответа на комментарий")
    void postReplayCreationTest() {
        given()
                .spec(requestSpecification)
                .formParam("image_id", CURRENT_IMAGE_ID)
                .formParam("comment", "The siamese cats are the best!")
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .log()
                .all()
                .when()
                .get("comment/" + CURRENT_COMMENT_ID);
    }

    @Test
    @Order(6)
    @DisplayName("Добавление голоса за комментарий")
    void postVoteTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .log()
                .all()
                .when()
                .post("comment/" + CURRENT_COMMENT_ID + "/vote/up");

    }

    @Test
    @Order(7)
    @DisplayName("Удаляем комментарий")
    void deleteCommentTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .log()
                .all()
                .when()
                .delete("comment/" + CURRENT_COMMENT_ID);
    }

    @Test
    @Order(8)
    @DisplayName("Удаляем изображение")
    void deleteImageById() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .log()
                .all()
                .when()
                .delete("image/" + CURRENT_IMAGE_ID);
    }






}
