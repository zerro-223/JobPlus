App({
  globalData: { user: null, apiBase: 'http://localhost:8080/api/v1' },
  onLaunch() {
    const user = wx.getStorageSync('user');
    if (user) this.globalData.user = user;
  },
  request(url, method='GET', data={}) {
    return new Promise((resolve, reject) => {
      wx.request({
        url: this.globalData.apiBase + url,
        method, data,
        header: { 'Content-Type': 'application/json' },
        withCredentials: true,
        success(res) {
          if (res.data.code === 200) resolve(res.data.data);
          else reject(res.data);
        },
        fail: reject
      });
    });
  }
});
