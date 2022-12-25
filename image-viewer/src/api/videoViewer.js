// 导入axios实例
import httpRequest from '@/request/index'

// 获取用户信息
export function getVideoList() {
    return httpRequest({
		url: '/image-viewer/getVideoList',
		method: 'get',
	})
}
