package order;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrderStep extends Endpoints {

    private static final RequestSpecification requestSpecWithLogs = new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .build()
            .spec(Endpoints.requestSpecification());

    @Step("Создание заказа с авторизацией")
    public static ValidatableResponse createOrderAuthorised(CreateOrder createOrder, String accessToken) {
        return given()
                .spec(requestSpecWithLogs)
                .header("authorization", accessToken)
                .body(createOrder)
                .post(Endpoints.GET_ORDER)
                .then().log().all();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorisation(CreateOrder createOrder) {
        return given()
                .spec(requestSpecWithLogs)
                .body(createOrder)
                .post(Endpoints.POST_ORDER)
                .then().log().all();
    }

    @Step("Получение заказа авторизованного пользователя")
    public static ValidatableResponse getOrderWithAuth(String accessToken) {
        return given()
                .spec(requestSpecWithLogs)
                .header("authorization", accessToken)
                .when()
                .get(POST_ORDER)
                .then()
                .log().all();
    }

    @Step("Получение заказа неавторизованного пользователя")
    public ValidatableResponse createOrderWithoutAuth() {
        return given()
                .spec(requestSpecWithLogs)
                .get(POST_ORDER)
                .then()
                .log().all();
    }

    @Step("Получение списка всех ингредиентов")
    public ValidatableResponse getAllIngredients() {
        return given()
                .spec(requestSpecWithLogs)
                .post(Endpoints.GET_INGREDIENTS)
                .then().log().all();
    }

}