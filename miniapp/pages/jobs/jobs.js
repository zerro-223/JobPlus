const app = getApp();
Page({
  data: { keyword:'', city:'', list:[], page:1 },
  onLoad(opts) { this.setData({ keyword: opts.keyword||'', city: opts.city||'' }); this.load(); },
  onKw(e) { this.setData({ keyword: e.detail.value }); },
  onCity(e) { this.setData({ city: e.detail.value }); },
  search() { this.setData({ page:1 }); this.load(); },
  load() {
    app.request('/positions', 'GET', { keyword: this.data.keyword, city: this.data.city, page: this.data.page, size:20 })
      .then(res => this.setData({ list: res.list || [] }));
  },
  goDetail(e) { wx.navigateTo({ url: '/pages/detail/detail?id='+e.currentTarget.dataset.id }); }
});
