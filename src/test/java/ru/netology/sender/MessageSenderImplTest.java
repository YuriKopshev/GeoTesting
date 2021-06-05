package ru.netology.sender;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;
import static ru.netology.sender.MessageSenderImpl.*;

public class MessageSenderImplTest {
    Map<String, String> headers = new HashMap<String, String>();

    @BeforeEach
    public void clearMap() {
        headers.clear();
    }

    @Test
    public void testSendRusText() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(RUSSIA)).thenReturn("Добро пожаловать");
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172.123.12.19")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String expected = "Добро пожаловать";
        String actual = messageSender.send(headers);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSendEngText() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.155");
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(USA)).thenReturn("Welcome");
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.44.183.155")).thenReturn(new Location("New York", Country.USA, null, 0));
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String expected = "Welcome";
        String actual = messageSender.send(headers);
        Assertions.assertEquals(expected, actual);

    }

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
    public void testLocalesRus() {
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(RUSSIA)).thenReturn("Добро пожаловать");
        String expected = localizationService.locale(RUSSIA);
        String actual = "Добро пожаловать";
        Assertions.assertEquals(expected, actual);
    }

    // Mockito Spy tests
    @Test
    public void testLocaleRusSpy() {
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Assertions.assertEquals(localizationService.locale(RUSSIA), "Добро пожаловать");
    }

    @Test
    public void testLocaleUsaSpy() {
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Assertions.assertEquals(localizationService.locale(USA), "Welcome");
    }

    @Test
    public void testByIpNewYorkSpy() {
        GeoServiceImpl geoService = Mockito.spy(GeoServiceImpl.class);
        Location actual = geoService.byIp("96.44.183.149");
        Assertions.assertEquals("New York", actual.getCity());
    }


    //Unit tests without Mockito
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

    @Test
    public void testCheckLocaleRus() {
        LocalizationServiceImpl localized = new LocalizationServiceImpl();
        String expected = "Добро пожаловать";
        String actual = localized.locale(RUSSIA);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCheckLocaleREnglish() {
        LocalizationServiceImpl localized = new LocalizationServiceImpl();
        String expected = "Welcome";
        String actual = localized.locale(USA);
        Assertions.assertEquals(expected, actual);
    }
}
