<template>
  <div class="chat-container">
    <!-- 添加本人头像区域 -->
    <div class="my-avatar">
      <img :src="myAvatar" alt="My Avatar" class="avatar" @click="handleMyAvatarClick" />
      <!-- 添加人的矢量图标 -->
      <el-icon>
        <UserFilled class="el-icon-user-solid"/>
      </el-icon>
    </div>
    <div class="contact">
      <ContactList
          :contacts="contacts"
          :activeContact="activeContact"
          @contact-selected="handleContactSelect"
      />
    </div>
    <div class="chat-main">
      <div class="header">
        <h3>{{ activeContactName || '选择联系人开始聊天' }}</h3>
      </div>
      <MessageList :messages="messages" />
      <InputArea @send-message="handleMessageSend" />
    </div>
  </div>
</template>

<script>
import MessageList from './MessageList.vue';
import InputArea from './InputArea.vue';
import ContactList from './ContactList.vue';

export default {
  components: {
    MessageList,
    InputArea,
    ContactList
  },
  name: 'ChatWindow',
  data() {
    return {
      activeContact: null,
      contacts: [],
      messages: []
    }
  },
  computed: {
    activeContactName() {
      const contact = this.contacts.find(c => c.id === this.activeContact);
      return contact ? contact.name : '';
    }
  },
  methods: {
    handleContactSelect(contactId) {
      this.activeContact = contactId;
      // 这里可以添加加载历史消息的逻辑
    },
    handleMessageSend(message) {
      // 更新消息内容关联当前联系人
      message.contactId = this.activeContact;

      // 模拟接收消息
      setTimeout(() => {
        this.messages.push({
          ...message,
          isSelf: false,
          avatar: 'https://example.com/other-avatar.jpg',
          contactId: this.activeContact
        });
      }, 1000);

      this.messages.push({
        ...message,
        contactId: this.activeContact
      });
    }
  }
}
</script>

<style scoped>
.chat-container {
  display: flex;
  justify-content: center
}
.my-avatar {
  width: 80px; /* 调整头像区域宽度 */
  display: flex;
  flex-direction: column;  /*设置为垂直排列 */
  align-items: center;
  padding: 10px;
  background-color: rgb(119, 129, 133);
}
.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
}
.contact {
  width: 400px;
}
.chat-main {
  display: flex;
  flex-direction: column;
  width: 600px;
}

.header {
  background: #f5f5f5;
  padding: 15px;
  border-bottom: 1px solid #eee;
}
</style>