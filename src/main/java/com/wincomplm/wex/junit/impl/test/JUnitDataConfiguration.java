/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wincomplm.wex.junit.impl.test;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Yang Hao Zhang
 */
public class JUnitDataConfiguration extends JunitTestAbstract{
     //this methods takes an ID to wait for, otherwise it would cause a race condition
    protected void cleanUp(String ReloadID) throws Exception {
        try {
            List<WebElement> isFound = driver.findElements(By.xpath("//i[@class='fas fa-minus']"));
            isFound.stream().forEach(element -> element.click());
            //Update button 
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            driver.get(driver.getCurrentUrl());

            new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.elementToBeClickable(By.id(ReloadID)));

        } catch (Exception e) {
            // Element not found, do nothing
        }
    }

    protected String getRandomValueArray(String[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    protected WebElement getRandomValueList(List<WebElement> list) {
        int randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
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

    protected String getRandomValueFromDropDownByXpath(String Xpath) {
        String value = getRandomValueList(driver.findElement(By.id(Xpath))
                .findElements(By.xpath(".//option")))
                .getText();
        while (value == "") {
            getRandomValueFromDropDownByXpath(Xpath);
        }
        return value;
    }

    
}
