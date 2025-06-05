<template>
    <div class="create-article-container">
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
        <el-form-item label="分类">
          <el-input v-model="form.categoryId" placeholder="请输入分类ID（如无可留空）" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">发布</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { uploadArticleCover, createArticle } from '@/api/article'
  import { ElMessage } from 'element-plus'

  const router = useRouter()
  const form = ref({
    title: '',
    summary: '',
    content: '',
    coverImage: '',
    categoryId: '',
  })
  const beforeUpload = (file) => {
    if (!file.type.startsWith('image/')) {
      ElMessage.error('只能上传图片')
      return false
    }
    if (file.size > 5 * 1024 * 1024) {
      ElMessage.error('图片大小不能超过5MB')
      return false
    }
    return true
  }
  const coverImage1 = ref('')
  const handleCoverUpload = async (options) => {
    try {
      const formData = new FormData()
      formData.append('file', options.file)
      const res = await uploadArticleCover(formData)
      if (res.success) {
        coverImage1.value =res.data
        form.value.coverImage ='http://localhost:8082/'+ coverImage1.value
        console.log('封面上传成功:', form.value.coverImage)
        ElMessage.success('封面上传成功')
      } else {
        ElMessage.error(res.message || '封面上传失败')
      }
    } catch (error) {
      console.error('封面上传失败:', error)
      ElMessage.error(error.response?.data?.message || '封面上传失败')
    }
  }
  
  const handleSubmit = async () => {
    if (!form.value.title) {
      ElMessage.warning('请输入文章标题')
      return
    }
    if (!form.value.summary) {
      ElMessage.warning('请输入文章摘要')
      return
    }
    if (!form.value.content) {
      ElMessage.warning('请输入文章内容')
      return
    }
    if (!form.value.coverImage) {
      ElMessage.warning('请上传封面图片')
      return
    }
    // categoryId 可选，如有分类可校验
    // if (!form.value.categoryId) {
    //   ElMessage.warning('请选择分类')
    //   return
    // }
    console.log('提交的文章数据:', form.value)
    try {
      const res = await createArticle({
        title: form.value.title,
        summary: form.value.summary,
        content: form.value.content,
        categoryId: form.value.categoryId || null,
        coverImage: coverImage1.value || null,
        status: '1', // 或 1，视后端支持
      })
      if (res.success) {
        ElMessage.success('文章发布成功')
        router.push('/articles')
      } else {
        ElMessage.error(res.message || '发布失败')
      }
    } catch (error) {
      console.error('发布文章失败:', error)
      ElMessage.error('发布失败')
    }
  }
  </script>