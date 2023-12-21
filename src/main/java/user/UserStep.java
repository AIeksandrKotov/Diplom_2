package user;

import api.Endpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserStep extends Endpoints {
    @Step("Регистрация нового пользователя")
    public static ValidatableResponse registerUser(CreateUser createUser) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(createUser).log().all()
                .when()
                .post(Endpoints.REGISTER_USER)
                .then().log().all();
    }

    @Step("Авторизация пользователя")
    public static ValidatableResponse loginUser(LoginUser loginUser) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(loginUser)
                .when()
                .post(Endpoints.LOGIN_USER)
                .then().log().all();
    }

    @Step("Изменение данных пользователя с авторизацией")
    public static ValidatableResponse changeUserAuthorised(CreateUser createUser, String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("authorization", accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(createUser)
                .when()
                .patch(Endpoints.DATA_USER)
                .then();
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse changeUserWithoutAuthorised(CreateUser createUser) {
        return given()
                .spec(requestSpecification())
                .body(createUser)
                .when()
                .patch(DATA_USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public static ValidatableResponse userDelete(String accessToken) {
        return given()
                .spec(requestSpecification())
                .auth().oauth2(accessToken)
                .when()
                .delete(Endpoints.DELETE_USER)
                .then();
    }
}
