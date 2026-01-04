// 图书馆管理系统前端逻辑

// 模拟数据
let books = [
    { id: 1, isbn: '978-7-111-26350-4', title: 'JavaScript高级程序设计', author: 'Nicholas C. Zakas', publisher: '机械工业出版社', category: '科技', stock: 5, location: 'A区-001' },
    { id: 2, isbn: '978-7-115-32030-2', title: 'JavaScript权威指南', author: 'David Flanagan', publisher: '人民邮电出版社', category: '科技', stock: 3, location: 'A区-002' },
    { id: 3, isbn: '978-7-111-54544-4', title: '你不知道的JavaScript', author: 'Kyle Simpson', publisher: '机械工业出版社', category: '科技', stock: 8, location: 'A区-003' },
    { id: 4, isbn: '978-7-111-26350-5', title: '深入理解计算机系统', author: 'Randal E. Bryant', publisher: '机械工业出版社', category: '科技', stock: 2, location: 'B区-001' },
    { id: 5, isbn: '978-7-302-20244-4', title: '算法导论', author: 'Thomas H.Cormen', publisher: '清华大学出版社', category: '科技', stock: 4, location: 'B区-002' },
    { id: 6, isbn: '978-7-108-01922-2', title: '围城', author: '钱钟书', publisher: '生活·读书·新知三联书店', category: '文学', stock: 10, location: 'C区-001' },
    { id: 7, isbn: '978-7-02-008767-2', title: '活着', author: '余华', publisher: '人民文学出版社', category: '文学', stock: 7, location: 'C区-002' },
    { id: 8, isbn: '978-7-5442-7087-8', title: '百年孤独', author: '加西亚·马尔克斯', publisher: '南海出版公司', category: '文学', stock: 6, location: 'C区-003' }
];

let users = [
    { id: 1, name: '张三', email: 'zhangsan@example.com', phone: '13800138001', type: 'reader', status: 'active', password: '123456' },
    { id: 2, name: '李四', email: 'lisi@example.com', phone: '13800138002', type: 'reader', status: 'active', password: '123456' },
    { id: 3, name: '王五', email: 'wangwu@example.com', phone: '13800138003', type: 'admin', status: 'active', password: '123456' },
    { id: 4, name: '赵六', email: 'zhaoliu@example.com', phone: '13800138004', type: 'reader', status: 'active', password: '123456' }
];

let borrowingRecords = [
    { id: 1, bookId: 1, userId: 1, bookTitle: 'JavaScript高级程序设计', userName: '张三', borrowDate: '2024-01-15', dueDate: '2024-02-15', status: 'borrowed' },
    { id: 2, bookId: 3, userId: 2, bookTitle: '你不知道的JavaScript', userName: '李四', borrowDate: '2024-01-10', dueDate: '2024-02-10', status: 'borrowed' },
    { id: 3, bookId: 6, userId: 4, bookTitle: '围城', userName: '赵六', borrowDate: '2024-01-05', dueDate: '2024-02-05', status: 'returned', returnDate: '2024-01-25' }
];

// DOM元素
const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('main-content');
const toggleSidebarBtn = document.getElementById('toggle-sidebar');
const navItems = document.querySelectorAll('.nav-item');
const pages = document.querySelectorAll('.page');
const modals = document.querySelectorAll('.modal');
const closeBtns = document.querySelectorAll('.close');
const cancelBtns = document.querySelectorAll('.cancel');

// 初始化应用
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
    setupEventListeners();
    loadDashboardData();
});

// 初始化应用
function initializeApp() {
    // 设置当前用户
    document.getElementById('current-user').textContent = '管理员';
    
    // 加载各页面数据
    loadBooksTable();
    loadUsersTable();
    loadBorrowingTable();
}

