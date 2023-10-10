const {defineConfig} = require('@vue/cli-service');
const AutoImport = require('unplugin-auto-import/webpack');
const Components = require('unplugin-vue-components/webpack');
const {ElementPlusResolver} = require('unplugin-vue-components/resolvers');

module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,
    //配置请求跨域
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: {
            '/api': {
                target: 'http://localhost:8888',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/': '/'
                }
            }
        }
    },
    //按需引入ElementPlus
    configureWebpack: {
        plugins: [
            AutoImport({
                resolvers: [ElementPlusResolver({importStyle: false})],
            }),
            Components({
                resolvers: [ElementPlusResolver({importStyle: false})],
            })
        ]
    }
});
