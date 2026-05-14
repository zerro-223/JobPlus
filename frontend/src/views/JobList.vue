<template>
<div style="max-width:1200px;margin:20px auto;padding:0 20px">
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card>
        <h4>筛选条件</h4>
        <el-divider />
        <div style="margin-bottom:15px"><label>关键词</label><el-input v-model="filters.keyword" placeholder="职位/公司" size="small" /></div>
        <div style="margin-bottom:15px"><label>城市</label><el-input v-model="filters.city" placeholder="城市" size="small" /></div>
        <div style="margin-bottom:15px"><label>最低薪资(K)</label><el-input-number v-model="filters.salaryMin" :min="0" size="small" style="width:100%" /></div>
        <div style="margin-bottom:15px"><label>学历要求</label><el-select v-model="filters.education" size="small" style="width:100%" clearable><el-option label="大专" value="大专"/><el-option label="本科" value="本科"/><el-option label="硕士" value="硕士"/><el-option label="不限" value="不限"/></el-select></div>
        <div style="margin-bottom:15px"><label>职位分类</label><el-select v-model="filters.categoryId" size="small" style="width:100%" clearable><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></div>
        <el-button type="primary" size="small" style="width:100%" @click="search">筛选</el-button>
      </el-card>
    </el-col>
    <el-col :span="18">
      <div v-if="loading" style="text-align:center;padding:50px"><i class="el-icon-loading" style="font-size:30px" /></div>
      <div v-else>
        <el-card v-for="job in jobs" :key="job.id" shadow="hover" style="margin-bottom:12px;cursor:pointer" @click.native="$router.push('/jobs/'+job.id)">
          <div style="display:flex;justify-content:space-between;align-items:center">
            <div>
              <div style="font-size:16px;font-weight:bold;margin-bottom:5px">{{ job.title }} <el-tag size="mini" type="success" v-if="job.salaryMin">{{ job.salaryMin }}-{{ job.salaryMax }}K</el-tag></div>
              <div style="color:#666;font-size:13px">{{ job.companyName }} | {{ job.workplace }} | {{ job.education }} | {{ job.experience }}</div>
              <div style="color:#999;font-size:12px;margin-top:4px" v-if="job.tags"><el-tag size="mini" v-for="t in (job.tags||'').split(',')" :key="t" style="margin-right:4px">{{ t }}</el-tag></div>
            </div>
            <div style="color:#999;font-size:12px">{{ job.createdAt?.substring(0,10) }}</div>
          </div>
        </el-card>
        <div v-if="jobs.length===0" style="text-align:center;padding:60px;color:#999">暂未找到匹配的职位</div>
        <el-pagination v-if="total>0" style="text-align:center;margin-top:20px" background layout="prev,pager,next" :total="total" :page-size="filters.size" :current-page="filters.page" @current-change="p=>{filters.page=p;search()}" />
      </div>
    </el-col>
  </el-row>
</div>
</template>
<script>
export default {
  data() {
    const q = this.$route.query;
    return {
      filters: { keyword: q.keyword||'', city: q.city||'', salaryMin: null, education: '', categoryId: q.categoryId?Number(q.categoryId):null, page:1, size:10 },
      jobs: [], total: 0, loading: false, categories: []
    };
  },
  mounted() {
    this.$api.get('/positions/categories').then(r => this.categories = (r.data||[]).filter(c => c.parentId));
    this.search();
  },
  methods: {
    search() {
      this.loading = true;
      const params = { ...this.filters };
      if (!params.salaryMin) delete params.salaryMin;
      if (!params.education) delete params.education;
      if (!params.categoryId) delete params.categoryId;
      this.$api.get('/positions', { params }).then(r => {
        if (r.data) { this.jobs = r.data.list; this.total = r.data.total; }
      }).finally(() => this.loading = false);
    }
  }
};
</script>
