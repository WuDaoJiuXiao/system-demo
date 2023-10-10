<template>
    <div class="login-box">
        <el-form
                ref="ruleFormRef"
                :model="loginForm"
                status-icon
                :rules="rules"
                class="login-form"
        >
            <h3>VSS系统DEMO</h3>
            <el-form-item prop="username" class="submit-info">
                <el-input v-model="loginForm.username" prefix-icon="User" placeholder="用户名" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="loginForm.password" prefix-icon="Key" placeholder="密码" type="password"
                          autocomplete="off"/>
            </el-form-item>
            <el-form-item class="box-btn">
                <el-button class="login-btn" type="primary" @click="submitForm(ruleFormRef)">登 录</el-button>
                <el-button class="login-btn" type="info" @click="resetForm(ruleFormRef)">重 置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts">
    import {defineComponent, reactive, toRefs, ref} from 'vue';
    import {useRouter} from "vue-router";
    import {ElMessage} from "element-plus";
    import {UserLoginImpl} from "@/type/impl/userImpl";
    import type {FormInstance} from "element-plus";
    import {login} from "@/api/user";

    export default defineComponent({
        setup() {
            // 实例化登录对象
            const data = reactive(new UserLoginImpl());

            // 登录信息校验规则
            const rules = {
                username: [{required: true, message: "请输入用户名", trigger: 'blur'}, {
                    min: 3,
                    max: 10,
                    message: "用户名长度在3-10之间",
                    trigger: 'blur'
                }],
                password: [{required: true, message: "请输入密码", trigger: 'blur'}, {
                    min: 3,
                    max: 12,
                    message: "密码长度在3-12之间",
                    trigger: 'blur'
                }]
            };

            const ruleFormRef = ref<FormInstance>();
            const router = useRouter();

            // 提交表单
            const submitForm = (formEl: FormInstance | undefined) => {
                if (!formEl) return;
                formEl.validate((valid) => {
                    if (valid) {
                        login(data.loginForm).then((res) => {
                            if (res != undefined) {
                                // 登陆成功之后，将后端的 jwt 令牌存入 sessionStorage 中，每次请求都携带该令牌
                                let tokenHead = res.data.tokenHead;
                                let token = res.data.token;
                                let userToken = tokenHead + " " + token;
                                window.sessionStorage.setItem("userToken", userToken);
                                router.replace('/');
                            }
                        })
                    } else {
                        showError();
                        return false;
                    }
                })
            };

            //重置表单
            const resetForm = (formEl: FormInstance | undefined) => {
                if (!formEl) return;
                formEl.resetFields();
            };

            //显示登录失败的消息
            const showError = () => {
                ElMessage.error('登录失败，请检查登录信息');
            };

            return {...toRefs(data), rules, ruleFormRef, submitForm, resetForm};
        }
    });
</script>

<style lang="scss" scoped>
    .login-box {
        width: 100%;
        height: 100%;
        background: rgb(64, 64, 64) url("../assets/image/bg.png");
        background-size: cover; /* 让背景图基于容器大小伸缩 */
        padding: 1px; /* 防止登录表单和背景外边距重叠 */
        .login-form {
            width: 380px;
            margin: 12em auto;
            padding: 20px;
            border-radius: 20px;
            background: rgba(0, 0, 0, 0.45);
            box-sizing: border-box;
            box-shadow: 0 15px 25px rgba(0, 0, 0, 0.6);
        }

        .box-btn {
            margin: 5px 15px 0 15px;
        }

        .login-btn {
            margin: 5px 10px 0 10px;
            width: 43%;
        }

        h3 {
            margin: 0 0 20px;
            padding: 0;
            color: #d1f0ff;
            text-align: center;
        }

        /* 修改el-form默认的组件样式 */
        .el-input {
            margin: 0 15px 5px 15px;
            --el-input-placeholder-color: rgba(151, 182, 191, 0.7);
            --el-input-text-color: #ccdef1;
            --el-input-bg-color: #0221B4 opacity(0.2);
            --el-input-border-color: #2e45ac;
            --el-input-hover-border-color: #0057c4;
            --el-input-border-bottom: none;
        }
    }
</style>