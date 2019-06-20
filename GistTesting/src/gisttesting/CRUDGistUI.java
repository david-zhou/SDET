/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gisttesting;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author davidzhou
 */
public class CRUDGistUI {
    private WebDriver driver = null;
    private String username;
    private String driverURL;
    private String gistBaseURL;
    
    public CRUDGistUI() {
        username = ReadPropertyFile.getProperty("username");
        driverURL = ReadPropertyFile.getProperty("chromedriverurl");
        gistBaseURL = ReadPropertyFile.getProperty("gistbaseurl");
    }
    
    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriba la contraseña del correo " + username);
        String password = sc.nextLine();
        
        System.setProperty("webdriver.chrome.driver", driverURL);
        
        driver = new ChromeDriver();

        driver.get(gistBaseURL);
        driver.findElement(By.linkText("Sign in")).click();
        
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password")).submit();
    }
}
