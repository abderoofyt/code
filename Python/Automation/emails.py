import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

def send_email(subject, body, to_email, from_email, password):
    """
    Sends an email with the given subject and body to the specified recipient.
    """
    try:
        # Create a MIMEText object to represent the email body
        message = MIMEMultipart()
        message['From'] = from_email
        message['To'] = to_email
        message['Subject'] = subject
        message.attach(MIMEText(body, 'plain'))

        # Connect to the SMTP server using SSL
        with smtplib.SMTP_SSL('smtp.gmail.com', 465) as server:
            server.login(from_email, password)
            server.sendmail(from_email, to_email, message.as_string())
            print("Email sent successfully!")
    except smtplib.SMTPAuthenticationError:
        print("Authentication failed. Make sure you have provided correct credentials.")
    except smtplib.SMTPException as e:
        print("An error occurred while sending the email:", e)

# Example usage:
if __name__ == "__main__":
    from_email = "junaid.abderoof@gmail.com"
    password = "harryPotter45!darkGmail"
    to_email = "abderoofyt@gmail.com"
    subject = "Test Email"
    body = "This is a test email sent from Python."

    send_email(subject, body, to_email, from_email, password)
