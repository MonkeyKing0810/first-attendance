import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
import axios from 'axios'
Vue.prototype.$axios = axios;
axios.defaults.withCredentials = true
axios.defaults.baseURL = 'http://localhost:8091'

import store from "./store";

import Moment from 'moment'
Vue.prototype.moment = Moment

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {// 判断跳转的路由是否需要登录
    if (store.state.user.username) {// vuex.state判断user.username是否存在
      // alert("用户已登录!")// 已登录
      next()
    } else {
      next({
        path: '/login',
        query: { redirect: to.fullPath }// 将跳转的路由path作为参数
      })
    }
  } else {
    next()
  }
}
)
Vue.config.productionTip = false

new Vue({
  router,
  axios,
  store,
  render: h => h(App)
}).$mount('#app')
