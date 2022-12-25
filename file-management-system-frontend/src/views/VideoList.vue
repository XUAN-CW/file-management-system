<template>
  <el-affix>
    <el-popover placement="bottom" trigger="hover">
      <template #reference>
        <el-button>更多工具</el-button>
      </template>
      <div>
        <el-pagination :total="videoPage.total" :page-size="videoPage.pageSize" v-model:current-page="videoPage.page" />
      </div>
      <el-button v-for="fileGradeOption in fileGradeOptions" :key="fileGradeOption.grade"
        @click="setCurrentPageNullFileGrade(fileGradeOption.grade)">{{
            fileGradeOption.name
        }}</el-button>
      <!-- <el-button @click="postCurrentPageFileGrade()" type="danger">推送</el-button> -->
    </el-popover>
  </el-affix>
  <el-pagination :total="videoPage.total" :page-size="videoPage.pageSize" v-model:current-page="videoPage.page"
    v-model:pager-count="videoPage.pageCount" />
  <div id="VideoList">
    <ul class="videoListUlCSS">
      <li class="videoListLiCSS" v-for="(videoInfo) in currentPageVideoList" :key="videoInfo.fileMetadata.sha512">
        <div class="videoContainer">
          <HVideoPlayer :videoSrc="videoInfo.videoHttpUrl" />
          <div class="videoOptions">
            <el-radio-group v-if="(videoInfo.fileGrade.grade === null)" v-model="videoInfo.fileGrade.grade">
              <el-radio-button v-for="fileGradeOption in fileGradeOptions" :key="fileGradeOption.grade"
                @change="postVideoGrade(videoInfo.fileGrade)" :label="fileGradeOption.grade">{{
                    fileGradeOption.name
                }}</el-radio-button>
            </el-radio-group>
          </div>
          <div class="videoDetail">

            <el-popover placement="right" :width="400" trigger="click">
              <template #reference>
                <el-button style="margin-right: 16px">更多信息</el-button>
              </template>
              <div>
                <a :href="videoInfo.videoHttpUrl" target="_blank">{{ videoInfo.fileMetadata.fileName }}</a>
                <div>{{ videoInfo.fileMetadata.absolutePath }}</div>
                <a :href="videoInfo.videoHttpAbsolutePathHexBinaryUrl" target="_blank">{{
                    videoInfo.fileMetadata.fileName
                }} - absolutePathHexBinary</a>
              </div>
            </el-popover>

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
import * as videoApi from "@/api/videoApi";
import * as fileGradeApi from "@/api/fileGradeApi";
import HVideoPlayer from '@/components/HVideoPlayer.vue'

export default {
  name: 'VideoList',
  components: {
    HVideoPlayer
  },
  data() {
    return {
      videoInfoList: [],
      videoPage: {
        total: 0,
        pageSize: 99,
        page: 1,
        pageCount: 30
      },
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
    currentPageVideoList() {
      return this.videoInfoList.slice((this.videoPage.page - 1) * this.videoPage.pageSize, this.videoPage.page * this.videoPage.pageSize);
    },
  },

  mounted() {
    videoApi.getVideoList()
      .then(response => {
        this.videoInfoList = response
        this.videoPage.total = this.videoInfoList.length
        for (let videoInfo of this.videoInfoList) {
          if (videoInfo.fileGrade == null) {
            videoInfo["fileGrade"] = { "sha512": videoInfo.fileMetadata.sha512, "grade": null }
            videoInfo["selected"] = false
          }
        }
        this.videoInfoList.sort((a, b) => {
          let aGrade = a.fileGrade.grade
          let bGrade = b.fileGrade.grade
          if (aGrade != null) { aGrade += 10 }
          if (bGrade != null) { bGrade += 10 }
          return bGrade - aGrade;
        }).reverse()
      })
      .catch(function (error) { // 请求失败处理
        console.log(error);
      });
    // console.log(this.videoInfoList)
  },

  methods: {
    setCurrentPageNullFileGrade(fileGrade) {
      let fileGradeList = []
      for (let videoInfo of this.currentPageVideoList) {
        if (videoInfo.fileGrade.grade == null) {
          videoInfo.fileGrade.grade = fileGrade
          fileGradeList.push(videoInfo.fileGrade)
        }
      }
      fileGradeApi.insertOrUpdate(fileGradeList).then(response => {
        this.$message.success('推送成功');
      }).catch(function (error) { // 请求失败处理
        this.$message.error(error);
      });
    },
    // postCurrentPageFileGrade() {
    //   let fileGradeList = []
    //   for (let videoInfo of this.currentPageVideoList) {
    //     fileGradeList.push(videoInfo.fileGrade)
    //   }
    //   console.log(fileGradeList)
    //   fileGradeApi.insertOrUpdate(fileGradeList).then(response => {
    //     this.$message.success('推送成功');
    //   }).catch(function (error) { // 请求失败处理
    //     this.$message.error(error);
    //   });
    // },

    postVideoGrade(videoGrade) {
      let postVideoGradeList = []
      postVideoGradeList.push(videoGrade)
      fileGradeApi.insertOrUpdate(postVideoGradeList).then(response => {
        this.$message.success('上传成功');
      }).catch(function (error) { // 请求失败处理
        this.$message.error(error);
      });
    }
  }
}
</script>
<style lang="less">
.videoContainer {
  position: relative;
}

.videoOptions:hover {
  opacity: 1;
}

.videoOptions {
  opacity: 0;
  position: absolute;
  top: 0%;
  left: 0%;
  z-index: 10;
}

.videoStatus {
  opacity: 1;
  position: absolute;
  top: 0%;
  right: 0%;
  z-index: 10;
}


.videoDetail {
  opacity: 0;
  position: absolute;
  bottom: 0%;
  right: 0%;
  z-index: 10;
}

.videoDetail:hover {
  opacity: 1;
}

.videoFileGradeStatus {
  width: 40%;
  float: right;
}

.videoListUlCSS {
  display: flex;
  flex-wrap: wrap;
  padding: 0;
  margin: 0;

  .videoListLiCSS {
    width: 33%;
    height: 100%;
    display: inline-block;
  }
}
</style>