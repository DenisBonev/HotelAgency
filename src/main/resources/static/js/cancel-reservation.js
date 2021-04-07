document.querySelectorAll('button').forEach(b => b.addEventListener('click', cancelReservation));

function cancelReservation(e) {
    fetch('http://localhost:8080/reservations/delete/'+e.target.id,
        {
            method: 'delete',
        }).
    then(res => {
        console.log(res);
        e.target.parentElement.parentElement.remove();
    });
}