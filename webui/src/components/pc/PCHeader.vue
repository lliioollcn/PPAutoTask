<template>
  <el-row class="el-row-content">
    <el-col :span="3">
      <!-- LOGO -->
      <div class="el-row-title">
        <h1>AutoTask</h1>
      </div>
    </el-col>
    <el-col :span="15">
      <!-- 标签 -->
      <div>
        <el-tabs
            v-model="editableTabsValue"
            closable
            class="el-row-tabs"
            @tab-remove="removeTab"
            @tab-change="tabChange"
        >
          <el-tab-pane
              v-for="item in editableTabs"
              :key="item.path"
              :label="item.name"
              :name="item.path"
              :closable=item.closable
          />
        </el-tabs>
      </div>

    </el-col>
    <el-col :span="6" style="margin: auto">
      <!-- 功能 -->
      <div class="el-row-avatar">
        <el-dropdown @command="handleCommand">
          <el-avatar :size="30" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"/>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">


import {ref, watch} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const handleCommand = (command: string | number | object) => {
  if (command === "logout") {
    localStorage.setItem("at_token", "")
    ElMessage({
      showClose: true,
      message: '退出成功',
    })
    setTimeout(() => {
      location.reload()
    }, 500)
  }
}

interface TabMenuData {
  name: any
  path: any
  closable: boolean
}

const editableTabsValue = ref("/")
let tabs = new Array<TabMenuData>()
tabs.push({
  name: "主页",
  path: "/pc",
  closable: false
})
const editableTabs = ref(tabs)
const route = useRoute()
const router = useRouter()

watch(() => route.path, () => {
  let has = false;
  editableTabs.value.forEach(v => {
    if (v.path == route.path) {
      has = true
    }
  })
  if (!has) {
    editableTabs.value.push({
      name: route.name,
      path: route.path,
      closable: true
    })
  }
  editableTabsValue.value = route.path
})

const removeTab = (targetName: string) => {
  if (targetName !== "/pc") {
    const tabs = editableTabs.value
    let activeName = editableTabsValue.value
    if (activeName === targetName) {
      let i = 0
      console.log(tabs.length)
      tabs.forEach(tab => {
        if (tab.path == targetName) {
          editableTabsValue.value = tabs[i - 1].path
        }
        i++
      })
    }
    editableTabs.value = tabs.filter((tab) => tab.path !== targetName)
  }
}

const tabChange = (name: string) => {
  router.push(name)
}


</script>

<style scoped>

.el-row-content {
  display: flex;
  position: relative;
  width: 100%;
}

.el-row-title {
  display: flex;
  place-content: center;
}

.el-row-tabs {
  position: absolute;
  bottom: 0;
  padding: 0;
  margin: 0;
}

.el-row-avatar {
  display: flex;
  place-content: center;
  margin-left: 250px;
}
</style>