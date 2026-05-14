<template>
<div style="display:flex;min-height:calc(100vh - 60px)">
  <div style="width:200px;background:#1a1a2e;color:#fff;padding:20px 0">
    <div style="padding:10px 20px;font-size:18px;font-weight:bold;margin-bottom:10px">管理后台</div>
    <div v-for="m in menus" :key="m.key" style="padding:12px 20px;cursor:pointer" :style="{background:current===m.key?'#1890ff':''}" @click="current=m.key">{{ m.label }}</div>
  </div>
  <div style="flex:1;padding:20px">
    <el-card v-if="current==='stats'"><h3>数据统计</h3>
      <el-row :gutter="20">
        <el-col :span="8"><el-card shadow="hover" style="text-align:center"><div style="font-size:30px;color:#1890ff">{{ stats.userCount }}</div><div>注册用户</div></el-card></el-col>
        <el-col :span="8"><el-card shadow="hover" style="text-align:center"><div style="font-size:30px;color:#52c41a">{{ stats.companyCount }}</div><div>企业数量</div></el-card></el-col>
        <el-col :span="8"><el-card shadow="hover" style="text-align:center"><div style="font-size:30px;color:#fa8c16">{{ stats.positionCount }}</div><div>职位数量</div></el-card></el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top:20px">
        <el-col :span="8"><el-card shadow="hover" style="text-align:center"><div style="font-size:30px;color:#722ed1">{{ stats.deliveryCount }}</div><div>投递总数</div></el-card></el-col>
        <el-col :span="8"><el-card shadow="hover" style="text-align:center"><div style="font-size:30px;color:#eb2f96">{{ stats.pendingCompanyCount }}</div><div>待审核企业</div></el-card></el-col>
        <el-col :span="8"><el-card shadow="hover" style="text-align:center"><div style="font-size:30px;color:#13c2c2">{{ stats.pendingPositionCount }}</div><div>待审核职位</div></el-card></el-col>
      </el-row>
    </el-card>

    <el-card v-if="current==='companies'"><h3>企业审核</h3>
      <el-table :data="pendingCompanies">
        <el-table-column prop="name" label="企业名称" />
        <el-table-column prop="industry" label="行业" />
        <el-table-column prop="contact" label="联系人" />
        <el-table-column label="操作"><template slot-scope="s">
          <el-button size="mini" type="success" @click="auditCompany(s.row.id,true)">通过</el-button>
          <el-button size="mini" type="danger" @click="rejectCompany(s.row)">驳回</el-button>
        </template></el-table-column>
      </el-table>
    </el-card>

    <el-card v-if="current==='positions'"><h3>职位审核</h3>
      <el-table :data="pendingPositions">
        <el-table-column prop="title" label="职位" />
        <el-table-column prop="companyName" label="企业" />
        <el-table-column label="操作"><template slot-scope="s">
          <el-button size="mini" type="success" @click="auditPos(s.row.id,true)">通过</el-button>
          <el-button size="mini" type="danger" @click="rejectPos(s.row)">驳回</el-button>
        </template></el-table-column>
      </el-table>
    </el-card>

    <el-card v-if="current==='users'"><h3>用户管理</h3>
      <el-table :data="users">
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column label="角色"><template slot-scope="s">{{ ['','求职者','企业','管理员'][s.row.role] }}</template></el-table-column>
        <el-table-column label="状态"><template slot-scope="s"><el-tag :type="s.row.status===1?'success':'danger'">{{ s.row.status===1?'正常':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作"><template slot-scope="s">
          <el-button size="mini" :type="s.row.status===1?'danger':''" @click="toggleUser(s.row)">{{ s.row.status===1?'禁用':'启用' }}</el-button>
        </template></el-table-column>
      </el-table>
    </el-card>
  </div>
</div>
</template>
<script>
export default {
  data() {
    return {
      current: 'stats',
      menus: [{key:'stats',label:'数据统计'},{key:'companies',label:'企业审核'},{key:'positions',label:'职位审核'},{key:'users',label:'用户管理'}],
      stats: {}, pendingCompanies: [], pendingPositions: [], users: []
    };
  },
  mounted() { this.loadStats(); },
  methods: {
    loadStats() { this.$api.get('/admin/stats').then(r => this.stats = r.data||{}); },
    loadPendingCompanies() { this.$api.get('/admin/companies/pending').then(r => this.pendingCompanies = r.data||[]); },
    loadPendingPositions() { this.$api.get('/admin/positions/pending').then(r => this.pendingPositions = r.data||[]); },
    loadUsers() { this.$api.get('/admin/users').then(r => this.users = r.data||[]); },
    auditCompany(id, approved) {
      this.$api.put('/admin/companies/'+id+'/audit', null, { params: { approved, rejectReason: '' } }).then(() => { this.$message.success('操作成功'); this.loadPendingCompanies(); });
    },
    rejectCompany(row) {
      this.$prompt('驳回原因', '驳回', { inputType: 'textarea' }).then(({value}) => {
        this.$api.put('/admin/companies/'+row.id+'/audit', null, { params: { approved: false, rejectReason: value } }).then(() => { this.$message.success('已驳回'); this.loadPendingCompanies(); });
      });
    },
    auditPos(id, approved) {
      this.$api.put('/admin/positions/'+id+'/audit', null, { params: { approved, rejectReason: '' } }).then(() => { this.$message.success('操作成功'); this.loadPendingPositions(); });
    },
    rejectPos(row) {
      this.$prompt('驳回原因', '驳回', { inputType: 'textarea' }).then(({value}) => {
        this.$api.put('/admin/positions/'+row.id+'/audit', null, { params: { approved: false, rejectReason: value } }).then(() => { this.$message.success('已驳回'); this.loadPendingPositions(); });
      });
    },
    toggleUser(row) {
      const newStatus = row.status === 1 ? 0 : 1;
      this.$api.put('/admin/users/'+row.id+'/status', null, { params: { status: newStatus } }).then(() => { row.status = newStatus; this.$message.success('已更新'); });
    }
  },
  watch: {
    current(v) {
      if (v === 'companies') this.loadPendingCompanies();
      if (v === 'positions') this.loadPendingPositions();
      if (v === 'users') this.loadUsers();
      if (v === 'stats') this.loadStats();
    }
  }
};
</script>
