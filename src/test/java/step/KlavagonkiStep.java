package step;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;
import static java.lang.Thread.sleep;

public class KlavagonkiStep {
    private final SelenideElement closeWindowButton = $x("//input[@value='Закрыть']");
    private final SelenideElement startGameButton = $x("//a[@id='host_start']");
    private final SelenideElement highlightWord = $x("//span[@id='typefocus']");
    private final SelenideElement inputField = $x("//input[@id='inputtext']");
    private final SelenideElement afterFocusWord = $x("//span[@id='afterfocus']");
    private final SelenideElement resultText = $x("//td[text()='Это вы']//ancestor-or-self::div//div[@class='stats']//div[2]/span/span");


    @When("Начинаем игру")
    public void startGame() {
        closeWindowButton.click();
        if(startGameButton.isDisplayed()){
            startGameButton.click();
        }
    }


    @And("Ждем начало игры")
    public void waitForStartGame() {
        highlightWord.click();
    }
    private String getCurrentWord(){
        return highlightWord.getText().replaceAll("c", "с").replaceAll("o", "о");
    }
    @And("Вводим подсвеченое слово в цикле")
    public void playGame() throws InterruptedException {
        while (true){
            String currentWord = getCurrentWord();
            String afterFocusSymbol = afterFocusWord.getText();
            inputField.sendKeys(currentWord);
            if(afterFocusSymbol.equals(".")){
                inputField.sendKeys(".");
                break;
            }
            inputField.sendKeys(Keys.SPACE);
            sleep(500);
        }
    }

    @Then("Фиксируем что игра завершена и символов в минуту больше чем {int}")
    public void endGame(int minValue) {
        String result = resultText.getText();
        int resultNumber = Integer.parseInt(result);
        System.out.println("Количество знаков в минуту: " + resultNumber);
        Assertions.assertTrue(resultNumber > minValue, "Актуальный результат был: " + resultNumber);
    }
}
// В @Then вместо {int} можно засунуть регулярное выражение (\\d+)