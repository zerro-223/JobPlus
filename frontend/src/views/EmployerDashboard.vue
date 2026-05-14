<template>
<div style="display:flex;min-height:calc(100vh - 60px)">
  <div style="width:200px;background:#1a1a2e;color:#fff;padding:20px 0">
    <div style="padding:10px 20px;font-size:18px;font-weight:bold;margin-bottom:10px">企业后台</div>
    <div v-for="m in menus" :key="m.key" style="padding:12px 20px;cursor:pointer" :style="{background:current===m.key?'#1890ff':''}" @click="current=m.key">{{ m.label }}</div>
  </div>
  <div style="flex:1;padding:20px;overflow-y:auto">
    <!-- 企业信息 -->
    <el-card v-if="current==='profile'"><h3>企业信息</h3>
      <el-form :model="company" label-width="100px">
        <el-form-item label="企业全称"><el-input v-model="company.name" /></el-form-item>
        <el-form-item label="简称"><el-input v-model="company.shortName" /></el-form-item>
        <el-form-item label="行业"><el-input v-model="company.industry" /></el-form-item>
        <el-form-item label="规模"><el-select v-model="company.scale"><el-option label="1-49人" value="1-49人"/><el-option label="50-99人" value="50-99人"/><el-option label="100-499人" value="100-499人"/><el-option label="500-999人" value="500-999人"/><el-option label="1000人以上" value="1000人以上"/></el-select></el-form-item>
        <el-form-item label="简介"><el-input v-model="company.description" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="company.address" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="company.contact" /></el-form-item>
        <el-form-item label="联系电话"><el-input v-model="company.contactPhone" /></el-form-item>
        <el-form-item><el-button type="primary" @click="saveCompany">保存</el-button></el-form-item>
      </el-form>
    </el-card>

    <!-- 认证资料 -->
    <el-card v-if="current==='cert'"><h3>企业认证</h3>
      <el-form :model="cert" label-width="100px">
        <el-form-item label="营业执照"><el-input v-model="cert.licenseUrl" placeholder="营业执照图片路径" /></el-form-item>
        <el-form-item label="信用代码"><el-input v-model="cert.licenseNumber" /></el-form-item>
        <el-form-item label="法定代表人"><el-input v-model="cert.legalPerson" /></el-form-item>
        <el-form-item><el-button type="primary" @click="submitCert">提交认证</el-button></el-form-item>
      </el-form>
    </el-card>

    <!-- 职位管理 -->
    <el-card v-if="current==='positions'"><h3>职位管理</h3>
      <el-button type="primary" style="margin-bottom:15px" @click="showPosForm=true">发布新职位</el-button>
      <el-table :data="positions">
        <el-table-column prop="title" label="职位" />
        <el-table-column label="状态"><template slot-scope="s"><el-tag :type="s.row.status===1?'success':s.row.status===0?'warning':'info'">{{ ['待审核','招聘中','已下线','审核驳回'][s.row.status] }}</el-tag></template></el-table-column>
        <el-table-column label="投递数"><template slot-scope="s">{{ s.row.deliveryCount }}</template></el-table-column>
        <el-table-column label="操作"><template slot-scope="s"><el-button size="mini" @click="offline(s.row.id)" v-if="s.row.status===1">下线</el-button></template></el-table-column>
      </el-table>
      <el-dialog title="发布职位" :visible.sync="showPosForm" width="600px">
        <el-form :model="posForm" label-width="80px">
          <el-form-item label="标题"><el-input v-model="posForm.title" /></el-form-item>
          <el-form-item label="分类"><el-input-number v-model="posForm.categoryId" :min="1" /></el-form-item>
          <el-form-item label="薪资下限K"><el-input-number v-model="posForm.salaryMin" /></el-form-item>
          <el-form-item label="薪资上限K"><el-input-number v-model="posForm.salaryMax" /></el-form-item>
          <el-form-item label="学历"><el-input v-model="posForm.education" /></el-form-item>
          <el-form-item label="经验"><el-input v-model="posForm.experience" /></el-form-item>
          <el-form-item label="地点"><el-input v-model="posForm.workplace" /></el-form-item>
          <el-form-item label="标签"><el-input v-model="posForm.tags" placeholder="逗号分隔" /></el-form-item>
          <el-form-item label="描述"><el-input v-model="posForm.description" type="textarea" :rows="6" /></el-form-item>
          <el-form-item label="要求"><el-input v-model="posForm.requirement" type="textarea" :rows="6" /></el-form-item>
        </el-form>
        <span slot="footer"><el-button @click="showPosForm=false">取消</el-button><el-button type="primary" @click="publishPos">发布</el-button></span>
      </el-dialog>
    </el-card>

    <!-- 收到的简历 -->
    <el-card v-if="current==='deliveries'"><h3>收到的简历</h3>
      <el-table :data="deliveries">
        <el-table-column prop="candidateName" label="候选人" />
        <el-table-column prop="positionTitle" label="投递职位" />
        <el-table-column label="状态"><template slot-scope="s"><el-tag>{{ ['已投递','被查看','邀面试','不合适'][s.row.status] }}</el-tag></template></el-table-column>
        <el-table-column label="时间"><template slot-scope="s">{{ (s.row.createdAt||'').substring(0,10) }}</template></el-table-column>
        <el-table-column label="操作"><template slot-scope="s">
          <el-button size="mini" @click="updateDelStatus(s.row.id,2)">邀面试</el-button>
          <el-button size="mini" type="danger" @click="updateDelStatus(s.row.id,3)">不合适</el-button>
        </template></el-table-column>
      </el-table>
    </el-card>

    <!-- 面试管理 -->
    <el-card v-if="current==='interviews'"><h3>面试管理</h3>
      <el-table :data="interviews">
        <el-table-column label="面试时间"><template slot-scope="s">{{ (s.row.interviewTime||'').substring(0,16) }}</template></el-table-column>
        <el-table-column prop="location" label="地点" />
        <el-table-column label="状态"><template slot-scope="s"><el-tag>{{ ['待确认','已接受','已拒绝','已完成'][s.row.status] }}</el-tag></template></el-table-column>
      </el-table>
    </el-card>
  </div>
