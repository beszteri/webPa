function onSaveGameButtonClicked() {
    const adminFormEl = document.forms['admin-form'];

    const nameInputEl = adminFormEl.querySelector('input[name="name"]');
    const platformInputEl = adminFormEl.querySelector('input[name="platform"]');
    const imageUrlInputEl = adminFormEl.querySelector('input[name="imageUrl"]');
    const priceInputEl = adminFormEl.querySelector('input[name="price"]');


    const name = nameInputEl.value;
    const platform = platformInputEl.value;
    const imageUrl = imageUrlInputEl.value;
    const price = priceInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('platform', platform);
    params.append('imageUrl', imageUrl);
    params.append('price', price);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSaveGameRespond);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/games');
    xhr.send(params);
}

function onSaveGameRespond() {
    if (this.status === OK) {
        const game = JSON.parse(this.responseText);
        alert('New game saved !');
        showContents(['welcome-content']);
    } else {
     onOtherResponse(document.getElementById('register-form'), this);
    }
}

