<script setup>
import { useAuthStore } from '@/stores/auth';
import { computed } from 'vue';

const props = defineProps({ username: String });
// const avatar = `/api/member/${props.username}/avatar`;
const auth = useAuthStore();
const avatar = computed(() => auth.avatarUrl);
</script>

<template>
  <li class="nav-item">
    <router-link class="nav-link" to="/auth/profile">
      <img :src="avatar" class="avatar avatar-sm" />
      {{ username }}
    </router-link>
  </li>
</template>
