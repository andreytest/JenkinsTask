package com.epam.Andrii_Yavtushenko.Page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LoginForm {

    private SelenideElement loginField() {
        return $("#login-form-login");
    }

    private SelenideElement passwordField() {
        return $("#login-form-password");
    }

    private SelenideElement loginButton() {
        return $("#loginButton");
    }

    private SelenideElement loginByFacebook() {
        return $x("//*[@href='/my/o/facebook/']");
    }

    private SelenideElement loginByGoogle() {
        return $x("//*[@href='/my/o/google/']");
    }

    private ElementsCollection loginBySocial() {
        return $$x("//*[contains(@class,'form-btn form-btn')]");
    }
    public SelenideElement loginErrorMassage() {

        return $x("//*[@id='login-form']/div[2]/div");
    }
    public void loginWithSocial(){
        loginByGoogle().click();
    }
    public void logining(String loginEMail, String logingPassword) {
        loginField().setValue(loginEMail);
        passwordField().setValue(logingPassword);
        loginButton().click();
    }
}
