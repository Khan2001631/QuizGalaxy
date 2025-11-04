from weaviate.classes.config import Property, DataType, Configure

quiz_question_schema = {
    "name": "QuizQuestion",
    "description": "A question for the quiz application",
    "properties": [
        Property(name="questionText", data_type=DataType.TEXT),              # main question text
        Property(name="questionType", data_type=DataType.TEXT),      # MCQ, TRUE_FALSE, etc.
        Property(name="options", data_type=DataType.TEXT_ARRAY),     # answer options if MCQ
        Property(name="correctAnswer", data_type=DataType.TEXT),     # correct option or text
        Property(name="difficulty", data_type=DataType.TEXT),        # Easy, Medium, Hard
        Property(name="topic", data_type=DataType.TEXT),             # e.g. Java, Spring Boot
        Property(name="explanation", data_type=DataType.TEXT),       # optional explanation
    ],
    # "vectorizer_config": Configure.Vectorizer.none()  # you handle embeddings
    "vectorizer_config": Configure.Vectorizer.text2vec_google(project_id="integral-linker-428705-d8"),

}
