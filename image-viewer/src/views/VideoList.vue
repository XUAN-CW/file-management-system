<template>
    <div id="VideoList">
        <ul>
            <li v-for="(videoInfo, index) in videoInfoList" :key="index">
                <div class="container">
                    <HVideoPlayer :videoSrc="videoInfo.videoHttpUrl" />
                    {{ videoInfo.videoHttpUrl }}
                    <!-- <HVideoPlayer videoSrc="http://localhost:8080/static/ADN-046.mp4" /> -->
                    <div class="videoOptions">
                        <el-radio-group v-if="(videoInfo.fileGrade.grade === null)" v-model="videoInfo.fileGrade.grade">
                            <el-radio-button v-for="fileGradeOption in fileGradeOptions" :key="fileGradeOption.grade"
                                @change="postVideoGrade(videoInfo.fileGrade)" :label="fileGradeOption.grade">{{
                                        fileGradeOption.name
                                }}</el-radio-button>
                        </el-radio-group>
                    </div>
                    <div class="videoStatus">
                        <div v-if="(videoInfo.fileGrade.grade !== null)">
                            <el-select v-model="videoInfo.fileGrade.grade" filterable allow-create placeholder="请选择"
                                class="videoFileGradeStatus" @change="postVideoGrade(videoInfo.fileGrade)">
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
import * as videoViewer from "@/api/videoViewer";
import * as fileGrade from "@/api/fileGrade";
import HVideoPlayer from '@/components/HVideoPlayer.vue'

export default {
    name: 'VideoList',
    components: {
        HVideoPlayer
    },
    data() {
        return {
            videoInfoList: [],
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
        videoGradeList() {
            return this.videoInfoList.map(videoInfo => videoInfo.fileGrade);
        },
    },
    mounted() {
        videoViewer.getVideoList()
            .then(response => {
                this.videoInfoList = response
                for (let videoInfo of this.videoInfoList) {
                    if (videoInfo.fileGrade == null) {
                        videoInfo["fileGrade"] = { "sha512": videoInfo.fileMetadata.sha512, "grade": null }
                    }
                }
                this.videoInfoList.sort((a, b) => {
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
        // console.log(this.videoInfoList)
    },

    methods: {
        postVideoGradeList() {
            fileGrade.insertOrUpdate(this.videoGradeList).then(response => {
                this.$message.success("videoGradeList 上传成功");
            }).catch(function (error) { // 请求失败处理
                this.$message.error("videoGradeList 上传失败");
            });
        },
        postVideoGrade(videoGrade) {
            let postVideoGradeList = []
            postVideoGradeList.push(videoGrade)
            fileGrade.insertOrUpdate(postVideoGradeList).then(response => {
                this.$message.success('上传成功');
            }).catch(function (error) { // 请求失败处理
                this.$message.error(error);
            });
        }
    }
}
</script>
<style lang="less">
.videoOptions:hover {
    opacity: 1;
}

.videoOptions {
    opacity: 0;
    position: absolute;
    top: 0%;
    left: 0%;
}

.videoStatus {
    opacity: 1;
    position: absolute;
    top: 0%;
    right: 0%;
}

.videoFileGradeStatus {
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

        video {
            height: 100%;
            width: 100%;
            object-fit: cover;
            vertical-align: bottom;
        }
    }
}
</style>