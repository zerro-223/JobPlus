<template>
<div style="max-width:800px;margin:20px auto">
  <el-card>
    <h3>我的简历</h3>
    <el-tabs>
      <el-tab-pane label="基本信息">
        <el-form :model="form" label-width="100px">
          <el-form-item label="真实姓名"><el-input v-model="form.realName" /></el-form-item>
          <el-form-item label="性别"><el-radio-group v-model="form.gender"><el-radio label="男">男</el-radio><el-radio label="女">女</el-radio></el-radio-group></el-form-item>
          <el-form-item label="联系电话"><el-input v-model="form.phone" /></el-form-item>
          <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
          <el-form-item label="最高学历"><el-select v-model="form.education"><el-option label="大专" value="大专"/><el-option label="本科" value="本科"/><el-option label="硕士" value="硕士"/><el-option label="博士" value="博士"/></el-select></el-form-item>
          <el-form-item label="毕业院校"><el-input v-model="form.school" /></el-form-item>
          <el-form-item label="专业"><el-input v-model="form.major" /></el-form-item>
          <el-form-item label="毕业年份"><el-input v-model="form.graduationYear" placeholder="如2024" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveBasic" :loading="saving">保存基本信息</el-button></el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="教育经历">
        <div v-for="(e,i) in eduList" :key="i" style="margin-bottom:10px;padding:10px;border:1px solid #eee;border-radius:4px">
          <el-input v-model="e.school" placeholder="学校" size="small" style="width:200px;margin-right:8px" />
          <el-input v-model="e.major" placeholder="专业" size="small" style="width:150px;margin-right:8px" />
          <el-input v-model="e.degree" placeholder="学历" size="small" style="width:100px;margin-right:8px" />
          <el-input v-model="e.startDate" placeholder="开始 YYYY-MM" size="small" style="width:120px;margin-right:8px" />
          <el-input v-model="e.endDate" placeholder="结束 YYYY-MM" size="small" style="width:120px;margin-right:8px" />
          <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="eduList.splice(i,1)" />
        </div>
        <el-button size="small" @click="eduList.push({})">+ 添加教育经历</el-button>
        <el-button type="primary" size="small" @click="saveEdu" style="margin-left:10px">保存</el-button>
      </el-tab-pane>
      <el-tab-pane label="工作经历">
        <div v-for="(w,i) in workList" :key="i" style="margin-bottom:10px;padding:10px;border:1px solid #eee;border-radius:4px">
          <el-input v-model="w.company" placeholder="公司" size="small" style="width:200px;margin-right:8px" />
          <el-input v-model="w.position" placeholder="职位" size="small" style="width:150px;margin-right:8px" />
          <el-input v-model="w.startDate" placeholder="开始" size="small" style="width:120px;margin-right:8px" />
          <el-input v-model="w.endDate" placeholder="结束" size="small" style="width:120px;margin-right:8px" />
          <el-input v-model="w.description" placeholder="工作描述" size="small" style="width:300px;margin-right:8px" />
          <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="workList.splice(i,1)" />
        </div>
        <el-button size="small" @click="workList.push({})">+ 添加工作经历</el-button>
        <el-button type="primary" size="small" @click="saveWork" style="margin-left:10px">保存</el-button>
      </el-tab-pane>
      <el-tab-pane label="项目经验">
        <div v-for="(p,i) in projectList" :key="i" style="margin-bottom:10px;padding:10px;border:1px solid #eee;border-radius:4px">
          <el-input v-model="p.name" placeholder="项目名" size="small" style="width:200px;margin-right:8px" />
          <el-input v-model="p.role" placeholder="角色" size="small" style="width:120px;margin-right:8px" />
          <el-input v-model="p.description" placeholder="描述" size="small" style="width:300px;margin-right:8px" />
          <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="projectList.splice(i,1)" />
        </div>
        <el-button size="small" @click="projectList.push({})">+ 添加项目经验</el-button>
        <el-button type="primary" size="small" @click="saveProject" style="margin-left:10px">保存</el-button>
      </el-tab-pane>
      <el-tab-pane label="技能与评价">
        <el-form label-width="100px">
          <el-form-item label="技能标签"><el-input v-model="form.skillTags" placeholder="用逗号分隔，如 Java,Spring,Vue" /></el-form-item>
          <el-form-item label="自我评价"><el-input v-model="form.selfEvaluation" type="textarea" :rows="4" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveBasic">保存</el-button></el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="附件简历">
        <el-upload action="/api/v1/resume/attachment" :with-credentials="true" :on-success="onUploadSuccess" :before-upload="beforeUpload" drag>
          <i class="el-icon-upload" />
          <div>拖拽或点击上传 PDF/Word 文件</div>
        </el-upload>
        <div v-if="form.attachmentUrl" style="margin-top:10px">已上传: {{ form.attachmentName || form.attachmentUrl }}</div>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</div>
</template>
<script>
export default {
  data() { return { form: {}, eduList: [], workList: [], projectList: [], saving: false }; },
  mounted() {
    this.$api.get('/resume').then(r => {
      if (r.data) {
        this.form = r.data;
        this.eduList = r.data.educationList || [];
        this.workList = r.data.workList || [];
        this.projectList = r.data.projectList || [];
      }
    });
  },
  methods: {
    saveBasic() {
      this.saving = true;
      this.$api.put('/resume', this.form).then(() => this.$message.success('保存成功')).finally(() => this.saving=false);
    },
    saveEdu() { this.$api.put('/resume/education', this.eduList).then(() => this.$message.success('保存成功')); },
    saveWork() { this.$api.put('/resume/work', this.workList).then(() => this.$message.success('保存成功')); },
    saveProject() { this.$api.put('/resume/project', this.projectList).then(() => this.$message.success('保存成功')); },
    beforeUpload(file) { const isValid = file.size < 10*1024*1024; if(!isValid) this.$message.error('文件不能超过10MB'); return isValid; },
    onUploadSuccess(res) { this.form.attachmentUrl = res.data; this.$message.success('上传成功'); }
  }
};
</script>
