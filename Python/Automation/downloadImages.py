from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# Set the path to the Chrome WebDriver executable
driver_path = "C:\\Users\\ghs6kor\\Desktop\\Python\\chromedriver.exe"

# Set Chrome WebDriver executable path as system property
webdriver.chrome.driver = driver_path

# Configure Chrome options for headless mode and full screen
chrome_options = Options()
chrome_options.add_argument("--headless")  # Run Chrome in headless mode
chrome_options.add_argument("--start-maximized")  # Maximize window

# Initialize the Chrome WebDriver with configured options
driver = webdriver.Chrome(options=chrome_options)

# Implicitly wait for 5 seconds
driver.implicitly_wait(5)

# Open Google homepage
driver.get("https://www.google.com/")

# Identify the search box element
search_box = driver.find_element(By.NAME, "q")

# Enter text with send_keys() then apply submit()
search_box.send_keys("Sana Twice")
search_box.submit()

# Explicitly wait for the Images tab to be clickable
wait = WebDriverWait(driver, 10)
images_tab = wait.until(EC.element_to_be_clickable((By.XPATH, '//div[@jsname="bVqjv" and text()="Images"]')))

# Click on the Images tab
images_tab.click()

# Take a screenshot
driver.save_screenshot("screenshot.png")

# Close the browser window
driver.quit()
