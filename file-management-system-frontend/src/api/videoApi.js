// 导入axios实例
import httpRequest from '@/request/index'

export function getVideoList() {
    return httpRequest({
		url: '/video/getVideoList',
		method: 'get',
	})
}
