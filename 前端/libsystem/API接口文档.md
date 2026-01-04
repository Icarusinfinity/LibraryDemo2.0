# 图书馆管理系统API接口文档

## 项目概述
- 项目名称：图书馆管理系统
- 接口版本：v1.0
- 协议：HTTP/HTTPS
- 请求格式：JSON
- 响应格式：JSON
- 字符编码：UTF-8

## 通用响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": "2024-01-04T10:30:00.000+00:00"
}
```

### 响应码说明
- 200: 成功
- 400: 请求参数错误
- 401: 未授权
- 404: 资源不存在
- 500: 服务器内部错误

## 1. 用户管理模块

### 1.1 用户登录
- **接口地址**：`POST /api/auth/login`
- **功能描述**：用户登录验证
- **请求参数**：
  ```json
  {
    "username": "admin",
    "password": "123456"
  }
  ```
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "登录成功",
    "data": {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "user": {
        "id": 1,
        "username": "admin",
        "name": "管理员",
        "email": "admin@example.com",
        "type": "admin",
        "createTime": "2024-01-01"
      }
    }
  }
  ```

### 1.2 用户注册
- **接口地址**：`POST /api/auth/register`
- **功能描述**：用户注册
- **请求参数**：
  ```json
  {
    "name": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138001",
    "password": "123456",
    "type": "reader"
  }
  ```
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "注册成功",
    "data": {
      "id": 2,
      "name": "张三",
      "email": "zhangsan@example.com",
      "phone": "13800138001",
      "type": "reader"
    }
  }
  ```

### 1.3 获取用户列表
- **接口地址**：`GET /api/users`
- **功能描述**：获取用户列表（支持分页和搜索）
- **请求参数**：
  - page: 页码（默认1）
  - size: 每页数量（默认10）
  - keyword: 搜索关键词（可选）
  - type: 用户类型（reader/admin，可选）
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "list": [
        {
          "id": 1,
          "name": "管理员",
          "email": "admin@example.com",
          "phone": "13800000000",
          "type": "admin",
          "status": "active",
          "createTime": "2024-01-01"
        }
      ],
      "total": 10,
      "page": 1,
      "size": 10
    }
  }
  ```

### 1.4 获取用户详情
- **接口地址**：`GET /api/users/{id}`
- **功能描述**：获取用户详情
- **路径参数**：id - 用户ID
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "name": "管理员",
      "email": "admin@example.com",
      "phone": "13800000000",
      "type": "admin",
      "status": "active",
      "createTime": "2024-01-01"
    }
  }
  ```

### 1.5 创建用户
- **接口地址**：`POST /api/users`
- **功能描述**：创建用户
- **请求参数**：
  ```json
  {
    "name": "李四",
    "email": "lisi@example.com",
    "phone": "13800138002",
    "type": "reader",
    "password": "123456"
  }
  ```

### 1.6 更新用户
- **接口地址**：`PUT /api/users/{id}`
- **功能描述**：更新用户信息
- **路径参数**：id - 用户ID
- **请求参数**：
  ```json
  {
    "name": "李四",
    "email": "lisi@example.com",
    "phone": "13800138002",
    "type": "admin"
  }
  ```

### 1.7 删除用户
- **接口地址**：`DELETE /api/users/{id}`
- **功能描述**：删除用户
- **路径参数**：id - 用户ID

## 2. 图书管理模块

### 2.1 获取图书列表
- **接口地址**：`GET /api/books`
- **功能描述**：获取图书列表（支持分页、搜索和分类筛选）
- **请求参数**：
  - page: 页码（默认1）
  - size: 每页数量（默认10）
  - keyword: 搜索关键词（可选）
  - category: 分类（可选）
  - isbn: ISBN（可选）
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "list": [
        {
          "id": 1,
          "isbn": "978-7-111-26350-4",
          "title": "JavaScript高级程序设计",
          "author": "Nicholas C. Zakas",
          "publisher": "机械工业出版社",
          "category": "科技",
          "stock": 5,
          "location": "A区-001",
          "createTime": "2024-01-01"
        }
      ],
      "total": 100,
      "page": 1,
      "size": 10
    }
  }
  ```

