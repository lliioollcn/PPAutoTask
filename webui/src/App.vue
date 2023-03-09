<template>
  <div class="content">
    <el-container>
      <!-- 验证界面 -->
      <el-dialog
          v-model="isLogin"
          title="登录"
          :show-close="false"
          :draggable="true"
          :close-on-press-escape="false"
          :close-on-click-modal="false"
          :close-on-hash-change="false"
          width="500px">
        <el-form :model="form">
          <el-form-item>
            <el-input v-model="form.email"
                      autocomplete="off"
                      maxlength="200"
                      placeholder="邮箱"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.pass"
                      autocomplete="off"
                      maxlength="200"
                      placeholder="密码"
                      show-password/>
          </el-form-item>
          <el-form-item>
            <VaptchaVerify @onSuccess="vaptchaSuccess" @onCancel="vaptchaCancel" vid="5e39249176cb1970819eab8d"
                           :open="openVerify"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickLogin">
          登录
        </el-button>
      </span>
        </template>
      </el-dialog>

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

<script setup lang="ts">
import TaskMenu from "@/components/TaskMenu.vue";
import Header from "@/components/Header.vue";
import VaptchaVerify from "@/components/VaptchaVerify.vue";
import {ElLoading, ElMessageBox} from "element-plus";
import {authLogin, authStatus} from "@/request/auth";
import {reactive, ref} from "vue";


const loading = ElLoading.service({
  lock: true,
  text: "加载中",
  background: 'rgba(0, 0, 0, 0.7)'
})
const isLogin = ref(false)
const isRegister = ref(false)
const isAuth = ref(false)
const openVerify = ref(false)

const form = reactive({
  email: '',
  pass: '',
  passAgain: '',
})

const tryAuthStatus = () => {
  authStatus().then((resp) => {
    if (resp.data.status == 0) {
      isLogin.value = false
      isRegister.value = false
      isAuth.value = true
    } else {
      isLogin.value = true
      isRegister.value = false
      isAuth.value = false
      if (resp.data.status != 1001) {
        ElMessageBox.alert(resp.data.msg, "错误", {
          confirmButtonText: "确定"
        })
      }
    }
    loading.close()

  }).catch((error) => {
    if (error.response) {
      // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
      console.log(error.response.data);
      console.log(error.response.status);
      console.log(error.response.headers);
    }
    ElMessageBox.alert("服务器错误", "错误", {
      confirmButtonText: "确定"
    })
    loading.close()
  })
}

tryAuthStatus()

const clickLogin = () => {
  openVerify.value = true
}

const vaptchaCancel = () => {
  openVerify.value = false
}
const vaptchaSuccess = (token: string, server: string) => {
  authLogin({
    'email': form.email,
    'token': token,
    'server': server,
    'passWord': form.pass,
  }).then((resp) => {
    openVerify.value = false
    if (resp.data.status == -1) {
      ElMessageBox.alert("登录失败，原因:" + resp.data.msg, "登录失败", {
        confirmButtonText: "确定"
      })
    } else {
      localStorage.setItem("at_token", resp.data.data.at_token)
      location.reload()
    }
  })

}
</script>


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
