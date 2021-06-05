package ru.netology.geo;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.USA;

public class GeoServiceImplTest {

    @Test
    public void testByIpNewYork() {
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(USA)).thenReturn("Welcome");
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
        Location actual = geoService.byIp("96.44.183.149");
        String expected = "New York";
        Assertions.assertEquals(expected, actual.getCity());
    }

    @Test
    public void testByIpLocalHost() {
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(USA)).thenReturn("Welcome");
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("127.0.0.1")).thenReturn(new Location(null, null, null, 0));
        Location actual = geoService.byIp("127.0.0.1");
        assertNull(actual.getCity());
    }

    @Test
    public void testCheckByIpCountry() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp("96.44.183.149");
        Country actual = location.getCountry();
        Assertions.assertEquals(USA, actual);
    }

    @Test
    public void testCheckByIpMoscow() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location location = geoService.byIp("172.0.32.11");
        String actual = location.getCity();
        Assertions.assertEquals("Moscow", actual);
    }

}