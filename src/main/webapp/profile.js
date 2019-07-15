function onProfileLoad(user) {
    if (user.role === 'ADMIN') {
        showContents(['welcome-content', 'admin-content']);
    } else if (user.role === 'REGISTERED') {
        showContents('welcome-content');
    } else if (user.role === 'UNREGISTERED') {
        showContents(['login-content', 'register-content']);
    }
}
function onProfileButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'protected/personalInfos');
    xhr.onload = function () {
        if (this.status === OK) {
            let personalInfos = JSON.parse(this.responseText);
            let output = '<div id="profile-content" class="content profile">' +
                '<ul>' +
                '<li>Name: ' + personalInfos.name + '</li>' +
                '<li>Address: ' + personalInfos.address + '</li>' +
                '<li>Phone number: ' + personalInfos.phoneNumber + '</li>' +
                '</ul>' +
                '</div>';
            document.getElementById('profile-content').innerHTML = output;
        }
    }
    showContents(['welcome-content', 'profile-content', 'change-profile-infos-content','add-to-balance-content']);
    xhr.send();
}

function onChangeProfileInfosButtonClicked() {
    const changeProfilInfosEl = document.forms['change-profile-infos-form'];

    const nameInputEl = changeProfilInfosEl.querySelector('input[name="name"]');
    const addressInputEl = changeProfilInfosEl.querySelector('input[name="address"]');
    const phoneNumberInputEl = changeProfilInfosEl.querySelector('input[name="phoneNumber"]');


    const name = nameInputEl.value;
    const address = addressInputEl.value;
    const phoneNumber = phoneNumberInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('address', address);
    params.append('phoneNumber', phoneNumber);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onChangeProfileInfosRespond);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/personalInfos');
    xhr.send(params);
}

function onChangeProfileInfosRespond() {
    if (this.status === OK) {
        const personalInfos = JSON.parse(this.responseText);
        alert('Profile infos updated !');
        showContents(['welcome-content']);
    } //else {
    // onOtherResponse(document.getElementById('register-form'), this);
    //}
}

function onProfileLoadResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        showContents(['welcome-content', 'profile-content']);
        onProfileLoad(user);
    } //else {
        //onOtherResponse(document.getElementById('login-form'), this);
   // }
}
