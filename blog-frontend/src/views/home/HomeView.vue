  <template>
    <div class="home-container">
      <div :class="['navbar', { 'navbar-solid': navbarSolid }]">
        <div class="navbar-logo">博客系统</div>
        <div class="navbar-links">
          <span class="nav-link" @click="goToHome">首页</span>
          <span class="nav-link" @click="goToArticles">文章</span>
          <span class="nav-link" @click="goToTags">标签</span>
          <span class="nav-link" @click="goToAbout">关于我们</span>
          <span class="nav-link" v-if="!userInfo" @click="goToLogin">登录</span>
          <span class="nav-link" v-if="!userInfo" @click="goToRegister">注册</span>
          <el-dropdown v-if="userInfo" class="nav-avatar-dropdown">
            <span class="nav-avatar">
              <el-avatar :size="32" :src="userInfo.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"></el-avatar>
              <span class="nav-username">{{ userInfo.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToProfile">个人中心</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      
      
      <div class="hero-section">
        <div class="hero-content">
          <h2>分享知识，连接思想</h2>
          <p class="subtitle">一个专注于技术分享与学习的平台</p>
          <div id="text-container">
            {{ currentText }}<span class="type-cursor"></span>
          </div>
          <div class="cta-buttons">
            <el-button type="primary" size="large" @click="goToArticles">浏览文章</el-button>
            <el-button size="large" @click="goToCreate" v-if="userInfo">写文章</el-button>
            <el-button type="primary" size="large" @click="goToTags">浏览标签</el-button>
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
        
        <div class="feature-card" @click="goToCommunity" style="cursor:pointer;">
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
  import { computed, ref, onMounted, onUnmounted } from 'vue'
  import {  ChatDotRound, Pointer, Link } from '@element-plus/icons-vue'

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

  function goToHome() {
    router.push('/')
  }

  function goToAbout() {
    // 这里可以跳转到关于我们页面，暂时用 alert 占位
    alert('关于我们：这是一个专注于技术分享的博客平台。')
  }

  function goToCommunity() {
    router.push('/community')
  }

  const navbarSolid = ref(false)

  function handleScroll() {
    navbarSolid.value = window.scrollY > 30
  }

  onMounted(() => {
    window.addEventListener('scroll', handleScroll)
  })
  onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll)
  })

  // 打字机动画
  const currentText = ref('')
  const messages = [
    '欢迎来到小技巧日常分享',
    '这里有各种前端开发小技巧',
    '分享HTML、CSS、JavaScript等知识'
  ]
  let currentIndex = 0
  let charIndex = 0
  let typing = true
  let timer = null

  function typeWriter() {
    if (typing) {
      if (charIndex < messages[currentIndex].length) {
        currentText.value += messages[currentIndex][charIndex]
        charIndex++
        timer = setTimeout(typeWriter, 80)
      } else {
        typing = false
        timer = setTimeout(typeWriter, 1200) // 停留一会再删除
      }
    } else {
      if (charIndex > 0) {
        currentText.value = currentText.value.slice(0, -1)
        charIndex--
        timer = setTimeout(typeWriter, 40)
      } else {
        typing = true
        currentIndex = (currentIndex + 1) % messages.length
        timer = setTimeout(typeWriter, 300)
      }
    }
  }

  onMounted(() => {
    timer = setTimeout(typeWriter, 600)
  })
  onUnmounted(() => {
    clearTimeout(timer)
  })
  </script>

  <style scoped>
  .home-container {
    width: 100vw;
    min-width: 0;
    max-width: none;
    margin: 0;
    /* padding: 20px; */
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background: url('../../assets/bgd.png') center center/cover no-repeat;
    position: relative;
  }

  .home-container::before {
    content: '';
    position: absolute;
    left: 0; top: 0; right: 0; bottom: 0;
    /* background: rgba(255,255,255,0.7);
    z-index: 0; */
    pointer-events: none;
  }

  .home-container > * {
    position: relative;
    z-index: 1;
  }

  .navbar {
    width: 100%;
    height: 60px;
    background: rgba(255,255,255,0.0);
    box-shadow: 0 2px 8px rgba(110,142,251,0.08);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 40px;
    position: sticky;
    top: 0;
    z-index: 100;
    transition: background 0.4s cubic-bezier(.4,0,.2,1), box-shadow 0.4s cubic-bezier(.4,0,.2,1);
  }

  .navbar-solid {
    background: #fff;
    box-shadow: 0 2px 8px rgba(110,142,251,0.08);
  }

  .navbar-logo {
    font-size: 22px;
    font-weight: bold;
    color: #6e8efb;
    letter-spacing: 2px;
  }

  .navbar-links {
    display: flex;
    gap: 28px;
  }

  .nav-link {
    font-size: 16px;
    color: #333;
    cursor: pointer;
    transition: color 0.2s;
    position: relative;
  }

  .nav-link:hover {
    color: #6e8efb;
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

  .hero-section {
    display: flex;
    align-items: center;
    gap: 50px;
    margin: 60px 0;
    padding: 30px 0;
  }

  .hero-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    
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

  .subtitle {
    font-size: 18px;
    color: #666;
    margin-bottom: 30px;
  }

  .cta-buttons {
    display: flex;
    gap: 15px;
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
    border-radius: 0;
    padding: 40px 20px;
    margin-top: 60px;
    max-width: none;
    width: 100%;
  }

  .footer-content {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 40px;
    max-width: none !important;
    width: 100%;
    margin: 0;
    padding: 0 0 40px 0;
    box-sizing: border-box;
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

  .nav-avatar-dropdown {
    display: flex;
    align-items: center;
  }
  .nav-avatar {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    padding: 0 8px;
  }
  .nav-username {
    font-size: 15px;
    color: #333;
  }

  #text-container {
    font-size: 24px;
    color: #333;
    min-height: 32px;
    margin-bottom: 18px;
    letter-spacing: 1px;
    white-space: pre-wrap;
    height: 32px;
    display: flex;
    align-items: center;
    width: auto;
    max-width: 100%;
    box-sizing: border-box;
    overflow-wrap: break-word;
    word-break: break-all;
    overflow: visible;
  }
  .type-cursor {
    display: inline-block;
    width: 2px;
    height: 1.2em;
    background: #6e8efb;
    margin-left: 2px;
    animation: blink-cursor 0.8s steps(1) infinite;
    vertical-align: middle;
  }
  @keyframes blink-cursor {
    0%, 100% { opacity: 1; }
    50% { opacity: 0; }
  }
  </style>