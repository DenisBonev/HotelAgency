const hotelsList = document.getElementById('cardsList');
const nameInput = document.getElementById('hotelName');
const starsInput = document.getElementById('stars');
const addressInput = document.getElementById('address');
const searchBtn = document.getElementById('searchBtn');
const allHotels = [];
fetch("http://localhost:8080/hotels/all")
    .then(response => response.json())
    .then(data => {
        for (let d of data) {
            allHotels.push(d);
        }
    })
    .then(p => displayHotels(allHotels));

searchBtn.addEventListener('click', (e) => {
    const hotelsSearchingCharacters = nameInput.value.toLowerCase();
    const addressSearchingCharacters = addressInput.value.toLowerCase();
    const stars = starsInput.value;
    console.log(allHotels);
    let filteredHotels = allHotels.filter(hotel => {
        if (stars === '') {
            return hotel.name.toLowerCase().includes(hotelsSearchingCharacters)
                && hotel.address.toLowerCase().includes(addressSearchingCharacters);
        }
        return hotel.name.toLowerCase().includes(hotelsSearchingCharacters)
            && hotel.address.toLowerCase().includes(addressSearchingCharacters)
            && hotel.stars === stars;
    });
    console.log(filteredHotels);
    displayHotels(filteredHotels);
});


const displayHotels = (hotels) => {
    hotelsList.innerHTML = '';
    let row = [];
    for (let i = 0; i < hotels.length; i++) {
        let card = document.createElement('div');
        card.classList.add('card');
        card.classList.add('my-card');
        let img = document.createElement('img');
        img.className = 'card-img-top card-image';
        img.src = hotels[i].mainPictureUrl;
        img.alt = 'Card image cap';
        let cardBody = document.createElement('div');
        cardBody.className = 'card-body';
        let cardTitle = document.createElement('h5');
        cardTitle.className = 'card-title';
        cardTitle.innerText = hotels[i].name;
        let cardText = document.createElement('p');
        cardText.className = 'card-text';
        cardText.innerText = hotels[i].address;
        let a = document.createElement('a');
        a.className = 'btn btn-primary';
        a.href = '/hotels/details/' + hotels[i].id;
        a.innerText = 'Visit';
        cardBody.appendChild(cardTitle);
        cardBody.appendChild(cardText);
        cardBody.appendChild(a);
        card.appendChild(img);
        card.appendChild(cardBody);
        row.push(card);
        if ((i + 1) % 3 === 0 || hotels.length - i === 1) {
            let cardGroup = document.createElement('div');
            cardGroup.classList.add('card-group');
            cardGroup.classList.add('align-content-center');
            cardGroup.classList.add('mx-auto');
            for (let j = 0; j < row.length; j++) {
                cardGroup.appendChild(row[j]);
            }
            hotelsList.appendChild(cardGroup);
            row = [];
        }
    }
}


