const saveJSON = process.env.NODE_ENV === 'development' && process.env.APP_SAVE_JSON === 'true'
const context = process.env.VUE_APP_API
module.exports = {
  devServer: {
    // proxy: "http://192.168.137.1:9090/", //web
    proxy: "http://localhost:9999/", //web
    // proxy: "http://192.168.43.236:8089/", //web
    // proxy: "http://192.168.137.1:8089/", //web
    // proxy: "http://192.168.137.1:9091/", //后管
    // proxy: "http://192.168.43.236:8088/",//后管
    // port: 8080 //dev模式下的前端服务端口，也就是浏览器通过localhost：8085 访问前端页面
  },
  // 基本路径   整个文件夹在哪
  publicPath: './',
  // 输出文件目录   文件夹名
  // outputDir: 'dist',
  // productionSourceMap: false,
  // devServer: {
  //   proxy: {
  //     [context]: {
  //       target: 'http://localhost:9090/eweb/',
  //       // target: "http://192.168.137.1:9090/",
  //       // target: "http://12.4.6.42:9090/",
  //       changeOrigin: true,
  //       onProxyRes: saveJSON ? recordProxyJson : null,
  //       pathRewrite: {
  //         "^/api": ''
  //       }
  //     },
  //     https: true
  //   }
  // }
}