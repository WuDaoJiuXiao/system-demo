<template>
    <div class="home">
        <img alt="Vue logo" src="../assets/image/logo.png">
        <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>
        <el-button class="login-btn" type="primary" @click="getUserInfo">获取登录用户信息</el-button>
        <div>{{nickname}}</div>
    </div>
</template>

<script lang="ts">
    import {defineComponent, ref} from 'vue';
    import {info} from "@/api/user";

    export default defineComponent({
        setup() {
            let nickname = ref();
            const getUserInfo = () => {
                info().then(res => {
                  nickname.value = res.data.nickname
                });
            };

            return {
              nickname,
              getUserInfo
            }
        }
    });
</script>
