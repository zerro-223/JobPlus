<template>
<div style="max-width:600px;margin:20px auto">
  <el-card>
    <h3>个人信息</h3>
    <el-form :model="form" label-width="80px">
      <el-form-item label="邮箱"><el-input v-model="form.email" disabled /></el-form-item>
      <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
      <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
      <el-form-item><el-button type="primary" @click="save" :loading="saving">保存</el-button></el-form-item>
    </el-form>
    <el-divider />
    <h3>修改密码</h3>
    <el-form :model="pwdForm" label-width="80px">
      <el-form-item label="旧密码"><el-input v-model="pwdForm.oldPwd" type="password" /></el-form-item>
      <el-form-item label="新密码"><el-input v-model="pwdForm.newPwd" type="password" /></el-form-item>
      <el-form-item><el-button @click="changePwd" :loading="changing">修改密码</el-button></el-form-item>
    </el-form>
  </el-card>
</div>
</template>
<script>
export default {
  data() { return { form: { email:'', nickname:'', phone:'' }, pwdForm: { oldPwd:'', newPwd:'' }, saving: false, changing: false }; },
  mounted() {
    this.$api.get('/user/profile').then(r => { Object.assign(this.form, r.data); });
  },
  methods: {
    save() {
      this.saving = true;
      this.$api.put('/user/profile', this.form).then(() => this.$message.success('保存成功')).finally(() => this.saving=false);
    },
    changePwd() {
      if (!this.pwdForm.oldPwd || !this.pwdForm.newPwd) return this.$message.warning('请填写密码');
      this.changing = true;
      this.$api.put('/user/password', null, { params: this.pwdForm }).then(() => { this.$message.success('密码修改成功'); this.pwdForm = {oldPwd:'',newPwd:''}; }).finally(() => this.changing=false);
    }
  }
};
</script>
