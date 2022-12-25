// 导入axios实例
import httpRequest from '@/request/index'

export function insertOrUpdate(fileGradeList) {
    return httpRequest({
		url: '/filemetadata/fileGrade/insertOrUpdate',
		method: 'post',
		data: fileGradeList
	})
}
