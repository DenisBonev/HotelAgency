const tbody = document.querySelector('tbody');
const allRooms = [];
const id = document.getElementById('hotel').getAttribute('hotelid');
fetch('http://localhost:8080/hotels/rooms/'+id).then(r => r.json()).then(data => {
    for (let d of data) {
        allRooms.push(d);
    }
}).then(p => showRooms(allRooms));



function showRooms(rooms) {
    tbody.innerHTML = '';
    for (let r of rooms) {
        const id = document.createElement('td');
        id.innerText = r.id;
        const type = document.createElement('td');
        type.innerText = r.type;
        const name = document.createElement('td');
        name.innerText = r.name;
        const price = document.createElement('td');
        price.innerText = r.price;
        const singleBeds = document.createElement('td');
        singleBeds.innerText = r.singleBedsCount;
        const twinBeds = document.createElement('td');
        twinBeds.innerText = r.twinBedsCount;
        const count = document.createElement('td');
        count.innerText = r.count;
        const btn = document.createElement('td');
        const a = document.createElement('a');
        a.type = 'button';
        a.className = 'btn btn-danger w-100';
        a.href = '/hotels/edit-room/' + r.id;
        a.innerText = 'EDIT';
        btn.appendChild(a);
        const tr = document.createElement('tr');
        tr.appendChild(id);
        tr.appendChild(type);
        tr.appendChild(name);
        tr.appendChild(price);
        tr.appendChild(singleBeds);
        tr.appendChild(twinBeds);
        tr.appendChild(count);
        tr.appendChild(btn);
        tbody.appendChild(tr);
    }
}
