<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  images: { Type: Array, required: true },
});

const activeIndex = ref(0);
const activeImage = computed(() => props.images[activeIndex.value]?.url);

// 썸네일 클릭 처리
const onClick = (ix) => {
  activeIndex.value = ix;
};
</script>

<template>
  <div class="w-100 my-4">
    <!-- 메인 이미지 -->
    <img :src="activeImage" style="width: 100%; height: 400px" />

    <!-- 썸네일 목록 -->
    <div class="d-flex">
      <div class="flex-fill" v-for="(image, ix) in images" :key="image.no">
        <img
          class="thumbnail"
          :src="image.url"
          style="width: 100%; height: 100px"
          @click="onClick(ix)"
          :class="{ active: activeIndex === ix }"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
img {
  object-fit: cover; /* 이미지 비율 유지 */
}

.thumbnail {
  cursor: pointer; /* 클릭 가능 표시 */
}

.active {
  border: 3px solid red; /* 활성 썸네일 표시 */
}
</style>
