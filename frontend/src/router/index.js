import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/views/Home';
Vue.use(Router);

export default new Router({
  mode: 'hash',
  routes: [
    { path: '/', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: () => import('@/views/Login') },
    { path: '/register', name: 'Register', component: () => import('@/views/Register') },
    { path: '/jobs', name: 'JobList', component: () => import('@/views/JobList') },
    { path: '/jobs/:id', name: 'JobDetail', component: () => import('@/views/JobDetail') },
    { path: '/company/:id', name: 'CompanyDetail', component: () => import('@/views/CompanyDetail') },
    { path: '/profile', name: 'Profile', component: () => import('@/views/Profile'), meta: { requiresAuth: true } },
    { path: '/resume', name: 'Resume', component: () => import('@/views/Resume'), meta: { requiresAuth: true } },
    { path: '/deliveries', name: 'Deliveries', component: () => import('@/views/Deliveries'), meta: { requiresAuth: true } },
    { path: '/favorites', name: 'Favorites', component: () => import('@/views/Favorites'), meta: { requiresAuth: true } },
    { path: '/notifications', name: 'Notifications', component: () => import('@/views/Notifications'), meta: { requiresAuth: true } },
    { path: '/employer', name: 'Employer', component: () => import('@/views/EmployerDashboard'), meta: { requiresAuth: true, role: 2 } },
    { path: '/admin', name: 'Admin', component: () => import('@/views/AdminDashboard'), meta: { requiresAuth: true, role: 3 } },
  ]
});
