# Vue学习过程

## 准备工作

由于小白的我是小白，从头开始讲起这个vue3。

在 Vue 项目开发中，npm、Vite 和 Node.js 是密切相关且各有其独特作用的，以下是它们之间的关系：

- **Node.js**
  - **基础环境** ：它是 JavaScript 运行时环境，允许在服务器端执行 JavaScript 代码。为 Vue 项目提供了运行所需的环境基础，使得前端开发者能够使用 JavaScript 在本地构建和运行项目，而不再局限于浏览器环境。
  - **包管理支持** ：Node.js 带有 npm（Node Package Manager），这是全球最大的 JavaScript 包管理器，Vue 项目中大量的依赖库和工具都是通过 npm 来进行安装和管理的，为项目开发提供了丰富的资源和强大的功能支持。
- **npm**
  - **包管理工具** ：npm 是 Vue 项目中默认的包管理工具，用于安装、更新、卸载和管理项目所需的依赖包。在 Vue 项目初始化时，通过 npm 安装 Vue 框架本身以及其他相关的依赖，如路由库、状态管理库等。
  - **项目构建与运行** ：npm 脚本可以定义和执行项目构建、开发服务器启动、测试等任务。例如，通过`npm run serve`命令启动开发服务器，`npm run build`命令进行项目构建等，这些脚本通常在项目的`package.json`文件中进行配置，方便开发者快速执行常见的项目操作。
- **Vite**
  - **构建工具** ：Vite 是一种现代的前端构建工具，它采用了全新的构建方式，利用原生 ES 模块热更新（HMR）等技术，极大地提高了开发过程中的构建速度和性能。在 Vue 项目中，Vite 可以替代传统的构建工具（如 Webpack）来加速项目开发，尤其是在项目较大、依赖较多时，Vite 的优势更加明显。
  - **开发服务器** ：Vite 自带开发服务器，能够快速启动并提供热更新功能。当开发者修改代码时，Vite 可以立刻将更新反映到浏览器中，无需等待整个项目重新构建，大大提高了开发效率。
  - **依赖优化** ：Vite 在开发过程中会对依赖进行优化处理，例如对静态资源的处理和加载进行了改进，使得项目加载速度更快，开发体验更加流畅。

三者共同协作，构成了 Vue 项目开发的基本技术栈。Node.js 提供运行环境和基础支持，npm 负责项目依赖的管理和部分任务执行，Vite 则专注于项目的快速构建和开发体验优化，通过合理配置和使用，可以高效地开发出高质量的 Vue 应用。

我们需要下载nvm，这是一个node的管理器，我们可以通过nvm去下载，切换node。

然后配置环境，这是一个很复杂容易搞混的，各位记得看教程看仔细一点

##　vue项目结构

初始化第一个vue项目了。

```powershell
npm install -g @vue/cli //下载
vue create blog-frontend //创建一个项目，叫做blog-frontend
npm install //下载对应模块依赖
npm run serve  //启动项目
```

```c
BLOG-FRONTEND  项目名称
├── node_modules node 模块
├── public 
├── src 主要操作的地方
│   ├── api 与后端相对应，请求后端的api
│   ├── assets 一些静态资源
│   ├── components 
│   ├── router 路由的地方，跳转通过的就是这个
│   ├── store 保存信息的地方
│   ├── utils 工具类，放一些请求过滤
│   ├── views 页面的主要地方
│   ├── App.vue 
│   └── main.js 
├── .env
├── .gitignore
├── babel.config.js
├── jsconfig.json
├── package-lock.json
├── package.json
├── README.md
└── vue.config.js
```

## vue代码和值得注意的地方

### src/main.js

```javascript
import { createApp } from 'vue'           // Vue 3 的应用创建函数
import App from './App.vue'                // 根组件
import router from './router'              // 路由实例
import { createPinia } from 'pinia'        // 状态管理
import ElementPlus from 'element-plus'     // UI 组件库
import 'element-plus/dist/index.css'       // Element Plus 的样式

const app = createApp(App)                 
app.use(router)           // 启用路由
app.use(createPinia())    // 启用 Pinia 状态管理
app.use(ElementPlus)      // 启用 Element Plus 组件库

app.mount('#app')
console.log('Vue app is initializing...')
console.log('Base URL:', process.env.BASE_URL)
```

### src/api/user.js

