<script setup lang="ts">
import {RouterLink, RouterView} from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import TaskMenu from "@/components/TaskMenu.vue";
</script>

<template>
  <div class="content">
    <el-container>
      <el-header style="background: #2c3e50">Header</el-header>
      <el-container>
        <el-aside class="aside">
          <TaskMenu/>
        </el-aside>
        <el-main style="background: aquamarine">
          <RouterView/>
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

</style>
