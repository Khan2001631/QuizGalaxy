import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [tailwindcss()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Backend
        changeOrigin: true,
        secure: false,
      },
    },
    cors: true,
    origin: 'http://localhost:5173'
  }
});
