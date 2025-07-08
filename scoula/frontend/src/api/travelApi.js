import api from '@/api';

const BASE_URL = '/api/travel';

export default {
  // 여행지 목록 조회 (페이징 포함)
  async getList(params) {
    const { data } = await api.get(BASE_URL, { params });
    console.log('TRAVEL GET LIST: ', data);
    return data;
  },

  // 특정 여행지 상세 정보 조회
  async get(no) {
    const { data } = await api.get(`${BASE_URL}/${no}`);
    console.log('TRAVEL GET', data);
    return data;
  },
};
