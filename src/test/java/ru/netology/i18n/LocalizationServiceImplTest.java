package ru.netology.i18n;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;

public class LocalizationServiceImplTest {
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

    //Unit tests without Mockito

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