<template>
  <el-header class="navbar" height="60px">
    <div class="navbar-inner">
      <div class="navbar-left">
        <router-link to="/" class="logo">JobPlus</router-link>
        <el-menu
          mode="horizontal"
          :default-active="activeMenu"
          background-color="transparent"
          text-color="#333"
          active-text-color="#1890ff"
          class="navbar-menu"
          router
        >
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/jobs">职位搜索</el-menu-item>
        </el-menu>
      </div>
      <div class="navbar-right">
        <template v-if="isLoggedIn">
          <el-badge :value="unreadCount" :hidden="unreadCount === 0" class="nav-icon-badge">
            <i class="el-icon-bell" @click="goNotifications" style="font-size:20px;cursor:pointer"></i>
          </el-badge>
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="user-dropdown">
              <el-avatar :size="32" icon="el-icon-user-solid"></el-avatar>
              <span class="username">{{ userName }}</span>
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="role === 1" command="resume">我的简历</el-dropdown-item>
              <el-dropdown-item v-if="role === 1" command="deliveries">投递记录</el-dropdown-item>
              <el-dropdown-item v-if="role === 1" command="favorites">我的收藏</el-dropdown-item>
              <el-dropdown-item command="profile">个人信息</el-dropdown-item>
              <el-dropdown-item v-if="role === 2" command="employer">企业管理</el-dropdown-item>
              <el-dropdown-item v-if="role === 3" command="admin">后台管理</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
        <template v-else>
          <el-button type="text" @click="$router.push('/login')">登录</el-button>
          <el-button type="primary" size="small" @click="$router.push('/register')">注册</el-button>
        </template>
      </div>
    </div>
  </el-header>
</template>

<script>
export default {
  name: 'NavBar',
  data() {
    return {
      unreadCount: 0
    }
  },
  computed: {
    isLoggedIn() {
      return this.$store.isLoggedIn
    },
    userName() {
      return this.$store.user ? (this.$store.user.nickname || this.$store.user.email || '用户') : ''
    },
    role() {
      return this.$store.role
    },
    activeMenu() {
      const path = this.$route.path
      if (path === '/' || path.startsWith('/jobs')) return path
      return '/'
    }
  },
  methods: {
    handleCommand(cmd) {
      if (cmd === 'logout') {
        this.$http.post('/user/logout').catch(() => {})
        this.$store.logout()
        this.$router.push('/')
        this.$message.success('已退出登录')
      } else {
        this.$router.push('/' + cmd)
      }
    },
    goNotifications() {
      this.$router.push('/notifications')
    },
    fetchUnreadCount() {
      if (!this.isLoggedIn) return
      this.$http.get('/notifications/unread-count').then(res => {
        if (res.data.code === 200) {
          this.unreadCount = res.data.data || 0
        }
      }).catch(() => {})
    }
  },
  watch: {
    isLoggedIn(val) {
      if (val) this.fetchUnreadCount()
    }
  },
  mounted() {
    if (this.isLoggedIn) this.fetchUnreadCount()
  }
}
</script>

<style scoped>
.navbar {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,0,0,0.08);
  position: sticky;
  top: 0;
  z-index: 1000;
  padding: 0 40px;
}
.navbar-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  max-width: 1200px;
  margin: 0 auto;
}
.navbar-left {
  display: flex;
  align-items: center;
}
.logo {
  font-size: 22px;
  font-weight: 700;
  color: #1890ff;
  text-decoration: none;
  margin-right: 30px;
}
.navbar-menu {
  border-bottom: none !important;
}
.navbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
}
.user-dropdown {
  display: flex;
  align-items: center;
  cursor: pointer;
  gap: 6px;
}
.username {
  font-size: 14px;
  color: #333;
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.nav-icon-badge {
  cursor: pointer;
}
</style>
