import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import zhLocale from 'element-ui/lib/locale/lang/zh-CN';
import App from './App.vue';
import router from './router';
import api from './utils/api';

Vue.use(ElementUI, { locale: zhLocale });
Vue.prototype.$api = api;
Vue.config.productionTip = false;

// Check login state from sessionStorage
const savedUser = sessionStorage.getItem('jobplus_user');
if (savedUser) {
  try { Vue.prototype.$user = JSON.parse(savedUser); } catch(e) {}
}

router.beforeEach((to, from, next) => {
  const user = Vue.prototype.$user;
  if (to.meta.requiresAuth && !user) {
    return next('/login');
  }
  if (to.meta.role && (!user || user.role !== to.meta.role)) {
    return next('/');
  }
  next();
});

new Vue({ router, render: h => h(App) }).$mount('#app');
