import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import UserTask from "@/views/UserTask.vue";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: '主页',
            component: HomeView
        },
        {
            path: '/task/create',
            name: '添加任务',
            component: HomeView
        },
        {
            path: '/task/list',
            name: '任务列表',
            component: UserTask
        }
    ]
})

export default router;
