<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <c:url value="/style.css" var="styleUrl"/>
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/profile.js" var="profileScriptUrl"/>
    <c:url value="/games.js" var="gamesScriptUrl"/>
    <c:url value="/purchases.js" var="purchasesScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/guest.js" var="guestScriptUrl"/>
    <c:url value="/balance.js" var="balanceScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/admin.js" var="adminScriptUrl"/>
    <link rel="stylesheet" type="text/css" href="${styleUrl}">
    <script src="${indexScriptUrl}"></script>
    <script src="${profileScriptUrl}"></script>
    <script src="${gamesScriptUrl}"></script>
    <script src="${purchasesScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${guestScriptUrl}"></script>
    <script src="${balanceScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${adminScriptUrl}"></script>
    <title>Document</title>
</head>
<body>
<div id="login-content" class="content">
    <h1>Login</h1>
    <form id="login-form" onsubmit="return false;">
        <input type="text" name="email"><br>
        <input type="text" name="password"><br>
        <button id="login-button">Login</button><br>
        <button id="guest-button">Guest</button>
    </form>
</div>

<div id="register-content" class="content">
    <h1>Register</h1>
    <form id="register-form" onsubmit="return false;">
        <input type="text" name="email"><br>
        <input type="text" name="password"><br>
        <button id="register-button">Register</button>
    </form>
</div>

<div id="guest-content" class="hidden content">
    <button id="back-to-login-page">Back to login page</button>
</div>

<div id="guest-loaded-content" class="hidden content">
</div>

<div id="welcome-content" class="hidden content">
    <h1>GameShop</h1>
    <button id="mainpage-button" >Main Page</button>
    <button id="games-button" >Load games</button>
    <button id="profile-button" >My profile</button>
    <button id="my-purchases-button">My purchases</button>
    <button id="logout-button">Logout</button>
    <br>
</div>

<div id="games-content" class="hidden content games"></div>

<div id="profile-content" class="hidden content">
</div>

<div id="change-profile-infos-content" class="hidden content">
    <br>
    <h2>If you want to update your profile infos, please fill the field below</h2>
    <br>
    <form id="change-profile-infos-form" onsubmit="return false;">
        <input type="text" name="name" placeholder="name"><br>
        <input type="text" name="address" placeholder="address"><br>
        <input type="text" name="phoneNumber" placeholder="phone number"><br>
        <button id="change-profile-infos-button">Change profile infos</button>
    </form>
</div>

<div id="add-to-balance-content" class="hiddent content">
    <form id="add-to-balance-form" onsubmit="return false;">
        <br><br>
        <input type="text" name="balance" placeholder="123456"><br>
        <button id="add-to-balance-button">Add to balance</button>
    </form>
    <button id="actual-balance-button">My actual balance</button>
</div>

<div id="my-purchases-content" class="hidden content">
</div>

<div id="admin-content" class="hidden content">
    <form id="admin-form" onsubmit="return false;">
        <br><br>
        <h2>You logged in as an admin, if you want to add a game to the database, please
        fill the fields below:</h2>
        <br>
        <input type="text" name="name" placeholder="new game name"><br>
        <input type="text" name="platform" placeholder="new game platform"><br>
        <input type="url" name="imageUrl" placeholder="new game image url"><br>
        <input type="number" name="price" placeholder="new game price"><br>
        <button id="admin-button">Save game</button>
    </form>
</div>


</body>
</html>
