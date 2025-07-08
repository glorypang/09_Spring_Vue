<script setup>
import TravelHeader from '@/components/travel/TravelHeader.vue';
import TravelImages from '@/components/travel/TravelImages.vue';
import TravelMap from '@/components/travel/TravelMap.vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/api/travelApi';
import { ref } from 'vue';

const cr = useRoute();
const router = useRouter();
const no = cr.params.no;
const travel = ref(null); // 처음에 조회된 객체가 없음을 확실히 명시

// 목록으로 돌아가기 함수
const back = () => {
  router.push({ name: 'travel/list', query: cr.query });
};

const load = async () => {
  travel.value = await api.get(no);

  // 문장 끝 마침표 뒤에 단락 구분 추가
  let descriptions = travel.value.description.replace(
    /\. /gm,
    (t) => t + '<p/><p>'
  );
  travel.value.description = `<p>${descriptions}</p>`;
};

load();
</script>
<template>
  <!-- 데이터가 로드된 후에만 표시하도록 조건부 렌더링 -->
  <div v-if="travel">
    <travel-header :travel="travel" />

    <!-- HTML 형식의 설명 렌더링 -->
    <div class="content" v-html="travel.description"></div>

    <!-- 이미지 갤러리 -->
    <travel-images :images="travel.images"></travel-images>

    <!-- 연락처 정보 -->
    <div>
      <i class="fa-solid fa-square-phone-flip"></i>
      전화번호: {{ travel.phone }}
    </div>

    <!-- 지도 위치 (추후 구현) -->
    <div class="my-5">
      <!-- 지도 컴포넌트 영역 -->
      <travel-map :title="travel.title" :address="travel.address" />
    </div>

    <!-- 네비게이션 버튼 -->
    <div class="my-5">
      <button class="btn btn-primary me-2" @click="back">
        <i class="fa-solid fa-list"></i> 목록
      </button>
    </div>
  </div>
</template>
