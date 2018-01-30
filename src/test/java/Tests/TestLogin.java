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
    public void searchInCategory() {
        MainPage mainPage = new MainPage();
        ElementsCollection namesOfProductsOnPage = mainPage
                .goToSubCategory("Электроника", "Мобильные телефоны")
                .searchInCategory("iphone")
                .getNamesOfProductsOnPage();
        for (SelenideElement str : namesOfProductsOnPage) {
            str.shouldHave(text("iphone"));
        }
    }


    @Test
    public void filterByPrice() {
        String priceMin = "1000";
        String priceMax = "3000";
        Set<Boolean> havePriceOutCostRange = new HashSet<>();

        MainPage mainPage = new MainPage();
        mainPage
                .goToSubCategory("Электроника", "Мобильные телефоны")
                .filterByPrice(priceMin, priceMax)
                .priceProductsOnPage()
                .iterator()
                .forEachRemaining(
                        priceProduct -> {
                            if (priceProduct < Integer.parseInt(priceMin) || priceProduct > Integer.parseInt(priceMax))
                                havePriceOutCostRange.add(true);
                        });
        Assert.assertFalse(havePriceOutCostRange.contains(true));
        ProductPage productPage = new ProductPage();
        productPage.cancelFiltrByPrice();
    }

    @Test
    public void sortByPriceAscending() {
        MainPage mainPage = new MainPage();
        List<Integer> actual = mainPage
                .goToSubCategory("Электроника", "Мобильные телефоны")
                .sortBy("цена от дешевых")
                .priceProductsOnPage();
        assertThat(actual).isSorted();
    }

    @Test
    public void sortByPriceDescending() {
        MainPage mainPage = new MainPage();
        List<Integer> actual = mainPage
                .goToSubCategory("Электроника", "Мобильные телефоны")
                .sortBy("цена от дорогих")
                .priceProductsOnPage();
        List<Integer> expected = new ArrayList<>(actual);
        Collections.sort(expected, Collections.reverseOrder());
        Assert.assertTrue(expected.equals(actual));
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

    @Test
    public void changeCity() {
        MainPage mainPage = new MainPage();
        mainPage.changeCity();
        mainPage.changeCityButton().shouldHave(text("Днепр"));
    }

}
