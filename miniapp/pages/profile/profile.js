const app = getApp();
Page({
  data: { user: null, email:'', password:'' },
  onShow() { this.setData({ user: app.globalData.user }); },
  onEmail(e) { this.setData({ email: e.detail.value }); },
  onPwd(e) { this.setData({ password: e.detail.value }); },
  login() {
    app.request('/user/login','POST',{email:this.data.email,password:this.data.password}).then(user => {
      app.globalData.user = user; wx.setStorageSync('user', user);
      this.setData({ user });
    }).catch(e => wx.showToast({ title:e.message||'登录失败', icon:'none' }));
  },
  logout() {
    app.globalData.user = null; wx.removeStorageSync('user');
    this.setData({ user: null, email:'', password:'' });
  },
  goResume() { wx.navigateTo({ url: '/pages/resume/resume' }); },
  goFavorites() { wx.navigateTo({ url: '/pages/favorites/favorites' }); },
  goDeliveries() { wx.navigateTo({ url: '/pages/deliveries/deliveries' }); }
});
