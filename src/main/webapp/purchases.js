function onMyPurchasesButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'protected/users');
    xhr.onload = function () {
        if (this.status === OK) {
            let games = JSON.parse(this.responseText);
            let output = '';
            for (var i in games) {
                output += '<div id="my-purchases-content" class="content">' +
                    '<ul>' +
                    '<li><img src="' + games[i].imageUrl + '" width="150" height="150"></li>' +
                    '<li>Name: ' + games[i].name + '</li>' +
                    '<li>Platform: ' + games[i].platform + '</li>' +
                    '</ul>' +
                    '</div>';
            }
            document.getElementById('my-purchases-content').innerHTML = output;
        }
    }
    showContents(['welcome-content', 'my-purchases-content']);
    xhr.send();
}
