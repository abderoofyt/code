import os
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# Function to check if authentication token is valid
def token_is_valid():
    # Check if token file exists
    return os.path.exists("whatsapp_token.txt")

# Function to log in using stored authentication token
def login_with_token(driver):
    # Read token from file
    with open("whatsapp_token.txt", "r") as file:
        token = file.read().strip()

    # Use token to log in
    driver.execute_script(f"localStorage.setItem('WABrowserId', '{token}');")
    driver.get("https://web.whatsapp.com/")

# Function to extract authentication token
def extract_authentication_token(driver):
    # Execute JavaScript to retrieve token from Local Storage
    token = driver.execute_async_script("""
        var callback = arguments[arguments.length - 1];
        var token = localStorage.getItem('WABrowserId');
        callback(token);
    """)
    return token

# Function to store authentication token
def store_authentication_token(token):
    # Store authentication token to file
    with open("whatsapp_token.txt", "w") as file:
        file.write(token)

# Set the path to the Chrome WebDriver executable
driver_path = "C:\\Users\\ghs6kor\\Desktop\\Python\\chromedriver.exe"

# Set Chrome WebDriver executable path as system property
webdriver.chrome.driver = driver_path

# Configure Chrome options for headless mode and full screen
chrome_options = Options()
# chrome_options.add_argument("--headless")  # Run Chrome in headless mode
chrome_options.add_argument("--start-maximized")  # Maximize window

# Initialize the Chrome WebDriver with configured options
driver = webdriver.Chrome(options=chrome_options)

# Check if authentication token is already stored
if token_is_valid():
    # Use stored token to log in
    login_with_token(driver)
else:
    # Wait for the QR code to load
    time.sleep(10)  # Adjust this value based on your network speed and computer performance

    # Wait for the user to scan the QR code and then press Enter
    input("Please scan the QR code and then press Enter.")

    # Extract and store authentication token
    token = extract_authentication_token(driver)
    store_authentication_token(token)

# Wait for the user to log in and load the chats
input("Please log in and load the chats, then press Enter.")

# Locate the search box element by its title attribute
search_box = driver.find_element(By.XPATH, "//div[@title='💙 Shamika 💙']")

# Click on the search box to focus
search_box.click()

# Wait for the search box to become editable
WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.XPATH, "//div[@contenteditable='true']")))

# Type the name of the contact "Shamika"
search_box.send_keys("Shamika")

# Press Enter to perform the search
search_box.send_keys(Keys.ENTER)

# Wait for the search results
WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, "//div[@class='_3Bxar']")))

# Locate the chat input field and send a message
chat_input = driver.find_element(By.XPATH, "//div[@contenteditable='true']")
chat_input.send_keys("Hello, Shamika! This is an automated message sent using Python and Selenium.")
chat_input.send_keys(Keys.ENTER)

# Close the browser window
driver.quit()
