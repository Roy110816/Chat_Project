<template>
  <div class="message-list">
    <div
        v-for="(msg, index) in messages"
        :key="index"
        :class="['message', msg.isSelf ? 'self' : 'other']"
    >
      <img :src="msg.avatar" class="avatar" alt="avatar">
      <div class="bubble">
        <div class="content">{{ msg.content }}</div>
        <div class="time">{{ formatTime(msg.timestamp) }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['messages'],
  methods: {
    formatTime(timestamp) {
      const date = new Date(timestamp);
      return date.toLocaleTimeString();
    }
  }
}
</script>

<style scoped>
.message-list {
  padding: 20px;
  height: 70vh;
  overflow-y: auto;
}

.message {
  display: flex;
  margin-bottom: 15px;
}

.message.self {
  justify-content: flex-end;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.bubble {
  max-width: 60%;
  background: #95ec69;
  padding: 10px 15px;
  border-radius: 8px;
  position: relative;
}

.message.other .bubble {
  background: #fff;
  margin-left: 10px;
}

.time {
  font-size: 0.8em;
  color: #666;
  margin-top: 5px;
  text-align: right;
}
</style>