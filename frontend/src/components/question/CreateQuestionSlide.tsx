// CreateQuestionSlide.jsx
import { useState } from "react";
import CreateQuestion from "./CreateQuestion";
import { redirect, useSubmit } from "react-router-dom";
interface Question {
  category: string;
  topic: string;
  question: string;
  option1: string;
  option2: string;
  option3: string;
  option4: string;
  correctAnswer: string;
}
function CreateQuestionSlide() {
  const emptyQuestion: Question = {
    category: "Easy",
    topic: "",
    question: "",
    option1: "",
    option2: "",
    option3: "",
    option4: "",
    correctAnswer: "",
  };

  const [questionArrays, setQuestionArrays] = useState<Question[]>([
    emptyQuestion,
  ]);
  const [totalSlides,setTotalSlides] = useState(0);
  const [currentSlide, setCurrentSlide] = useState(0);

  const submit = useSubmit();
  
  const handleChange = (idx: number, updatedQuestion: Question) => {
    setQuestionArrays((prevQuestions) =>
      prevQuestions.map((q, i) => (i === idx ? updatedQuestion : q))
    );
  };
  console.log(questionArrays);

  function addSlide() {
   setQuestionArrays(prev => [...prev, {...emptyQuestion}]);
   setTotalSlides(prev => prev+1)
   setCurrentSlide(() => totalSlides+1);
    
  };

  function handleSubmit(){
    submit(
      {questions: JSON.stringify(questionArrays)},
      {method: "post"}
    )
    
  }



  return (
    <div className="bg-gray-50 min-h-screen flex flex-col">
      {/* Main Question Area */}
      <main className="flex-1 p-8 flex justify-center items-center">
        <CreateQuestion
          idx={currentSlide}
          question={questionArrays[currentSlide]}
          onChange={handleChange}
        />
      </main>

      {/* Footer with slide navigation */}
      <footer className="bg-white shadow-inner py-4 px-8 flex items-center justify-between">
        {/* Slide navigation buttons */}
        <div className="flex gap-2">
          {questionArrays.map((_, index) => (
            <button
              key={index}
              onClick={() => setCurrentSlide(index)}
              className={`w-6 h-6 rounded-full border-2 transition ${
                index === currentSlide
                  ? "bg-blue-600 border-blue-600"
                  : "bg-white border-gray-400 hover:bg-blue-100"
              }`}
            ></button>
          ))}
        </div>

        {/* Action Buttons */}
        <div className="flex gap-4">
          <button
            onClick={addSlide}
            className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600 transition"
          >
            Add Question
          </button>
          <button className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition" onClick={handleSubmit}>
            Submit
          </button>
        </div>
      </footer>
    </div>
  );
}

export default CreateQuestionSlide;





