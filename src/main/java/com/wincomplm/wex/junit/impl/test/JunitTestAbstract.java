/*
 * Copyright (c) 2022 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */
package com.wincomplm.wex.junit.impl.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import wt.util.WTProperties;

/**
 *
 * @author SimonHeath
 */
public class JunitTestAbstract {

    protected WebDriver driver;
    protected Map<String, Object> vars;
    JavascriptExecutor js;
    String baseurl;

    static public String auth = "wcadmin:wcadmin";

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public static void setAuth(String auth) {
        JunitTestAbstract.auth = auth;
    }

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        options.merge(capabilities);
        driver = new ChromeDriver(options);

        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected String getAuthUrl() {
        return getAuthUrl(auth);
    }

    protected String getAuthUrl(String auth) {
        try {
            String url = WTProperties.getLocalProperties().getProperty("wt.server.codebase");
            int endOfProtocol = url.indexOf("/") + 2;
            String authUrl = url.substring(0, endOfProtocol) + auth + "@" + url.substring(endOfProtocol);
            return authUrl;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void checkNonce(String url) {
        driver.get(getAuthUrl() + url);
        String code = driver.getPageSource();
        boolean securityFalse = code.contains("INVALID_NONCE") || code.contains("\"success\":false");
        assertEquals(true, securityFalse);
    }

}
