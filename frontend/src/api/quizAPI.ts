import axiosInstance from "./axiosInstance";

export const startQuiz = async (quizConfig: {
  topicIds: number[];
  difficultyLevels: string[]; // e.g. ['EASY', 'MEDIUM', 'HARD']
  totalQuestions: number;
  allowSkipping: boolean;
}) => {
  return axiosInstance.post('/quiz/start', quizConfig);
};

export const finishQuiz = async (payload: {
  sessionId: number;
  answers: {
    questionId: number;
    selectedOptionId: number | null; // null if skipped
  }[];
}) => {
  return axiosInstance.post('/quiz/finish', payload);
};