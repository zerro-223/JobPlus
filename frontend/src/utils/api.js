import axios from 'axios';

const api = axios.create({
  baseURL: '/api/v1',
  withCredentials: true,
  timeout: 15000
});

api.interceptors.response.use(
  res => res.data,
  err => {
    // 401 = 未登录 → 清状态跳登录
    if (err.response && err.response.status === 401) {
      sessionStorage.removeItem('jobplus_user');
      window.location.hash = '#/login';
    }
    const msg = err.response?.data?.message || err.message || '请求失败';
    return Promise.reject(new Error(msg));
  }
);

export default api;
