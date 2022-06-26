package com.joebrooks.mapshotimageapi.driver;


import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v99.network.Network;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ChromeDriverConfig {

    @Bean
    public ChromeOptions chromeOptions(){
        String chromeDriverPath = "CHROMEDRIVER_PATH";
        String chromeBinaryPath = "CHROME_BINARY";

        ChromeOptions options = new ChromeOptions();

        if(System.getProperty("os.name").toLowerCase().contains("win")){
            System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", System.getenv(chromeDriverPath));
            options.setBinary(System.getenv(chromeBinaryPath));
        }

        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--hide-scrollbars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--incognito");

        return options;
    }

    @Bean(destroyMethod = "quit")
    public ChromeDriverExtends chromeDriverExtends() throws Exception {
        ChromeDriverExtends chromeDriverExtends = new ChromeDriverExtends(chromeOptions());
        chromeDriverExtends.manage().window().setSize(new Dimension(1000, 1000));
        DevTools devTools = chromeDriverExtends.getDevTools();
        devTools.createSession();

        devTools.send(
                Network.enable(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(100000000)));
        devTools.send(Network.setCacheDisabled(true));
        devTools.close();

        return chromeDriverExtends;
    }

    @Bean
    public WebDriverWait webDriverWait() throws Exception {
        return new WebDriverWait(chromeDriverExtends(), 40L);
    }

}
