package com.geekbrains.backend.test.imgur;

import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MyImgurApiFunctionalTest extends ImgurApiAbstractTest{

    private static String CURRENT_ALBUM_ID;
    private static String CURRENT_IMAGE_ID;
    String userName = "e1enamail";


    @Test
    @Order(1)
    @DisplayName("Количество альбомов до начала тестов")
    void getAlbumCountBefore() {

        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("data", is(9))
                .log()
                .all()
                .when()
                .get("account/" + userName + "/albums/count");


    }

    @Test
    @Order(2)
    @DisplayName("Создание нового альбома, получение ID")
    void createAlbumTest() {
        CURRENT_ALBUM_ID = given()
                .spec(requestSpecification)
                .formParam("title", "My cats!")
                .formParam("description", "The most beautiful cats!")
                .log()
                .all()
                .expect()
                .log()
                .all()
                .when()
                .post("album")
                .body()
                .jsonPath()
                .getString("data.id");
        System.out.println(CURRENT_ALBUM_ID);
    }

    @Test
    @Order(3)
    @DisplayName("Загрузка изображения, получение ID")
    void postImageTest() {
        CURRENT_IMAGE_ID = given()
                .spec(requestSpecification)
                .multiPart("image", getFileResource("cat.jpeg"))
                .formParam("name", "Siamese cat")
                .formParam("title", "My favorite cat!")
                .log()
                .all()
                .expect()
                .body("data.type", is("image/jpeg"))
                .body("data.name", is("Siamese cat"))
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
    @Order(4)
    @DisplayName("Добавление изображения в альбом")
    void addImagesToAlbumTest() {
        given()
                .spec(requestSpecification)
                .formParam("ids[]", CURRENT_IMAGE_ID)
                .log()
                .all()
                .expect()
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post("album/" + CURRENT_ALBUM_ID + "/add");

    }

    @Test
    @Order(5)
    @DisplayName("Удаление альбома")
    void deleteAlbumTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete("account/" + userName + "/album/" + CURRENT_ALBUM_ID);
    }

    @Test
    @Order(6)
    @DisplayName("Количество альбомов после тестов")
    void getAlbumCountAfter() {

        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("data", is(9))
                .log()
                .all()
                .when()
                .get("account/" + userName + "/albums/count");


    }

    @Test
    @Order(7)
    @DisplayName("Удаление картинки из коллекции")
    void deleteImageById() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete("image/" + CURRENT_IMAGE_ID);
    }

}



















