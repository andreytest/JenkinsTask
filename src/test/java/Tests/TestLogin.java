package Tests;

import com.codeborne.selenide.Configuration;
import com.epam.Andrii_Yavtushenko.Page.LoginForm;
import com.epam.Andrii_Yavtushenko.Page.MainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class TestLogin {

    @BeforeTest
    public void init() {
        Configuration.browser = "chrome";
    }

    @BeforeMethod
    public void openMainPage() {
        open("https://pn.com.ua/");
    }

    @Test
    public void invalidLogin() {
        MainPage mainPage = new MainPage();
        LoginForm loginForm = mainPage.openLoginForm();
        loginForm.logining("qwerty@qwerty.com", "12345678");
        loginForm.loginErrorMassage().shouldBe(visible);
    }

    @Test
    public void loginWithGoogle() {
        MainPage mainPage = new MainPage();
        LoginForm loginForm = mainPage.openLoginForm();
        loginForm.loginWithGoogle();
        switchTo().window(1);
        Assert.assertTrue(url().contains("google"));
        close();
    }

    @Test
    public void loginWithFacebook() {
        MainPage mainPage = new MainPage();
        LoginForm loginForm = mainPage.openLoginForm();
        loginForm.loginWithFacebook();
        switchTo().window(1);
        Assert.assertTrue(url().contains("facebook"));
        close();
    }

    @Test
    public void changeCity() {
        MainPage mainPage = new MainPage();
        mainPage.changeCity()
                .changeCityButton()
                .shouldHave(text("Днепр"));
    }

}
