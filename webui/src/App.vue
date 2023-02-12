<script setup lang="ts">
import TaskMenu from "@/components/TaskMenu.vue";
import Header from "@/components/Header.vue";
import {ElLoading} from "element-plus";

const loading = ElLoading.service({
  lock: true,
  text: "加载中",
  background: 'rgba(0, 0, 0, 0.7)'
})
loading.close()
</script>

<template>
  <div class="content">
    <el-container>
      <el-header class="header">
        <Header/>
      </el-header>
      <el-container>
        <el-aside class="aside">
          <TaskMenu/>
        </el-aside>
        <el-main class="main">
          <el-scrollbar>
            <RouterView/>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>

.aside {
  width: auto;
  overflow: hidden;
  display: flex;
  place-items: center;
  min-width: 64px;
}

.header {
  padding: 0;
  margin: 0;
}

.main {
  padding: 0;
  margin: 10px 0 0 10px;
}
</style>
