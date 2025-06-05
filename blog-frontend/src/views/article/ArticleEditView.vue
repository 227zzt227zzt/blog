<template>
  <div class="edit-article-container">
    <el-form :model="form" label-width="80px">
      <el-form-item label="标题">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="摘要">
        <el-input v-model="form.summary" />
      </el-form-item>
      <el-form-item label="内容">
        <el-input type="textarea" v-model="form.content" :rows="10" />
      </el-form-item>
      <el-form-item label="封面">
        <el-upload
          :show-file-list="false"
          :before-upload="beforeUpload"
          :http-request="handleCoverUpload"
          accept="image/*"
        >
          <el-button>上传封面</el-button>
        </el-upload>
        <img v-if="form.coverImage" :src="form.coverImage" style="max-width: 200px; margin-left: 20px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { uploadArticleCover, getArticleById, updateArticle } from '@/api/article'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const form = ref({
  id: null,
  title: '',
  summary: '',
  content: '',
  coverImage: ''
})

const loadArticle = async () => {
  try {
    const res = await getArticleById(route.params.id)
    if (res.success) {
      const article = res.data
      form.value = {
        id: article.id,
        title: article.title,
        summary: article.summary,
        content: article.content,
        coverImage: article.coverImage
      }
    } else {
      ElMessage.error(res.message || '获取文章失败')
      router.push('/articles')
    }
  } catch (error) {
    console.error('获取文章失败:', error)
    ElMessage.error('获取文章失败')
    router.push('/articles')
  }
}

const beforeUpload = (file) => {
  if (!file.type.startsWith('image/')) {
    ElMessage.error('只能上传图片')
    return false
  }
  return true
}

const handleCoverUpload = async (options) => {
  const formData = new FormData()
  formData.append('file', options.file)
  formData.append('articleId', form.value.id)
  const res = await uploadArticleCover(formData)
  if (res.success) {
    form.value.coverImage = res.data
    ElMessage.success('封面上传成功')
  } else {
    ElMessage.error(res.message)
  }
}

const handleSubmit = async () => {
  if (!form.value.title) {
    ElMessage.warning('请输入文章标题')
    return
  }
  if (!form.value.content) {
    ElMessage.warning('请输入文章内容')
    return
  }
  
  try {
    const res = await updateArticle(form.value.id, {
      title: form.value.title,
      summary: form.value.summary,
      content: form.value.content,
      coverImage: form.value.coverImage
    })
    
    if (res.success) {
      ElMessage.success('文章更新成功')
      router.push(`/article/${form.value.id}`)
    } else {
      ElMessage.error(res.message || '更新失败')
    }
  } catch (error) {
    console.error('更新文章失败:', error)
    ElMessage.error('更新失败')
  }
}

const goBack = () => {
  router.back()
}

onMounted(() => {
  loadArticle()
})
</script>

<style scoped>
.edit-article-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-input__inner) {
  border-radius: 4px;
}

:deep(.el-textarea__inner) {
  border-radius: 4px;
  font-family: monospace;
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
