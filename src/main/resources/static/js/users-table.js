const tbody = document.querySelector('tbody');
const searchInput = document.getElementById('searchInput');
const allUsers = [];
fetch('http://localhost:8080/users/all').then(r => r.json()).then(data => {
    for (let d of data) {
        allUsers.push(d);
    }
}).then(p => showUsers(allUsers));


searchInput.addEventListener('keyup', () => {
    let searchingCharacters = searchInput.value;
    const filteredUsers = allUsers.filter(u => u.email.includes(searchingCharacters));
    showUsers(filteredUsers);
})

function showUsers(users) {
    tbody.innerHTML = '';
    for (let u of users) {
        const id = document.createElement('td');
        id.innerText = u.id;
        const username = document.createElement('td');
        username.innerText = u.email;
        const isUser = document.createElement('td');
        isUser.innerText = u.roles.includes('USER');
        const isOwner = document.createElement('td');
        isOwner.innerText = u.roles.includes('HOTEL_OWNER');
        const isAdmin = document.createElement('td');
        isAdmin.innerText = u.roles.includes('ADMIN');
        const btn = document.createElement('td');
        const a = document.createElement('a');
        a.type = 'button';
        a.className = 'btn btn-danger w-100';
        a.href = '/users/' + u.id;
        a.innerText = 'Change Roles';
        btn.appendChild(a);
        const tr = document.createElement('tr');
        tr.appendChild(id);
        tr.appendChild(username);
        tr.appendChild(isUser);
        tr.appendChild(isOwner);
        tr.appendChild(isAdmin);
        tr.appendChild(btn);
        tbody.appendChild(tr);
    }
}
