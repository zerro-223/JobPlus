module.exports = {
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: { '^/api': '/JobPlus/api' },
        // Tomcat returns JSESSIONID with path=/JobPlus but browser
        // is on localhost:8081 — rewrite the cookie path to /
        cookiePathRewrite: { '/JobPlus': '/' }
      }
    }
  },
  outputDir: '../src/main/webapp',
  publicPath: process.env.NODE_ENV === 'production' ? '/JobPlus/' : '/'
};
