module.exports = {
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: { '^/api': '/JobPlus/api' }
      }
    }
  },
  outputDir: '../src/main/webapp',
  publicPath: '/JobPlus/'
};
