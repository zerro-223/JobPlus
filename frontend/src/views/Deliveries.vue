<template>
<div style="max-width:1000px;margin:20px auto">
  <el-card><h3>投递记录</h3>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="positionTitle" label="职位" />
      <el-table-column prop="companyName" label="公司" />
      <el-table-column label="状态">
        <template slot-scope="s"><el-tag :type="statusType(s.row.status)">{{ statusText(s.row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createdAt" label="投递时间"><template slot-scope="s">{{ (s.row.createdAt||'').substring(0,10) }}</template></el-table-column>
      <el-table-column label="操作"><template slot-scope="s"><el-button size="mini" @click="$router.push('/jobs/'+s.row.positionId)">查看</el-button></template></el-table-column>
    </el-table>
  </el-card>
</div>
</template>
<script>
export default {
  data() { return { list: [], loading: false }; },
  mounted() { this.fetch(); },
  methods: {
    fetch() {
      this.loading = true;
      this.$api.get('/deliveries').then(r => this.list = r.data || []).finally(() => this.loading = false);
    },
    statusText(s) { return ['已投递','被查看','邀面试','不合适','已取消'][s] || '未知'; },
    statusType(s) { return ['info','','success','danger','warning'][s] || 'info'; }
  }
};
</script>
