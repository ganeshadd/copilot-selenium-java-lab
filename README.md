# copilot-selenium-java-lab
Minimal Selenium test in Java

## Overview
This project contains a minimal Selenium WebDriver test in Java that tests a local HTML login page (`dummy_login.html`).

## Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome browser
- ChromeDriver (should be available in your PATH)

## Project Structure
```
.
├── pom.xml                          # Maven project configuration
├── dummy_login.html                 # Local HTML login page for testing
└── src/test/java/com/example/
    └── LoginTest.java               # Selenium test class
```

## Running the Tests
To run the tests, execute:
```bash
mvn test
```

This will run three test cases:
1. **testSuccessfulLogin** - Tests login with valid credentials (admin/password)
2. **testFailedLogin** - Tests login with invalid credentials
3. **testPageElements** - Verifies that all required page elements are present

## Test Details
The `LoginTest` class uses:
- **Selenium WebDriver** to automate Chrome browser
- **JUnit 4** as the testing framework
- **Headless Chrome** for running tests in CI/CD environments

The tests verify:
- Page title and elements are present
- Successful login with correct credentials shows success message
- Failed login with incorrect credentials shows error message

## dummy_login.html
A simple HTML page with:
- Username input field
- Password input field
- Login button
- JavaScript validation (username: admin, password: password)
- Success/error message display

