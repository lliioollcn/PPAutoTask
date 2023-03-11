<template>
  <el-empty style="height: 100%" v-if="isEmpty" description="还没有任务哦，耐心等管理员添加吧~"/>
  <div v-for="task in tasks" style="padding-left: 20px;padding-right: 20px;padding-top: 10px;">
    <el-dialog v-model="editShow" title="添加任务">
      <el-form :model="form">
        <el-form-item label="Token">
          <el-input v-model="form.cookie" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="editShow = false">取消</el-button>
        <el-button type="primary" @click="clickAddSubmit(editId)">
          添加
        </el-button>
      </span>
      </template>
    </el-dialog>
    <div class="task-box" :style="{
          boxShadow: `var(--el-box-shadow)`,
          borderRadius: `var(--el-border-radius-round)`,
        }">
      <div class="task-box-content">
        <el-descriptions
            :border="true"
            :column="1">
          <el-descriptions-item label="任务名称:">{{ task.taskName }}</el-descriptions-item>

        </el-descriptions>
      </div>
      <div class="task-box-btn">
        <el-button type="success" :icon="Plus" circle @click="clickAdd(task.taskType)"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

import {reactive, ref} from "vue";
import {sysTasks, taskAdd, taskDelete, taskEdit, taskLog, taskRun} from "@/request/task";
import {Plus} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

interface SysTaskData {
  taskType: any
  taskName: any
}


const isEmpty = ref(true)

const tasks = new Array<SysTaskData>()

sysTasks().then((resp) => {
  if (resp.data.status == 0) {
    resp.data.data.forEach((a: SysTaskData) => {
      tasks.push(a)
    })
    if (tasks.length > 0) {
      isEmpty.value = false
    }
  }
})

const editId = ref(0)
const editShow = ref(false)
const form = reactive({
  cookie: '',
})

const clickAdd = (id: any) => {
  editId.value = id
  editShow.value = true
}
const clickAddSubmit = (id: any) => {
  if (form.cookie.trim().length < 1) {
    ElMessageBox.alert("Token不能为空!", "保存失败", {
      confirmButtonText: "确定"
    })
  } else {
    taskAdd({
      "id": id,
      "cookie": form.cookie,
    }).then((resp) => {
      if (resp.data.status == 0) {
        editId.value = 0
        editShow.value = false
        ElMessage({
          showClose: true,
          message: '添加成功',
        })
      } else {
        editId.value = 0
        editShow.value = false
        ElMessage({
          showClose: true,
          message: resp.data.data,
        })
      }
    })
  }
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
