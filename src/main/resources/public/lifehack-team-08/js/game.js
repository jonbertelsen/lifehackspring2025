let eggs = 0;
let chickenFeedTier = 0;
let predatorTier = 0;
let eggGainInterval = null;


/* Clicking the chicken increases egg gain */
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

/* Upgraded gain for eggs */
function startEggGain() {
    // Clear existing interval to prevent stacking
    if (eggGainInterval) clearInterval(eggGainInterval);

    // Calculate eggs gained per second
    let gainPerSecond = predatorTier > 0 ? Math.pow(4, predatorTier - 1) : 0;

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

            // Remove previous predator image (if any)
            let existingPredator = document.getElementById("predator-img");
            if (existingPredator) {
                existingPredator.remove();
            }

            // Create a new predator image
            let predator = document.createElement("img");
            predator.src = `/lifehack-team-08/images/predator${predatorTier}.png`;
            predator.id = "predator-img";
            predator.classList.add("predatorImg");

            // Set custom sizes based on tier
            let sizes = [600, 300, 750, 900, 500]; // Define sizes for each tier
            predator.style.width = `${sizes[predatorTier - 1]}px`;
            predator.style.height = "auto"; // Keep aspect ratio

            // Assign different positions based on predatorTier
            let positions = [
                { top: "365px", left: "600px" },
                { top: "275px", right: "350px" },
                { bottom: "-235px", left: "550px" },
                { bottom: "-310px", right: "50px" },
                { bottom: "-310px", left: "750px" }
            ];

            let pos = positions[predatorTier - 1]; // Get position for the current tier
            predator.style.position = "absolute";
            if (pos.top) predator.style.top = pos.top;
            if (pos.bottom) predator.style.bottom = pos.bottom;
            if (pos.left) predator.style.left = pos.left;
            if (pos.right) predator.style.right = pos.right;

            // Add predator to the page
            document.body.appendChild(predator);
            startEggGain();
        } else {
            alert("Not enough eggs!");
        }
    }
}




