import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAllTopics } from '../api/topicAPI';
import { startQuiz } from '../api/quizApi';
interface Topic {
    id: number;
    name: string;
    description: string;
    createdAt: string;
}

const DIFFICULTIES = ['EASY', 'MEDIUM', 'HARD'];

const QuizSetupPage: React.FC = () => {
    const [topics, setTopics] = useState<Topic[]>([]);
    const [selectedTopic, setSelectedTopic] = useState<Topic | null>(null);
    const [selectedDifficulties, setSelectedDifficulties] = useState<string[]>([]);
    const [questionCount, setQuestionCount] = useState<number>(5);
    const [duration, setDuration] = useState<number>(5); // in minutes
    const [maxDuration, setMaxDuration] = useState<number>(5);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchTopics = async () => {
        const res = await getAllTopics();
        console.log(res);
        
        setTopics(res.data);
        };
        fetchTopics();
    }, []);

    useEffect(() => {
        const calculated = questionCount * 1; // 1 min per question
        setMaxDuration(calculated);
        if (duration > calculated) setDuration(calculated);
    }, [questionCount]);

    const toggleDifficulty = (diff: string) => {
        setSelectedDifficulties((prev) =>
        prev.includes(diff) ? prev.filter((d) => d !== diff) : [...prev, diff]
        );
    };

    const handleStart = async () => {
        if (!selectedTopic || selectedDifficulties.length === 0) return;

        const quizConfig = {
            topicIds: [selectedTopic.id],
            difficultyLevels: selectedDifficulties,
            totalQuestions: questionCount,
            allowSkipping: true,
        };

        try {
            const res = await startQuiz(quizConfig);
            const { sessionId, questions, startTime } = res.data;

            localStorage.setItem('quiz-session', JSON.stringify({
            sessionId,
            questions,
            currentIndex: 0,
            startTime,
            }));

            navigate('/quiz/start'); // ✅ CONFIRMED: This is the right route
        } catch (error) {
            console.error("Error starting quiz", error);
            alert("Failed to start quiz. Please try again.");
        }
    };

    console.log(topics)


    return (
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-6">
        <h1 className="text-3xl font-bold text-center text-indigo-800 mb-8">
            Select a Topic
        </h1>

        <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-5xl mx-auto mb-12">
            {topics?.map((topic) => (
            <div
                key={topic.id}
                className={`p-6 rounded-xl shadow-md cursor-pointer transition-all border-2 ${
                selectedTopic?.id === topic.id ? 'border-indigo-500 bg-white' : 'border-transparent bg-indigo-50'
                }`}
                onClick={() => setSelectedTopic(topic)}
            >
                <h2 className="text-xl font-semibold text-indigo-700 mb-2">{topic.name}</h2>
                <p className="text-gray-700 text-sm">{topic.description}</p>
            </div>
            ))}
        </div>

        {selectedTopic && (
            <div className="max-w-xl mx-auto bg-white p-6 rounded-xl shadow-lg">
            <h2 className="text-2xl font-bold text-indigo-700 mb-4">Quiz Setup</h2>

            <div className="mb-4">
                <label className="block mb-2 font-medium">Select Difficulty:</label>
                <div className="flex gap-2 flex-wrap">
                {DIFFICULTIES?.map((diff) => (
                    <button
                    key={diff}
                    onClick={() => toggleDifficulty(diff)}
                    className={`px-4 py-2 rounded-lg border ${
                        selectedDifficulties.includes(diff)
                        ? 'bg-indigo-600 text-white border-indigo-600'
                        : 'bg-white border-gray-300 text-gray-700'
                    }`}
                    >
                    {diff}
                    </button>
                ))}
                </div>
            </div>

            <div className="mb-4">
                <label className="block mb-1 font-medium">Number of Questions</label>
                <input
                type="number"
                min={1}
                max={50}
                value={questionCount}
                onChange={(e) => setQuestionCount(Number(e.target.value))}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-400"
                />
            </div>

            <div className="mb-4">
                <label className="block mb-1 font-medium">Duration (in minutes)</label>
                <input
                type="number"
                min={1}
                max={maxDuration}
                value={duration}
                onChange={(e) => setDuration(Number(e.target.value))}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-400"
                />
                <p className="text-sm text-gray-500 mt-1">
                Suggested: {maxDuration} min. You can reduce it but not increase it.
                </p>
            </div>

            <button
                onClick={handleStart}
                className="w-full mt-4 px-6 py-3 bg-indigo-600 text-white font-semibold rounded-lg hover:bg-indigo-700 transition-all"
                disabled={selectedDifficulties.length === 0 || questionCount <= 0}
            >
                Start Quiz
            </button>
            </div>
        )}
        </div>
    );
};

export default QuizSetupPage;
