# 用户模块API文档

## 基础信息
- 基础URL: `http://localhost:8080`
- 认证方式: JWT Bearer Token
- 响应格式: JSON

## 通用响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

## API接口

### 1. 用户注册
- **URL**: `/api/auth/register`
- **方法**: POST
- **认证**: 不需要
- **请求体**:
```json
{
  "username": "testuser",
  "password": "123456",
  "phone": "13800138000"
}
```
- **响应**: 成功返回200，失败返回错误信息

### 2. 用户登录
- **URL**: `/api/auth/login`
- **方法**: POST
- **认证**: 不需要
- **请求体**:
```json
{
  "username": "testuser",
  "password": "123456"
}
```
- **响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "userInfo": {
      "id": 1,
      "username": "testuser",
      "phone": "13800138000",
      "avatar": null,
      "role": 0,
      "status": 1,
      "createTime": "2026-04-27T12:00:00"
    }
  }
}
```

### 3. 获取用户信息
- **URL**: `/api/user/info`
- **方法**: GET
- **认证**: 需要Bearer Token
- **请求头**: `Authorization: Bearer {token}`
- **响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "testuser",
    "phone": "13800138000",
    "avatar": null,
    "role": 0,
    "status": 1,
    "createTime": "2026-04-27T12:00:00"
  }
}
```

### 4. 更新用户信息
- **URL**: `/api/user/info`
- **方法**: PUT
- **认证**: 需要Bearer Token
- **请求头**: `Authorization: Bearer {token}`
- **请求体**:
```json
{
  "username": "newusername",
  "phone": "13900139000",
  "avatar": "http://example.com/avatar.jpg"
}
```
- **响应**: 成功返回200，失败返回错误信息

### 5. 修改密码
- **URL**: `/api/user/password`
- **方法**: PUT
- **认证**: 需要Bearer Token
- **请求头**: `Authorization: Bearer {token}`
- **请求体**:
```json
{
  "oldPassword": "123456",
  "newPassword": "654321"
}
```
- **响应**: 成功返回200，失败返回错误信息

## 数据验证规则

### 用户名
- 长度: 4-20位
- 字符: 字母、数字、下划线
- 示例: `test_user`, `user123`

### 密码
- 长度: 6-20位
- 字符: 字母、数字、下划线
- 示例: `password123`

### 手机号
- 格式: 1开头，第二位3-9，共11位数字
- 示例: `13800138000`

## 错误码说明
- 200: 成功
- 400: 请求参数错误
- 500: 服务器内部错误

## 使用示例

### 使用curl测试API

1. 注册用户:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"123456","phone":"13800138000"}'
```

2. 登录:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"123456"}'
```

3. 获取用户信息 (需要替换TOKEN):
```bash
curl -X GET http://localhost:8080/api/user/info \
  -H "Authorization: Bearer TOKEN"
```

## 注意事项
1. 所有需要认证的接口都需要在请求头中携带JWT Token
2. Token有效期为7天 (604800000毫秒)
3. 用户角色: 0-普通用户, 1-管理员
4. 用户状态: 0-禁用, 1-正常
5. 密码使用BCrypt加密存储
