---
title: file-management-system
tags: 
date: 2022-11-16 23:33:09
id: 1668612790038093600
---
# 摘要





# firefox下载文件管理

1. 创建硬链接

   ```
   mklink /H C:\core\java\my-project\file-management-system\file-info\download-from-firefox\places.sqlite C:\Users\33719\AppData\Roaming\Mozilla\Firefox\Profiles\4pnxltcf.default-release-1668190769718\places.sqlite
   ```

2. 文件随便下载就是，但文件不能随意删除或修改，在删除前必须计算 hash 值

3. 火狐下载历史存储在 `places.sqlite` 文件，参考  [Mozilla Firefox History Location _ Firefox History Viewer.html](assets\references\Mozilla Firefox History Location _ Firefox History Viewer.html) ，找到文件后可以使用下面的语句查询出哪个

```sql
SELECT
	moz_annos.id,
	moz_annos.content,
	moz_annos.dateAdded,
	moz_places.url,
	moz_places.title
FROM
	moz_annos,
	moz_places 
WHERE
	moz_annos.place_id = moz_places.id 
	AND anno_attribute_id = "1" 
ORDER BY
	moz_annos.lastModified DESC;
```







