package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.is;
import static org.apache.http.HttpStatus.SC_OK;

public class LoginUserTest {
    CreateUser createUser;
    private UserStep userStep;
    private LoginUser loginUser;
    private String accessToken;

    @Before
    public void setUp() {
        userStep = new UserStep();
        createUser = UserRandom.getUserRandom();
        userStep.registerUser(createUser);

    }

    @Test
    @DisplayName("Логинимся под существующим пользователем,")
    public void loginUserResultOk() {
        loginUser = new LoginUser(createUser.getEmail(), createUser.getPassword());
        ValidatableResponse responseLogin = userStep.loginUser(loginUser);
        responseLogin
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Логинимся с неверным email")
    @Description("Проверяем ввод не верного email")
    public void loginUserWrongEmail() {
        loginUser = new LoginUser( createUser.getEmail(),"test@ya.ru");
        ValidatableResponse responseLogin = userStep.loginUser(loginUser);
        responseLogin
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", is("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логинимся с неверным паролем")
    @Description("Проверяем ввод не верного пароля")
    public void loginUserWrongPassword() {
        loginUser = new LoginUser( createUser.getPassword(),"1");
        ValidatableResponse responseLogin = userStep.loginUser(loginUser);
        responseLogin
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", is("email or password are incorrect"));
    }

    @After
    @DisplayName("Удаляем юзера")
    public void deleteUser() {
        if (accessToken != null) {
            UserStep.userDelete(accessToken);
        }
    }
}