// 导入axios实例
import httpRequest from '@/request/index'

// 获取用户信息
export function insert(fileStatusList) {
    return httpRequest({
		url: '/image-viewer/getImageList',
		method: 'post',
	    data: fileStatusList
	})
}
