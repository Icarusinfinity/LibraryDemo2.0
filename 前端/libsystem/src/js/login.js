// 登录页面逻辑

document.addEventListener('DOMContentLoaded', function() {
    setupLoginEvents();
});

// 设置登录页面事件
function setupLoginEvents() {
    // 登录表单提交
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        handleLogin();
    });
    
    // 注册表单提交
    document.getElementById('registerFormContent').addEventListener('submit', function(e) {
        e.preventDefault();
        handleRegister();
    });
    
    // 显示注册表单
    document.getElementById('showRegister').addEventListener('click', function(e) {
        e.preventDefault();
        showRegisterForm();
    });
    
    // 显示登录表单
    document.getElementById('showLogin').addEventListener('click', function(e) {
        e.preventDefault();
        showLoginForm();
    });
    
    // 密码确认验证
    document.getElementById('regConfirmPassword').addEventListener('input', validatePasswordMatch);
}

// 处理登录
function handleLogin() {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    
    // 简单验证
    if (!username || !password) {
        alert('请输入用户名和密码');
        return;
    }
    
    // 模拟登录验证（实际项目中应调用后端API）
    if (validateUser(username, password)) {
        // 保存登录状态
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('currentUser', username);
        
        // 跳转到主页面
        window.location.href = 'index.html';
    } else {
        alert('用户名或密码错误');
    }
}

// 验证用户（模拟）
function validateUser(username, password) {
    // 在实际项目中，这里会调用后端API验证用户
    // 这里使用模拟数据进行验证
    const mockUsers = [
        { username: 'admin', password: '123456', name: '管理员', type: 'admin' },
        { username: 'zhangsan', password: '123456', name: '张三', type: 'reader' },
        { username: 'lisi', password: '123456', name: '李四', type: 'reader' }
    ];
    
    return mockUsers.some(user => 
        (user.username === username || user.name === username) && 
        user.password === password
    );
}

// 处理注册
function handleRegister() {
    const name = document.getElementById('regName').value.trim();
    const email = document.getElementById('regEmail').value.trim();
    const phone = document.getElementById('regPhone').value.trim();
    const password = document.getElementById('regPassword').value;
    const confirmPassword = document.getElementById('regConfirmPassword').value;
    
    // 验证输入
    if (!name || !email || !phone || !password) {
        alert('请填写所有必填字段');
        return;
    }
    
    if (password !== confirmPassword) {
        alert('两次输入的密码不一致');
        return;
    }
    
    if (password.length < 6) {
        alert('密码长度至少为6位');
        return;
    }
    
    // 验证邮箱格式
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('请输入有效的邮箱地址');
        return;
    }
    
    // 验证手机号格式
    const phoneRegex = /^1[3-9]\d{9}$/;
    if (!phoneRegex.test(phone)) {
        alert('请输入有效的手机号码');
        return;
    }
    
    // 模拟注册（实际项目中应调用后端API）
    alert('注册成功！请使用注册的账户登录。');
    
    // 清空表单并返回登录页面
    document.getElementById('registerFormContent').reset();
    showLoginForm();
}

// 验证密码匹配
function validatePasswordMatch() {
    const password = document.getElementById('regPassword').value;
    const confirmPassword = document.getElementById('regConfirmPassword').value;
    const messageElement = document.querySelector('.password-message');
    
    if (confirmPassword && password !== confirmPassword) {
        if (!messageElement) {
            const errorMsg = document.createElement('div');
            errorMsg.className = 'password-message';
            errorMsg.textContent = '两次输入的密码不一致';
            errorMsg.style.color = 'red';
            errorMsg.style.fontSize = '12px';
            errorMsg.style.marginTop = '5px';
            document.getElementById('regConfirmPassword').parentElement.appendChild(errorMsg);
        }
    } else if (messageElement) {
        messageElement.remove();
    }
}

// 显示注册表单
function showRegisterForm() {
    document.querySelector('.login-form').style.display = 'none';
    document.querySelector('.register-form').style.display = 'block';
}

// 显示登录表单
function showLoginForm() {
    document.querySelector('.register-form').style.display = 'none';
    document.querySelector('.login-form').style.display = 'block';
}

// 检查登录状态（用于其他页面）
function checkLoginStatus() {
    return localStorage.getItem('isLoggedIn') === 'true';
}

// 登出
function logout() {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('currentUser');
    window.location.href = 'login.html';
}

// 页面加载时检查登录状态
window.addEventListener('load', function() {
    // 如果是主页面，检查登录状态
    if (window.location.pathname.includes('index.html') || window.location.pathname.endsWith('/')) {
        if (!checkLoginStatus()) {
            window.location.href = 'login.html';
        }
    }
});