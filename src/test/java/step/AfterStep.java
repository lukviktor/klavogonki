package step;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

public class AfterStep {
    public void tearDown(){
        WebDriverRunner.getWebDriver().quit();
    }
    @io.cucumber.java.AfterStep
    public void makeSkreenshot(){
        Selenide.screenshot(System.currentTimeMillis() + "step");
    }
}
