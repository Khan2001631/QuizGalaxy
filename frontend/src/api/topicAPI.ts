import axiosInstance from './axiosInstance';

export const createTopic = async (data: {
  name: string;
  description: string;
}) => {
  return axiosInstance.post('/topics', data, {
    withCredentials: true,
  });
};

export const getAllTopics = async () => {
  const response = await axiosInstance.get('/topics', {
    withCredentials: true,
  });
  return response.data;
};

