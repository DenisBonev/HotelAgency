document.querySelectorAll("#removeBtn").forEach(e=>e.addEventListener('click', removeImage));

function removeImage(e) {
    fetch('http://localhost:8080/picture/delete',{
        method:'delete',
        headers:{
            'Content-Type':'application/json'
        },
        body:e.target.parentElement.parentElement.children.item(0).getAttribute('src')
    });
}