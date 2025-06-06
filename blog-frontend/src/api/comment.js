import request from '@/utils/request'

// 保存评论
export function saveComment(commentDTO) {
  return request.post('/comments', commentDTO)
}

// 获取所有评论
export function getAllComments() {
  return request.get('/comments')
}

// 根据ID获取评论
export function getCommentById(id) {
  return request.get(`/comments/${id}`)
} 