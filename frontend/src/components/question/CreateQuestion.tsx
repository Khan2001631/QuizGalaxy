import { useEffect, useState } from "react";

// CreateQuestion.jsx
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
interface CreateQuestionProps {
  idx: number;
  question: Question;
  onChange: (index: number, updatedQuestion: Question) => void;
}

function CreateQuestion({ idx, question, onChange }: CreateQuestionProps) {
 
  return (
    <div className="flex flex-col md:flex-row gap-10 w-full max-w-7xl mx-auto">
      {/* Quiz Preview Panel */}

      <div className="w-full md:w-96 bg-white shadow-2xl rounded-2xl p-6 flex flex-col">
        <h2 className="text-2xl font-bold text-gray-900 mb-4 border-b pb-2">
          Preview
        </h2>

        {/* Question */}
        <div className="mb-6">
          <div className="text-gray-800 text-lg font-medium mb-4 max-h-60 overflow-y-auto break-words">
            {question?.question || "Question Text"}
          </div>

          {/* Options */}
          <div className="flex flex-col gap-3">
            {["option1", "option2", "option3", "option4"].map((optKey, idx) => {
              const value =
                question[optKey as keyof Question] || `Option ${idx + 1}`;
              const isCorrect = value === question.correctAnswer;

              return (
                <p
                  key={optKey}
                  className={`text-left p-3 border rounded-xl transition break-words
              ${
                isCorrect
                  ? "bg-green-200 hover:bg-green-300"
                  : "bg-gray-100 hover:bg-blue-50"
              }`}
                >
                  {value}
                </p>
              );
            })}
          </div>
        </div>

        <p className="text-sm text-gray-500 mt-auto">
          Category: {question.category || "Easy"} | Topic:{" "}
          {question.topic || "Select a topic"}
        </p>
      </div>

      {/* Question Form */}
      <div className="flex-1 bg-white shadow-2xl rounded-2xl p-8 flex flex-col gap-6">
        <h2 className="text-3xl font-bold text-gray-900 mb-4">
          Create Question
        </h2>

        {/* Category and Topic */}
        <div className="flex flex-col md:flex-row gap-4">
          <select
            onChange={(e) =>
              onChange(idx, { ...question, category: e.target.value })
            }
            value={question.category || "Easy"}
            className="flex-1 p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 text-gray-700 bg-gradient-to-r from-green-50 to-green-100"
          >
            <option value="Easy">Easy</option>
            <option value="Medium">Medium</option>
            <option value="Hard">Hard</option>
          </select>
          <input
            className="flex-1 p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 text-gray-700 bg-gradient-to-r from-purple-50 to-purple-100"
            placeholder="Topic"
            value={question.topic}
            onChange={(e) =>
              onChange(idx, { ...question, topic: e.target.value })
            }
          />
        </div>

        {/* Question Text */}
        <textarea
          className="p-4 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-blue-400 text-gray-700 resize-none h-28 bg-gray-50 shadow-inner"
          placeholder="Type your question here..."
          value={question.question}
          onChange={(e) =>
            onChange(idx, { ...question, question: e.target.value })
          }
        />

        {/* Options */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <input
            className="p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gradient-to-r from-pink-50 to-pink-100"
            placeholder="Option 1"
            value={question.option1}
            onChange={(e) =>
              onChange(idx, { ...question, option1: e.target.value })
            }
          />
          <input
            className="p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gradient-to-r from-pink-50 to-pink-100"
            placeholder="Option 2"
            value={question.option2}
            onChange={(e) =>
              onChange(idx, { ...question, option2: e.target.value })
            }
          />
          <input
            className="p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gradient-to-r from-pink-50 to-pink-100"
            placeholder="Option 3"
            value={question.option3}
            onChange={(e) =>
              onChange(idx, { ...question, option3: e.target.value })
            }
          />
          <input
            className="p-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-50 bg-gradient-to-r from-pink-50 to-pink-100"
            placeholder="Option 4"
            value={question.option4}

            onChange={(e) =>
              onChange(idx, { ...question, option4: e.target.value })
            }
          />
        </div>

        {/* Correct Answer */}
        <input
          className="p-3 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-blue-400 text-gray-700 bg-yellow-50"
          placeholder="Correct Answer"
          value={question.correctAnswer}
          onChange={(e) =>
            onChange(idx, { ...question, correctAnswer: e.target.value })
          }
        />
      </div>
    </div>
  );
}

export default CreateQuestion;
