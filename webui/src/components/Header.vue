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
    <el-col :span="6">
      <!-- 功能 -->
      <div style="background: #F56C6C;height: 60px;"/>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">


import {ref, watch} from 'vue'
import {useRoute, useRouter} from "vue-router";

interface TabMenuData {
  name: any
  path: any
  closable: boolean
}

const editableTabsValue = ref("/")
let tabs = new Array<TabMenuData>()
tabs.push({
  name: "主页",
  path: "/",
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
  if (targetName !== "/") {
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
</style>