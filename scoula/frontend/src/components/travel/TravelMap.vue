<script setup>
import { reactive, ref } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';

// Props 정의 - 부모 컴포넌트에서 제목과 주소 전달받기
const props = defineProps({
  title: { type: String, required: true },    // 여행지 제목
  address: { type: String, required: true },  // 여행지 주소
});

// 서울 시청 좌표 (기본값)
const coordinate = reactive({
  lat: 37.566826,    // 위도
  lng: 126.9786567,  // 경도
});
</script>

<template>
  <div>
    <i class="fa-solid fa-map-location-dot"></i> 주소: {{ address }}
  </div>
  <kakao-map
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :level="3"
    :draggable="true"
    style="width: 100%">
    <kakao-map-marker
      :lat="coordinate.lat"
      :lng="coordinate.lng" />
  </kakao-map>
</template>