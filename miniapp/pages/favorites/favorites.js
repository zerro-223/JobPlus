const app = getApp();
Page({
  data: { list: [] },
  onShow() { this.load(); },
  load() { app.request('/favorites').then(list => this.setData({ list: list || [] })); },
  remove(e) {
    const id = e.currentTarget.dataset.id;
    app.request('/favorites/'+id, 'DELETE').then(() => {
      this.setData({ list: this.data.list.filter(x => x.id !== id) });
      wx.showToast({ title:'已取消', icon:'success' });
    });
  },
  goDetail(e) { wx.navigateTo({ url: '/pages/detail/detail?id='+e.currentTarget.dataset.id }); }
});
