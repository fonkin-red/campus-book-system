<template>
  <div class="profile-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <div class="logo">
            <el-icon :size="30" color="#fff"><Reading /></el-icon>
            <span>校园二手图书交易平台</span>
          </div>
          <div class="user-actions">
            <span class="welcome">欢迎，{{ userInfo?.username }}</span>
            <el-button type="primary" @click="router.push('/home')">返回首页</el-button>
            <el-button type="danger" @click="handleLogout">退出登录</el-button>
          </div>
        </div>
      </el-header>

      <el-main>
        <el-card class="profile-card">
          <template #header>
            <div class="card-header">
              <span>个人中心</span>
            </div>
          </template>

          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本信息" name="info">
              <el-form
                ref="profileFormRef"
                :model="profileForm"
                :rules="profileRules"
                label-width="100px"
                class="profile-form"
              >
                <el-form-item label="用户ID">
                  <el-input v-model="userInfo?.id" disabled />
                </el-form-item>

                <el-form-item label="用户名" prop="username">
                  <el-input v-model="profileForm.username" placeholder="请输入用户名" />
                </el-form-item>

                <el-form-item label="手机号" prop="phone">
                  <el-input v-model="profileForm.phone" placeholder="请输入手机号" />
                </el-form-item>

                <el-form-item label="头像URL">
                  <el-input v-model="profileForm.avatar" placeholder="请输入头像URL" />
                </el-form-item>

                <el-form-item label="注册时间">
                  <el-input :value="formatDate(userInfo?.createTime)" disabled />
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" :loading="updateLoading" @click="handleUpdateInfo">
                    保存修改
                  </el-button>
                  <el-button @click="resetProfileForm">重置</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>

            <el-tab-pane label="修改密码" name="password">
              <el-form
                ref="passwordFormRef"
                :model="passwordForm"
                :rules="passwordRules"
                label-width="100px"
                class="password-form"
              >
                <el-form-item label="旧密码" prop="oldPassword">
                  <el-input
                    v-model="passwordForm.oldPassword"
                    type="password"
                    placeholder="请输入旧密码"
                    show-password
                  />
                </el-form-item>

                <el-form-item label="新密码" prop="newPassword">
                  <el-input
                    v-model="passwordForm.newPassword"
                    type="password"
                    placeholder="请输入新密码（6-20位字母、数字或下划线）"
                    show-password
                  />
                </el-form-item>

                <el-form-item label="确认密码" prop="confirmPassword">
                  <el-input
                    v-model="passwordForm.confirmPassword"
                    type="password"
                    placeholder="请再次输入新密码"
                    show-password
                  />
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" :loading="passwordLoading" @click="handleChangePassword">
                    修改密码
                  </el-button>
                  <el-button @click="resetPasswordForm">重置</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Reading } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { updateUserInfo, changePassword } from '@/api/auth'

const router = useRouter()
const userStore = useUserStore()

const activeTab = ref('info')
const profileFormRef = ref(null)
const passwordFormRef = ref(null)
const updateLoading = ref(false)
const passwordLoading = ref(false)

const userInfo = computed(() => userStore.userInfo)

const profileForm = reactive({
  username: '',
  phone: '',
  avatar: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入新密码'))
  } else if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const profileRules = {
  username: [
    {
      pattern: /^[a-zA-Z0-9_]{4,20}$/,
      message: '用户名必须是4-20位的字母、数字或下划线',
      trigger: 'blur'
    }
  ],
  phone: [
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '请输入正确的手机号',
      trigger: 'blur'
    }
  ]
}

const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入旧密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    {
      pattern: /^[a-zA-Z0-9_]{6,20}$/,
      message: '密码必须是6-20位的字母、数字或下划线',
      trigger: 'blur'
    }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

onMounted(async () => {
  userStore.initUserInfo()
  if (userInfo.value) {
    initProfileForm()
  } else {
    try {
      await userStore.fetchUserInfo()
      initProfileForm()
    } catch (error) {
      console.error('获取用户信息失败:', error)
    }
  }
})

const initProfileForm = () => {
  if (userInfo.value) {
    profileForm.username = userInfo.value.username || ''
    profileForm.phone = userInfo.value.phone || ''
    profileForm.avatar = userInfo.value.avatar || ''
  }
}

const handleUpdateInfo = async () => {
  if (!profileFormRef.value) return

  await profileFormRef.value.validate(async (valid) => {
    if (valid) {
      updateLoading.value = true
      try {
        await updateUserInfo(profileForm)
        await userStore.fetchUserInfo()
        ElMessage.success('修改成功')
      } catch (error) {
        console.error('修改失败:', error)
      } finally {
        updateLoading.value = false
      }
    }
  })
}

const resetProfileForm = () => {
  initProfileForm()
}

const handleChangePassword = async () => {
  if (!passwordFormRef.value) return

  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      passwordLoading.value = true
      try {
        await changePassword({
          oldPassword: passwordForm.oldPassword,
          newPassword: passwordForm.newPassword
        })
        ElMessage.success('密码修改成功，请重新登录')
        userStore.logout()
        router.push('/login')
      } catch (error) {
        console.error('修改密码失败:', error)
      } finally {
        passwordLoading.value = false
      }
    }
  })
}

const resetPasswordForm = () => {
  passwordFormRef.value?.resetFields()
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

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN')
}
</script>

<style scoped>
.profile-container {
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

.profile-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}

.profile-form,
.password-form {
  max-width: 500px;
  margin: 20px auto;
}
</style>
