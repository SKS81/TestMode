import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utils.DataGenerator.Registration.getRegisteredUser;
import static utils.DataGenerator.Registration.getUser;

public class VerificationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void loginRegUserOK() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $(".button__text").click();
        $(".heading").shouldHave(text("  Личный кабинет"),
                        Duration.ofSeconds(5))
                .shouldBe(visible);
    }
    @Test
    void notRegUserError() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());
        $(".button__text").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
    }

    @Test
    void blockUserError() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $(".button__text").click();
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован"),
                        Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
    }

    @Test
    void loginError() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $(".button__text").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
    }

    @Test
    void passwordError() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);
        $(".button__text").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
    }
}