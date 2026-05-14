const app = getApp();
Page({
  data: { list: [] },
  onShow() { this.load(); },
  load() { app.request('/deliveries').then(list => this.setData({ list: list || [] })); },
  goDetail(e) { wx.navigateTo({ url: '/pages/detail/detail?id='+e.currentTarget.dataset.id }); }
});
