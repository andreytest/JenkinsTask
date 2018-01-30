package com.epam.Andrii_Yavtushenko.Page;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    public SelenideElement userInfo() {
        return $x("//*[@class='user-info']");
    }

    private SelenideElement loginButton() {
        return $x("//*[@id='userSettings']/li[4]/a");
    }

    private SelenideElement logoutButton() {
        return $x("//*[@id='userSettings']//*[@href='/my/logout/']");
    }

    public SelenideElement changeCityButton() {
        return $x("//*[@class='logo-city']");
    }

    private ElementsCollection citiesForChoose() {
        return $$x("//*[contains(@href,'vseceni.ua')]");

    }

    public LoginForm openLoginForm() {
        userInfo().click();
        loginButton().click();
        return page(LoginForm.class);
    }

    public void logout() {
        userInfo().click();
        logoutButton().click();
    }

    public MainPage changeCity() {
        changeCityButton().click();
        citiesForChoose().get(1).shouldHave().click();
        return page(MainPage.class);
    }

    private ElementsCollection categoryItem() {
        return $$x("//*[@class='page-cloud-row clearfix']/div");
    }

    private SelenideElement getCategoryBlock(String categoryName) {
        SelenideElement selenideElement = null;
        for (SelenideElement category : categoryItem()
                ) {
            if (category.$x("./a").getText().contains(categoryName)) {
                selenideElement = category;
            }
        }
        return selenideElement;
    }

    private ElementsCollection listOfSubCategory(String nameOfCategory) {
        return getCategoryBlock(nameOfCategory).$$x("./ul[@class='prb-menu']/li");
    }

    private SelenideElement getSubCategoryByName(String nameOfCategory, String nameOfSubCategory) {
        SelenideElement selenideElement = null;
        for (SelenideElement subCategory : listOfSubCategory(nameOfCategory)
                ) {
            if (subCategory.getText().equals(nameOfSubCategory)) {
                selenideElement = subCategory;
            }
        }
        return selenideElement;
    }

    public ProductsPage goToSubCategory(String nameOfCategory, String nameOfSubCategory) {
        getSubCategoryByName(nameOfCategory, nameOfSubCategory).click();
        return page(ProductsPage.class);
    }


}
