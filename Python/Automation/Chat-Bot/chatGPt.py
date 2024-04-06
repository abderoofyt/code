import nltk
from nltk.chat.util import Chat, reflections
import googlesearch
import requests
from bs4 import BeautifulSoup
from mean_phrases import phrases

class SimpleChatbot:
    def __init__(self, pairs, reflections={}):
        self.chatbot = Chat(pairs, reflections)

    def respond(self, user_input):
        response = self.chatbot.respond(user_input)
        if not response:
            response = self.google_search(user_input)
        return response

    def google_search(self, query):
        try:
            search_results = list(googlesearch.search(query, num=3, stop=3, pause=2))
            for result in search_results:
                page = requests.get(result)
                soup = BeautifulSoup(page.content, 'html.parser')
                text = ' '.join([p.text.strip() for p in soup.find_all('p')])  # Extract text from paragraphs
                if text:
                    return text[:300] + '...' if len(text) > 300 else text  # Limit response length
        except Exception as e:
            print("An error occurred during Google search:", e)
        return "Sorry, I couldn't find an answer to that question."

def initialize_chatbot():
    pairs = phrases()
    bot = SimpleChatbot(pairs, reflections)
    return bot

def start_chat(bot):
    print("Hello! My name is Mika I'm your chatbot.")

    while True:
        user_input = input("You: ").lower()
        if user_input == 'quit':
            print("Goodbye!")
            break
        response = bot.respond(user_input)
        print("Bot:", response)

if __name__ == "__main__":
    nltk.download('punkt')
    bot = initialize_chatbot()
    start_chat(bot)