### 2.2 获取图书详情
- **接口地址**：`GET /api/books/{id}`
- **功能描述**：获取图书详情
- **路径参数**：id - 图书ID
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "id": 1,
      "isbn": "978-7-111-26350-4",
      "title": "JavaScript高级程序设计",
      "author": "Nicholas C. Zakas",
      "publisher": "机械工业出版社",
      "category": "科技",
      "stock": 5,
      "location": "A区-001",
      "description": "本书介绍了JavaScript语言的核心概念和编程技巧...",
      "createTime": "2024-01-01"
    }
  }
  ```

### 2.3 创建图书
- **接口地址**：`POST /api/books`
- **功能描述**：创建图书
- **请求参数**：
  ```json
  {
    "isbn": "978-7-111-26350-5",
    "title": "Vue.js实战",
    "author": "梁灏",
    "publisher": "清华大学出版社",
    "category": "科技",
    "stock": 10,
    "location": "A区-002",
    "description": "Vue.js框架实战指南..."
  }
  ```

### 2.4 更新图书
- **接口地址**：`PUT /api/books/{id}`
- **功能描述**：更新图书信息
- **路径参数**：id - 图书ID
- **请求参数**：
  ```json
  {
    "isbn": "978-7-111-26350-5",
    "title": "Vue.js实战（第二版）",
    "author": "梁灏",
    "publisher": "清华大学出版社",
    "category": "科技",
    "stock": 15,
    "location": "A区-002",
    "description": "Vue.js框架实战指南（第二版）..."
  }
  ```

### 2.5 删除图书
- **接口地址**：`DELETE /api/books/{id}`
- **功能描述**：删除图书
- **路径参数**：id - 图书ID

### 2.6 图书分类列表
- **接口地址**：`GET /api/books/categories`
- **功能描述**：获取图书分类列表
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {"value": "文学", "label": "文学"},
      {"value": "科技", "label": "科技"},
      {"value": "历史", "label": "历史"},
      {"value": "艺术", "label": "艺术"},
      {"value": "教育", "label": "教育"},
      {"value": "其他", "label": "其他"}
    ]
  }
  ```

## 3. 借阅管理模块

### 3.1 借书
- **接口地址**：`POST /api/borrow`
- **功能描述**：借书操作
- **请求参数**：
  ```json
  {
    "userId": 1,
    "bookId": 1
  }
  ```
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "借书成功",
    "data": {
      "id": 1,
      "userId": 1,
      "bookId": 1,
      "bookTitle": "JavaScript高级程序设计",
      "userName": "张三",
      "borrowDate": "2024-01-15",
      "dueDate": "2024-02-15",
      "status": "borrowed"
    }
  }
  ```

### 3.2 还书
- **接口地址**：`POST /api/return`
- **功能描述**：还书操作
- **请求参数**：
  ```json
  {
    "borrowId": 1
  }
  ```
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "还书成功",
    "data": {
      "id": 1,
      "userId": 1,
      "bookId": 1,
      "bookTitle": "JavaScript高级程序设计",
      "userName": "张三",
      "borrowDate": "2024-01-15",
      "returnDate": "2024-01-25",
      "dueDate": "2024-02-15",
      "status": "returned"
    }
  }
  ```

### 3.3 借阅记录列表
- **接口地址**：`GET /api/borrowings`
- **功能描述**：获取借阅记录列表
- **请求参数**：
  - page: 页码（默认1）
  - size: 每页数量（默认10）
  - status: 状态（borrowed/returned/overdue，可选）
  - userId: 用户ID（可选）
  - bookId: 图书ID（可选）
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "list": [
        {
          "id": 1,
          "userId": 1,
          "bookId": 1,
          "bookTitle": "JavaScript高级程序设计",
          "userName": "张三",
          "borrowDate": "2024-01-15",
          "dueDate": "2024-02-15",
          "returnDate": null,
          "status": "borrowed"
        }
      ],
      "total": 50,
      "page": 1,
      "size": 10
    }
  }
  ```

### 3.4 续期
- **接口地址**：`POST /api/extend/{borrowId}`
- **功能描述**：借阅续期操作
- **路径参数**：borrowId - 借阅ID
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "续期成功",
    "data": {
      "id": 1,
      "dueDate": "2024-03-15",
      "newDueDate": "2024-03-22"
    }
  }
  ```

