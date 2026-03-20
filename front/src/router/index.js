import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    redirect: '/person',
    children: [
      {
        path: 'person',
        name: 'Person',
        component: () => import('../views/Person.vue'),
        meta: { title: '人员信息管理' }
      },
      {
        path: 'department',
        name: 'Department',
        component: () => import('../views/Department.vue'),
        meta: { title: '部门管理' }
      },
      {
        path: 'party-branch',
        name: 'PartyBranch',
        component: () => import('../views/PartyBranch.vue'),
        meta: { title: '党支部管理' }
      },
      {
        path: 'party-member',
        name: 'PartyMember',
        component: () => import('../views/PartyMember.vue'),
        meta: { title: '党员管理' }
      },
      {
        path: 'leader',
        name: 'Leader',
        component: () => import('../views/Leader.vue'),
        meta: { title: '领导信息管理' }
      },
      {
        path: 'meeting',
        name: 'Meeting',
        component: () => import('../views/Meeting.vue'),
        meta: { title: '会议管理' }
      },
      {
        path: 'time-node',
        name: 'TimeNode',
        component: () => import('../views/TimeNode.vue'),
        meta: { title: '年度时间节点管理' }
      },
      {
        path: 'affair',
        name: 'Affair',
        component: () => import('../views/Affair.vue'),
        meta: { title: '事务台账管理' }
      },
      {
        path: 'announcement',
        name: 'Announcement',
        component: () => import('../views/Announcement.vue'),
        meta: { title: '公告通知管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 导航守卫
router.beforeEach((to, from) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    return true
  } else {
    if (token) {
      return true
    } else {
      return '/login'
    }
  }
})

export default router