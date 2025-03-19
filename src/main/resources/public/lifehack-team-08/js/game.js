let eggs = 10000000000;
let chickenFeedTier = 0;
let predatorTier = 0;
let eggGainInterval = null;

function incrementCounter() {
    let eggGain = chickenFeedTier + 1; // 1 by default, increases with feed upgrades
    eggs += eggGain;
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

function startEggGain() {
    // Clear existing interval to prevent stacking
    if (eggGainInterval) clearInterval(eggGainInterval);

    // Calculate eggs gained per second
    let gainPerSecond = predatorTier > 0 ? Math.pow(2, predatorTier - 1) : 0;

    if (gainPerSecond > 0) {
        eggGainInterval = setInterval(() => {
            eggs += gainPerSecond;
            document.getElementById("egg-counter").textContent = eggs;
        }, 1000);
    }
}

function buyItem(item) {
    if (item === 'chickenFeed') {
        if (chickenFeedTier >= 5) {  // Max level now 5
            document.getElementById("buy-chicken-feed").classList.add("disabled-button");
            document.getElementById("buy-chicken-feed").disabled = true;
            document.getElementById("upgrade-text-1").textContent = "Max level reached!";
            return;
        }

        let cost = Math.pow(chickenFeedTier + 1, 2) * 50;
        if (eggs >= cost) {
            eggs -= cost;
            chickenFeedTier++;
            document.getElementById("egg-counter").textContent = eggs;

            if (chickenFeedTier < 5) {
                document.getElementById("upgrade-text-1").textContent = `Upgrade ${chickenFeedTier}/5 - ${Math.pow(chickenFeedTier + 1, 2) * 50} eggs`;
            } else {
                document.getElementById("upgrade-text-1").textContent = "Max level reached!";
                document.getElementById("buy-chicken-feed").style.display = "none";
            }
        } else {
            alert("Not enough eggs!");
        }
    }
    else if (item === 'predator') {
        if (predatorTier >= 5) { // Max level now 5
            document.getElementById("buy-predator").classList.add("disabled-button");
            document.getElementById("buy-predator").disabled = true;
            document.getElementById("upgrade-text-2").textContent = "Max level reached!";
            return;
        }

        let cost = Math.pow(predatorTier + 1, 2) * 100;
        if (eggs >= cost) {
            eggs -= cost;
            predatorTier++;
            document.getElementById("egg-counter").textContent = eggs;

            if (predatorTier < 5) {
                document.getElementById("upgrade-text-2").textContent = `Upgrade ${predatorTier}/5 - ${Math.pow(predatorTier + 1, 2) * 100} eggs`;
            } else {
                document.getElementById("upgrade-text-2").textContent = "Max level reached!";
                document.getElementById("buy-predator").style.display = "none";
            }

            updatePredatorImage(predatorTier);
            startEggGain();
        } else {
            alert("Not enough eggs!");
        }
    }
}

function updatePredatorImage(tier) {
    let predatorContainer = document.getElementById("predator-container");
    predatorContainer.innerHTML = ""; // Clear previous predator image

    let predatorImg = document.createElement("img");
    predatorImg.classList.add("predatorImg");

    predatorImg.src = `../../public/lifehack-team-08/images/predator${tier}.png`;
    predatorImg.alt = `Predator Level ${tier}`;

    predatorContainer.appendChild(predatorImg);
}

