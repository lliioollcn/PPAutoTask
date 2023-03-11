<template>
  <el-empty style="height: 100%" v-if="isEmpty" description="还没有任务哦，快去添加一个吧~"/>
  <div v-for="task in tasks" style="padding-left: 20px;padding-right: 20px;padding-top: 10px;">
    <div class="task-box" :style="{
          boxShadow: `var(--el-box-shadow)`,
          borderRadius: `var(--el-border-radius-round)`,
        }">
      <div class="task-box-content">
        <el-descriptions
            :border="true"
            :column="1">
          <el-descriptions-item label="任务名称:">{{ task.taskName }}</el-descriptions-item>
          <el-descriptions-item label="任务账号:">{{ task.account }}</el-descriptions-item>
          <el-descriptions-item label="任务状态:">
            {{ task.taskStatus == 0 ? "成功" : task.taskStatus == 1 ? "运行中" : "失败" }}
          </el-descriptions-item>
          <el-descriptions-item label="上次执行:">{{
              task.lastTime == 0 ? "从未执行" : new Date(task.lastTime).toLocaleString()
            }}
          </el-descriptions-item>

        </el-descriptions>
      </div>
      <div class="task-box-btn">
        <el-button type="success" :icon="CaretRight" circle @click="clickRun(task.id)"/>
        <el-button type="primary" :icon="Edit" circle/>
        <el-button type="info" :icon="More" circle @click="clickLog(task.id)"/>
        <el-button type="danger" :icon="Delete" circle/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

import {ref} from "vue";
import {taskLog, taskRun, userTasks} from "@/request/task";
import {CaretRight, Delete, Edit, More} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

interface UserTaskData {
  mid: any
  taskType: any
  id: any
  lastTime: any
  cookie: any
  taskStatus: boolean
  taskName: any
  account: any
}


const isEmpty = ref(true)

const tasks = new Array<UserTaskData>()

userTasks().then((resp) => {
  if (resp.data.status == 0) {
    resp.data.data.forEach((a: UserTaskData) => {
      tasks.push(a)
    })
    if (tasks.length > 0) {
      isEmpty.value = false
    }
  }
})

const clickRun = (id: any) => {
  taskRun(id).then((resp) => {
    ElMessage({
      showClose: true,
      message: '运行成功',

    })
  })
}

const clickLog = (id: any) => {
  taskLog(id).then((resp) => {
    ElMessageBox.alert(resp.data.data, '任务日志', {
      confirmButtonText: '关闭',
      dangerouslyUseHTMLString: true,
    })
  })
}
</script>

<style>
.el-scrollbar__view {
  height: 100%;
}

.task-box {
  width: 300px;
  display: block;
}

.task-box-btn {
  display: flex;
  place-content: end;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-right: 10px;
  width: auto;
  height: auto;
}

.task-box-content {
  display: flex;
  place-content: center;
  place-items: center;
  padding-top: 30px;
}
</style>