// 设置事件监听器
function setupEventListeners() {
    // 侧边栏切换
    toggleSidebarBtn.addEventListener('click', toggleSidebar);
    
    // 导航菜单点击事件
    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const pageId = this.getAttribute('data-page');
            showPage(pageId);
        });
    });
    
    // 模态框关闭事件
    closeBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            this.closest('.modal').style.display = 'none';
        });
    });
    
    cancelBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            this.closest('.modal').style.display = 'none';
        });
    });
    
    // 点击模态框外部关闭
    window.addEventListener('click', function(event) {
        modals.forEach(modal => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    });
    
    // 图书管理相关事件
    document.getElementById('add-book-btn').addEventListener('click', showAddBookModal);
    document.getElementById('book-form').addEventListener('submit', addBook);
    document.getElementById('search-btn').addEventListener('click', searchBooks);
    document.getElementById('book-search').addEventListener('keyup', function(e) {
        if (e.key === 'Enter') searchBooks();
    });
    
    // 用户管理相关事件
    document.getElementById('add-user-btn').addEventListener('click', showAddUserModal);
    document.getElementById('user-form').addEventListener('submit', addUser);
    document.getElementById('user-search-btn').addEventListener('click', searchUsers);
    document.getElementById('user-search').addEventListener('keyup', function(e) {
        if (e.key === 'Enter') searchUsers();
    });
    
    // 借阅管理相关事件
    document.getElementById('borrow-book-btn').addEventListener('click', showBorrowModal);
    document.getElementById('return-book-btn').addEventListener('click', showReturnModal);
    document.getElementById('borrow-form').addEventListener('submit', borrowBook);
    document.getElementById('return-form').addEventListener('submit', returnBook);
    
    // 统计报表相关事件
    document.getElementById('generate-report-btn').addEventListener('click', generateReport);
    
    // 退出登录
    document.getElementById('logout-btn').addEventListener('click', logout);
}

// 切换侧边栏
function toggleSidebar() {
    sidebar.classList.toggle('collapsed');
    mainContent.style.marginLeft = sidebar.classList.contains('collapsed') ? '70px' : '260px';
}

// 显示页面
function showPage(pageId) {
    // 隐藏所有页面
    pages.forEach(page => page.classList.remove('active'));
    navItems.forEach(item => item.classList.remove('active'));
    
    // 显示选中页面
    document.getElementById(`${pageId}-page`).classList.add('active');
    
    // 高亮选中导航项
    document.querySelector(`[data-page="${pageId}"]`).classList.add('active');
    
    // 根据页面加载数据
    switch(pageId) {
        case 'dashboard':
            loadDashboardData();
            break;
        case 'books':
            loadBooksTable();
            break;
        case 'users':
            loadUsersTable();
            break;
        case 'borrowing':
            loadBorrowingTable();
            break;
        case 'reports':
            loadReportsData();
            break;
    }
}

// 显示添加图书模态框
function showAddBookModal() {
    document.getElementById('book-form').reset();
    document.getElementById('add-book-modal').style.display = 'block';
}

// 添加图书
function addBook(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const newBook = {
        id: books.length > 0 ? Math.max(...books.map(b => b.id)) + 1 : 1,
        isbn: formData.get('isbn'),
        title: formData.get('title'),
        author: formData.get('author'),
        publisher: formData.get('publisher'),
        category: formData.get('category'),
        stock: parseInt(formData.get('stock')),
        location: formData.get('location')
    };
    
    books.push(newBook);
    loadBooksTable();
    document.getElementById('add-book-modal').style.display = 'none';
    
    alert('图书添加成功！');
}

// 搜索图书
function searchBooks() {
    const searchTerm = document.getElementById('book-search').value.toLowerCase();
    const category = document.getElementById('book-category').value;
    
    const filteredBooks = books.filter(book => {
        const matchesSearch = book.title.toLowerCase().includes(searchTerm) || 
                             book.author.toLowerCase().includes(searchTerm) ||
                             book.isbn.includes(searchTerm);
        const matchesCategory = !category || book.category === category;
        return matchesSearch && matchesCategory;
    });
    
    renderBooksTable(filteredBooks);
}

// 加载图书表格
function loadBooksTable() {
    renderBooksTable(books);
}

