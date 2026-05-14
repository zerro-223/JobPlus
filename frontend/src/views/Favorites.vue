<template>
<div style="max-width:1000px;margin:20px auto">
  <el-card><h3>我的收藏</h3>
    <div v-if="list.length===0" style="text-align:center;padding:60px;color:#999">暂无收藏</div>
    <el-card v-for="j in list" :key="j.id" shadow="hover" style="margin-bottom:10px">
      <div style="display:flex;justify-content:space-between;align-items:center">
        <div>
          <div style="font-size:15px;font-weight:bold;cursor:pointer" @click="$router.push('/jobs/'+j.id)">{{ j.title }}</div>
          <div style="color:#666;font-size:13px">{{ j.companyName }} | {{ j.workplace }} | {{ j.salaryMin }}-{{ j.salaryMax }}K</div>
        </div>
        <el-button size="mini" @click="remove(j)">取消收藏</el-button>
      </div>
    </el-card>
  </el-card>
</div>
</template>
<script>
export default {
  data() { return { list: [] }; },
  mounted() { this.fetch(); },
  methods: {
    fetch() { this.$api.get('/favorites').then(r => this.list = r.data || []); },
    remove(j) { this.$api.delete('/favorites/'+j.id).then(() => { this.list = this.list.filter(x => x.id !== j.id); this.$message.success('已取消'); }); }
  }
};
</script>
