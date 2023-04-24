<template>
  <el-menu
      :default-active="pathNow"
      class="el-menu-horizontal"
      mode="horizontal"
      menu-trigger="click"
      @open="handleOpen"
      @close="handleClose"
      @select="handleSelect"
      :ellipsis="false"
      :router="true"
  >
    <el-row class="el-menu-h" justify="space-evenly" style="width: 100%">
      <el-col :span="6" class="el-content">
        <el-menu-item index="/mobile">
          <template #title>
            <div style="display: grid;place-items: center">
              <el-icon>
                <House/>
              </el-icon>
              <p style="margin: 0;line-height: normal">主页</p>
            </div>
          </template>
        </el-menu-item>
      </el-col>
      <el-col :span="6" class="el-content">
        <el-menu-item index="/mobile/task/create">
          <template #title>
            <div style="display: grid;place-items: center">
              <el-icon>
                <CirclePlus/>
              </el-icon>
              <p style="margin: 0;line-height: normal">添加任务</p>
            </div>
          </template>
        </el-menu-item>
      </el-col>
      <el-col :span="6" class="el-content">
        <el-menu-item index="/mobile/task/list">
          <template #title>
            <div style="display: grid;place-items: center">
              <el-icon>
                <Memo/>
              </el-icon>
              <p style="margin: 0;line-height: normal">任务列表</p>
            </div>
          </template>
        </el-menu-item>
      </el-col>
    </el-row>


  </el-menu>
</template>

<script setup lang="ts">


import {ref, watch} from 'vue'
import {
  Location,
  ArrowRight
} from '@element-plus/icons-vue'
import {useRoute} from "vue-router";
import router from "@/router";

const isCollapse = ref(false)
const collapseButtonIcon = ref(ArrowRight)
ref("el-menu-button-in");
const handleOpen = (key: string, keyPath: string[]) => {

  console.log(key, keyPath)
}
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const collapseMenu = () => {
  isCollapse.value = !isCollapse.value;
}

const route = useRoute()

const pathNow = ref("/")
if (route.path == "/") {
  router.push("/mobile")
}

watch(() => route.path, () => {
  pathNow.value = route.path
})


</script>

<style scoped>
.el-menu-horizontal {
  width: 100%;
}


.el-menu-h {
  display: flex;
  place-items: center;
  width: 100%;
  height: 100%;
}

.el-content {
  display: flex;
  place-content: center;
  min-height: 30px;
}
</style>