import axios from './axiosInstance';

export const registerUser = async (data: {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  gender?: string;
}) => {
  return axios.post('/auth/register', data);
};

export const loginUser = async (data: {
  email: string;
  password: string;
}) => {
  return axios.post('/auth/login', data);
};

export const logoutUser = async () => {
  return axios.post('/auth/logout');
};
