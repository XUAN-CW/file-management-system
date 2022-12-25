import { createRouter, createWebHistory } from 'vue-router'
import videoList from '../views/VideoList.vue'

const routes = [
  {
    path: '/',
    name: 'videoList',
    component: videoList
  },
  {
    path: '/imageList',
    name: 'imageList',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/ImageList.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
