package stepDefinition.hook;

import in.co.driverFactory.DriverFactory;
import in.co.pages.BasePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;

public class LoginPage {
    protected WebDriver driver;
    public LoginPage(){
        this.driver= DriverFactory.getDriver();
    }

    @When("user Enter Login Page")
    public void user_enter_login_page() throws InterruptedException {
         BasePage basePage = new BasePage();
         basePage.searchENter();
    }

    @When("User Enter Valid {string} and {string}")
    public void user_enter_valid_username_and_password(String username, String password) throws InterruptedException {

    }


}
