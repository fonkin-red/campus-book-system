# 校园二手图书交易平台 - 前端

## 技术栈
- Vue 3
- Element Plus
- Vue Router
- Pinia
- Axios
- Vite

## 安装依赖

```bash
cd frontend
npm install
```

## 运行项目

```bash
npm run dev
```

项目将在 `http://localhost:5173` 启动

## 构建项目

```bash
npm run build
```

## 功能说明

### 已完成功能
- ✅ 用户登录
- ✅ 用户注册
- ✅ 个人中心
- ✅ 修改用户信息
- ✅ 修改密码
- ✅ 路由守卫
- ✅ 状态管理

### 开发中功能
- 🚧 图书列表
- 🚧 图书发布
- 🚧 购物车
- 🚧 订单管理

## 项目结构

```
frontend/
├── src/
│   ├── api/              # API接口
│   │   └── auth.js      # 用户相关接口
│   ├── assets/           # 静态资源
│   ├── components/       # 组件
│   ├── router/           # 路由配置
│   │   └── index.js
│   ├── stores/           # 状态管理
│   │   └── user.js      # 用户状态
│   ├── utils/            # 工具函数
│   │   └── request.js   # Axios封装
│   ├── views/            # 页面组件
│   │   ├── Login.vue    # 登录页
│   │   ├── Register.vue # 注册页
│   │   ├── Home.vue     # 首页
│   │   └── Profile.vue  # 个人中心
│   ├── App.vue          # 根组件
│   └── main.js          # 入口文件
├── index.html           # HTML模板
├── package.json         # 项目配置
└── vite.config.js       # Vite配置
```

## 注意事项

1. 确保后端服务已启动（默认端口8081）
2. API请求已配置代理，会自动转发到后端
3. 登录后的Token会保存在localStorage中
