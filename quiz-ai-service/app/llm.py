from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.messages import HumanMessage
from dotenv import load_dotenv
import os

load_dotenv()
GOOGLE_API_KEY = os.environ.get("GOOGLE_API_KEY")


# Centralized LLM setup
llm = ChatGoogleGenerativeAI(
    model="gemini-2.0-flash",  # can be swapped later with OpenAI / Ollama
    api_key=GOOGLE_API_KEY,
    temperature=0.7
)

# Simple test prompt
response = llm.invoke([HumanMessage(content="Say hello in one sentence.")])

print(response.content)
