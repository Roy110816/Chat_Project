<template>
  <div class="input-area">
    <textarea
        v-model="inputText"
        @keyup.enter="sendMessage"
        placeholder="输入消息..."
    ></textarea>
    <button @click="sendMessage">发送</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inputText: '',
    }
  },
  methods: {
    sendMessage() {
      if (this.inputText.trim()) {
        this.$emit('send-message', {
          content: this.inputText,
          timestamp: Date.now(),
          isSelf: true,
          avatar: 'https://example.com/avatar.jpg' // 替换为实际头像地址
        });
        this.inputText = '';
      }
    }
  }
}
</script>

<style scoped>
.input-area {
  display: flex;
  padding: 20px;
  border-top: 1px solid #eee;
}

textarea {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: none;
}

button {
  margin-left: 10px;
  padding: 8px 20px;
  background: #07c160;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>