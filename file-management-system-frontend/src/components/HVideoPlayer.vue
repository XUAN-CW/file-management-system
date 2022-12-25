<template>
  <div ref="videoPlayerDiv">
    <video ref="videoPlayer" :height="videoPlayerHeight" preload="auto" controls class="video-js">
      <source :src="videoSrc">
    </video>
  </div>
</template>

<script>
import 'video.js/dist/video-js.css'
import videojs from 'video.js';
export default {
  name: 'HVideoPlayer',
  props: {
    videoSrc: String
  },

  data() {
    return {
      // videoPlayerWidth: 650,
      videoPlayerHeight: 330

    };
  },

  mounted() {
    this.player = videojs(this.$refs.videoPlayer, {
      controls: true,
      autoplay: false,
      preload: 'auto'
    })
    // this.videoPlayerWidth = this.$refs.videoPlayerDiv.offsetWidth
    this.videoPlayerHeight = this.$refs.videoPlayerDiv.offsetHeight

  },
  beforeUnmount() {
    if (this.player) {
      this.player.dispose();
    }
  },
}
</script>
