<template>
  <video-player :src="videoSrc" controls />
</template>

<script>
import { VideoPlayer } from '@videojs-player/vue'
import 'video.js/dist/video-js.css'
export default {
  name: 'HVideoPlayer',
  props: {
    videoSrc: String
  },
  components: {
    VideoPlayer
  }
}
</script>
