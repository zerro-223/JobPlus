<template>
<div>
  <el-menu mode="horizontal" background-color="#1a1a2e" text-color="#fff" active-text-color="#1890ff" router>
    <el-menu-item index="/" style="font-size:20px;font-weight:bold;">JobPlus</el-menu-item>
    <el-menu-item index="/jobs">职位搜索</el-menu-item>
    <div style="flex:1"></div>
    <template v-if="user">
      <el-menu-item index="/deliveries">投递记录</el-menu-item>
      <el-menu-item index="/favorites">收藏夹</el-menu-item>
      <el-submenu index="user" style="float:right">
        <template slot="title">{{ user.nickname || user.email }}</template>
        <el-menu-item index="/profile">个人中心</el-menu-item>
        <el-menu-item index="/resume">我的简历</el-menu-item>
        <el-menu-item index="/notifications">消息通知</el-menu-item>
        <el-menu-item v-if="user.role===2" index="/employer">企业后台</el-menu-item>
        <el-menu-item v-if="user.role===3" index="/admin">管理后台</el-menu-item>
        <el-menu-item @click="logout">退出登录</el-menu-item>
      </el-submenu>
    </template>
    <template v-else>
      <el-menu-item index="/login">登录</el-menu-item>
      <el-menu-item index="/register">注册</el-menu-item>
    </template>
  </el-menu>

  <div class="hero" style="background:linear-gradient(135deg,#1a1a2e 0%,#16213e 100%);padding:60px 0;text-align:center;color:#fff">
    <h1 style="font-size:36px;margin-bottom:10px">找到属于你的理想工作</h1>
    <p style="font-size:16px;color:#aaa;margin-bottom:30px">JobPlus 综合性在线招聘平台，连接人才与企业</p>
    <div style="max-width:700px;margin:0 auto;display:flex;gap:10px">
      <el-input v-model="keyword" placeholder="搜索职位、公司或关键词" size="large" @keyup.enter="search" />
      <el-input v-model="city" placeholder="城市" size="large" style="width:150px" @keyup.enter="search" />
      <el-button type="primary" size="large" @click="search">搜索</el-button>
    </div>
  </div>

  <div style="max-width:1200px;margin:30px auto;padding:0 20px">
    <h3 style="margin-bottom:15px">热门职位分类</h3>
    <div style="display:flex;flex-wrap:wrap;gap:10px;margin-bottom:40px">
      <el-tag v-for="cat in categories" :key="cat.id" type="info" style="cursor:pointer;padding:8px 16px" @click="$router.push('/jobs?categoryId='+cat.id)">{{ cat.name }}</el-tag>
    </div>

    <h3 style="margin-bottom:15px">推荐职位</h3>
    <el-row :gutter="20">
      <el-col :span="8" v-for="job in positions" :key="job.id" style="margin-bottom:20px">
        <el-card shadow="hover" style="cursor:pointer" @click.native="$router.push('/jobs/'+job.id)">
          <div style="font-size:16px;font-weight:bold;margin-bottom:8px">{{ job.title }}</div>
          <div style="color:#1890ff;font-size:18px;margin-bottom:8px">{{ job.salaryMin }}-{{ job.salaryMax }}K</div>
          <div style="color:#666;font-size:13px;margin-bottom:5px">{{ job.companyName }}</div>
          <div style="color:#999;font-size:12px">{{ job.workplace }} | {{ job.education }} | {{ job.experience }}</div>
        </el-card>
      </el-col>
    </el-row>

    <h3 style="margin:30px 0 15px">知名企业</h3>
    <el-row :gutter="20">
      <el-col :span="6" v-for="c in companies" :key="c.id">
        <el-card shadow="hover" style="text-align:center;cursor:pointer" @click.native="$router.push('/company/'+c.id)">
          <div style="font-size:18px;font-weight:bold;margin-bottom:5px">{{ c.name }}</div>
          <div style="color:#999;font-size:13px">{{ c.industry }} | {{ c.scale }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</div>
</template>
<script>
import Vue from 'vue';
export default {
  data() {
    return { keyword: '', city: '', categories: [], positions: [], companies: [] };
  },
  computed: {
    user() { return this.$user; }
  },
  mounted() {
    this.$api.get('/positions/categories').then(r => this.categories = (r.data||[]).filter(c => !c.parentId));
    this.$api.get('/positions', { params: { page:1, size:6 } }).then(r => { if(r.data) this.positions = r.data.list; });
    // Fetch companies
    this.$api.get('/company/1').catch(()=>{});
    this.companies = [{id:1,name:'阿里云',industry:'互联网/IT',scale:'1000人以上'},{id:2,name:'华为',industry:'通信/IT',scale:'1000人以上'},{id:3,name:'字节跳动',industry:'互联网',scale:'1000人以上'}];
  },
  methods: {
    search() {
      this.$router.push({ path: '/jobs', query: { keyword: this.keyword, city: this.city } });
    },
    logout() {
      this.$api.post('/user/logout').finally(() => {
        sessionStorage.removeItem('jobplus_user');
        Vue.prototype.$user = null;
        this.$router.push('/');
      });
    }
  }
};
</script>
