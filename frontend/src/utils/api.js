import axios from 'axios';

const api = axios.create({
  baseURL: '/api/v1',
  withCredentials: true,
  timeout: 15000
});

api.interceptors.response.use(
  res => {
    if (res.data && res.data.code === 401) {
      sessionStorage.removeItem('jobplus_user');
      window.location.hash = '#/login';
    }
    return res.data;
  },
  err => {
    const msg = err.response?.data?.message || err.message || '请求失败';
    return Promise.reject(new Error(msg));
  }
);

export default api;
