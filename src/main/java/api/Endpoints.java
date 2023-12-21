package api;

import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;


public class Endpoints {

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static RequestSpecification requestSpecification(){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Endpoints.BASE_URL);
    }
    // ингредиенты
    public static final String GET_INGREDIENTS = "/api/ingredients";
    // заказы конкретного пользователя
    public static final String USER_ORDER = "/api/orders";

    // создание заказа
    public static final String POST_ORDER = "/api/orders";
    public static final String GET_ORDER = "/api/orders";

    // сброс пароля
    public static final String RECOVERY_PASSWORD = "/api/password-reset";
    public static final String RESET_PASSWORD = "/api/password-reset/reser";

    // регистрация, создание пользователя
    public static final String REGISTER_USER = "/api/auth/register";

    // авторизация пользователя
    public static final String LOGIN_USER = "/api/auth/login";

    // выход из системы
    public static final String LOGOUT_USER = "/api/auth/logout";

    // обновление токена
    public static final String UPDATE_TOKEN = "/api/auth/token";

    // данные о пользователе
    public static final String DATA_USER = "/api/auth/user";

    // удаление пользователя
    public static final String DELETE_USER = "api/auth/user";
}
