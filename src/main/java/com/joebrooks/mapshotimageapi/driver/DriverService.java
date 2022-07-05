package com.joebrooks.mapshotimageapi.driver;

import com.joebrooks.mapshotimageapi.driver.exception.LoadPageException;
import com.joebrooks.mapshotimageapi.driver.exception.ScreenshotException;
import com.joebrooks.mapshotimageapi.driver.exception.ScrollException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

/*
크롬 웹 드라이버의 동작을 관리합니다.
페이지를 로드, 스크롤, 캡쳐하는 기능을 포함하고 있습니다.
*/
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
            throw new LoadPageException(uri.toString(), e);
        }

    }

    public void scrollPage(int x, int y){
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("scroll(");
            sb.append(x);
            sb.append(",");
            sb.append(y);
            sb.append(")");

            chromeDriverExtends.executeScript(sb.toString());
        } catch (Exception e){
            throw new ScrollException(e);
        }

    }

    public ByteArrayResource capturePage(){
        try {
            return new ByteArrayResource(chromeDriverExtends.getScreenshot());
        } catch (Exception e){
            throw new ScreenshotException(e);
        }
    }

}