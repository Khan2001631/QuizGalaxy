from fastapi import FastAPI
from app.weaviate_client import WeaviateClient
from app.llm import llm
from langchain_core.messages import HumanMessage

response = llm.invoke([HumanMessage(content="Say hello in one sentence.")])
print("LLM Test:", response.content)

app = FastAPI()

user_answers = [
    {"questionText": "What is the capital of France?", "questionType": "MCQ", "userAnswer": "Paris"},
    {"questionText": "Who wrote the play 'Romeo and Juliet'?", "questionType": "MCQ", "userAnswer": "Mark Twain"},
    {"questionText": "Explain the process of photosynthesis.", "questionType": "TEXT", "userAnswer": "Plants make food using sunlight, water, and carbon dioxide."},
]

# Connect to Weaviate when app starts
@app.on_event("startup")
def startup_event():
    global w_client
    w_client = WeaviateClient()
    print("Weaviate ready:", w_client.is_ready())
    # w_client.create_quiz_question_schema()
    w_client.insert_dummy_questions()
    # w_client.evaluate_user_answers(user_answers)

# Close connection on shutdown
@app.on_event("shutdown")
def shutdown_event():
    w_client.close()
