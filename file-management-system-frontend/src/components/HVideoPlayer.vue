<template>
  <div ref="videoPlayerDiv">
    <video ref="videoPlayer" :height="videoPlayerHeight" preload="auto" controls class="video-js"
      @mouseenter="player.play()" @mouseleave="pauseAfter10s()">
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
      // dataSetup: '{"fluid": true}',
      isInViewport: false
    };
  },

  methods: {
    pauseAfter10s() {
      setTimeout(() => {
        this.player.pause();
      }, 10000);
    }
  },

  watch: {
    // isInViewport(newValue, oldValue) {
    //   console.log(`The message has changed from "${oldValue}" to "${newValue}"`);
    //   if (newValue) {
    //     this.player.play()
    //   } else {
    //     this.player.pause()
    //   }
    // }
  },

  mounted() {
    const player = videojs(this.$refs.videoPlayer)
    this.player = player

    // this.videoPlayerWidth = this.$refs.videoPlayerDiv.offsetWidth
    this.videoPlayerHeight = this.$refs.videoPlayerDiv.offsetHeight

    // const observer = new IntersectionObserver(entries => {
    //   if (entries[0].isIntersecting) {
    //     this.isInViewport = true;
    //   } else {
    //     this.isInViewport = false;

    //   }
    // });
    // observer.observe(this.$refs.videoPlayer);
  },
  beforeUnmount() {
    if (this.player) {
      this.player.dispose();
    }
  },
}
</script>
