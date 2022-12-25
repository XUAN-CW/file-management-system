// 导入axios实例
import httpRequest from '@/request/index'

// 获取用户信息
export function insertOrUpdate(fileGradeList) {
    return httpRequest({
		url: '/filemetadata/fileGrade/insertOrUpdate',
		method: 'post',
		data: fileGradeList
	})
}