```javascript
import request from '@/utils/request'
    export const login = async (loginData) => {
        try {
          return await request.post(`/users/login`, {
            ...loginData,
          })  
        } catch (error) {
          return { 
            success: false, 
            message: error.response?.data?.message || '请求出错，请稍后再试' 
          }
        }
      }
  
  export const register = async (registerData) => {
    try {
      const response = await request.post(`/users/register`, registerData)
      if (response.data && response.data.code === 200) {
        return { success: true, message: '注册成功' }
      } else {
        return { success: false, message: response.data.message || '注册失败' }
      }
    } catch (error) {
      return { 
        success: false, 
        message: error.response?.data?.message || '请求出错，请稍后再试' 
      }
    }
  }
  
  export const getCaptcha1 = async () => {
    try {
        
        const response = await request.get(`/captcha/generate`)
        console.log('获取验证码响应:', response)
        // 如果响应数据包含 code 字段且值为 200，表示成功
        if (response.data && response.code === 200) {
            return { 
                success: true, 
                data: response.data, 
                sessionId: response.sessionId,
                message: '验证码获取成功' 
            }
        } else {
            return { 
                success: false, 
                message: response.data.message || '验证码获取失败' 
            }
        }
    } catch (error) {
      return { 
        success: false, 
        message: '获取验证码失败，请稍后再试' 
      }
    }
  }
```

