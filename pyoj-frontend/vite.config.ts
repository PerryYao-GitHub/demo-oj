import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { viteMockServe } from 'vite-plugin-mock'
import path from 'path'

export default defineConfig(({ command }) => {
  return {
    plugins: [
      vue(),
      viteMockServe({
        mockPath: 'src/mock',
        enable: command === 'serve', 
      })
    ],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src')
      }
    }
  }
})