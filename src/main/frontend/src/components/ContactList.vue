<template>
  <div class="contact-list">
    <div class="header">
      <h4>联系人列表</h4>
    </div>
    <div class="contacts">
      <div
          v-for="contact in contacts"
          :key="contact.id"
          :class="['contact-item', activeContact === contact.id ? 'active' : '']"
          @click="selectContact(contact.id)"
      >
        <img :src="contact.avatar" class="avatar" alt="avatar">
        <div class="info">
          <div class="name">{{ contact.name }}</div>
          <div class="last-message">{{ contact.lastMessage }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['contacts', 'activeContact'],
  methods: {
    selectContact(contactId) {
      this.$emit('contact-selected', contactId);
    }
  }
}
</script>

<style scoped>
.contact-list {
  width: 400px;
  background: #fff;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
}

.header {
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.contacts {
  overflow-y: auto;
  height: calc(100vh - 60px);
}

.contact-item {
  padding: 12px 15px;
  cursor: pointer;
  display: flex;
  align-items: center;
  transition: background 0.2s;
}

.contact-item:hover {
  background: #f5f5f5;
}

.contact-item.active {
  background: #e8f5e9;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 12px;
}

.name {
  font-weight: bold;
}

.last-message {
  font-size: 0.9em;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>