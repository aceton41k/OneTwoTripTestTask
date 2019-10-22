import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Plan {

    @Test
    public void accessibility() {
        Main.accesibility();
    }

    @Test
    public void searchForCities() {
        Main.search("мос", "geo", "Москва,Мостар,Моссел-Бей");
    }

    @Test
    public void searchForHotels() {
        Main.search("мос", "hotel", "Москва Гост,МОСКВА 1,Красивые апартаменты в новом доме рядом с мостами и дворцами");
    }

    @Test
    public void searchForAirports() {
        Main.search("мос", "airport", "Шереметьево,Домодедово,Внуково");
    }

    @Test
    public void redirectToWWW() {
        Main.redirectTest("https://onetwotrip.com", "https://www.onetwotrip.com/");
    }

    @Test
    public void redirectToRu() {
        Main.redirectTest("https://www.onetwotrip.com", "https://www.onetwotrip.com/ru/");
    }
}
