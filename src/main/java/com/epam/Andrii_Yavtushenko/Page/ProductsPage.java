package com.epam.Andrii_Yavtushenko.Page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ProductsPage {
    private SelenideElement inputMinPrice() {
        return $("#price-min");
    }

    private SelenideElement inputMaxPrice() {
        return $("#price-max");
    }

    private SelenideElement buttonFilterByPrice() {
        return $("#pricebutton");
    }

    private SelenideElement sortButton() {
        return $x("//*[@class='toggler']");
    }

    private ElementsCollection criterionsOfSort() {
        return $$x("//*[@class='dropdown dropdown-sorting active']/ul/li");
    }

    public ElementsCollection getNamesOfProductsOnPage() {
        return $$x("//*[@class='catalog-block-head']");
    }

    public ProductsPage filterByPrice(String priceMin, String priceMax) {
        inputMinPrice().setValue(priceMin);
        inputMaxPrice().setValue(priceMax);
        buttonFilterByPrice().click();
        return page(ProductsPage.class);
    }

    public ProductsPage sortBy(String sortBy) {
        sortButton().click();
        criterionsOfSort()
                .stream()
                .filter(s ->
                        s.getText().equals(sortBy)
                )
                .findFirst()
                .get().click();
        return page(ProductsPage.class);
    }

    public ProductsPage searchInCategory(String searchText) {
        $(By.className("search-text-input")).setValue(searchText).pressEnter();
        return page(ProductsPage.class);
    }

    private SelenideElement buttonCloseSort() {
        return $x("//*[@class='close']");
    }

    private ElementsCollection productsInCatalog() {
        return $$x("//*[@class='item td-table']//div[@class='catalog-block-head']/a");
    }

    public List<Integer> priceProductsOnPage() {
        List<Integer> prices = new ArrayList<>();
        $$x("//*[contains(@class,'price')]/span/strong[1]")
                .forEach(s -> {
                            Integer priceProduct = Integer.parseInt(s.getText().replaceAll("[^\\d]", ""));
                            prices.add(priceProduct);
                        }
                );
        return prices;
    }

    public ProductPage openFirsProduct(){
        productsInCatalog().first().click();
        return page(ProductPage.class);
    }

    public ProductsPage cancelFiltrByPrice() {
        buttonCloseSort().click();
        return page(ProductsPage.class);
    }

}
