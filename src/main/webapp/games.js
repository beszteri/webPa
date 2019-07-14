function loadGames() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'games');
    xhr.onload = function () {
        if (this.status == OK) {
            let games = JSON.parse(this.responseText);
            const tbodyEl = document.createElement('tbody');
            tbodyEl.setAttribute('class', 'buy-cells');


            for (let i = 0; i < games.length; i++) {
                const game = games[i];

                const imageTdEl = document.createElement('td');
                imageTdEl.classList.add('default-cell');
                imageTdEl.innerHTML = '<img src="' + game.imageUrl + '" width="150" height="150">';

                const nameTdEl = document.createElement('td');
                nameTdEl.classList.add('default-cell');
                nameTdEl.textContent = game.name;

                const platformTdEl = document.createElement('td');
                platformTdEl.classList.add('default-cell');
                platformTdEl.textContent = game.platform;

                const priceTdEl = document.createElement('td');
                priceTdEl.classList.add('default-cell');
                priceTdEl.textContent = game.price;

                const buttonBuyEl = document.createElement('button');
                buttonBuyEl.textContent = "Buy game";
                buttonBuyEl.setAttribute('id', 'buy-game' + game.id);

                buttonBuyEl.dataset.gameBuyId = game.id;
                buttonBuyEl.addEventListener('click', onBuyButtonClicked);

                buttonBuyEl.dataset.gamePrice = game.price;

                const buttonOneTdEl = document.createElement('td');
                buttonOneTdEl.appendChild(buttonBuyEl);
                buttonOneTdEl.setAttribute('id', 'buy-game-' + game.id);

                const trEl = document.createElement('tr');
                trEl.setAttribute('id', 'row-game-id-' + game.id);
                trEl.appendChild(imageTdEl);
                trEl.appendChild(nameTdEl);
                trEl.appendChild(platformTdEl);
                trEl.appendChild(priceTdEl);
                if (getCurrentUser().role === 'REGISTERED') {
                    trEl.appendChild(buttonOneTdEl);
                }
                tbodyEl.appendChild(trEl);
                gamesContentDivEl.innerHTML = '';
                gamesContentDivEl.appendChild(tbodyEl);
            }


            //return tbodyEl;





            /*          if (this.status == OK) {
                          let games = JSON.parse(this.responseText);
                          let output = '';
                          for (var i in games) {
                              output += '<div id="games-content" class="content games">' +
                                  '<img src="' + games[i].imageUrl + '" width="150" height="150">' +
                                  '<ul>' +
                                  '<li>Name: ' + games[i].name + '</li>' +
                                  '<li>Platform: ' + games[i].platform + '</li>' +
                                  '<li>Price: ' + games[i].price + '</li>' +
                                  '<li><button id="buy-game" name="buy-game" content="gameId' + games[i].id + '">Buy game</button> </li>' +
                                  '</ul>' +
                                  '</div>';
                          }
                        //  document.getElementById('games-content').innerHTML = output;
                      } */
        }
    }
    showContents(['welcome-content', 'games-content']);
    xhr.send();

}








function onBuyButtonClicked() {
    const gameBuyId = this.dataset.gameBuyId;

    const params = new URLSearchParams();
    params.append('gameBuyId', gameBuyId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onGameBuyResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/purchases');

    xhr.send(params);
}

function onGameBuyResponse() {
    if (this.status === OK) {
        const message = JSON.parse(this.responseText);
        alert(message.message);
        showContents(['welcome-content', 'games-content']);
    }else if (this.status === INTERNAL_SERVER_ERROR){
        const json = JSON.parse(this.responseText);{
            alert(`Already in purchase list ! \nServer error: ${json.message}`);
        }
    }
    else {
        onOtherResponse(gamesContentDivEl, this);
    }
}
