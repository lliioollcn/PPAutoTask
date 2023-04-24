<template>
  <MobileView v-if="isMobile"></MobileView>
  <PCView v-if="!isMobile"></PCView>
</template>

<script setup lang="ts">

import {ref} from "vue";
import PCView from "@/views/pc/PCView.vue";
import MobileView from "@/views/mobile/MobileView.vue";

const isMobile = ref(false)

function _isMobile() {
  let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
  return flag;
}

if (_isMobile()) {
  isMobile.value = true
}


</script>
