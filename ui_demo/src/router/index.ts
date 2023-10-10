import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router';
import {ElMessage} from 'element-plus';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        meta: {
            isShow: false
        },
        component: () => import(/* webpackChunkName: "login" */ '../views/LoginView.vue')
    },
    {
        path: '/',
        name: 'home',
        component: () => import(/* webpackChunkName: "home" */ '../views/HomeView.vue')
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router

//路由守卫:未登录不能访问除登录页之外的其他页面
router.beforeEach((to, from, next) => {
    //如果访问的是登录页，直接放行
    if (to.path === '/login') return next();
    //其他情况下，检查是否有 jwt 令牌
    let userToken = window.sessionStorage.getItem("userToken");
    if (userToken == undefined) {
        ElMessage.error("尚未登录，请登录");
        router.replace("/login").then(r => {
        });
    }
    //直接放行
    next();
});
