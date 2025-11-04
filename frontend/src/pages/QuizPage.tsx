import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { finishQuiz } from '../api/quizApi';

interface Option {
  id: number;
  optionText: string;
  optionOrder: number;
}

interface Question {
  id: number;
  questionText: string;
  difficultyLevel: string;
  options: Option[];
}

interface QuizSession {
  sessionId: number;
  questions: Question[];
  currentIndex: number;
  startTime: string;
}

const QuizPage: React.FC = () => {
  const navigate = useNavigate();
  const [session, setSession] = useState<QuizSession | null>(null);
  const [selectedOptionId, setSelectedOptionId] = useState<number | null>(null);
  const [timeLeft, setTimeLeft] = useState<number>(0);

   const handleFinish = async () => {
    if (!session) return;

    const payload = {
      sessionId: session.sessionId,
      answers: session.questions.map((q: any) => ({
        questionId: q.id,
        selectedOptionId: q.selectedOptionId ?? null
      }))
    };

    console.log("Sending payload to API:", payload);

    try {
      await finishQuiz(payload);
      localStorage.removeItem("quiz-session");
      navigate("/results"); // or wherever results should show
    } catch (error) {
      console.error("Error finishing quiz:", error);
    }
  };

  useEffect(() => {
    const data = localStorage.getItem("quiz-session");
    if (!data) {
      navigate("/");
      return;
    }

    const parsed: QuizSession = JSON.parse(data);
    setSession(parsed);

    const durationInSeconds = 150;
    const startTime = new Date(parsed.startTime).getTime();
    const endTime = startTime + durationInSeconds * 1000;

    const interval = setInterval(() => {
      const now = new Date().getTime();
      const diff = Math.floor((endTime - now) / 1000);

      if (diff <= 0) {
        clearInterval(interval);
        alert("Time up!");
        handleFinish(); // ✅ auto-submit remaining answers
      }

      setTimeLeft(Math.max(diff, 0));
    }, 1000);

    return () => clearInterval(interval);
  }, [navigate, handleFinish]);


  if (!session) return null;

  const question = session.questions[session.currentIndex];

  console.log(session);



  

  const handleNext = () => {
    if (!session) return;

    const updatedSession: any = {
      ...session,
      currentIndex: session.currentIndex + 1
    };

    setSession(updatedSession);
    setSelectedOptionId(
      updatedSession.questions[updatedSession.currentIndex]?.selectedOptionId ?? null
    );
    localStorage.setItem("quiz-session", JSON.stringify(updatedSession));
  };



  const handleOptionSelect = (optionId: number) => {
    setSelectedOptionId(optionId);

    if (session) {
      const updatedQuestions = session.questions.map((q, idx) =>
        idx === session.currentIndex ? { ...q, selectedOptionId: optionId } : q
      );

      const updatedSession = { ...session, questions: updatedQuestions };
      setSession(updatedSession);
      localStorage.setItem('quiz-session', JSON.stringify(updatedSession));
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-4 space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-xl font-semibold">Question {session.currentIndex + 1}/{session.questions.length}</h2>
        <span className="text-red-600 font-bold">⏳ {Math.floor(timeLeft / 60)}:{String(timeLeft % 60).padStart(2, '0')}</span>
      </div>

      <div className="bg-white shadow-md rounded-xl p-6">
        <p className="text-lg font-medium mb-4">{question.questionText}</p>
        <div className="space-y-3">
          {question.options
            .sort((a, b) => a.optionOrder - b.optionOrder)
            .map((opt) => (
              <label
                key={opt.id}
                className={`block border p-3 rounded-lg cursor-pointer ${
                  selectedOptionId === opt.id ? 'border-blue-500 bg-blue-50' : 'border-gray-300'
                }`}
              >
                <input
                  type="radio"
                  name="option"
                  value={opt.id}
                  checked={selectedOptionId === opt.id}
                  onChange={() => handleOptionSelect(opt.id)}
                  className="mr-2"
                />
                {opt.optionText}
              </label>
            ))}
        </div>
      </div>

      <div className="flex justify-end">
      {session.currentIndex + 1 < session.questions.length ? (
        <button
          onClick={handleNext}
          className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg"
        >
          Next
        </button>
      ) : (
        <button
          onClick={handleFinish}
          className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
        >
          Finish
        </button>
      )}
    </div>

    </div>
  );
};

export default QuizPage;
