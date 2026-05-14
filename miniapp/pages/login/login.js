const app = getApp();
Page({
  data: { email:'', password:'' },
  onEmail(e) { this.setData({ email: e.detail.value }); },
  onPwd(e) { this.setData({ password: e.detail.value }); },
  login() {
    app.request('/user/login', 'POST', { email: this.data.email, password: this.data.password }).then(user => {
      app.globalData.user = user;
      wx.setStorageSync('user', user);
      wx.showToast({ title:'登录成功', icon:'success' });
      wx.navigateBack();
    }).catch(e => wx.showToast({ title: e.message||'登录失败', icon:'none' }));
  }
});
