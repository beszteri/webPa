function onRegisterButtonClicked() {
    const registerFormEl = document.forms['register-form'];

    const emailInputEl = registerFormEl.querySelector('input[name="email"]');
    const passwordInputEl = registerFormEl.querySelector('input[name="password"]');

    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onRegisterResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

function onRegisterResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        alert('Thank you for registering  !')
        showContents(['login-content']);
    } else {
        onOtherResponse(document.getElementById('register-form'), this);
    }
}