正如这里所看到的，login和后面两个方法有带你不太一样 ，至于什么原因我也还在学习。我先讲一下后面的方法，request.get/post（/* / *），对应后端的api的请求，用response来获取到这个结果。比如后端传过来的是一个result的泛型，那么前端就能把后端获取到的Result<>转换为json文本，前端是没有实体这个概念的。如果响应数据包含 code 字段且值为 200，表示成功，做了一个简单的判断。如果符合判断，就赋予返回的对象一些属性。例如 const result = await getCaptcha1()，那么result.data=response.data，result.sessionId= response.sessionId。就像后端里面的对象转换，下面是属性。

### src/router/index.js

```javascript
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue')
  }
]

const router = createRouter({
  // 使用 process.env.BASE_URL 替代 import.meta.env.VUE_APP_BASE_URL
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

```

这里就是各个页面的跳转。比如基础路径下什么都没有就跳转到HomeView.vue

### src/store/use.js

```java
import { defineStore } from 'pinia'
import { ref } from 'vue'
import CryptoJS from 'crypto-js'

// 加密密钥，建议放在环境变量中
const SECRET_KEY = 'your-secret-key'

// 加密函数
const encrypt = (data) => {
  return CryptoJS.AES.encrypt(JSON.stringify(data), SECRET_KEY).toString()
}

// 解密函数
const decrypt = (encryptedData) => {
  try {
    const bytes = CryptoJS.AES.decrypt(encryptedData, SECRET_KEY)
    return JSON.parse(bytes.toString(CryptoJS.enc.Utf8))
  } catch (e) {
    console.error('解密失败:', e)
    return null
  }
}

export const useUserStore = defineStore('user', () => {
  const token = ref('')
  const userInfo = ref(null)
  
  // 初始化时从 sessionStorage 获取数据
  const initFromStorage = () => {
    const storedUserInfo = sessionStorage.getItem('userInfo')
    if (storedUserInfo) {
      try {
        const decryptedData = decrypt(storedUserInfo)
        if (decryptedData && decryptedData.expires > Date.now()) {
          userInfo.value = decryptedData.data
        } else {
          // 数据已过期，清除存储
          clearUserInfo()
        }
      } catch (e) {
        console.error('初始化用户数据失败:', e)
        clearUserInfo()
      }
    }
  }

  // 初始化
  initFromStorage()
  
  const setUserInfo = (userData) => {
    // 设置 token（建议通过后端设置 httpOnly cookie）
    token.value = userData.token
    
    // 设置用户信息，添加过期时间（例如24小时）
    const userDataWithExpiry = {
      data: userData.user,
      expires: Date.now() + 24 * 60 * 60 * 1000
    }
    
    // 加密后存储
    const encryptedData = encrypt(userDataWithExpiry)
    sessionStorage.setItem('userInfo', encryptedData)
    userInfo.value = userData.user
  }
  
  const clearUserInfo = () => {
    token.value = ''
    userInfo.value = null
    sessionStorage.removeItem('userInfo')
  }

  // 检查用户信息是否有效
  const isUserInfoValid = () => {
    return userInfo.value !== null
  }
  
  return {
    token,
    userInfo,
    setUserInfo,
    clearUserInfo,
    isUserInfoValid
  }
})
```

---

#### 1. **引入依赖**
```javascript
import { defineStore } from 'pinia'
import { ref } from 'vue'
import CryptoJS from 'crypto-js'
```

- 引入 Pinia 的 `defineStore` 来创建一个全局状态存储。
- 使用 Vue 的 `ref` 创建响应式数据。
- 引入 `crypto-js` 库用于加密和解密用户信息。

---

#### 2. **定义加密常量与函数**
```javascript
const SECRET_KEY = 'your-secret-key'

const encrypt = (data) => {
  return CryptoJS.AES.encrypt(JSON.stringify(data), SECRET_KEY).toString()
}

const decrypt = (encryptedData) => {
  try {
    const bytes = CryptoJS.AES.decrypt(encryptedData, SECRET_KEY)
    return JSON.parse(bytes.toString(CryptoJS.enc.Utf8))
  } catch (e) {
    console.error('解密失败:', e)
    return null
  }
}
```

- `SECRET_KEY`：用于加密/解密的密钥，**建议从环境变量读取**以提高安全性。
- `encrypt`：将传入的数据序列化为 JSON 字符串后进行 AES 加密，并返回字符串形式的加密结果。
- `decrypt`：对加密数据进行解密，若失败则捕获异常并返回 `null`。

---

#### 3. **创建 Pinia Store**
```javascript
export const useUserStore = defineStore('user', () => {
```

使用 Pinia 的 `defineStore` 创建名为 `user` 的 store，采用 Composition API 风格编写。

---

#### 4. **定义响应式状态**
```javascript
const token = ref('')
const userInfo = ref(null)
```

- `token`：保存用户的认证 token。
- `userInfo`：保存用户的基本信息对象。

---

#### 5. **初始化方法：从 `sessionStorage` 恢复数据**
```javascript
const initFromStorage = () => {
  const storedUserInfo = sessionStorage.getItem('userInfo')
  if (storedUserInfo) {
    try {
      const decryptedData = decrypt(storedUserInfo)
      if (decryptedData && decryptedData.expires > Date.now()) {
        userInfo.value = decryptedData.data
      } else {
        clearUserInfo()
      }
    } catch (e) {
      console.error('初始化用户数据失败:', e)
      clearUserInfo()
    }
  }
}
```

- 从浏览器 `sessionStorage` 中获取用户信息。
- 如果存在且未过期，则恢复到 `userInfo`。
- 若已过期或解密失败，调用 `clearUserInfo()` 清除无效数据。

---

#### 6. **设置用户信息方法**
```javascript
const setUserInfo = (userData) => {
  token.value = userData.token

  const userDataWithExpiry = {
    data: userData.user,
    expires: Date.now() + 24 * 60 * 60 * 1000
  }

  const encryptedData = encrypt(userDataWithExpiry)
  sessionStorage.setItem('userInfo', encryptedData)
  userInfo.value = userData.user
}
```

- 接收包含 `token` 和 `user` 的 `userData` 对象。
- 给用户信息添加过期时间（24 小时）。
- 加密后存入 `sessionStorage`，同时更新响应式状态。

---

#### 7. **清除用户信息方法**
```javascript
const clearUserInfo = () => {
  token.value = ''
  userInfo.value = null
  sessionStorage.removeItem('userInfo')
}
```

- 清空响应式状态中的 `token` 和 [userInfo](file://D:\project\blog\blog\blog-frontend\src\views\HomeView.vue#L8-L8)。
- 删除 `sessionStorage` 中的加密用户信息。

---

#### 8. **检查用户信息是否有效**
```javascript
const isUserInfoValid = () => {
  return userInfo.value !== null
}
```

- 返回当前是否有有效的用户信息。

---

#### 9. **导出状态与方法**
```javascript
return {
  token,
  userInfo,
  setUserInfo,
  clearUserInfo,
  isUserInfoValid
}
```

- 导出所有状态和操作方法，供组件或其他模块调用。

---

#### 总结

该文件实现了一个基于 Pinia 的用户状态管理模块，主要功能包括：

| 功能     | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| 状态管理 | 使用 Pinia 存储 `token` 和 [userInfo](file://D:\project\blog\blog\blog-frontend\src\views\HomeView.vue#L8-L8) |
| 持久化   | 使用 `sessionStorage` 存储加密后的用户信息                   |
| 安全性   | 数据加密后存储，防止敏感信息泄露                             |
| 自动恢复 | 页面刷新时自动恢复用户状态                                   |
| 过期控制 | 设置用户信息有效期，避免长期保留                             |

此设计适用于需要在客户端临时保存用户登录状态的场景。

### src/util/request.js

```javascript
import axios from 'axios';

const API_URL = 'http://localhost:8080';

const instantce = axios.create({
    baseURL: API_URL,
    timeout: 10000, // 设置请求超时时间为10秒
})

instantce.interceptors.request.use(config => {
    // 在发送请求之前做些什么
    return config;
}, error => {
    // 对请求错误做些什么
    return Promise.reject(error);
});

instantce.interceptors.response.use(response => {
    // 对响应数据做点什么
    return response.data;
}, error => {
    // 对响应错误做点什么
    return Promise.reject(error);

}); 


export default instantce;
```

### src/views/HomeView.vue

```javascript
<template>
  <div class="home-container">
    <div class="header">
      <div class="logo">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
          <path d="M19 22H5a3 3 0 0 1-3-3V3a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v12h4v4a3 3 0 0 1-3 3zm-1-5v2a1 1 0 0 0 2 0v-2h-2zM6 7v2h8V7H6zm0 4v2h8v-2H6zm0 4v2h5v-2H6z"/>
        </svg>
        <h1>博客系统</h1>
      </div>
      
      <div class="user-info" v-if="userInfo">
        <el-dropdown>
          <span class="user-dropdown">
            <el-avatar :size="36" :src="userInfo.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"></el-avatar>
            <span>{{ userInfo.username }}</span>
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>我的文章</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div v-else class="auth-buttons">
        <el-button type="primary" @click="goToLogin" class="login-btn">登录</el-button>
        <el-button @click="goToRegister" class="register-btn">注册</el-button>
      </div>
    </div>
    
    <div class="hero-section">
      <div class="hero-content">
        <h2>分享知识，连接思想</h2>
        <p class="subtitle">一个专注于技术分享与学习的平台</p>
        <div class="cta-buttons">
          <el-button type="primary" size="large" @click="goToArticles">浏览文章</el-button>
          <el-button size="large" @click="goToCreate" v-if="userInfo">写文章</el-button>
        </div>
      </div>
      <div class="hero-image">
        <div class="image-placeholder">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M21 8v12.993A1 1 0 0 1 20.007 22H3.993A.993.993 0 0 1 3 21.008V2.992C3 2.455 3.449 2 4.002 2h10.995L21 8zm-2 1h-5V4H5v16h14V9zM8 7h3v2H8V7zm0 4h8v2H8v-2zm0 4h8v2H8v-2z"/>
          </svg>
        </div>
      </div>
    </div>
    
    <div class="features">
      <div class="feature-card">
        <div class="icon">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M12 22C6.477 22 2 17.523 2 12S6.477 2 12 2s10 4.477 10 10-4.477 10-10 10zm0-2a8 8 0 1 0 0-16 8 8 0 0 0 0 16zm-1-5h2v2h-2v-2zm2-1.645V14h-2v-1.5a1 1 0 0 1 1-1 1.5 1.5 0 1 0-1.471-1.794l-1.962-.393A3.501 3.501 0 1 1 13 13.355z"/>
          </svg>
        </div>
        <h3>技术分享</h3>
        <p>分享你的技术经验和解决方案</p>
      </div>
      
      <div class="feature-card">
        <div class="icon">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M4 5v14h16V7h-8.414l-2-2H4zm8.414 0H21a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h7.414l2 2z"/>
          </svg>
        </div>
        <h3>知识沉淀</h3>
        <p>记录学习过程中的点点滴滴</p>
      </div>
      
      <div class="feature-card">
        <div class="icon">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M14 14.252V22H4a8 8 0 0 1 10-7.748zM12 13c-3.315 0-6-2.685-6-6s2.685-6 6-6 6 2.685 6 6-2.685 6-6 6zm6 4v-3h2v3h3v2h-3v3h-2v-3h-3v-2h3z"/>
          </svg>
        </div>
        <h3>社区互动</h3>
        <p>与志同道合的人交流讨论</p>
      </div>
    </div>
    
    <div class="footer">
      <div class="footer-content">
        <div class="footer-section">
          <h4>关于我们</h4>
          <p>致力于打造高质量的技术分享社区</p>
        </div>
        <div class="footer-section">
          <h4>联系方式</h4>
          <p>邮箱: contact@blog.com</p>
        </div>
        <div class="footer-section">
          <h4>关注我们</h4>
          <div class="social-icons">
            <el-icon :size="24"><ChatDotRound /></el-icon>
            <el-icon :size="24"><Pointer /></el-icon>
            <el-icon :size="24"><Link /></el-icon>
          </div>
        </div>
      </div>
      <div class="copyright">
        © 2024 博客系统 - 保留所有权利
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { computed } from 'vue'
import { ArrowDown, ChatDotRound, Pointer, Link } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)

const logout = () => {
  userStore.clearUserInfo()
  router.push('/login')
}

const goToLogin = () => {
  router.push('/login')
}

const goToRegister = () => {
  router.push('/register')
}

const goToArticles = () => {
  router.push('/articles')
}

const goToCreate = () => {
  router.push('/articles/create')
}
</script>
```

```javascript
<template>
  <div class="login-container">
    <div class="login-form">
      <div class="header">
        <div class="logo">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M19 22H5a3 3 0 0 1-3-3V3a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v12h4v4a3 3 0 0 1-3 3zm-1-5v2a1 1 0 0 0 2 0v-2h-2zM6 7v2h8V7H6zm0 4v2h8v-2H6zm0 4v2h5v-2H6z"/>
          </svg>
          <h1>博客系统</h1>
        </div>
        <p class="subtitle">分享你的知识与见解</p>
      </div>
      
      <el-form ref="loginForm" :model="form" :rules="rules" class="form-content">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="用户名"
            prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item prop="captcha">
          <div class="captcha-container">
            <el-input 
              v-model="form.captcha" 
              placeholder="验证码" 
              prefix-icon="Key"
              class="captcha-input"
            />
            <div class="captcha-image" @click="refreshCaptcha">
              <img v-if="captchaImage" :src="captchaImage" alt="验证码" />
              <div v-else class="captcha-placeholder">
                <el-icon><Refresh /></el-icon>
              </div>
            </div>
          </div>
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin"
            :loading="loading"
            class="login-btn"
          >
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="footer">
        <p>还没有账号? <span class="link" @click="goToRegister">立即注册</span></p>
        <p class="copyright">© 2024 博客系统 - 分享你的知识</p>
      </div>
    </div>
    
    <div class="decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'


import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'
import {  Refresh } from '@element-plus/icons-vue'
import {getCaptcha1, login} from '@/api/user'
const router = useRouter()
const userStore = useUserStore()

const form = ref({
  username: '',
  password: '',
  captcha: '',
  sessionId: ''
})

const captchaImage = ref('')
const loading = ref(false)

const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' }
  ]
})

// 获取验证码
const getCaptcha = async () => {
  const result = await getCaptcha1()
  captchaImage.value = result.data
  form.value.sessionId = result.sessionId
}
// 刷新验证码
const refreshCaptcha = () => {
  getCaptcha()
}

// 处理登录
const handleLogin = async () => {
  loading.value = true
  try {
    const result = await login({
      username: form.value.username,
      password: form.value.password,
      captcha: form.value.captcha,
      sessionId: form.value.sessionId
    })
    if (result.code === 200) {
      ElMessage.success('登录成功')
      // 保存用户信息到 store
      userStore.setUserInfo({
        token: result.data.token,
        user: result.data.user
      })
      router.push('/')
    } else {
      ElMessage.error(result.message)
      refreshCaptcha()
    }
  } catch (error) {
    ElMessage.error('登录失败，请稍后重试')
    refreshCaptcha()
  } finally {
    loading.value = false
  }
}

// 跳转到注册页面
const goToRegister = () => {
  router.push('/register')
}

onMounted(() => {
  refreshCaptcha()
})
</script>
```

### src/views/RegisterView.vue

```javascript
<template>
  <div class="register-container">
    <div class="register-form">
      <div class="header">
        <div class="logo">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M19 22H5a3 3 0 0 1-3-3V3a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v12h4v4a3 3 0 0 1-3 3zm-1-5v2a1 1 0 0 0 2 0v-2h-2zM6 7v2h8V7H6zm0 4v2h8v-2H6zm0 4v2h5v-2H6z"/>
          </svg>
          <h1>创建账号</h1>
        </div>
        <p class="subtitle">加入我们的知识社区</p>
      </div>
      
      <el-form ref="registerForm" :model="form" :rules="rules" class="form-content">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="用户名"
            prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="form.confirmPassword" 
            type="password" 
            placeholder="确认密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item prop="email">
          <el-input 
            v-model="form.email" 
            placeholder="邮箱"
            prefix-icon="Message"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleRegister"
            :loading="loading"
            class="register-btn"
          >
            <span v-if="!loading">注册账号</span>
            <span v-else>注册中...</span>
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="footer">
        <p>已有账号? <span class="link" @click="goToLogin">立即登录</span></p>
        <div class="agreement">
          <el-checkbox v-model="agreed">我已阅读并同意</el-checkbox>
          <span class="link">用户协议</span>和<span class="link">隐私政策</span>
        </div>
        <p class="copyright">© 2024 博客系统 - 分享你的知识</p>
      </div>
    </div>
    
    <div class="decoration">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
  email: ''
})

const agreed = ref(false)
const loading = ref(false)

const validatePassword = (rule, value, callback) => {
  if (value !== form.value.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' },
    { pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/, 
      message: '必须包含大小写字母和数字', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
  ]
})

// 处理注册
const handleRegister = async () => {
  if (!agreed.value) {
    ElMessage.warning('请阅读并同意用户协议和隐私政策')
    return
  }
  
  loading.value = true
  const result = await userStore.register({
    username: form.value.username,
    password: form.value.password,
    email: form.value.email
  })
  
  loading.value = false
  
  if (result.success) {
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } else {
    ElMessage.error(result.message)
  }
}

// 跳转到登录页面
const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #a777e3, #6e8efb);
  position: relative;
  overflow: hidden;
}

.register-form {
  width: 450px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 16px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
  z-index: 10;
  position: relative;
}

.header {
  text-align: center;
  margin-bottom: 30px;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  margin-bottom: 10px;
}

.logo svg {
  width: 36px;
  height: 36px;
  fill: #a777e3;
}

.logo h1 {
  font-size: 28px;
  color: #333;
  font-weight: 600;
}

.subtitle {
  color: #666;
  font-size: 16px;
  margin-top: 5px;
}

.form-content {
  margin-top: 20px;
}

.register-btn {
  width: 100%;
  height: 45px;
  font-size: 16px;
  margin-top: 10px;
}

.footer {
  margin-top: 25px;
  text-align: center;
  font-size: 14px;
  color: #666;
}

.link {
  color: #a777e3;
  cursor: pointer;
  font-weight: 500;
  transition: color 0.3s;
}

.link:hover {
  color: #8e5fd9;
  text-decoration: underline;
}

.agreement {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  margin: 15px 0;
  font-size: 13px;
}

.copyright {
  margin-top: 15px;
  font-size: 12px;
  color: #999;
}

.decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.shape {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
}

.shape-1 {
  width: 250px;
  height: 250px;
  top: -80px;
  left: -80px;
  border-radius: 30% 70% 70% 30% / 30% 30% 70% 70%;
  background: linear-gradient(45deg, rgba(255,255,255,0.1), rgba(255,255,255,0.2));
}

.shape-2 {
  width: 180px;
  height: 180px;
  bottom: -50px;
  right: 10%;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.15);
}

.shape-3 {
  width: 120px;
  height: 120px;
  top: 40%;
  right: 20%;
  border-radius: 40% 60% 60% 40% / 40% 40% 60% 60%;
  background: rgba(255, 255, 255, 0.1);
}
</style>
```

### src/views/HomeView.vue

```javascript
  <template>
    <div class="home-container">
      <div class="header">
        <div class="logo">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M19 22H5a3 3 0 0 1-3-3V3a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v12h4v4a3 3 0 0 1-3 3zm-1-5v2a1 1 0 0 0 2 0v-2h-2zM6 7v2h8V7H6zm0 4v2h8v-2H6zm0 4v2h5v-2H6z"/>
          </svg>
          <h1>博客系统</h1>
        </div>
        
        <div class="user-info" v-if="userInfo">
          <el-dropdown>
            <span class="user-dropdown">
              <el-avatar :size="36" :src="userInfo.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"></el-avatar>
              <span>{{ userInfo.username }}</span>
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item>我的文章</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <div v-else class="auth-buttons">
          <el-button type="primary" @click="goToLogin" class="login-btn">登录</el-button>
          <el-button @click="goToRegister" class="register-btn">注册</el-button>
        </div>
      </div>
      
      <div class="hero-section">
        <div class="hero-content">
          <h2>分享知识，连接思想</h2>
          <p class="subtitle">一个专注于技术分享与学习的平台</p>
          <div class="cta-buttons">
            <el-button type="primary" size="large" @click="goToArticles">浏览文章</el-button>
            <el-button size="large" @click="goToCreate" v-if="userInfo">写文章</el-button>
          </div>
        </div>
        <div class="hero-image">
          <div class="image-placeholder">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
              <path d="M21 8v12.993A1 1 0 0 1 20.007 22H3.993A.993.993 0 0 1 3 21.008V2.992C3 2.455 3.449 2 4.002 2h10.995L21 8zm-2 1h-5V4H5v16h14V9zM8 7h3v2H8V7zm0 4h8v2H8v-2zm0 4h8v2H8v-2z"/>
            </svg>
          </div>
        </div>
      </div>
      
      <div class="features">
        <div class="feature-card">
          <div class="icon">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 22C6.477 22 2 17.523 2 12S6.477 2 12 2s10 4.477 10 10-4.477 10-10 10zm0-2a8 8 0 1 0 0-16 8 8 0 0 0 0 16zm-1-5h2v2h-2v-2zm2-1.645V14h-2v-1.5a1 1 0 0 1 1-1 1.5 1.5 0 1 0-1.471-1.794l-1.962-.393A3.501 3.501 0 1 1 13 13.355z"/>
            </svg>
          </div>
          <h3>技术分享</h3>
          <p>分享你的技术经验和解决方案</p>
        </div>
        
        <div class="feature-card">
          <div class="icon">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
              <path d="M4 5v14h16V7h-8.414l-2-2H4zm8.414 0H21a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h7.414l2 2z"/>
            </svg>
          </div>
          <h3>知识沉淀</h3>
          <p>记录学习过程中的点点滴滴</p>
        </div>
        
        <div class="feature-card">
          <div class="icon">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
              <path d="M14 14.252V22H4a8 8 0 0 1 10-7.748zM12 13c-3.315 0-6-2.685-6-6s2.685-6 6-6 6 2.685 6 6-2.685 6-6 6zm6 4v-3h2v3h3v2h-3v3h-2v-3h-3v-2h3z"/>
            </svg>
          </div>
          <h3>社区互动</h3>
          <p>与志同道合的人交流讨论</p>
        </div>
      </div>
      
      <div class="footer">
        <div class="footer-content">
          <div class="footer-section">
            <h4>关于我们</h4>
            <p>致力于打造高质量的技术分享社区</p>
          </div>
          <div class="footer-section">
            <h4>联系方式</h4>
            <p>邮箱: contact@blog.com</p>
          </div>
          <div class="footer-section">
            <h4>关注我们</h4>
            <div class="social-icons">
              <el-icon :size="24"><ChatDotRound /></el-icon>
              <el-icon :size="24"><Pointer /></el-icon>
              <el-icon :size="24"><Link /></el-icon>
            </div>
          </div>
        </div>
        <div class="copyright">
          © 2024 博客系统 - 保留所有权利
        </div>
      </div>
    </div>
  </template>

  <script setup>
  import { useRouter } from 'vue-router'
  import { useUserStore } from '@/store/user'
  import { computed } from 'vue'
  import { ArrowDown, ChatDotRound, Pointer, Link } from '@element-plus/icons-vue'

  const router = useRouter()
  const userStore = useUserStore()

  const userInfo = computed(() => userStore.userInfo)

  const logout = () => {
    userStore.clearUserInfo()
    router.push('/login')
  }

  const goToLogin = () => {
    router.push('/login')
  }

  const goToRegister = () => {
    router.push('/register')
  }

  const goToArticles = () => {
    router.push('/articles')
  }

  const goToCreate = () => {
    router.push('/articles/create')
  }
  </script>

```

