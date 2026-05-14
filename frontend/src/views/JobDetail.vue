     1|<template>
     2|<div style="max-width:1000px;margin:20px auto;padding:0 20px">
     3|  <el-row :gutter="20" v-if="job">
     4|    <el-col :span="16">
     5|      <el-card>
     6|        <h2>{{ job.title }}</h2>
     7|        <div style="color:#1890ff;font-size:24px;margin:10px 0">{{ job.salaryMin }}-{{ job.salaryMax }}K/月</div>
     8|        <div style="color:#666;margin-bottom:10px">
     9|          <span style="margin-right:15px"><i class="el-icon-location-outline" /> {{ job.workplace }}</span>
    10|          <span style="margin-right:15px">{{ job.education }}</span>
    11|          <span style="margin-right:15px">{{ job.experience }}</span>
    12|        </div>
    13|        <div v-if="job.tags" style="margin-bottom:15px"><el-tag v-for="t in (job.tags||'').split(',')" :key="t" style="margin-right:4px">{{ t }}</el-tag></div>
    14|        <el-divider />
    15|        <h3>职位描述</h3>
    16|        <div style="white-space:pre-wrap;line-height:1.8">{{ job.description }}</div>
    17|        <el-divider v-if="job.requirement" />
    18|        <h3 v-if="job.requirement">任职要求</h3>
    19|        <div style="white-space:pre-wrap;line-height:1.8">{{ job.requirement }}</div>
    20|        <div style="margin-top:30px">
    21|          <el-button type="primary" size="large" @click="deliver" :disabled="!canDeliver" :loading="delivering">立即投递</el-button>
    22|          <el-button :type="job.isFavorited?'warning':''" @click="toggleFavorite" :disabled="!user">{{ job.isFavorited?'已收藏':'收藏' }}</el-button>
    23|        </div>
    24|      </el-card>
    25|    </el-col>
    26|    <el-col :span="8">
    27|      <el-card style="margin-bottom:15px;cursor:pointer" @click.native="goCompany">
    28|        <div style="text-align:center">
    29|          <div style="font-size:18px;font-weight:bold;margin-bottom:5px">{{ job.companyName }}</div>
    30|          <div style="color:#999;font-size:13px">{{ job.workplace }}</div>
    31|        </div>
    32|        <el-divider />
    33|        <div style="font-size:13px;color:#666">浏览量: {{ job.viewCount }} | 投递数: {{ job.deliveryCount }}</div>
    34|      </el-card>
    35|    </el-col>
    36|  </el-row>
    37|  <el-dialog title="选择简历" :visible.sync="resumeDialog" width="400px">
    38|    <el-radio-group v-model="selectedResume">
    39|      <el-radio :label="1">在线简历</el-radio>
    40|    </el-radio-group>
    41|    <span slot="footer"><el-button @click="resumeDialog=false">取消</el-button><el-button type="primary" @click="confirmDeliver">确认投递</el-button></span>
    42|  </el-dialog>
    43|</div>
    44|</template>
    45|<script>
    46|export default {
    47|  data() { return { job: null, delivering: false, resumeDialog: false, selectedResume: 1 }; },
    48|  computed: {
    49|    user() { return this.$global.user; },
    50|    canDeliver() { return this.user && this.user.role === 1; }
    51|  },
    52|  mounted() { this.fetchDetail(); },
    53|  methods: {
    54|    fetchDetail() {
    55|      const id = this.$route.params.id;
    56|      this.$api.get('/positions/'+id).then(r => this.job = r.data);
    57|    },
    58|    deliver() {
    59|      if (!this.user) { this.$router.push('/login'); return; }
    60|      this.resumeDialog = true;
    61|    },
    62|    confirmDeliver() {
    63|      this.delivering = true;
    64|      this.$api.post('/deliveries', null, { params: { positionId: this.job.id, resumeId: this.selectedResume } }).then(() => {
    65|        this.$message.success('投递成功！');
    66|        this.resumeDialog = false;
    67|      }).catch(e => this.$message.error(e.message||'投递失败')).finally(() => this.delivering = false);
    68|    },
    69|    toggleFavorite() {
    70|      if (!this.user) { this.$router.push('/login'); return; }
    71|      if (this.job.isFavorited) {
    72|        this.$api.delete('/favorites/'+this.job.id).then(() => { this.job.isFavorited = false; this.$message.success('已取消收藏'); });
    73|      } else {
    74|        this.$api.post('/favorites', null, { params: { positionId: this.job.id } }).then(() => { this.job.isFavorited = true; this.$message.success('已收藏'); });
    75|      }
    76|    },
    77|    goCompany() { this.$router.push('/company/'+this.job.companyId); }
    78|  }
    79|};
    80|</script>
    81|