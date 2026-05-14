<template>
<div style="max-width:1000px;margin:20px auto;padding:0 20px" v-if="company">
  <el-card style="margin-bottom:20px">
    <div style="display:flex;align-items:center;gap:20px">
      <div>
        <h2>{{ company.name }}</h2>
        <div style="color:#666">{{ company.industry }} | {{ company.scale }}</div>
        <div style="color:#999;font-size:13px;margin-top:5px" v-if="company.address"><i class="el-icon-location" /> {{ company.address }}</div>
      </div>
    </div>
    <el-divider />
    <div style="white-space:pre-wrap;line-height:1.8">{{ company.description }}</div>
    <div v-if="company.website" style="margin-top:10px">官网: <a :href="company.website" target="_blank">{{ company.website }}</a></div>
  </el-card>
  <h3>在招职位 ({{ positions.length }})</h3>
  <el-card v-for="p in positions" :key="p.id" shadow="hover" style="margin-bottom:8px;cursor:pointer" @click.native="$router.push('/jobs/'+p.id)">
    <div style="display:flex;justify-content:space-between">
      <div><strong>{{ p.title }}</strong> | {{ p.workplace }} | {{ p.education }}</div>
      <div style="color:#1890ff">{{ p.salaryMin }}-{{ p.salaryMax }}K</div>
    </div>
  </el-card>
</div>
</template>
<script>
export default {
  data() { return { company: null, positions: [] }; },
  mounted() {
    const id = this.$route.params.id;
    this.$api.get('/company/'+id).then(r => this.company = r.data);
    this.$api.get('/company/'+id+'/positions').then(r => this.positions = r.data || []);
  }
};
</script>
