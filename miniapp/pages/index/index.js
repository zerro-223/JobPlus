const app = getApp();
Page({
  data: { keyword:'', categories:[], positions:[] },
  onLoad() {
    app.request('/positions/categories').then(cats => this.setData({ categories: cats }));
    app.request('/positions', 'GET', { page:1, size:10 }).then(res => this.setData({ positions: res.list || [] }));
  },
  onKeyword(e) { this.setData({ keyword: e.detail.value }); },
  search() { wx.navigateTo({ url: '/pages/jobs/jobs?keyword='+this.data.keyword }); },
  goCategory(e) { wx.navigateTo({ url: '/pages/jobs/jobs?categoryId='+e.currentTarget.dataset.id }); },
  goDetail(e) { wx.navigateTo({ url: '/pages/detail/detail?id='+e.currentTarget.dataset.id }); }
});
