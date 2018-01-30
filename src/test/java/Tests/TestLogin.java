package Tests;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.epam.Andrii_Yavtushenko.Page.LoginForm;
import com.epam.Andrii_Yavtushenko.Page.MainPage;
import com.epam.Andrii_Yavtushenko.Page.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;


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
    public void validLogin() {
        MainPage mainPage = new MainPage();
        LoginForm loginForm = mainPage.openLoginForm();
        loginForm.logining("andreytest1@i.ua", "difficultpassword");
        mainPage.userInfo().shouldHave(text("andreytest1"));
        mainPage.logout();
    }

    @Test
    public void loginWithGoogle() {
        MainPage mainPage = new MainPage();
        LoginForm loginForm = mainPage.openLoginForm();
        loginForm.loginWithSocial();
        switchTo().window(1);
        Assert.assertTrue(url().contains("google"));
        close();
    }


}
