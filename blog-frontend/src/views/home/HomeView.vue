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
                <el-dropdown-item @click="goToProfile">个人中心</el-dropdown-item>
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
            <el-button size="primary" @click="goToTags">浏览标签</el-button>
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
    router.push('/article/create')
  }

  const goToProfile = () => {
    router.push('/profile')
  }

  const goToTags = () => {
    router.push('/tags')
  }
  </script>

  <style scoped>
  .home-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px 0;
  }

  .logo {
    display: flex;
    align-items: center;
    gap: 12px;
  }

  .logo svg {
    width: 36px;
    height: 36px;
    fill: #6e8efb;
  }

  .logo h1 {
    font-size: 24px;
    font-weight: 600;
    color: #333;
  }

  .user-dropdown {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 20px;
    transition: background 0.3s;
  }

  .user-dropdown:hover {
    background: #f5f7fa;
  }

  .auth-buttons {
    display: flex;
    gap: 12px;
  }

  .login-btn {
    background: #6e8efb;
    border-color: #6e8efb;
  }

  .register-btn {
    background: #a777e3;
    border-color: #a777e3;
    color: white;
  }

  .hero-section {
    display: flex;
    align-items: center;
    gap: 50px;
    margin: 60px 0;
    padding: 30px 0;
  }

  .hero-content {
    flex: 1;
  }

  .hero-content h2 {
    font-size: 42px;
    font-weight: 700;
    line-height: 1.2;
    color: #333;
    margin-bottom: 20px;
  }

  .subtitle {
    font-size: 18px;
    color: #666;
    margin-bottom: 30px;
  }

  .cta-buttons {
    display: flex;
    gap: 15px;
  }

  .hero-image {
    flex: 1;
    display: flex;
    justify-content: center;
  }

  .image-placeholder {
    width: 400px;
    height: 300px;
    background: linear-gradient(135deg, #6e8efb, #a777e3);
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .image-placeholder svg {
    width: 150px;
    height: 150px;
    fill: rgba(255, 255, 255, 0.8);
  }

  .features {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 30px;
    margin: 60px 0;
  }

  .feature-card {
    background: white;
    border-radius: 12px;
    padding: 30px;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.05);
    text-align: center;
    transition: transform 0.3s, box-shadow 0.3s;
  }

  .feature-card:hover {
    transform: translateY(-10px);
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  }

  .icon {
    width: 70px;
    height: 70px;
    background: rgba(110, 142, 251, 0.1);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 20px;
  }

  .icon svg {
    width: 36px;
    height: 36px;
    fill: #6e8efb;
  }

  .feature-card h3 {
    font-size: 20px;
    margin-bottom: 15px;
    color: #333;
  }

  .feature-card p {
    color: #666;
    line-height: 1.6;
  }

  .footer {
    margin-top: auto;
    background: #f9fafc;
    border-radius: 16px;
    padding: 40px 20px;
    margin-top: 60px;
  }

  .footer-content {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 40px;
    max-width: 1200px;
    margin: 0 auto;
  }

  .footer-section {
    text-align: center;
  }

  .footer-section h4 {
    font-size: 18px;
    margin-bottom: 15px;
    color: #333;
  }

  .footer-section p {
    color: #666;
    line-height: 1.6;
  }

  .social-icons {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-top: 15px;
  }

  .copyright {
    text-align: center;
    margin-top: 40px;
    padding-top: 20px;
    border-top: 1px solid #eee;
    color: #999;
    font-size: 14px;
  }
  </style>