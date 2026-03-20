<template>
  <div class="home-container">
    <!-- 左侧菜单 -->
    <el-aside :width="isCollapse ? '64px' : '200px'" class="aside">
      <div class="logo" @click="isCollapse = !isCollapse">
        <img v-if="!isCollapse" src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=government%20institution%20logo%2C%20blue%20color%2C%20no%20text%2C%20abstract%20symbol%2C%20official%20style%2C%20professional&image_size=square" alt="Logo" />
        <img v-else src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=minimal%20government%20logo%2C%20blue%20color%2C%20no%20text%2C%20abstract%20symbol&image_size=square" alt="Logo" />
      </div>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        background-color="#001529"
        text-color="#fff"
        active-text-color="#409EFF"
        @select="handleMenuSelect"
      >
        <el-sub-menu index="person">
          <template #title>
            <el-icon><User /></el-icon>
            <span v-if="!isCollapse">人员信息管理</span>
          </template>
          <el-menu-item index="/person">
            <span v-if="!isCollapse">人员列表</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="department">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span v-if="!isCollapse">部门管理</span>
          </template>
          <el-menu-item index="/department">
            <span v-if="!isCollapse">部门列表</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="party">
          <template #title>
            <el-icon><Star /></el-icon>
            <span v-if="!isCollapse">党建管理</span>
          </template>
          <el-menu-item index="/party-branch">
            <span v-if="!isCollapse">党支部管理</span>
          </el-menu-item>
          <el-menu-item index="/party-member">
            <span v-if="!isCollapse">党员管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="leader">
          <template #title>
            <el-icon><Position /></el-icon>
            <span v-if="!isCollapse">领导信息管理</span>
          </template>
          <el-menu-item index="/leader">
            <span v-if="!isCollapse">领导列表</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="meeting">
          <template #title>
            <el-icon><Calendar /></el-icon>
            <span v-if="!isCollapse">会议管理</span>
          </template>
          <el-menu-item index="/meeting">
            <span v-if="!isCollapse">会议列表</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="time-node">
          <template #title>
            <el-icon><Clock /></el-icon>
            <span v-if="!isCollapse">时间节点管理</span>
          </template>
          <el-menu-item index="/time-node">
            <span v-if="!isCollapse">时间节点列表</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="affair">
          <template #title>
            <el-icon><Document /></el-icon>
            <span v-if="!isCollapse">事务台账管理</span>
          </template>
          <el-menu-item index="/affair">
            <span v-if="!isCollapse">事务台账列表</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="announcement">
          <template #title>
            <el-icon><Bell /></el-icon>
            <span v-if="!isCollapse">公告通知管理</span>
          </template>
          <el-menu-item index="/announcement">
            <span v-if="!isCollapse">公告通知列表</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 右侧内容 -->
    <el-container class="main-container">
      <!-- 顶部导航 -->
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ breadcrumb }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32" :src="userAvatar"></el-avatar>
              <span style="margin-left: 8px">{{ userInfo.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区域 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { User, OfficeBuilding, Star, Position, Calendar, Clock, Document, Bell } from '@element-plus/icons-vue'

export default {
  name: 'Home',
  components: {
    User,
    OfficeBuilding,
    Star,
    Position,
    Calendar,
    Clock,
    Document,
    Bell
  },
  data() {
    return {
      isCollapse: false,
      activeMenu: '',
      breadcrumb: '',
      userInfo: JSON.parse(localStorage.getItem('user')) || {},
      userAvatar: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=professional%20government%20official%20avatar%2C%20formal%20style&image_size=square'
    }
  },
  mounted() {
    this.updateActiveMenu()
  },
  watch: {
    $route: {
      handler() {
        this.updateActiveMenu()
      },
      immediate: true
    }
  },
  methods: {
    updateActiveMenu() {
      const path = this.$route.path
      this.activeMenu = path
      // 根据路由路径更新面包屑
      const routeName = this.$route.meta.title || ''
      this.breadcrumb = routeName
    },
    handleMenuSelect(key) {
      this.$router.push(key)
    },
    logout() {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.aside {
  background-color: #001529;
  overflow-y: auto;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 64px;
  cursor: pointer;
}

.logo img {
  width: 40px;
  height: 40px;
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-left {
  flex: 1;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.main-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f5f7fa;
}
</style>