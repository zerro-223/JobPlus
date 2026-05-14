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

// 使用 Vue.observable 做全局登录状态（响应式）
const globalState = Vue.observable({ user: null });
Vue.prototype.$global = globalState;

// 从 sessionStorage 恢复登录态
const savedUser = sessionStorage.getItem('jobplus_user');
if (savedUser) {
  try { globalState.user = JSON.parse(savedUser); } catch(e) {}
}

router.beforeEach((to, from, next) => {
  const user = globalState.user;
  if (to.meta.requiresAuth && !user) {
    return next('/login');
  }
  if (to.meta.role && (!user || user.role !== to.meta.role)) {
    return next('/');
  }
  next();
});

// 避免路由重复导航报错
const originalPush = router.push;
router.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (err.name !== 'NavigationDuplicated') throw err;
  });
};

new Vue({ router, render: h => h(App) }).$mount('#app');
