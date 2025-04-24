import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  optimizeDeps: {
    include: ['@element-plus/icons-vue']
  },
  base: '/', // 确保不为空
  plugins: [vue(
    {
      template: {
        compilerOptions: {
          // 解决 Element Plus 组件白屏问题
          isCustomElement: (tag) => tag.startsWith('el-')
        }
      }
    }
  )],
  server: {
    host: true, // 允许外部访问
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Spring Boot 默认端口
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  esbuild: {
    exclude: ['**/*.vue']
  },
  define: {
    'process.env': {}
  }
})
