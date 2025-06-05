<template>
  <div class="article-content-container">
    <div class="article-header">
      <div class="back-button" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
        返回列表
      </div>
      <div class="article-actions" v-if="isAuthor">
        <el-button type="primary" @click="goToEdit">
          <el-icon><Edit /></el-icon>
          编辑文章
        </el-button>
        <el-button type="danger" @click="handleDelete">
          <el-icon><Delete /></el-icon>
          删除文章
        </el-button>
      </div>
    </div>

    <div class="article-main">
      <h1 class="article-title">{{ article.title }}</h1>
      
      <div class="article-meta">
        <span class="meta-item">
          <el-icon><User /></el-icon>
          {{ article.author?.username || '未知作者' }}
        </span>
        <span class="meta-item">
          <el-icon><Calendar /></el-icon>
          {{ formatDate(article.createTime) }}
        </span>
        <span class="meta-item">
          <el-icon><View /></el-icon>
          {{ article.viewCount || 0 }} 阅读
        </span>
      </div>

      <div class="article-cover" v-if="article.coverImage">
        <el-image 
          :src="'http://localhost8082/'+article.coverImage" 
          fit="cover"
          :preview-src-list="[article.coverImage]"
        >
          <template #error>
            <div class="image-placeholder">
              <el-icon><Picture /></el-icon>
            </div>
          </template>
        </el-image>
      </div>

      <div class="article-body markdown-body" v-html="renderedContent"></div>
    </div>

    <div class="article-footer">
      <div class="article-tags" v-if="article.tags && article.tags.length">
        <el-tag 
          v-for="tag in article.tags" 
          :key="tag.id"
          class="tag"
        >
          {{ tag.name }}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script setup name="ArticleContentView">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowLeft, Edit, Delete, User, Calendar, View, Picture } from '@element-plus/icons-vue'
import { getArticleById, deleteArticle } from '@/api/article'
import { marked } from 'marked'
import DOMPurify from 'dompurify'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const article = ref({})
const loading = ref(false)

const isAuthor = computed(() => {
  return userStore.userInfo && article.value.authorId === userStore.userInfo.id
})

const renderedContent = computed(() => {
  if (!article.value.content) return ''
  const rawHtml = marked(article.value.content)
  return DOMPurify.sanitize(rawHtml)
})

const loadArticle = async () => {
  try {
    loading.value = true
    const result = await getArticleById(route.params.id)
    if (result.code === 200) {
      article.value = result.data
      console.log('获取文章:', article.value)
    } 
    else {
      ElMessage.error(result.message || '获取文章失败')
      router.push('/articles')
    }
  } catch (error) {
    console.error('获取文章失败:', error)
    ElMessage.error('获取文章失败')
    router.push('/articles')
  } finally {
    loading.value = false
  }
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const goBack = () => {
  router.push('/articles')
}

const goToEdit = () => {
  router.push(`/article/edit/${article.value.id}`)
}

const handleDelete = async () => {
  try {
    await ElMessageBox.confirm(
      '确定要删除这篇文章吗？此操作不可恢复。',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    const result = await deleteArticle(article.value.id)
    if (result.code === 200) {
      ElMessage.success('删除成功')
      router.push('/articles')
    } else {
      ElMessage.error(result.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除文章失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

onMounted(() => {
  loadArticle()
})
</script>

<style scoped>
.article-content-container {
  max-width: 900px;
  margin: 40px auto;
  padding: 0 20px;
}

.article-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.back-button {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #666;
  cursor: pointer;
  font-size: 16px;
  transition: color 0.3s;
}

.back-button:hover {
  color: #6e8efb;
}

.article-actions {
  display: flex;
  gap: 12px;
}

.article-main {
  background: white;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.05);
}

.article-title {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  margin-bottom: 20px;
  line-height: 1.4;
}

.article-meta {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
  color: #666;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
}

.article-cover {
  margin: 30px -40px;
  height: 400px;
  overflow: hidden;
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
  font-size: 48px;
}

.article-body {
  margin-top: 40px;
  line-height: 1.8;
  color: #333;
}

.article-footer {
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.article-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.tag {
  background: #f5f7fa;
  border: none;
  color: #666;
}

:deep(.el-button--primary) {
  background: #6e8efb;
  border-color: #6e8efb;
}

:deep(.el-button--primary:hover) {
  background: #5a7de0;
  border-color: #5a7de0;
}

:deep(.el-button--danger) {
  background: #f56c6c;
  border-color: #f56c6c;
}

:deep(.el-button--danger:hover) {
  background: #e64242;
  border-color: #e64242;
}

/* Markdown 样式 */
:deep(.markdown-body) {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif;
}

:deep(.markdown-body h1),
:deep(.markdown-body h2),
:deep(.markdown-body h3),
:deep(.markdown-body h4),
:deep(.markdown-body h5),
:deep(.markdown-body h6) {
  margin-top: 24px;
  margin-bottom: 16px;
  font-weight: 600;
  line-height: 1.25;
}

:deep(.markdown-body p) {
  margin-top: 0;
  margin-bottom: 16px;
}

:deep(.markdown-body code) {
  padding: 0.2em 0.4em;
  margin: 0;
  font-size: 85%;
  background-color: rgba(27,31,35,0.05);
  border-radius: 3px;
}

:deep(.markdown-body pre) {
  padding: 16px;
  overflow: auto;
  font-size: 85%;
  line-height: 1.45;
  background-color: #f6f8fa;
  border-radius: 3px;
}

:deep(.markdown-body blockquote) {
  padding: 0 1em;
  color: #6a737d;
  border-left: 0.25em solid #dfe2e5;
  margin: 0 0 16px 0;
}

:deep(.markdown-body img) {
  max-width: 100%;
  box-sizing: content-box;
  background-color: #fff;
}
</style>
