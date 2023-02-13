<template>

</template>

<script>

import {ref, watch} from "vue";

export default {
  props: {
    'vid': String,
    'open': Boolean
  },
  methods: {},
  mounted() {
    watch(() => this.verifyToken, () => {
      this.$emit("onSuccess", this.verifyToken, this.verifyAddr)
    })
    watch(() => this.isCancel, () => {
      this.$emit("onCancel")
    })
  },
  setup(props, context) {
    const verifyAddr = ref("")
    const verifyToken = ref("")
    const isCancel = ref(false)
    let obj
    vaptcha({
      vid: props.vid,
      mode: 'invisible',
      scene: 0,
      container: '#VAPTCHAContainer',
      area: 'auto',
    }).then(function (VAPTCHAObj) {
      // 将VAPTCHA验证实例保存到局部变量中
      // 渲染验证组件
      obj = VAPTCHAObj;
      VAPTCHAObj.render();
      // 验证成功进行后续操作
      VAPTCHAObj.listen('pass', function () {
        const serverToken = VAPTCHAObj.getServerToken();
        verifyAddr.value = serverToken.server
        verifyToken.value = serverToken.token

      })

      VAPTCHAObj.listen('close', function () {
        isCancel.value = !isCancel.value
      })
    })

    watch(() => props.open, () => {
      if (props.open === true) {
        if (obj) {
          obj.validate();
        }
      } else {
        if (obj) {
          obj.reset();
        }
      }
    })

    return {
      verifyAddr,
      verifyToken,
      isCancel
    }
  }
}
</script>

<style scoped>

</style>