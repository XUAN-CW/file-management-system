<template>
  <div ref="videoPlayerDiv">
    <video ref="videoPlayer" :height="videoPlayerHeight" preload="auto" controls :data-setup="dataSetup"
      class="video-js">
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
      videoPlayerHeight: 330,
      dataSetup: '{"fluid": true}'

    };
  },

  mounted() {
    const player = videojs(this.$refs.videoPlayer)
    this.player = player 
    // this.videoPlayerWidth = this.$refs.videoPlayerDiv.offsetWidth
    this.videoPlayerHeight = this.$refs.videoPlayerDiv.offsetHeight
    // player.on('loadedmetadata', () => {
    //   player.poster(player.currentSrc() + '#t=3');
    // });
  },
  beforeUnmount() {
    if (this.player) {
      this.player.dispose();
    }
  },
}
</script>
