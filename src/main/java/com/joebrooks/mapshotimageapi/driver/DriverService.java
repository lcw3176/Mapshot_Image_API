package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
@RequiredArgsConstructor
public class DriverService{

    private final ChromeDriverExtends chromeDriverExtends;
    private final WebDriverWait webDriverWait;

    public void loadPage(UriComponents uri){
        try {
            chromeDriverExtends.get(uri.toString());
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));
        } catch (Exception e){
            throw new LoadMapException("지도 로딩 에러", e);
        }

    }

    public void scrollPage(int x, int y){
        StringBuilder sb = new StringBuilder();
        sb.append("scroll(");
        sb.append(x);
        sb.append(",");
        sb.append(y);
        sb.append(")");

        chromeDriverExtends.executeScript(sb.toString());
    }

    public ByteArrayResource capturePage(){
        try {
            return new ByteArrayResource(chromeDriverExtends.getScreenshot());
        } catch (Exception e){
            throw new ScreenshotException("스크린샷 에러", e);
        }
    }

}