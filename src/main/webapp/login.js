function hasAuthorization() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/profile');
    xhr.send();
    if (xhr.status === OK) {
        return true;
    }
}
function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}
function showLoginScreen() {
    showContents(['login-content', 'register-content']);
}

function getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));
}
function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}


function onLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        showContents('welcome-content')
        onProfileLoad(user);
    } else {
        onOtherResponse(document.getElementById('login-form'), this);
    }
}

function onLoginButtonClicked() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[name="email"]');
    const passwordInputEl = loginFormEl.querySelector('input[name="password"]');

    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'login');
    xhr.send(params);
}


function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}
