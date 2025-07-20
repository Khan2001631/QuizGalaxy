import axiosInstance from './axiosInstance';

export const registerUser = async (data: {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  gender?: string;
}) => {
  return axiosInstance.post('/auth/register', data);
};

export const loginUser = async (data: {
  email: string;
  password: string;
}) => {
  return axiosInstance.post('/auth/login', data);
};

export const logoutUser = async () => {
  return axiosInstance.post('/auth/logout');
};

export const getCurrentUser = async () => {
  const response = await axiosInstance.get("/user/me", {
    withCredentials: true, 
  });
  return response.data;
};

