package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static org.junit.Assert.*;

public class LoginTest {
    private WebDriver driver;
    private String htmlFilePath;

    @Before
    public void setUp() {
        // Set ChromeDriver path for environments where it's not in PATH
        String chromeDriverPath = System.getenv().getOrDefault("CHROMEDRIVER_PATH", "/usr/bin/chromedriver");
        File chromeDriverFile = new File(chromeDriverPath);
        if (chromeDriverFile.exists()) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        
        // Set up ChromeDriver with headless option for CI/CD environments
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        
        // Get the absolute path to the dummy_login.html file
        File htmlFile = new File("dummy_login.html");
        htmlFilePath = "file://" + htmlFile.getAbsolutePath();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSuccessfulLogin() {
        // Navigate to the login page
        driver.get(htmlFilePath);
        
        // Verify page title
        assertEquals("Login Page", driver.getTitle());
        
        // Find form elements
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        
        // Enter valid credentials
        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        
        // Click login button
        loginButton.click();
        
        // Wait for message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        
        // Verify success message
        assertTrue(message.isDisplayed());
        assertEquals("Login successful!", message.getText());
        assertTrue(message.getAttribute("class").contains("success"));
    }

    @Test
    public void testFailedLogin() {
        // Navigate to the login page
        driver.get(htmlFilePath);
        
        // Find form elements
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        
        // Enter invalid credentials
        usernameField.sendKeys("wronguser");
        passwordField.sendKeys("wrongpass");
        
        // Click login button
        loginButton.click();
        
        // Wait for message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        
        // Verify error message
        assertTrue(message.isDisplayed());
        assertEquals("Invalid username or password", message.getText());
        assertTrue(message.getAttribute("class").contains("error"));
    }

    @Test
    public void testPageElements() {
        // Navigate to the login page
        driver.get(htmlFilePath);
        
        // Verify all required form elements are present
        assertNotNull(driver.findElement(By.id("username")));
        assertNotNull(driver.findElement(By.id("password")));
        assertNotNull(driver.findElement(By.id("loginButton")));
        assertNotNull(driver.findElement(By.id("message")));
        
        // Verify page heading
        WebElement heading = driver.findElement(By.tagName("h2"));
        assertEquals("Login", heading.getText());
    }
}
