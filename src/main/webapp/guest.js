function onGuestButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'games');
    xhr.onload = function () {
        if (this.status == OK) {
            let games = JSON.parse(this.responseText);
            let output = '';
            for (var i in games) {
                output += '<div id="games-content" class="content games">' +
                    '<img src="' + games[i].imageUrl + '" width="150" height="150">' +
                    '<ul>' +
                    '<li>Name: ' + games[i].name + '</li>' +
                    '<li>Platform: ' + games[i].platform + '</li>' +
                    '<li>Price: ' + games[i].price + '</li>' +
                    '<li><button id="buy-game" content="' + games[i].id + '">Buy game</button> </li>' +
                    '</ul>' +
                    '</div>';
            }
            document.getElementById('guest-loaded-content').innerHTML = output;
        }
    }
    showContents(['guest-loaded-content', 'guest-content']);
    xhr.send();
}
