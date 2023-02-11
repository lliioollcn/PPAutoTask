<template>
  <el-menu
      default-active="2"
      class="el-menu-vertical"
      :collapse="isCollapse"
      menu-trigger="hover"
      @open="handleOpen"
      @close="handleClose"
  >
    <el-sub-menu index="1">
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>Navigator One</span>
      </template>
      <el-menu-item-group>
        <template #title><span>Group One</span></template>
        <el-menu-item index="1-1">item one</el-menu-item>
        <el-menu-item index="1-2">item two</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="Group Two">
        <el-menu-item index="1-3">item three</el-menu-item>
      </el-menu-item-group>
      <el-sub-menu index="1-4">
        <template #title><span>item four</span></template>
        <el-menu-item index="1-4-1">item one</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="2">
      <el-icon>
        <icon-menu/>
      </el-icon>
      <template #title>Navigator Two</template>
    </el-menu-item>
    <el-menu-item index="3" disabled>
      <el-icon>
        <document/>
      </el-icon>
      <template #title>Navigator Three</template>
    </el-menu-item>
    <el-menu-item index="4">
      <el-icon>
        <setting/>
      </el-icon>
      <template #title>Navigator Four</template>
    </el-menu-item>
  </el-menu>
  <el-button class="el-menu-button" :class="{'el-menu-button-in':!isCollapse}"
             :icon="collapseButtonIcon" size="small"
             circle
             @click="collapseMenu"/>
</template>

<script setup lang="ts">


import {ref} from 'vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting, ArrowRight, ArrowLeft
} from '@element-plus/icons-vue'

const isCollapse = ref(true)
const collapseButtonIcon = ref(ArrowRight)
const collapseButtonClass = ref("el-menu-button-in")
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const collapseMenu = () => {
  if (isCollapse.value) {
    //collapseButtonIcon.value = ArrowLeft
    //collapseButtonClass.value = "el-menu-button-in"
    isCollapse.value = false
  } else {
    isCollapse.value = true
    //collapseButtonClass.value = "el-menu-button-out"
    //collapseButtonIcon.value = ArrowRight
  }

}


</script>

<style scoped>
.el-menu-vertical {
  height: 100%;
}

.el-menu-button {
  margin-left: -12px;
  z-index: 0;
}

.el-menu-button-in {
  -webkit-animation-name: buttonRotateIn;
  -webkit-animation-duration: 500ms;
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