## 4. 统计报表模块

### 4.1 获取统计概览
- **接口地址**：`GET /api/dashboard/overview`
- **功能描述**：获取统计概览数据
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "totalBooks": 100,
      "totalUsers": 50,
      "totalBorrowed": 45,
      "overdueBooks": 3,
      "availableBooks": 97
    }
  }
  ```

### 4.2 获取借阅趋势
- **接口地址**：`GET /api/dashboard/borrow-trend`
- **功能描述**：获取借阅趋势数据
- **请求参数**：
  - period: 统计周期（month/quarter/year，默认month）
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "labels": ["1月", "2月", "3月", "4月", "5月", "6月"],
      "datasets": [
        {
          "label": "月借阅量",
          "data": [65, 59, 80, 81, 56, 55]
        }
      ]
    }
  }
  ```

### 4.3 获取热门图书
- **接口地址**：`GET /api/dashboard/popular-books`
- **功能描述**：获取热门图书排行
- **请求参数**：
  - limit: 返回数量（默认10）
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "bookId": 1,
        "title": "JavaScript高级程序设计",
        "borrowCount": 42
      },
      {
        "bookId": 2,
        "title": "你不知道的JavaScript",
        "borrowCount": 38
      }
    ]
  }
  ```

### 4.4 获取用户借阅统计
- **接口地址**：`GET /api/dashboard/user-borrow-stats`
- **功能描述**：获取用户借阅统计
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "userId": 1,
        "userName": "张三",
        "borrowCount": 8
      },
      {
        "userId": 2,
        "userName": "李四",
        "borrowCount": 5
      }
    ]
  }
  ```

### 4.5 获取图书分类统计
- **接口地址**：`GET /api/dashboard/category-stats`
- **功能描述**：获取图书分类统计
- **响应参数**：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": [
      {
        "category": "科技",
        "count": 35
      },
      {
        "category": "文学",
        "count": 25
      }
    ]
  }
  ```

## 5. 认证与授权

### 5.1 请求头
所有需要认证的接口需要在请求头中添加：
```
Authorization: Bearer {token}
Content-Type: application/json
```

### 5.2 Token刷新
- **接口地址**：`POST /api/auth/refresh`
- **功能描述**：刷新访问令牌
- **请求参数**：
  ```json
  {
    "refreshToken": "refresh_token_value"
  }
  ```

## 错误处理

### 通用错误响应格式
```json
{
  "code": 400,
  "message": "错误信息",
  "data": null,
  "timestamp": "2024-01-04T10:30:00.000+00:00"
}
```

### 常见错误码
- 40001: 参数验证失败
- 40101: 用户名或密码错误
- 40102: 令牌无效或已过期
- 40401: 资源不存在
- 40901: 资源已存在（重复创建）
- 50001: 服务器内部错误

## 数据模型

### 用户模型 (User)
- id: Long (主键)
- name: String (姓名)
- email: String (邮箱)
- phone: String (电话)
- password: String (密码)
- type: String (用户类型: reader/admin)
- status: String (状态: active/inactive)
- createTime: Date (创建时间)
- updateTime: Date (更新时间)

### 图书模型 (Book)
- id: Long (主键)
- isbn: String (ISBN)
- title: String (书名)
- author: String (作者)
- publisher: String (出版社)
- category: String (分类)
- stock: Integer (库存)
- location: String (位置)
- description: String (描述)
- createTime: Date (创建时间)
- updateTime: Date (更新时间)

### 借阅记录模型 (Borrowing)
- id: Long (主键)
- userId: Long (用户ID)
- bookId: Long (图书ID)
- borrowDate: Date (借阅日期)
- dueDate: Date (应还日期)
- returnDate: Date (归还日期)
- status: String (状态: borrowed/returned/overdue)
- createTime: Date (创建时间)
- updateTime: Date (更新时间)

## 注意事项

1. 所有日期时间格式统一使用ISO 8601格式（YYYY-MM-DDTHH:mm:ss.SSSZ）
2. 所有接口均需进行参数验证
3. 对于需要权限的操作，需验证用户角色
4. 分页参数需进行合理性验证
5. 借书操作需验证用户状态、图书库存、借阅数量限制等
6. 还书操作需验证借阅记录状态
7. 所有操作需记录操作日志