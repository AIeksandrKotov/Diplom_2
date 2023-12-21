package order;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;
/*Создание заказа:
с авторизацией,
без авторизации,
с ингредиентами,
без ингредиентов,
с неверным хешем ингредиентов.
Получение заказов конкретного пользователя:
авторизованный пользователь,
неавторизованный пользователь.*/
public class OrderStep extends Endpoints {


    @Step("Создание заказа с авторизацией")
    public static ValidatableResponse createOrderAuthorised(CreateOrder createOrder, String accessToken) {
        return given()
                .spec(Endpoints.requestSpecification())
                .header("authorization", accessToken)
                .body(createOrder).log().all()
                .post(Endpoints.GET_ORDER)
                .then().log().all();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorisation(CreateOrder createOrder) {
        return given()
                .spec(Endpoints.requestSpecification())
                .body(createOrder).log().all()
                .post(Endpoints.POST_ORDER)
                .then().log().all();
    }

    @Step("Получение заказа авторизованного пользователя")
    public static ValidatableResponse getOrderWithAuth(String accessToken) {
        return given()
                .spec(Endpoints.requestSpecification())
                .header("authorization", accessToken)
                .when()
                .get(POST_ORDER)
                .then()
                .log().all();
    }

    @Step("Получение заказа неавторизованного пользователя")
    public ValidatableResponse createOrderWithoutAuth() {
        return given()
                .spec(Endpoints.requestSpecification())
                .get(POST_ORDER)
                .then()
                .log().all();
    }
    @Step("Получение списка всех ингредиентов")
    public ValidatableResponse getAllIngredients() {
        return given()
                .spec(Endpoints.requestSpecification())
                .post(Endpoints.GET_INGREDIENTS)
                .then().log().all();
    }

}