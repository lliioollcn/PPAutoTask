<template>
  <el-menu
      :default-active="pathNow"
      class="el-menu-vertical"
      :collapse="isCollapse"
      :collapse-transition="true"
      menu-trigger="hover"
      @open="handleOpen"
      @close="handleClose"
      @select="handleSelect"
      :router="true"
  >
    <el-menu-item index="/">
      <el-icon>
        <el-icon>
          <House/>
        </el-icon>
      </el-icon>
      <template #title>主页</template>
    </el-menu-item>
    <el-sub-menu disabled>
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>测试</span>
      </template>
      <el-menu-item-group>
        <template #title>这里面什么都没有哦</template>
      </el-menu-item-group>
    </el-sub-menu>

    <el-menu-item index="/task/create">
      <el-icon>
        <el-icon>
          <CirclePlus/>
        </el-icon>
      </el-icon>
      <template #title>添加任务</template>
    </el-menu-item>
    <el-menu-item index="/task/list">
      <el-icon>
        <el-icon>
          <Memo/>
        </el-icon>
      </el-icon>
      <template #title>任务列表</template>
    </el-menu-item>
  </el-menu>
  <el-button class="el-menu-button" :class="{'el-menu-button-in':!isCollapse}"
             :icon="collapseButtonIcon" size="small"
             circle
             @click="collapseMenu"/>
</template>

<script setup lang="ts">


import {ref, watch} from 'vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting, ArrowRight
} from '@element-plus/icons-vue'
import {useRoute} from "vue-router";

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


watch(() => route.path, () => {
  pathNow.value = route.path
})


</script>

<style scoped>
.el-menu-vertical {
  height: 100%;
  min-width: 64px;
}

.el-menu-button {
  margin-left: -12px;
  z-index: 0;
}

.el-menu-button-in {
  -webkit-animation-name: buttonRotateIn;
  -webkit-animation-duration: 100ms;
  -webkit-animation-iteration-count: 1;
  -webkit-animation-delay: 0s;
  transform: rotate(180deg);
}

@-webkit-keyframes buttonRotateIn {
  0% {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(90deg);
  }
  100% {
    transform: rotate(180deg);
  }
}
</style>