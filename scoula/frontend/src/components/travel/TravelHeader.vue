<script setup>
import { computed } from 'vue';

const props = defineProps({
  travel: { type: Object, required: true },
});

// 첫 번째 이미지를 배경 이미지로 설정
const background = computed(() => {
  // 이미지가 있는지 확인 후 설정
  if (props.travel?.images?.[0]?.url) {
    return {
      backgroundImage: `url('${props.travel.images[0].url}')`,
    };
  }

  // 이미지가 없으면 기본 배경색
  return {
    backgroundColor: '#6c757d',
  };
});
</script>

<template>
  <div
    class="travel-header mb-4 d-flex justify-content-center"
    :style="background"
  >
    <h1 class="align-content-center">
      [{{ travel.district }}] {{ travel.title }}
    </h1>
  </div>
</template>

<style scoped>
.travel-header {
  height: 300px;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  color: white;
}
</style>
