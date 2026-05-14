const app = getApp();
Page({
  data: { resume: {} },
  onLoad() {
    app.request('/resume').then(r => this.setData({ resume: r || {} }));
  },
  onField(e) {
    const field = e.currentTarget.dataset.field;
    const value = e.detail.value;
    this.setData({ ['resume.'+field]: value });
  },
  save() {
    app.request('/resume','PUT', this.data.resume).then(() => wx.showToast({ title:'保存成功', icon:'success' }));
  }
});