</div>
</template>
<script>
export default {
  data() {
    return {
      current: 'profile',
      menus: [
        { key: 'profile', label: '企业信息' }, { key: 'cert', label: '企业认证' },
        { key: 'positions', label: '职位管理' }, { key: 'deliveries', label: '收到的简历' },
        { key: 'interviews', label: '面试管理' }
      ],
      company: {}, cert: {}, positions: [], deliveries: [], interviews: [],
      showPosForm: false,
      posForm: { title:'', categoryId:null, salaryMin:null, salaryMax:null, education:'本科', experience:'1-3年', workplace:'', tags:'', description:'', requirement:'' }
    };
  },
  mounted() { this.loadProfile(); },
  methods: {
    loadProfile() {
      this.$api.get('/company/profile').then(r => { if (r.data) this.company = r.data; });
    },
    saveCompany() { this.$api.put('/company/profile', this.company).then(() => this.$message.success('保存成功')); },
    submitCert() { this.$api.post('/company/certify', this.cert).then(() => this.$message.success('认证资料已提交')); },
    publishPos() {
      this.$api.post('/positions', this.posForm).then(() => { this.$message.success('职位已发布，等待审核'); this.showPosForm = false; this.loadPositions(); });
    },
    loadPositions() { this.$api.get('/company/'+(this.company.id||1)+'/positions').then(r => this.positions = r.data||[]); },
    loadDeliveries() { this.$api.get('/company/deliveries').then(r => this.deliveries = r.data||[]); },
    loadInterviews() { this.$api.get('/interviews').then(r => this.interviews = r.data||[]); },
    offline(id) { this.$api.put('/positions/'+id+'/status', null, { params: { status: 2 } }).then(() => { this.$message.success('已下线'); this.loadPositions(); }); },
    updateDelStatus(id, status) { this.$api.put('/deliveries/'+id+'/status', null, { params: { status } }).then(() => { this.$message.success('已更新'); this.loadDeliveries(); }); }
  },
  watch: {
    current(v) {
      if (v === 'positions') this.loadPositions();
      if (v === 'deliveries') this.loadDeliveries();
      if (v === 'interviews') this.loadInterviews();
      if (v === 'profile') this.loadProfile();
    }
  }
};
</script>
