<template>
  <div class="comment-page">
    <h2>社区评论区</h2>
    <el-form :model="form" class="comment-form" @submit.prevent="submitComment">
      <el-form-item>
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="3"
          placeholder="说点什么吧..."
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitComment">发表评论</el-button>
      </el-form-item>
    </el-form>
    <el-divider />
    <div class="comment-list">
      <div v-if="comments.length === 0" class="empty">暂无评论，快来抢沙发吧！</div>
      <el-card v-for="(item, idx) in comments" :key="idx" class="comment-item">
        <div class="comment-header">
          <el-avatar :size="36" :src="item.avatar" />
          <span class="comment-username">{{ item.username }}</span>
          <span class="comment-time">{{ item.time }}</span>
        </div>
        <div class="comment-content">{{ item.content }}</div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const form = ref({ content: '' })
const comments = ref([
  // 示例数据
  {
    username: '小明',
    avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
    time: '2024-06-01 12:00',
    content: '欢迎来到社区评论区！'
  }
])

function submitComment() {
  if (!form.value.content.trim()) {
    ElMessage.warning('评论内容不能为空！')
    return
  }
  comments.value.unshift({
    username: '游客',
    avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
    time: new Date().toLocaleString(),
    content: form.value.content
  })
  form.value.content = ''
  ElMessage.success('评论成功！')
}
</script>

<style scoped>
.comment-page {
  max-width: 600px;
  margin: 40px auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(110,142,251,0.08);
  padding: 32px 24px 40px 24px;
}
.comment-form {
  margin-bottom: 24px;
}
.comment-list {
  margin-top: 24px;
}
.comment-item {
  margin-bottom: 18px;
}
.comment-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}
.comment-username {
  font-weight: bold;
  color: #6e8efb;
}
.comment-time {
  color: #aaa;
  font-size: 13px;
  margin-left: auto;
}
.comment-content {
  font-size: 16px;
  color: #333;
  line-height: 1.7;
}
.empty {
  color: #bbb;
  text-align: center;
  margin: 32px 0;
}
</style>
