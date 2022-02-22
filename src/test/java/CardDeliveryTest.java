
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import utils.Date;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private final SelenideElement city = $("[data-test-id=city] input");
    private final SelenideElement cityInvalid = $("[data-test-id=city] .input__sub");
    private final SelenideElement date = $("[data-test-id=date] input");
    private final SelenideElement dateInvalid = $("[data-test-id=date] .input__sub");
    private final SelenideElement name = $("[data-test-id=name] input");
    private final SelenideElement nameInvalid = $("[data-test-id=name] .input__sub");
    private final SelenideElement phone = $("[data-test-id=phone] input");
    private final SelenideElement phoneInvalid = $("[data-test-id=phone] .input__sub");
    private final SelenideElement agreementCheckbox = $("[data-test-id=agreement]");
    private final SelenideElement book = $(withText("Забронировать"));
    private final SelenideElement notification = $("[data-test-id=notification]");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        clearBrowserCookies();
    }

    @Test
    void shouldTestOrderSuccess() {
        city.setValue("Саратов");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(5));
        name.setValue("Алена Иванова");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        notification.shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + Date.date(5)));
    }

    @Test
    void shouldTestInvalidCity() {
        city.setValue("TestCity");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(5));
        name.setValue("Алена-Иванова");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        cityInvalid.shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestInvalidDate() {
        city.setValue("Москва");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(1));
        name.setValue("Алена Иванова");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        dateInvalid.shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldTestInvalidDatePast() {
        city.setValue("Москва");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(-3));
        name.setValue("Алена Иванова");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        dateInvalid.shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestInvalidName() {
        city.setValue("Москва");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(3));
        name.setValue("Алена Иванова01");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        nameInvalid.shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestInvalidPhone() {
        city.setValue("Москва");
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(3));
        name.setValue("Алена Иванова");
        phone.setValue("89999999999");
        agreementCheckbox.click();
        book.click();
        phoneInvalid.shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestCityPopup() {
        city.setValue("Ек");
        $$(".menu-item__control").get(3).click();
        city.shouldHave(attribute("value", "Екатеринбург"));
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(Date.date(3));
        name.setValue("Алена Иванова");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        notification.shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + Date.date(3)));
    }

    @Test
    void shouldTestCalendarIcon() {
        city.setValue("Москва");
        $(".input__icon").click();
        $("[role=grid]").sendKeys(Keys.ARROW_DOWN);
        $("[role=grid]").sendKeys(Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT);
        $("[role=grid]").sendKeys(Keys.ENTER);
        name.setValue("Алена Иванова");
        phone.setValue("+79999999999");
        agreementCheckbox.click();
        book.click();
        notification.shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + date.getValue()));
    }
}


