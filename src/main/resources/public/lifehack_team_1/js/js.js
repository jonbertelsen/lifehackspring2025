function loadallplayers() {
    if (window.sessionStorage.teamcount) {
        for (let i = 0; i < window.sessionStorage.teamcount; i++) {
            var div = document.createElement('div');
            div.setAttribute('class', 'player_block');
            div.innerHTML = `
                        <p class="team_name">${"team" + (i+1)}</p>
                        <div class="flex-box">
                            <!--wrong variable-->
                            <button onclick="updatepoint(1, '${"team" + (i+1)}')">+</button>
                            <h2 id="${"team" + (i+1) + "_points"}"> ${getpoints(i+1)}</h2>
                            <!--wrong variable-->
                            <button onclick="updatepoint(-1, '${"team" + (i+1)}')">-</button>
                        </div>
                `;
            document.getElementById('fields').insertBefore(div,document.getElementById('addplayer_field'));
        }
    }
}
window.onload = loadallplayers;

function getpoints(p1){
    var point = Number(sessionStorage.getItem('team' + p1));
    if (point == null || point == 0) {
        return 0;
    }
    else {
        return point;
    }
}

function updatepoint(p1, team) {
    var point = Number(sessionStorage.getItem(team));
    if (point == null || point == 0) {
        window.sessionStorage.setItem(team,p1);
    }
    else {
        window.sessionStorage.setItem(team,point + p1);
    }
    document.getElementById(team + "_points").innerHTML = sessionStorage.getItem(team);
}

function addplayer() {
    if (window.sessionStorage.teamcount) {
        window.sessionStorage.teamcount = Number(window.sessionStorage.teamcount) + 1;
    } else {
        window.sessionStorage.teamcount = 1;
    }
    var div = document.createElement('div');
    div.setAttribute('class', 'player_block');
    div.innerHTML = `
                        <p class="team_name">${"team" + window.sessionStorage.teamcount}</p>
                        <div class="flex-box">
                            <!--wrong variable-->
                            <button onclick="updatepoint(1, '${"team" + window.sessionStorage.teamcount}')">+</button>
                            <h2 id="${"team" + window.sessionStorage.teamcount + "_points"}">0</h2>
                            <!--wrong variable-->
                            <button onclick="updatepoint(-1, '${"team" + window.sessionStorage.teamcount}')">-</button>
                        </div>
                `;
    document.getElementById('fields').insertBefore(div,document.getElementById('addplayer_field'));
}