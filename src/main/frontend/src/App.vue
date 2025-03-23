<template>
  <div id="app">
    <ChatWindow />
  </div>
</template>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
  background: #f0f2f5;
}

#app {
  height: 100vh;
}
</style>
<script setup lang="ts">
import ChatWindow from "@/components/ChatWindow.vue";
</script>