<template>
<div style="display:flex;justify-content:center;align-items:center;min-height:100vh;background:linear-gradient(135deg,#1a1a2e,#16213e)">
  <el-card style="width:450px">
    <h2 style="text-align:center;margin-bottom:20px">注册 JobPlus 账号</h2>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
      <el-form-item label="邮箱" prop="email"><el-input v-model="form.email" placeholder="用于登录" /></el-form-item>
      <el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" placeholder="选填" /></el-form-item>
      <el-form-item label="密码" prop="password"><el-input v-model="form.password" type="password" placeholder="至少6位" /></el-form-item>
      <el-form-item label="确认密码" prop="confirmPwd"><el-input v-model="form.confirmPwd" type="password" /></el-form-item>
      <el-form-item label="角色" prop="role">
        <el-radio-group v-model="form.role">
          <el-radio :label="1">求职者</el-radio>
          <el-radio :label="2">企业招聘方</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname"><el-input v-model="form.nickname" placeholder="选填" /></el-form-item>
      <el-form-item><el-button type="primary" style="width:100%" @click="register" :loading="loading">注册</el-button></el-form-item>
    </el-form>
    <div style="text-align:center"><router-link to="/login">已有账号？去登录</router-link></div>
  </el-card>
</div>
</template>
<script>
export default {
  data() {
    const validatePass = (rule, value, cb) => {
      if (value !== this.form.password) cb(new Error('两次密码不一致')); else cb();
    };
    return {
      form: { email:'', phone:'', password:'', confirmPwd:'', role:1, nickname:'' },
      rules: {
        email: [{required:true,message:'请输入邮箱'},{type:'email',message:'邮箱格式不正确'}],
        password: [{required:true,min:6,message:'密码至少6位'}],
        confirmPwd: [{required:true,validator:validatePass}],
        role: [{required:true}]
      },
      loading: false
    };
  },
  methods: {
    register() {
      this.$refs.formRef.validate(valid => {
        if (!valid) return;
        this.loading = true;
        this.$api.post('/user/register', this.form).then(() => {
          this.$message.success('注册成功，请登录');
          this.$router.push('/login');
        }).catch(e => this.$message.error(e.message||'注册失败')).finally(() => this.loading=false);
      });
    }
  }
};
</script>
