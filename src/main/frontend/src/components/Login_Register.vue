<template>
    <div class="container">
      <div class="left-panel" :class="{ 'login-view': isLogin, 'register-view': !isLogin }">
        <div class="prompt">
          <h2 v-if="isLogin" style="margin-bottom: 20px;font-size: 28px;">没有账号？</h2>
          <h2 v-else style="margin-bottom: 20px;font-size: 28px;">已有账号？</h2>
          <p v-if="isLogin" style="margin-bottom: 10px;">立即注册，开启你的聊天之旅！</p>
          <p v-else style="margin-bottom: 10px;">请使用账号登录,访问所有功能！</p>
          <button @click="toggleView" class="toggle-btn">
            {{ isLogin ? '去注册' : '去登录' }}
          </button>
        </div>
      </div>
  
      <div class="right-panel">
        <div v-if="!isLogin" class="form-container register-form">
          <h2 style="text-align: center;margin-bottom: 10px;font-size: 28px; color:white">用户注册</h2>
          <form @submit.prevent="handleRegister">
            <input type="text" v-model="username" placeholder="用户名" />
            <input type="password" v-model="password" placeholder="密码" />
            <input type="password" v-model="confirmPassword" placeholder="确认密码" />
            <div class="block">
              <el-slider  v-model="value" @input="updateButtonState" :show-tooltip="false" width="30px"></el-slider>
            </div>
            <button type="submit" class="submit-btn">注册</button>
          </form>
        </div>
  
        <div v-else class="form-container login-form">
          <h2 style="text-align: center;margin-bottom: 10px;font-size: 28px; color:white">用户登录</h2>
          <form @submit.prevent="handleLogin">
            <input type="text" v-model="username" placeholder="用户名" />
            <input type="password" v-model="password" placeholder="密码" />
            <div class="block">
              <el-slider v-model="value" @input="updateButtonState" :show-tooltip="false" width="30px"></el-slider>
            </div>
            <button type="submit" class="submit-btn">登录</button>
          </form>
        </div>
      </div>
  
    </div>
  </template>
  
  <script>
  import { useRouter } from 'vue-router';
  import axios from 'axios';

  export default {
    name: 'Login_Register',
    setup() {
    const router = useRouter()
    return { router }
    },
    data() {
      return {
        isLogin: true,
        username: "",
        password: "",
        confirmPassword: "",
        captcha: "",
        rememberPassword: false,
        showSuccessMessage: false,
      };
    },
    methods: {
      toggleView() {
        this.username = "";
        this.password = "";
        this.confirmPassword = "";
        localStorage.removeItem('rememberedUsername');
        localStorage.removeItem('rememberedPassword');
        this.isLogin = !this.isLogin;
      },
      async handleRegister() {
        if (this.username && this.password && this.password === this.confirmPassword) {
        try {
          const response = await axios.post('/api/user/register', {
            username: this.username,
            password: this.password
          },1000);
          if (response.data.success) {
            alert("注册成功");
            this.showSuccessMessage = true;
            setTimeout(() => {
              this.showSuccessMessage = false;
              this.isLogin = true;
              this.resetForm()
            },2000);
            this.username = "";
            this.password = "";
            this.confirmPassword = "";
          }
        } catch (error) {
          alert(response.data.msg);
        }
      }else {
        alert("请填写完整信息并且密码一致！");
      }
    },
    async handleLogin() {
      if (this.username && this.password) {
        try {
          const response = await axios.post('/api/user/login', {
            username: this.username,
            password: this.password
          });

          if (response.data.success) {
            alert("登录成功");
            router.push('/chatWindow');
            if (this.checked) {
              localStorage.setItem('rememberedUsername', this.username);
              localStorage.setItem('rememberedPassword', this.password);
            } else {
              localStorage.removeItem('rememberedUsername');
              localStorage.removeItem('rememberedPassword');
            }
            this.loginBoxVisible = false;
          } else {
            alert(response.data.msg);
          }
        } catch (error) {
          alert(response.data.msg);
          console.error(error);
        }
      } else {
        alert("请填写完整信息！");
      }
    },
    },
  };
  </script>
  
  <style scoped>
  .toggle-btn{
    margin-top: 10px;
  }
  .container {
    display: flex;
    height: 100vh;
    justify-content: center;
    align-items: center;
    background-color: rgb(240, 248, 255);
  }
  
  .left-panel {
    height: 50%;
    width: 25%;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.5s ease;
    border-top-left-radius: 10px;
    border-bottom-left-radius: 10px;
  }
  
  .login-view {
    background-color:rgb(202, 233, 255);
  }
  
  .register-view {
    background-color:rgb(202, 233, 255);
  }
  
  .prompt {
    text-align: center;
    color: white;
    font-family: '仿宋';
  }
  
  .prompt h2 {
    margin-bottom: 10px;
  }
  
  .prompt button {
    background-color: transparent;
    color: white;
    border: 2px solid white;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
  }
  
  .right-panel {
    height: 50%;
    width: 25%;
    background-color:rgb(255, 236, 214);
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-right-radius: 10px;
    border-bottom-right-radius: 10px;
  }
  
  .form-container {
    width: 70%;
  }
  
  form h2 {
    color: white;
    margin-bottom: 20px;
  }
  
  input {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    background-color:rgb(251, 216, 180);
    color: white;
    border: none;
    border-radius: 5px;
  }
  
  .remember-password {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    width: 100%;
  }
  
  .submit-btn {
    width: 50%;
    padding: 10px;
    background: linear-gradient(135deg,rgb(223, 240, 253), rgb(202, 233, 255));
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    margin-left: 70px;
  }
  
  .success-message {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: linear-gradient(135deg, #133c6d, #073c3f);
    color: white;
    padding: 20px;
    border-radius: 5px;
    text-align: center;
    animation: fadeInOut 0.5s ease-in-out;
  }
  
    .disabled-button {
      cursor: not-allowed;
    }
  
    .custom-slider {
    width: 100%;
    -webkit-appearance: none;
    appearance: none;
    height: 15px;
    border-radius: 5px;
    background:black;
    outline: none;
    opacity: 0.7;
    transition: opacity .2s;
  }
  
  @keyframes fadeInOut {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }
  </style>
  