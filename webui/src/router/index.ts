import {createRouter, createWebHistory} from 'vue-router'
import PCHomeView from "@/views/pc/PCHomeView.vue";
import PCUserTask from "@/views/pc/PCUserTask.vue";
import PCTaskAdd from "@/views/pc/PCTaskAdd.vue";
import MobileHomeView from "@/views/mobile/MobileHomeView.vue";
import MobileTaskAdd from "@/views/mobile/MobileTaskAdd.vue";
import MobileUserTask from "@/views/mobile/MobileUserTask.vue";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/pc',
            name: '主页',
            component: PCHomeView
        },
        {
            path: '/mobile',
            name: '统计',
            component: MobileHomeView
        },
        {
            path: '/pc/task/create',
            name: '添加任务',
            component: PCTaskAdd
        },
        {
            path: '/mobile/task/create',
            name: '添加',
            component: MobileTaskAdd
        },
        {
            path: '/pc/task/list',
            name: '任务列表',
            component: PCUserTask
        },
        {
            path: '/mobile/task/list',
            name: '任务',
            component: MobileUserTask
        }
    ]
})

export default router;
