<template>
  <div style="display: block;width: 100%;">
    <el-row>
      <el-col :span="12">
        <el-statistic title="注册用户数" :value="userTotal"/>
      </el-col>
      <el-col :span="12">
        <el-statistic title="任务数" :value="tableTotal"/>
      </el-col>
    </el-row>
    <el-table infinite-scroll-delay="1000" :data="tableData" style="width: 100%;">
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column prop="name" label="账号"/>
      <el-table-column prop="last" label="上次执行时间"/>
      <el-table-column prop="task" label="任务名称"/>
      <el-table-column prop="status" label="任务状态"/>
    </el-table>
    <div style="display: flex;place-content: end;padding-right: 20px;padding-top: 10px">
      <el-pagination
          @current-change="loadData"
          :page-sizes="12"
          :hide-on-single-page="true"
          layout="prev, pager, next" :total="tableTotal"/>
    </div>
  </div>

</template>


<script setup lang="ts">
import {ref} from "vue";
import {taskTotal, taskView} from "@/request/task";

interface UserTaskData {
  name: any
  email: any
  last: any
  task: any
  status: any
}

const tableData = ref(new Array<UserTaskData>())
const tableTotal = ref(1)
const userTotal = ref(1)

const loadData = (page: number) => {
  taskView(page - 1).then((resp) => {
    if (resp.data.status == 0) {
      tableTotal.value = resp.data.data.total
      tableData.value = new Array<UserTaskData>()
      resp.data.data.list.forEach((d: UserTaskData) => {
        d.status = d.status == 0 ? "成功" : d.status == 1 ? "进行中" : "失败"
        tableData.value.push(d)
      })
    }
  })
  taskTotal().then((resp) => {
    if (resp.data.status == 0) {
      tableTotal.value = resp.data.data.task
      userTotal.value = resp.data.data.user
    }
  })
}

loadData(1)


</script>
