import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi, getUserInfo } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)

  const isLogin = computed(() => !!token.value)

  const login = async (loginData) => {
    try {
      const data = await loginApi(loginData)
      token.value = data.token
      userInfo.value = data.userInfo
      localStorage.setItem('token', data.token)
      localStorage.setItem('userInfo', JSON.stringify(data.userInfo))
      return data
    } catch (error) {
      throw error
    }
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  const fetchUserInfo = async () => {
    try {
      const data = await getUserInfo()
      userInfo.value = data
      localStorage.setItem('userInfo', JSON.stringify(data))
      return data
    } catch (error) {
      throw error
    }
  }

  const initUserInfo = () => {
    const savedUserInfo = localStorage.getItem('userInfo')
    if (savedUserInfo) {
      userInfo.value = JSON.parse(savedUserInfo)
    }
  }

  return {
    token,
    userInfo,
    isLogin,
    login,
    logout,
    fetchUserInfo,
    initUserInfo
  }
})
