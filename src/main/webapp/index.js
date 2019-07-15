const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let welcomeContentDivEl;
let registerContentDivEl;
let guestContentDivEl;
let profileContentDivEl;
let gamesContentDivEl;
let adminContentDivEl;




function onLoad() {
    loginContentDivEl = document.getElementById('login-content');
    welcomeContentDivEl = document.getElementById('welcome-content');
    registerContentDivEl = document.getElementById('register-content');
    profileContentDivEl = document.getElementById('profile-content');
    gamesContentDivEl = document.getElementById('games-content');
    adminContentDivEl = document.getElementById('admin-content');

    const saveGameButtonEl = document.getElementById('admin-button');
    saveGameButtonEl.addEventListener('click', onSaveGameButtonClicked);

    const myPurchasesButtonEl = document.getElementById('my-purchases-button');
    myPurchasesButtonEl.addEventListener('click', onMyPurchasesButtonClicked);

    const addToBalanceButtonEl = document.getElementById('add-to-balance-button');
    addToBalanceButtonEl.addEventListener('click', onAddToBalanceButtonClicked);

    const myActualBalanceButtonEl = document.getElementById('actual-balance-button');
    myActualBalanceButtonEl.addEventListener('click', onActualBalanceButtonClicked);

    const changeProfileButtonEl = document.getElementById('change-profile-infos-button');
    changeProfileButtonEl.addEventListener('click', onChangeProfileInfosButtonClicked);

    const gamesButtonEl = document.getElementById('games-button');
    gamesButtonEl.addEventListener('click', loadGames);

    const backButtonEl = document.getElementById('mainpage-button');
    backButtonEl.addEventListener('click', backToWelcomeScreen);

    const registerButtonEl = document.getElementById('register-button');
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);

    const logoutButtonEl = document.getElementById('logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);

    const profileButtonEl = document.getElementById('profile-button');
    profileButtonEl.addEventListener('click', onProfileButtonClicked);

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const guestButtonEl = document.getElementById('guest-button');
    guestButtonEl.addEventListener('click', onGuestButtonClicked);

    const backToLoginPageButtonEl = document.getElementById('back-to-login-page');
    backToLoginPageButtonEl.addEventListener('click', showLoginScreen);


    getCurrentUser();
    if (hasAuthorization()) {
        onProfileLoad(getCurrentUser());
    } else {
        showContents(['login-content', 'register-content']);
    }
}
document.addEventListener('DOMContentLoaded', onLoad);
