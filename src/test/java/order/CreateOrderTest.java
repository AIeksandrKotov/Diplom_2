package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import user.CreateUser;
import user.UserRandom;
import user.UserStep;

import static order.Ingredients.ingredients;
import static order.Ingredients.wrongIngredients;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

public class CreateOrderTest {
    private CreateUser createUser;
    private CreateOrder createOrder;
    private UserStep userStep;
    private OrderStep orderStep;
    private String accessToken;

    @Before
    public void setUp() {
        userStep = new UserStep();
        createUser = new UserRandom().getUserRandom();
        orderStep = new OrderStep();
        ValidatableResponse createResponse = userStep.registerUser(createUser);
        accessToken = createResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с ингредиентами")
    public void createOrderWitAuthorization() {
        createOrder = new CreateOrder(ingredients);
        ValidatableResponse response = orderStep.createOrderAuthorised(createOrder, accessToken);
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с ингредиентами")
    public void createOrderWithoutAuthorization() {
        createOrder = new CreateOrder(ingredients);
        ValidatableResponse response = orderStep.createOrderWithoutAuthorisation(createOrder);
        response
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и без ингредиентов")
    public void createOrderWithIngredients() {
        createOrder = new CreateOrder(null);
        ValidatableResponse response = orderStep.createOrderAuthorised(createOrder, accessToken);
        response
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .and()
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с добавлением ингредиентов НЕ правильного ингредиента")
    public void createOrderWithoutIngredients() {
        createOrder = new CreateOrder(wrongIngredients);
        ValidatableResponse orderWithoutIngredients = orderStep.createOrderAuthorised(createOrder, accessToken);
        orderWithoutIngredients
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .and()
                .body("message", is("One or more ids provided are incorrect"));
    }
}



