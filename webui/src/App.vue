<script setup lang="ts">
import {RouterLink, RouterView} from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import TaskMenu from "@/components/TaskMenu.vue";
import Header from "@/components/Header.vue";
</script>

<template>
  <div class="content">
    <el-container>
      <el-header class="header">
        <Header @onHeaderRoute="onHeaderRoute"/>
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
}

.header {
  padding: 0;
  margin: 0;
}

.main {
  padding: 0;
  margin: 0;
}
</style>
