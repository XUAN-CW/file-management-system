<template>
    <div>
      <VideoList></VideoList>
      <ImageList></ImageList>
    </div>
  </template>
  
  <script>
  import ImageList from './views/ImageList.vue';
  import VideoList from './views/VideoList.vue';

  export default {
    components: {
        ImageList,
        VideoList
    }
  };
  </script>

  
<style lang="less">
/* 贴近浏览器顶部 */
html,
body {
    padding: 0;
    margin: 0;
    display: flex;
    align-items: center;
}

</style>