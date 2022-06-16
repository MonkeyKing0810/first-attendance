import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: "*",
    redirect: '/'
  },
  {
    path: '/',
    name: 'Login',
    component: Login,

  },
  {
    path: '/home',
    component: Home,
    hidden: true,
    meta: {
      requireAuth: true,
      keepAlive: false
    },
    children: [
      {
        path: 'leave',
        component: () => import('../views/leave/Leave.vue')
      },
      {
        name: 'userSeeLeave',
        path: 'userSeeLeave',
        component: () => import('../views/leave/UserSeeLeave.vue')
      },
      {
        path: 'addLeave',
        component: () => import('../views/leave/AddLeave.vue')

      },
      {
        path: 'setLeave',
        component: () => import('../views/setLeave/SetLeave.vue')
      },


      {
        name: 'adminSeeLeave',
        path: 'adminSeeLeave',
        component: () => import('../views/setLeave/AdminSeeLeave.vue')
      },
      {
        path: 'workOvertime',
        component: () => import('../views/workOvertime/WorkOvertime.vue')
      },
      {
        path: 'attendance',
        component: () => import('../views/counts/Attendance.vue')
      },
      {
        //子路由不加/
        path: 'user',
        component: () => import('../views/User.vue')
      }
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
