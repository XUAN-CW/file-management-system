<template>
    <div id="ImageList">
        <ul>
            <li v-for="(imageInfo, index) in imageInfoList" :key="index">
                <div class="container">
                    <img :src="imageInfo.imageHttpUrl" @click="(imageInfo.selected = !imageInfo.selected)" />
                    <div class="imgOptions">
                        <el-radio-group v-if="(imageInfo.fileGrade.grade === null)" v-model="imageInfo.fileGrade.grade">
                            <el-radio-button v-for="fileGradeOption in fileGradeOptions" :key="fileGradeOption.grade"
                                @change="postImageGrade(imageInfo.fileGrade)" :label="fileGradeOption.grade">{{
                                        fileGradeOption.name
                                }}</el-radio-button>
                        </el-radio-group>
                    </div>
                    <div class="imgStatus">
                        <div v-if="(imageInfo.fileGrade.grade !== null)">
                            <el-select v-model="imageInfo.fileGrade.grade" filterable allow-create placeholder="请选择"
                                class="imgFileGradeStatus" @change="postImageGrade(imageInfo.fileGrade)">
                                <el-option v-for="fileGradeOption in fileGradeOptions" :key="fileGradeOption.name"
                                    :label="fileGradeOption.name" :value="fileGradeOption.grade">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</template>
<script>
import * as imageViewer from "@/api/imageViewer";
import * as fileGrade from "@/api/fileGrade";

export default {
    name: 'ImageList',
    data() {
        return {
            imageInfoList: [],
            fileGradeOptions: [{
                grade: -1,
                name: '垃圾'
            }, {
                grade: 0,
                name: '犹豫',
            }, {
                grade: 1,
                name: '保留'
            }, {
                grade: 2,
                name: '合格'
            }, {
                grade: 3,
                name: '良品'
            }, {
                grade: 4,
                name: '精品'
            }, {
                grade: 5,
                name: '极品'
            }, {
                grade: 10,
                name: '射精'
            }]
        };
    },

    computed: {
        imageGradeList() {
            return this.imageInfoList.map(imageInfo => imageInfo.fileGrade);
        },
    },
    mounted() {
        imageViewer.getImageList()
            .then(response => {
                this.imageInfoList = response
                for (let imageInfo of this.imageInfoList) {
                    if (imageInfo.fileGrade == null) {
                        imageInfo["fileGrade"] = { "sha512": imageInfo.fileMetadata.sha512, "grade": null }
                    }
                }
                this.imageInfoList.sort((a, b) => {
                    let aGrade = a.fileGrade.grade
                    let bGrade = b.fileGrade.grade
                    if (aGrade != null) { aGrade += 10 }
                    if (bGrade != null) { bGrade += 10 }
                    return bGrade - aGrade;
                })
            })
            .catch(function (error) { // 请求失败处理
                console.log(error);
            });
        // console.log(this.imageInfoList)
    },

    methods: {
        postImageGradeList() {
            fileGrade.insertOrUpdate(this.imageGradeList).then(response => {
                this.$message.success("imageGradeList 上传成功");
            }).catch(function (error) { // 请求失败处理
                this.$message.error("imageGradeList 上传失败");
            });
        },
        postImageGrade(imageGrade) {
            let postImageGradeList = []
            postImageGradeList.push(imageGrade)
            fileGrade.insertOrUpdate(postImageGradeList).then(response => {
                this.$message.success('上传成功');
            }).catch(function (error) { // 请求失败处理
                this.$message.error(error);
            });
        }
    }
}
</script>
<style lang="less">
.imgOptions:hover {
    opacity: 1;
}

.imgOptions {
    opacity: 0;
    position: absolute;
    top: 0%;
    left: 0%;
}

.imgStatus {
    opacity: 1;
    position: absolute;
    top: 0%;
    right: 0%;
}

.imgFileGradeStatus {
    width: 40%;
    float: right;
}

ul {
    display: flex;
    flex-wrap: wrap;
    padding: 0;
    margin: 0;

    li {
        width: 25%;
        height: 100%;
        display: inline-block;

        img {
            height: 100%;
            width: 100%;
            object-fit: cover;
            vertical-align: bottom;
        }
    }
}
</style>