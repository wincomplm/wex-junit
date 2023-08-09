/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wincomplm.wex.junit.impl.test;

import com.wincomplm.wex.config.impl.ifc.IWexConfiguration;
import com.wincomplm.wex.store.commons.impl.persist.WexPersistor;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Yang Hao Zhang
 */
public class JUnitDataConfiguration extends JunitTestAbstract {

    protected WexPersistor<IWexConfiguration> getConfigPersistor(String pid) throws Exception {
        String name = (pid + " - CONFIG").toUpperCase();
        return WexPersistor.newWexPersistor(null, "/Default/wex/config", name);
    }

    protected void deleteConfigUsingPersistor(String pid) throws Exception {
        WexPersistor persistor = getConfigPersistor(pid);
        persistor.deleteDocument();
    }

    //this methods takes an ID to wait for, otherwise it would cause a race condition
    protected void reload(String elementToWaitID) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().refresh();
        new WebDriverWait(driver, Duration.ofSeconds(300)).until(ExpectedConditions.elementToBeClickable(By.id(elementToWaitID)));
    }

    protected String getRandomValueArray(String[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    protected WebElement getRandomValueList(List<WebElement> list) {
        int randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }

    protected String getRandomValueFromDropDownByXpath(String Xpath) {
        String value = getRandomValueList(driver.findElement(By.xpath(Xpath))
                .findElements(By.xpath(".//option")))
                .getText();
        while (value == "") {
            getRandomValueFromDropDownByXpath(Xpath);
        }
        return value;
    }
    
    protected String getRandomValueFromDropDownByID(String ID) {
        String value;
        do {
            value = getRandomValueList(driver.findElement(By.id(ID))
                    .findElements(By.xpath(".//option")))
                    .getText();

        } while (value.isEmpty());

        return value;
    }

    protected void loadConfigTab(String URLEnd, String firstElementByID) {
        //url
        String url = getBaseurl() + URLEnd;
        //wait for the page to load 
        driver.get(getAuthUrl() + url);
        reload(firstElementByID);
    }

    protected String getFirstOptionByXpath(String xpath) {
        String text = new Select(driver.findElement(By.xpath(xpath))).getFirstSelectedOption().getText();
        return text;
    }

    protected String getValueByXpath(String xpath) {
        String text = driver.findElement(By.xpath(xpath)).getAttribute("value");

        return text;
    }

    protected boolean isSelectedByXpath(String xpath) {
        boolean isSelected = driver.findElement(By.xpath(xpath)).isSelected();

        return isSelected;
    }

}
