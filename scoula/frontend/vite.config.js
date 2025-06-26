// Node.js의 URL 유틸리티에서 파일 경로 변환 함수들을 가져옴
// 용도: @ 별칭 설정 시 절대 경로 생성에 사용
import { fileURLToPath, URL } from 'node:url';

// Vite 설정을 정의하는 함수를 가져옴
// - Vite 설정 객체를 정의하고 타입 검사 지원
// - 장점: IDE에서 자동완성과 타입 체크 제공
// - 단점: 초기 설정 시 타입 정의 필요
import { defineConfig } from 'vite';

// Vue.js를 위한 Vite 플러그인을 가져옴
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },

  // 개발 서버 설정
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 백엔드 서버 연결
      },
    },
  },

  // 빌드 설정
  build: {
    outDir:
        'C:/Users/dys11/Desktop/KB IT\'s your life/09 Spring - Vue 연동/practice/scoula/backend/apiserver/src/main/webapp/resources',
  },
});