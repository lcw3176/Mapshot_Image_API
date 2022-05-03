package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
@RequiredArgsConstructor
public class DriverService{

    private final ChromeDriverExtends chromeDriverExtends;
    private final WebDriverWait webDriverWait;


    public byte[] capturePage(UriComponents uri){
        chromeDriverExtends.get(uri.toString());
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));

        return chromeDriverExtends.getFullScreenshot();
    }

}