const app = getApp();
Page({
  data: { job: null },
  onLoad(opts) {
    app.request('/positions/'+opts.id).then(job => this.setData({ job }));
  },
  deliver() {
    if (!app.globalData.user) { wx.navigateTo({ url: '/pages/login/login' }); return; }
    app.request('/deliveries', 'POST', { positionId: this.data.job.id, resumeId: 1 }).then(() => {
      wx.showToast({ title: '投递成功', icon: 'success' });
    }).catch(e => wx.showToast({ title: e.message||'投递失败', icon:'none' }));
  }
});
