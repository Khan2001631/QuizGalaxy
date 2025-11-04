import os
import weaviate
from weaviate.classes.init import Auth
from dotenv import load_dotenv
from app.schema import quiz_question_schema
from weaviate.classes.query import Filter



class WeaviateClient:
    def __init__(self):
        load_dotenv()
        self.url = os.environ.get("WEAVIATE_URL")
        self.api_key = os.environ.get("WEAVIATE_API_KEY")
        self.google_api_key = os.environ.get("GOOGLE_API_KEY")

        if not self.url or not self.api_key:
            raise ValueError("Missing WEAVIATE_URL or WEAVIATE_API_KEY in .env")
        
        if not self.google_api_key:
            raise ValueError("Missing GOOGLE_API_KEY in .env")

        # Connect to Weaviate
        self.client = weaviate.connect_to_weaviate_cloud(
            cluster_url=self.url,
            auth_credentials=Auth.api_key(self.api_key),
        )

    def is_ready(self) -> bool:
        return self.client.is_ready()

    def close(self):
        self.client.close()

    def create_quiz_question_schema(self):
        try:
            self.client.collections.create(**quiz_question_schema)
            print("✅ Schema QuizQuestion created")
        except weaviate.exceptions.WeaviateException as e:
            if "already exists" in str(e).lower():
                print("⚠️ Schema already exists, skipping creation.")
        except Exception as e:
            print("⚠️ Schema creation skipped or already exists:", e)

    def insert_dummy_questions(self):
        quiz_questions = [
            {
                "questionText": "What is the capital of France?",
                "questionType": "MCQ",
                "options": ["Paris", "London", "Berlin", "Madrid"],
                "correctAnswer": "Paris",
                "difficulty": "Easy",
                "topic": "Geography",
            },
            {
                "questionText": "Who wrote the play 'Romeo and Juliet'?",
                "questionType": "MCQ",
                "options": ["William Shakespeare", "Leo Tolstoy", "Mark Twain", "Charles Dickens"],
                "correctAnswer": "William Shakespeare",
                "difficulty": "Medium",
                "topic": "Literature",
            },
            {
                "questionText": "Explain the process of photosynthesis.",
                "questionType": "TEXT",
                "options": [],
                "correctAnswer": "Photosynthesis is the process by which green plants use sunlight to synthesize food from carbon dioxide and water.",
                "difficulty": "Hard",
                "topic": "Biology",
            },
        ]

        self.google_api_key = os.environ.get("GOOGLE_API_KEY")
        if  self.google_api_key:
            print(f"Google API Key: {self.google_api_key}")

        collection = self.client.collections.get("QuizQuestion")

        for q in quiz_questions:
            try:
                collection.data.insert(properties=q)
                print(f"✅ Inserted: {q['questionText']}")
            except weaviate.exceptions.WeaviateQueryException as e:
                print(f"⚠️ Failed to insert {q['questionText']}: {e}")
            except Exception as e:
                print(f"⚠️ Failed to insert {q['questionText']}: {e}")

    def evaluate_user_answers(self, user_answers):
        collection = self.client.collections.get("QuizQuestion")

        for ans in user_answers:
            print(f"\n🔹 Question: {ans['questionText']}")
            print(f"   User Answer: {ans['userAnswer']}")

            # Step 1: Fetch the correct answer
            response = collection.query.fetch_objects(
                filters=Filter.by_property("questionText").equal(ans["questionText"]),
                return_properties=["questionText", "correctAnswer", "questionType"]
            )

            if not response.objects:
                print("   ⚠️ Question not found in Weaviate")
                continue

            correct = response.objects[0].properties["correctAnswer"]
            qtype = response.objects[0].properties["questionType"]

            print(f"   Correct Answer: {correct}")

            # Step 2: Evaluate
            if qtype == "MCQ":
                if ans["userAnswer"].strip().lower() == correct.strip().lower():
                    print("   ✅ Correct")
                else:
                    print("   ❌ Incorrect")

            elif qtype == "TEXT":
                sim_response = collection.query.hybrid(
                    query=ans["userAnswer"],
                    limit=1,
                    alpha=0.5,
                    return_properties=["questionText", "correctAnswer"],
                    return_metadata=["score"]
                )

                if not sim_response.objects:
                    print("   ⚠️ No semantic match found")
                    continue

                best_match = sim_response.objects[0]
                similarity_score = best_match.metadata.score
                print(f"   Semantic Match → {best_match.properties['correctAnswer']}")
                print(f"   Similarity Score: {similarity_score}")

                if float(similarity_score) > 0.75:
                    print("   ✅ Answer accepted (semantically correct)")
                else:
                    print("   ❌ Answer not correct enough")