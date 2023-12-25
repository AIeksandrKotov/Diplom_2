package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.is;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserTest {
    private CreateUser createUser;
    private UserStep userStep;
    private String accessToken;


    @Before
    public void setUp() {
        userStep = new UserStep();
        createUser = new UserRandom().getUserRandom();
    }

    @Test
    @DisplayName("Создаем уникального пользователя")
    public void CreateUserResultOk() {
        ValidatableResponse response = userStep.registerUser(createUser);
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создаем пользователя, который уже зарегистрирован")
    @Description("Проверяем создание пользователя который уже зарегистрирован")
    public void CreateUserWrongOk() {
        ValidatableResponse responseRegistration = userStep.registerUser(createUser);
        accessToken = responseRegistration.extract().path("accessToken");
        responseRegistration
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
        ValidatableResponse responseRegisteredUser = userStep.registerUser(createUser);
        responseRegisteredUser
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .and()
                .body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Создаем пользователя и не заполняем одно из обязательных полей")
    @Description("Проверяем без заполнения поля пароля")
    public void CreateUserWithoutPassword() {
        createUser.setPassword("");
        ValidatableResponse responseRegistration = userStep.registerUser(createUser);
        responseRegistration
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));
    }

    @After
    @DisplayName("Удаляем юзера")
    public void deleteUser() {
        if (accessToken != null) {
            UserStep.userDelete(accessToken);
        }
    }
}