// 渲染图书表格
function renderBooksTable(booksToRender) {
    const tbody = document.getElementById('books-tbody');
    tbody.innerHTML = '';
    
    booksToRender.forEach(book => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${book.isbn}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.publisher}</td>
            <td>${book.category}</td>
            <td>${book.stock}</td>
            <td>${book.location}</td>
            <td>
                <div class="action-buttons">
                    <button class="action-btn btn-edit" onclick="editBook(${book.id})">
                        <i class="fas fa-edit"></i> 编辑
                    </button>
                    <button class="action-btn btn-delete" onclick="deleteBook(${book.id})">
                        <i class="fas fa-trash"></i> 删除
                    </button>
                </div>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// 编辑图书
function editBook(id) {
    const book = books.find(b => b.id === id);
    if (book) {
        document.getElementById('isbn').value = book.isbn;
        document.getElementById('title').value = book.title;
        document.getElementById('author').value = book.author;
        document.getElementById('publisher').value = book.publisher;
        document.getElementById('category').value = book.category;
        document.getElementById('stock').value = book.stock;
        document.getElementById('location').value = book.location;
        
        // 修改表单提交行为为更新
        const form = document.getElementById('book-form');
        form.onsubmit = function(e) {
            e.preventDefault();
            updateBook(id);
        };
        
        document.getElementById('add-book-modal').style.display = 'block';
    }
}

// 更新图书
function updateBook(id) {
    const form = document.getElementById('book-form');
    const formData = new FormData(form);
    
    const bookIndex = books.findIndex(b => b.id === id);
    if (bookIndex !== -1) {
        books[bookIndex] = {
            ...books[bookIndex],
            isbn: formData.get('isbn'),
            title: formData.get('title'),
            author: formData.get('author'),
            publisher: formData.get('publisher'),
            category: formData.get('category'),
            stock: parseInt(formData.get('stock')),
            location: formData.get('location')
        };
        
        loadBooksTable();
        document.getElementById('add-book-modal').style.display = 'none';
        
        // 恢复添加图书的表单提交行为
        document.getElementById('book-form').onsubmit = addBook;
        
        alert('图书信息更新成功！');
    }
}

// 删除图书
function deleteBook(id) {
    if (confirm('确定要删除这本图书吗？')) {
        books = books.filter(book => book.id !== id);
        loadBooksTable();
        alert('图书删除成功！');
    }
}

// 显示添加用户模态框
function showAddUserModal() {
    document.getElementById('user-form').reset();
    document.getElementById('add-user-modal').style.display = 'block';
    // 恢复添加用户的表单提交行为
    document.getElementById('user-form').onsubmit = addUser;
}

// 添加用户
function addUser(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const newUser = {
        id: users.length > 0 ? Math.max(...users.map(u => u.id)) + 1 : 1,
        name: formData.get('username'),
        email: formData.get('email'),
        phone: formData.get('phone'),
        type: formData.get('userType'),
        status: 'active',
        password: formData.get('password')
    };
    
    users.push(newUser);
    loadUsersTable();
    document.getElementById('add-user-modal').style.display = 'none';
    
    alert('用户添加成功！');
}

// 搜索用户
function searchUsers() {
    const searchTerm = document.getElementById('user-search').value.toLowerCase();
    const userType = document.getElementById('user-type').value;
    
    const filteredUsers = users.filter(user => {
        const matchesSearch = user.name.toLowerCase().includes(searchTerm) || 
                             user.email.toLowerCase().includes(searchTerm) ||
                             user.phone.includes(searchTerm);
        const matchesType = !userType || user.type === userType;
        return matchesSearch && matchesType;
    });
    
    renderUsersTable(filteredUsers);
}

// 加载用户表格
function loadUsersTable() {
    renderUsersTable(users);
}

// 渲染用户表格
function renderUsersTable(usersToRender) {
    const tbody = document.getElementById('users-tbody');
    tbody.innerHTML = '';
    
    usersToRender.forEach(user => {
        const statusClass = user.status === 'active' ? 'status-active' : 'status-inactive';
        const statusText = user.status === 'active' ? '正常' : '禁用';
        
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.phone}</td>
            <td>${user.type === 'admin' ? '管理员' : '读者'}</td>
            <td>${new Date().toLocaleDateString()}</td>
            <td><span class="status ${statusClass}">${statusText}</span></td>
            <td>
                <div class="action-buttons">
                    <button class="action-btn btn-edit" onclick="editUser(${user.id})">
                        <i class="fas fa-edit"></i> 编辑
                    </button>
                    <button class="action-btn btn-delete" onclick="deleteUser(${user.id})">
                        <i class="fas fa-trash"></i> 删除
                    </button>
                </div>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// 编辑用户
function editUser(id) {
    const user = users.find(u => u.id === id);
    if (user) {
        document.getElementById('username').value = user.name;
        document.getElementById('email').value = user.email;
        document.getElementById('phone').value = user.phone;
        document.getElementById('user-type-input').value = user.type;
        document.getElementById('password').value = user.password;
        
        // 修改表单提交行为为更新
        const form = document.getElementById('user-form');
        form.onsubmit = function(e) {
            e.preventDefault();
            updateUser(id);
        };
        
        document.getElementById('add-user-modal').style.display = 'block';
    }
}

// 更新用户
function updateUser(id) {
    const form = document.getElementById('user-form');
    const formData = new FormData(form);
    
    const userIndex = users.findIndex(u => u.id === id);
    if (userIndex !== -1) {
        users[userIndex] = {
            ...users[userIndex],
            name: formData.get('username'),
            email: formData.get('email'),
            phone: formData.get('phone'),
            type: formData.get('userType'),
            password: formData.get('password')
        };
        
        loadUsersTable();
        document.getElementById('add-user-modal').style.display = 'none';
        
        // 恢复添加用户的表单提交行为
        document.getElementById('user-form').onsubmit = addUser;
        
        alert('用户信息更新成功！');
    }
}

// 删除用户
function deleteUser(id) {
    if (confirm('确定要删除这个用户吗？')) {
        users = users.filter(user => user.id !== id);
        loadUsersTable();
        alert('用户删除成功！');
    }
}

// 显示借书模态框
function showBorrowModal() {
    document.getElementById('borrow-form').reset();
    document.getElementById('borrow-modal').style.display = 'block';
}

// 借书
function borrowBook(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const userId = parseInt(formData.get('userId'));
    const bookId = parseInt(formData.get('bookId'));
    
    const user = users.find(u => u.id === userId);
    const book = books.find(b => b.id === bookId);
    
    if (!user) {
        alert('用户不存在！');
        return;
    }
    
    if (!book) {
        alert('图书不存在！');
        return;
    }
    
    if (book.stock <= 0) {
        alert('图书库存不足！');
        return;
    }
    
    // 更新库存
    const bookIndex = books.findIndex(b => b.id === bookId);
    books[bookIndex].stock -= 1;
    
    // 创建借阅记录
    const newBorrow = {
        id: borrowingRecords.length > 0 ? Math.max(...borrowingRecords.map(br => br.id)) + 1 : 1,
        bookId: bookId,
        userId: userId,
        bookTitle: book.title,
        userName: user.name,
        borrowDate: new Date().toISOString().split('T')[0],
        dueDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0], // 30天后到期
        status: 'borrowed'
    };
    
    borrowingRecords.push(newBorrow);
    loadBorrowingTable();
    document.getElementById('borrow-modal').style.display = 'none';
    
    alert('借书成功！');
}

// 显示还书模态框
function showReturnModal() {
    document.getElementById('return-form').reset();
    document.getElementById('return-modal').style.display = 'block';
}

// 还书
function returnBook(e) {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    const borrowId = parseInt(formData.get('borrowId'));
    
    const borrowRecord = borrowingRecords.find(br => br.id === borrowId);
    if (!borrowRecord) {
        alert('借阅记录不存在！');
        return;
    }
    
    if (borrowRecord.status === 'returned') {
        alert('该图书已归还！');
        return;
    }
    
    // 更新借阅记录状态
    const recordIndex = borrowingRecords.findIndex(br => br.id === borrowId);
    borrowingRecords[recordIndex].status = 'returned';
    borrowingRecords[recordIndex].returnDate = new Date().toISOString().split('T')[0];
    
    // 更新图书库存
    const book = books.find(b => b.id === borrowRecord.bookId);
    if (book) {
        book.stock += 1;
    }
    
    loadBorrowingTable();
    document.getElementById('return-modal').style.display = 'none';
    
    alert('还书成功！');
}

// 加载借阅表格
function loadBorrowingTable() {
    renderBorrowingTable(borrowingRecords);
}

// 渲染借阅表格
function renderBorrowingTable(recordsToRender) {
    const tbody = document.getElementById('borrowing-tbody');
    tbody.innerHTML = '';
    
    recordsToRender.forEach(record => {
        let statusClass = '';
        let statusText = '';
        
        if (record.status === 'returned') {
            statusClass = 'status-returned';
            statusText = '已归还';
        } else {
            const dueDate = new Date(record.dueDate);
            const today = new Date();
            if (today > dueDate) {
                statusClass = 'status-overdue';
                statusText = '已逾期';
            } else {
                statusClass = 'status-borrowed';
                statusText = '借阅中';
            }
        }
        
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${record.id}</td>
            <td>${record.bookTitle}</td>
            <td>${record.userName}</td>
            <td>${record.borrowDate}</td>
            <td>${record.dueDate}</td>
            <td><span class="status ${statusClass}">${statusText}</span></td>
            <td>
                <div class="action-buttons">
                    <button class="action-btn btn-edit" onclick="extendDueDate(${record.id})">
                        <i class="fas fa-calendar-plus"></i> 续期
                    </button>
                </div>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// 续期
function extendDueDate(id) {
    const record = borrowingRecords.find(br => br.id === id);
    if (record && record.status === 'borrowed') {
        // 延长7天
        const dueDate = new Date(record.dueDate);
        dueDate.setDate(dueDate.getDate() + 7);
        record.dueDate = dueDate.toISOString().split('T')[0];
        
        loadBorrowingTable();
        alert('续期成功，新的归还日期是：' + record.dueDate);
    }
}

// 加载控制台数据
function loadDashboardData() {
    document.getElementById('total-books').textContent = books.length;
    document.getElementById('total-users').textContent = users.length;
    document.getElementById('total-borrowed').textContent = borrowingRecords.filter(br => br.status === 'borrowed').length;
    document.getElementById('overdue-books').textContent = borrowingRecords.filter(br => {
        if (br.status === 'borrowed') {
            const dueDate = new Date(br.dueDate);
            const today = new Date();
            return today > dueDate;
        }
        return false;
    }).length;
    
    // 初始化图表
    initDashboardCharts();
}

// 初始化控制台图表
function initDashboardCharts() {
    // 借阅趋势图
    if (window.borrowingChart) {
        window.borrowingChart.destroy();
    }
    
    const borrowingCtx = document.getElementById('borrowingChart').getContext('2d');
    window.borrowingChart = new Chart(borrowingCtx, {
        type: 'line',
        data: {
            labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            datasets: [{
                label: '月借阅量',
                data: [65, 59, 80, 81, 56, 55, 40, 55, 70, 75, 85, 90],
                borderColor: 'rgb(75, 192, 192)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                }
            }
        }
    });
    
    // 热门图书图
    if (window.popularBooksChart) {
        window.popularBooksChart.destroy();
    }
    
    const popularBooksCtx = document.getElementById('popularBooksChart').getContext('2d');
    window.popularBooksChart = new Chart(popularBooksCtx, {
        type: 'bar',
        data: {
            labels: ['JavaScript高级程序设计', '你不知道的JavaScript', '算法导论', '围城', '活着'],
            datasets: [{
                label: '借阅次数',
                data: [42, 38, 30, 25, 22],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 205, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)',
                    'rgb(75, 192, 192)',
                    'rgb(153, 102, 255)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// 加载报表数据
function loadReportsData() {
    // 初始化报表图表
    initReportCharts();
}

// 初始化报表图表
function initReportCharts() {
    // 借阅排行榜
    if (window.borrowRankingChart) {
        window.borrowRankingChart.destroy();
    }
    
    const borrowRankingCtx = document.getElementById('borrowRankingChart').getContext('2d');
    window.borrowRankingChart = new Chart(borrowRankingCtx, {
        type: 'doughnut',
        data: {
            labels: ['JavaScript高级程序设计', '你不知道的JavaScript', '算法导论', '围城', '活着'],
            datasets: [{
                data: [42, 38, 30, 25, 22],
                backgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56',
                    '#4BC0C0',
                    '#9966FF'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom',
                }
            }
        }
    });
    
    // 用户借阅统计
    if (window.userBorrowChart) {
        window.userBorrowChart.destroy();
    }
    
    const userBorrowCtx = document.getElementById('userBorrowChart').getContext('2d');
    window.userBorrowChart = new Chart(userBorrowCtx, {
        type: 'bar',
        data: {
            labels: ['张三', '李四', '王五', '赵六'],
            datasets: [{
                label: '借阅数量',
                data: [8, 5, 2, 6],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
    
    // 图书分类统计
    if (window.bookCategoryChart) {
        window.bookCategoryChart.destroy();
    }
    
    const bookCategoryCtx = document.getElementById('bookCategoryChart').getContext('2d');
    window.bookCategoryChart = new Chart(bookCategoryCtx, {
        type: 'pie',
        data: {
            labels: ['科技', '文学', '历史', '艺术'],
            datasets: [{
                data: [35, 25, 15, 10],
                backgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56',
                    '#4BC0C0'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom',
                }
            }
        }
    });
}

// 生成报表
function generateReport() {
    alert('报表生成成功！实际项目中会导出PDF或Excel文件。');
}

// 退出登录
function logout() {
    if (confirm('确定要退出登录吗？')) {
        alert('已退出登录');
        // 实际项目中会跳转到登录页面
    }
}

// 模拟数据更新函数（实际项目中会调用API）
function updateData() {
    // 这里可以添加与后端API交互的代码
    console.log('数据已更新');
}