package user;

import com.github.javafaker.Faker;

public class UserRandom {
    public static CreateUser getUserRandom() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().fullName();
        String password = faker.internet().password();
        return new CreateUser(email, name, password);
    }
}