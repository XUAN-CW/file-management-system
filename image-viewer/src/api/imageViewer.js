// 导入axios实例
import httpRequest from '@/request/index'

// 获取用户信息
export function getImageList() {
    return httpRequest({
		url: '/image-viewer/getImageList',
		method: 'get',
	})
}
