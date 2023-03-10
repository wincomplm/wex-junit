/*
 * Copyright (c) 2022 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */

package com.wincomplm.wex.junit.impl.test;

import com.wincomplm.wex.wt.framework.api.system.WTConstants;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
       
    protected String getAuthUrl() {
        return getAuthUrl(auth);
    }
        
    protected String getAuthUrl(String auth) {
        String url = WTConstants.CODEBASE;
        int endOfProtocol  = url.indexOf("/") + 2;
        String authUrl = url.substring(0,endOfProtocol) + auth + "@" + url.substring(endOfProtocol);
        return authUrl;
    }
        
    protected void checkNonce(String url) {
        driver.get(getAuthUrl() + url);
        assertEquals(driver.getPageSource().contains("INVALID_NONCE"),true);
    }
}
