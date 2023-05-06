package tests;

import io.qameta.allure.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import pages.HomePage;
import pages.HotelPage;
import pages.SearchPage;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

@Epic("Web tests")
@Feature("First Test")
@Story("Проверка корректности отображения данных")
@Link(name = "ссылка на сайт", url = "http://www.booking.com/")
@Owner("Григорий Герасимов")
public class FirstSelenideTest extends BaseTest {
    HomePage homePage = new HomePage();
    SearchPage searchPage = new SearchPage();
    HotelPage hotelPage = new HotelPage();
    private String city = "Никарагуа";

    static LocalDate startDate = LocalDate.now().plusDays(7);
    static LocalDate endDate = LocalDate.now().plusDays(21);

    private final static By DATA_START = By.xpath("//span[@ data-date='" + startDate + "']");

    private final static By DATA_END = By.xpath("//span[@ data-date='" + endDate + "']");

    //Первый тест-кейс:
//     1.1 зайти на сайт https://www.booking.com/
//     1.2 ввести в поиске любой город(заграничный)
//     1.3 выбрать случайные даты
//     1.4 нажать на кнопку «Найти»
//     1.5 нажать на кнопку «Показать на карте»
//     1.6 навести курсор на первый отель(карточка слева)
//     1.7 сохранить(в переменные) название отеля, количество звезд, среднюю оценку, количество отзывов, стоимость
//     1.8 нажать на прыгающий маркер на карте
//     1.9 на открывшейся странице отеля проверить название отеля, количество звезд, среднюю оценку, количество отзывов и стоимость
    @Test
    @DisplayName("Проверка корректности отображения данных отеля на карте")
    @Description("Находим отель, открываем его страницу через карту и проверяем совпадение данных на новой странице отеля")
    public void bookingFindHotel() {
        homePage.openHomePage()
                .acceptCookie()
                .searchCity(city)
                .selectData();
        $(DATA_START).click();
        $(DATA_END).click();
        homePage.searchHotels();
        searchPage.setMap()
                .hoverHotel();

        String nameHotel = searchPage.checkHotelName();
        String rateHotel = searchPage.checkHotelRate();
        int starsHotel = searchPage.checkHotelStar();
        String reviewsHotel = searchPage.checkHotelReview();
        String priceHotel = searchPage.checkHotelPrice();

        searchPage.clickMap();
        switchTo().window(1);

//      Проверки на новой странице
        hotelPage.openPageHotel()
                .checkHotelNamePage(nameHotel)
                .checkHotelRatePage(rateHotel)
                .checkHotelStarsPage(starsHotel)
                .checkHotelReviewPage(reviewsHotel)
                .checkHotelPricePage(priceHotel);

    }
}

