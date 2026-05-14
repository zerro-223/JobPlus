<template>
<div style="max-width:700px;margin:20px auto">
  <el-card>
    <div style="display:flex;justify-content:space-between;margin-bottom:15px"><h3>消息通知</h3><el-button size="small" @click="readAll">全部已读</el-button></div>
    <div v-for="n in list" :key="n.id" style="padding:12px;border-bottom:1px solid #eee;background:n.isRead?'#fff':'#f0f7ff';cursor:pointer" @click="markRead(n)">
      <div style="font-weight:bold;margin-bottom:4px">{{ n.title }} <el-tag size="mini" v-if="!n.isRead">未读</el-tag></div>
      <div style="color:#666;font-size:13px">{{ n.content }}</div>
      <div style="color:#999;font-size:12px">{{ (n.createdAt||'').substring(0,16) }}</div>
    </div>
    <div v-if="list.length===0" style="text-align:center;padding:60px;color:#999">暂无通知</div>
  </el-card>
</div>
</template>
<script>
export default {
  data() { return { list: [] }; },
  mounted() { this.fetch(); },
  methods: {
    fetch() { this.$api.get('/notifications').then(r => this.list = r.data || []); },
    markRead(n) { if (!n.isRead) this.$api.put('/notifications/'+n.id+'/read').then(() => { n.isRead = true; }); },
    readAll() { this.$api.put('/notifications/read-all').then(() => { this.list.forEach(n => n.isRead = true); this.$message.success('已全部标为已读'); }); }
  }
};
</script>
