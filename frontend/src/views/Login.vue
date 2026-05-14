<template>
<div class="login-bg" style="display:flex;justify-content:center;align-items:center;min-height:100vh;background:linear-gradient(135deg,#1a1a2e,#16213e)">
  <el-card style="width:400px">
    <h2 style="text-align:center;margin-bottom:20px">JobPlus 登录</h2>
    <el-form :model="form" :rules="rules" ref="formRef">
      <el-form-item prop="email"><el-input v-model="form.email" placeholder="邮箱" prefix-icon="el-icon-message" /></el-form-item>
      <el-form-item prop="password"><el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="el-icon-lock" @keyup.enter="login" /></el-form-item>
      <el-form-item><el-button type="primary" style="width:100%" @click="login" :loading="loading">登录</el-button></el-form-item>
    </el-form>
    <div style="text-align:center"><router-link to="/register">还没有账号？立即注册</router-link></div>
  </el-card>
</div>
</template>
<script>
export default {
  data() {
    return { form: { email: '', password: '' }, rules: { email: [{required:true,message:'请输入邮箱'}], password: [{required:true,message:'请输入密码'}] }, loading: false };
  },
  methods: {
    login() {
      this.$refs.formRef.validate(valid => {
        if (!valid) return;
        this.loading = true;
        this.$api.post('/user/login', this.form).then(res => {
          const user = res.data;
          this.$user = user;
          sessionStorage.setItem('jobplus_user', JSON.stringify(user));
          this.$message.success('登录成功');
          this.$router.push('/');
        }).catch(e => { this.$message.error(e.message || '登录失败'); }).finally(() => this.loading=false);
      });
    }
  }
};
</script>
