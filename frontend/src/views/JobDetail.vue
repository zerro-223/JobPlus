<template>
<div style="max-width:1000px;margin:20px auto;padding:0 20px">
  <el-row :gutter="20" v-if="job">
    <el-col :span="16">
      <el-card>
        <h2>{{ job.title }}</h2>
        <div style="color:#1890ff;font-size:24px;margin:10px 0">{{ job.salaryMin }}-{{ job.salaryMax }}K/月</div>
        <div style="color:#666;margin-bottom:10px">
          <span style="margin-right:15px"><i class="el-icon-location-outline" /> {{ job.workplace }}</span>
          <span style="margin-right:15px">{{ job.education }}</span>
          <span style="margin-right:15px">{{ job.experience }}</span>
        </div>
        <div v-if="job.tags" style="margin-bottom:15px"><el-tag v-for="t in (job.tags||'').split(',')" :key="t" style="margin-right:4px">{{ t }}</el-tag></div>
        <el-divider />
        <h3>职位描述</h3>
        <div style="white-space:pre-wrap;line-height:1.8">{{ job.description }}</div>
        <el-divider v-if="job.requirement" />
        <h3 v-if="job.requirement">任职要求</h3>
        <div style="white-space:pre-wrap;line-height:1.8">{{ job.requirement }}</div>
        <div style="margin-top:30px">
          <el-button type="primary" size="large" @click="deliver" :disabled="!canDeliver" :loading="delivering">立即投递</el-button>
          <el-button :type="job.isFavorited?'warning':''" @click="toggleFavorite" :disabled="!user">{{ job.isFavorited?'已收藏':'收藏' }}</el-button>
        </div>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card style="margin-bottom:15px;cursor:pointer" @click.native="goCompany">
        <div style="text-align:center">
          <div style="font-size:18px;font-weight:bold;margin-bottom:5px">{{ job.companyName }}</div>
          <div style="color:#999;font-size:13px">{{ job.workplace }}</div>
        </div>
        <el-divider />
        <div style="font-size:13px;color:#666">浏览量: {{ job.viewCount }} | 投递数: {{ job.deliveryCount }}</div>
      </el-card>
    </el-col>
  </el-row>
  <el-dialog title="选择简历" :visible.sync="resumeDialog" width="400px">
    <el-radio-group v-model="selectedResume">
      <el-radio :label="1">在线简历</el-radio>
    </el-radio-group>
    <span slot="footer"><el-button @click="resumeDialog=false">取消</el-button><el-button type="primary" @click="confirmDeliver">确认投递</el-button></span>
  </el-dialog>
</div>
</template>
<script>
export default {
  data() { return { job: null, delivering: false, resumeDialog: false, selectedResume: 1 }; },
  computed: {
    user() { return this.$user; },
    canDeliver() { return this.user && this.user.role === 1; }
  },
  mounted() { this.fetchDetail(); },
  methods: {
    fetchDetail() {
      const id = this.$route.params.id;
      this.$api.get('/positions/'+id).then(r => this.job = r.data);
    },
    deliver() {
      if (!this.user) { this.$router.push('/login'); return; }
      this.resumeDialog = true;
    },
    confirmDeliver() {
      this.delivering = true;
      this.$api.post('/deliveries', null, { params: { positionId: this.job.id, resumeId: this.selectedResume } }).then(() => {
        this.$message.success('投递成功！');
        this.resumeDialog = false;
      }).catch(e => this.$message.error(e.message||'投递失败')).finally(() => this.delivering = false);
    },
    toggleFavorite() {
      if (!this.user) { this.$router.push('/login'); return; }
      if (this.job.isFavorited) {
        this.$api.delete('/favorites/'+this.job.id).then(() => { this.job.isFavorited = false; this.$message.success('已取消收藏'); });
      } else {
        this.$api.post('/favorites', null, { params: { positionId: this.job.id } }).then(() => { this.job.isFavorited = true; this.$message.success('已收藏'); });
      }
    },
    goCompany() { this.$router.push('/company/'+this.job.companyId); }
  }
};
</script>
