function onAddToBalanceButtonClicked() {
    const addToBalanceFormEl = document.forms['add-to-balance-form'];

    const balanceInputEl = addToBalanceFormEl.querySelector('input[name="balance"]');

    const balance = balanceInputEl.value;

    const params = new URLSearchParams();
    params.append('balance', balance);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddToBalanceRespond);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/users');
    xhr.send(params);
}

function onAddToBalanceRespond() {
    if (this.status === OK) {
        const balance = JSON.parse(this.responseText);
        alert('Added to balance !')
        showContents(['welcome-content']);
    } else {
        onOtherResponse(document.getElementById('register-form'), this);
    }
}

function onActualBalanceButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'protected/profile');
    xhr.onload = function () {
        if (this.status === OK) {
            let user = JSON.parse(this.responseText);
            alert('Your actual balance is : ' + user.balance);
        }
    }
    xhr.send();
}
