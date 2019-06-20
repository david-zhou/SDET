/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gisttesting;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author davidzhou
 */
public class CRUDGistUI {
    private WebDriver driver = null;
    private String username;
    private String driverURL, gistBaseURL;
    private String loginField, passwordField, descriptionField, fileNameField, 
            newFileNameField;
    private String signInButton, addPublicGistButton, addPrivateGistButton, userIcon, 
            yourGistsOption, editButton, updateButton, deleteGistButton;
    
    // loads the fields names from config file
    public CRUDGistUI() {
        username = ReadPropertyFile.getProperty("username");
        driverURL = ReadPropertyFile.getProperty("chromedriverurl");
        gistBaseURL = ReadPropertyFile.getProperty("gistbaseurl");
        
        signInButton = ReadPropertyFile.getProperty("signinbutton");
        loginField = ReadPropertyFile.getProperty("loginfield");
        passwordField = ReadPropertyFile.getProperty("passwordfield");
        
        descriptionField = ReadPropertyFile.getProperty("descriptionfield");
        fileNameField = ReadPropertyFile.getProperty("filenamefield");
        addPublicGistButton = ReadPropertyFile.getProperty("addpublicgistbutton");
        addPrivateGistButton = ReadPropertyFile.getProperty("addprivategistbutton");
        
        userIcon = ReadPropertyFile.getProperty("usericon");
        yourGistsOption = ReadPropertyFile.getProperty("yourgists");
        editButton = ReadPropertyFile.getProperty("editbutton");
        newFileNameField = ReadPropertyFile.getProperty("newfilename");
        updateButton = ReadPropertyFile.getProperty("updatebutton");
        
        deleteGistButton = ReadPropertyFile.getProperty("deletegistbutton");
    }
    
    //logs into github with the username provided and asks for password
    public void login() {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriba la contraseña del correo " + username);
        String password = sc.nextLine();
        
        System.setProperty("webdriver.chrome.driver", driverURL);
        
        driver = new ChromeDriver();

        driver.get(gistBaseURL);
        driver.findElement(By.linkText(signInButton)).click();
        
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        
        driver.findElement(By.id(loginField)).sendKeys(username);
        driver.findElement(By.id(passwordField)).sendKeys(password);
        driver.findElement(By.id(passwordField)).submit();
    }
    
    // adds gist with the values in parameters
    public boolean addGist(String description, String fileName, String code, boolean isPublic) {
        WebElement button;
        
        driver.get(gistBaseURL);
        driver.findElement(By.name(descriptionField)).sendKeys(description);
        driver.findElement(By.name(fileNameField)).sendKeys(fileName);

        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);

        driver.switchTo().activeElement().sendKeys(code);
        
        if (isPublic) {
            button = driver.findElement(By.cssSelector(addPublicGistButton));
            if (!button.isEnabled()) {
                System.out.println("Botón no habilitado, sin código");
                return false;
            }
            
        } else {
            button = driver.findElement(By.cssSelector(addPrivateGistButton));
            if (!button.isEnabled()) {
                System.out.println("Botón no habilitado, sin código");
                return false;
            }
            
        }
        button.click();
        return true;
    }
    
    //edits the gist by the name given (if multiple ocurrences, edits the first one)
    public boolean editGistByName(String filename, String newDescription, String newFileName, String newCode) {
        try {
            driver.get(gistBaseURL);
            driver.findElement(By.id(userIcon)).click();
            driver.findElement(By.className(yourGistsOption)).click();


            driver.findElement(By.linkText(filename)).click();
            driver.findElement(By.cssSelector(editButton)).click();
            
            driver.findElement(By.name(descriptionField)).clear();
            driver.findElement(By.name(descriptionField)).sendKeys(newDescription);
            driver.findElement(By.cssSelector(newFileNameField)).clear();
            driver.findElement(By.cssSelector(newFileNameField)).sendKeys(newFileName);

            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            driver.switchTo().activeElement().sendKeys(Keys.TAB);

            driver.switchTo().activeElement().clear();
            driver.switchTo().activeElement().sendKeys(newCode);
            
            
            driver.findElement(By.cssSelector(updateButton)).click();
            
            return true;
        } catch (NoSuchElementException ex) {
            System.out.println("No such gist name");
            return false;
        }
    }
    
    //edits the gist by the url given
    public boolean editGistByUrl(String url, String newDescription, String newFileName, String newCode) {
        try {
            String newUrl = gistBaseURL + "/" + url;
            driver.get(newUrl);
            driver.findElement(By.cssSelector(editButton)).click();
            
            driver.findElement(By.name(descriptionField)).clear();
            driver.findElement(By.name(descriptionField)).sendKeys(newDescription);
            driver.findElement(By.cssSelector(newFileNameField)).clear();
            driver.findElement(By.cssSelector(newFileNameField)).sendKeys(newFileName);

            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            driver.switchTo().activeElement().sendKeys(Keys.TAB);

            driver.switchTo().activeElement().clear();
            driver.switchTo().activeElement().sendKeys(newCode);
            
            
            driver.findElement(By.cssSelector(updateButton)).click();
            
            return true;
        } catch (NoSuchElementException ex) {
            System.out.println("No such gist name");
            return false;
        }
    }
    
    // deletes the gist by name given (if multiple ocurrences, deletes the first one)
    public boolean deleteGistByName(String filename) {
        try {
            driver.get(gistBaseURL);
            driver.findElement(By.id(userIcon)).click();
            driver.findElement(By.className(yourGistsOption)).click();


            driver.findElement(By.linkText(filename)).click();
            driver.findElement(By.cssSelector(deleteGistButton)).click();
            Alert popup = driver.switchTo().alert();
            popup.accept();
            return true;
        } catch (NoSuchElementException ex) {
            System.out.println("No such gist name");
            return false;
        }
    }
    
    //deletes the gist by the url given
    public boolean deleteGistByUrl(String url) {
        String newUrl = gistBaseURL + "/" + url;
        try {
            driver.get(newUrl);
            driver.findElement(By.cssSelector(deleteGistButton)).click();
            Alert popup = driver.switchTo().alert();
            popup.accept();
            return true;
        } catch (NoSuchElementException ex) {
            System.out.println("No such gist name");
            return false;
        }
    }
}
