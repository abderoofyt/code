from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# Initialize WebDriver (choose browser: Chrome, Firefox, etc.)
driver = webdriver.Chrome()

# Open the web application URL
driver.get("https://example.com")

# Find and interact with web elements
try:
    # Wait for an element to be present (e.g., a login button)
    login_button = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.ID, "login-button"))
    )
    
    # Click on the login button
    login_button.click()

    # Find and interact with other elements (e.g., input fields, buttons)
    username_input = driver.find_element(By.ID, "username")
    password_input = driver.find_element(By.ID, "password")
    
    # Enter username and password
    username_input.send_keys("your_username")
    password_input.send_keys("your_password")

    # Submit the login form
    login_button = driver.find_element(By.XPATH, "//button[@type='submit']")
    login_button.click()

    # Wait for the next page to load (e.g., dashboard)
    WebDriverWait(driver, 10).until(
        EC.title_contains("Dashboard")
    )

    # Assert expected behavior (e.g., presence of elements, page content)
    assert "Welcome" in driver.page_source
    
    print("Test Passed: User successfully logged in.")

except Exception as e:
    print("Test Failed:", e)

finally:
    # Close the browser window
    driver.quit()
