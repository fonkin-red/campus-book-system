<template>
  <div class="home-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <div class="logo">
            <el-icon :size="30" color="#fff"><Reading /></el-icon>
            <span>校园二手图书交易平台</span>
          </div>
          <div class="user-actions">
            <span class="welcome">欢迎，{{ userInfo?.username }}</span>
            <el-button type="danger" @click="handleLogout">退出登录</el-button>
          </div>
        </div>
      </el-header>

      <el-main>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-card class="menu-card">
              <template #header>
                <div class="card-header">
                  <span>功能菜单</span>
                </div>
              </template>
              <el-menu
                :default-active="activeMenu"
                class="el-menu-vertical"
                @select="handleMenuSelect"
              >
                <el-menu-item index="profile">
                  <el-icon><User /></el-icon>
                  <span>个人中心</span>
                </el-menu-item>
                <el-menu-item index="books">
                  <el-icon><Reading /></el-icon>
                  <span>图书列表</span>
                </el-menu-item>
                <el-menu-item index="my-books">
                  <el-icon><Box /></el-icon>
                  <span>我的发布</span>
                </el-menu-item>
                <el-menu-item index="orders">
                  <el-icon><Document /></el-icon>
                  <span>我的订单</span>
                </el-menu-item>
              </el-menu>
            </el-card>
          </el-col>

          <el-col :span="18">
            <el-card class="content-card">
              <template #header>
                <div class="card-header">
                  <span>欢迎来到校园二手图书交易平台</span>
                </div>
              </template>
              <div class="welcome-content">
                <el-empty description="功能开发中，敬请期待">
                  <template #image>
                    <el-icon :size="100" color="#909399"><Reading /></el-icon>
                  </template>
                  <el-button type="primary" @click="router.push('/profile')">
                    前往个人中心
                  </el-button>
                </el-empty>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Reading, User, Box, Document } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const activeMenu = ref('')

const userInfo = computed(() => userStore.userInfo)

onMounted(() => {
  userStore.initUserInfo()
})

const handleMenuSelect = (index) => {
  activeMenu.value = index
  if (index === 'profile') {
    router.push('/profile')
  } else {
    ElMessage.info('功能开发中，敬请期待')
  }
}

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  } catch (error) {
    // 用户取消操作
  }
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
}

.el-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  display: flex;
  align-items: center;
  padding: 0 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 20px;
  font-weight: bold;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.welcome {
  font-size: 14px;
}

.el-main {
  padding: 20px;
}

.menu-card {
  height: calc(100vh - 180px);
}

.content-card {
  height: calc(100vh - 180px);
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}

.welcome-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 400px;
}
</style>
