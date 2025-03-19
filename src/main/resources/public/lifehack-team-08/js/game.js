let eggs = 10000000000;
let chickenFeedTier = 0;
let predatorTier = 0;

function incrementCounter() {
    eggs++;
    document.getElementById("egg-counter").textContent = eggs;

    let egg = document.createElement('div');
    egg.classList.add('egg');

    // Egg falling
    let chickenBox = document.querySelector('.chicken-box');
    egg.style.left = `${chickenBox.offsetLeft + chickenBox.offsetWidth / 2 - 25}px`;
    egg.style.top = `px`;

    document.body.appendChild(egg);

    egg.addEventListener('animationend', () => {
        egg.remove();
    });

    // Chicken jump
    let chickenImg = document.getElementById('chicken-img');
    chickenImg.classList.add('jump');

    chickenImg.addEventListener('animationend', () => {
        chickenImg.classList.remove('jump');
    });
}

function openShop() {
    document.getElementById("shop-window").style.display = "block";
}

function closeShop() {
    document.getElementById("shop-window").style.display = "none";
}

function buyItem(item) {
    if (item === 'chickenFeed') {
        if (chickenFeedTier >= 4) {
            document.getElementById("buy-chicken-feed").classList.add("disabled-button");
            document.getElementById("buy-chicken-feed").disabled = true;
            document.getElementById("upgrade-text-1").textContent = "Max level reached!";
            return;
        }

        let cost = Math.pow(chickenFeedTier + 1, 2) * 50; // Increase cost with each level
        if (eggs >= cost) {
            eggs -= cost; // Deduct the cost from the eggs
            chickenFeedTier++; // Upgrade the chicken feed level
            document.getElementById("egg-counter").textContent = eggs;
            document.getElementById("upgrade-text-1").textContent = `Upgrade ${chickenFeedTier}/5 - ${Math.pow(chickenFeedTier + 1, 2) * 50} eggs`;
        } else {
            alert("Not enough eggs!");
        }
    } else if (item === 'predator') {
        if (predatorTier >= 4) {
            document.getElementById("buy-predator").classList.add("disabled-button");
            document.getElementById("buy-predator").disabled = true;
            document.getElementById("upgrade-text-2").textContent = "Max level reached!";
            return;
        }

        let cost = Math.pow(predatorTier + 1, 2) * 100; // Increase cost with each level
        if (eggs >= cost) {
            eggs -= cost; // Deduct the cost from the eggs
            predatorTier++; // Upgrade the predator level
            document.getElementById("egg-counter").textContent = eggs;
            document.getElementById("upgrade-text-2").textContent = `Upgrade ${predatorTier}/5 - ${Math.pow(predatorTier + 1, 2) * 100} eggs`;
        } else {
            alert("Not enough eggs!");
        }
    }
}