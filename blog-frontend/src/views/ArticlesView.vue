<template>
  <div class="articles-container">
    <div class="header">
      <div class="logo">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
          <path d="M19 22H5a3 3 0 0 1-3-3V3a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v12h4v4a3 3 0 0 1-3 3zm-1-5v2a1 1 0 0 0 2 0v-2h-2zM6 7v2h8V7H6zm0 4v2h8v-2H6zm0 4v2h5v-2H6z"/>
        </svg>
        <h1>文章列表</h1>
      </div>
      <el-button type="primary" @click="goToCreate" v-if="userStore.userInfo">
        <el-icon><Plus /></el-icon>写文章
      </el-button>
    </div>

    <div class="articles-grid">
      <el-card v-for="article in articles" :key="article.id" class="article-card" @click="goToArticle(article.id)">
        <div class="article-cover">
          <el-image 
            :src="'http://localhost:8082' + article.coverImage"
            fit="cover"
            :preview-src-list="[]"
          >
            <template #error>
              <div class="image-placeholder">
                <el-icon><Picture /></el-icon>
              </div>
            </template>
          </el-image>
        </div>
        <div class="article-content">
          <h3 class="article-title">{{ article.title }}</h3>
          <p class="article-summary">{{ article.summary }}</p>
          <div class="article-meta">
            <span class="article-date">
              <el-icon><Calendar /></el-icon>
              {{ formatDate(article.createTime) }}
            </span>
            <span class="article-views">
              <el-icon><View /></el-icon>
              {{ article.viewCount || 0 }} 阅读
            </span>
          </div>
        </div>
      </el-card>
    </div>

    <div class="pagination" v-if="total > 0">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[12, 24, 36, 48]"
        :total="total"
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup name="ArticlesList">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'
import { Plus, Calendar, View, Picture } from '@element-plus/icons-vue'
import { getArticles } from '@/api/article'

const router = useRouter()
const userStore = useUserStore()

const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)

const loadArticles = async () => {
  try {
    const result = await getArticles({
      page: currentPage.value,
      size: pageSize.value
    })
    
    console.log('获取文章列表:', result)
    if (result.success) {
      articles.value = result.data.records
      total.value = result.data.total
    } else {
      ElMessage.error(result.message || '获取文章列表失败')
    }
  } catch (error) {
    console.error('获取文章列表失败:', error)
    ElMessage.error('获取文章列表失败')
  }
}

const handleSizeChange = (val) => {
  pageSize.value = val
  loadArticles()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  loadArticles()
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const goToArticle = (id) => {
  router.push(`/article/${id}`)
}

const goToCreate = () => {
  router.push('/article/create')
}

onMounted(() => {
  loadArticles()
})
</script>

<!-- 样式保持不变 -->

<style scoped>
.articles-container {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
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
  font-size: 28px;
  color: #333;
  font-weight: 600;
}

.articles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
  margin-bottom: 40px;
}

.article-card {
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.article-cover {
  height: 200px;
  overflow: hidden;
  border-radius: 8px;
  margin-bottom: 16px;
}

.article-cover .el-image {
  width: 100%;
  height: 100%;
}

.image-placeholder {
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
}

.article-content {
  padding: 0 4px;
}

.article-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-summary {
  font-size: 14px;
  color: #666;
  margin-bottom: 12px;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  color: #999;
}

.article-date, .article-views {
  display: flex;
  align-items: center;
  gap: 4px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

:deep(.el-button--primary) {
  background: #6e8efb;
  border-color: #6e8efb;
}

:deep(.el-button--primary:hover) {
  background: #5a7de0;
  border-color: #5a7de0;
}
</style>
