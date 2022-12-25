// 导入axios实例
import httpRequest from '@/request/index'

export function getImageList() {
    return httpRequest({
		url: '/image/getImageList',
		method: 'get',
	})
}
