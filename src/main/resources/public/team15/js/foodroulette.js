function playGif(category) {
    const proteinOptions = [
        { src: "/public/lifehackTeam15/images/Cow-G.gif", text: "Beef" },
        { src: "/public/lifehackTeam15/images/Chicken-G.gif", text: "Chicken" },
        { src: "/public/lifehackTeam15/images/Fish-G.gif", text: "Fish" }
    ];

    const sidesOptions = [
        { src: "/public/lifehackTeam15/images/Rice-G.gif", text: "Rice" },
        { src: "/public/lifehackTeam15/images/Pasta-GIF-Stop.gif", text: "Pasta" },
        { src: "/public/lifehackTeam15/images/Potatoe-G.gif", text: "Potatoes" }
    ];

    if (category === "protein-gif") {
        const selectedGif = proteinOptions[Math.floor(Math.random() * proteinOptions.length)];
        document.getElementById("protein-gif").src = selectedGif.src;
        document.getElementById("protein-result").innerText = selectedGif.text;
        document.getElementById("protein-gif").classList.remove("hidden");
    }

    // Håndter Sides-knappen
    else if (category === "sides-gif") {
        const selectedGif = sidesOptions[Math.floor(Math.random() * sidesOptions.length)];
        document.getElementById("sides-gif").src = selectedGif.src;
        document.getElementById("sides-result").innerText = selectedGif.text;
        document.getElementById("sides-gif").classList.remove("hidden");

    }
    showRecipe();
}

const recipeLinks = {
    "Beef": {
        "Rice": [
            {name: "Fyldte peberfrugter med mexicansk krydret oksekød", link: "https://emmaolsen.dk/fyldte-peberfrugter-med-mexicansk-krydret-oksekoed/"},
            {name: "Mexicansk gryderet med oksekød", link: "https://emmaolsen.dk/mexicansk-gryderet-med-oksekoed/"},
            {name: "Koreansk ris-bowl",link:"https://emmaolsen.dk/koreansk-ris-bowl/"}
        ],
        "Pasta": [
            {name: "Italienske kødboller i tomatsovs", link: "https://emmaolsen.dk/italienske-kodboller-i-tomatsovs/"}
        ],
        "Potatoes": [
            {name: "Kartoffelfad med oksekød og grønt", link: "https://emmaolsen.dk/kartoffelfad-med-oksekoed-og-groent/"}
        ]
    },
    "Chicken": {
        "Rice": [
            {name: "Kylling med ris og broccoli", link: "https://example.com/kylling-ris"}
        ],
        "Pasta": [
            {name: "Kylling i flødesovs med pasta", link: "https://example.com/kylling-pasta"}
        ],
        "Potatoes": [
            {name: "Oksekød med kartofler og pebersauce", link: "https://example.com/oksekoed-kartofler"}
        ]
    },
    "Fish": {
        "Rice": [
            {name: "IPSUM", link: "https://example.com/oksekoed-med-ris"},
            {name: "IPSUM", link: "https://example.com/oksekoed-teriyaki-ris"}
        ],
        "Pasta": [
            {name: "Oksekød og pasta med tomatsovs", link: "https://example.com/oksekoed-pasta"}
        ],
        "Potatoes": [
            {name: "Oksekød med kartofler og pebersauce", link: "https://example.com/oksekoed-kartofler"}
        ]
        // Flere opskrifter kan tilføjes her...
    }
}

function showRecipe() {
    const protein = document.getElementById("protein-result").innerText;
    const sides = document.getElementById("sides-result").innerText;

    if (protein && sides) {
        // Find opskriftslinks for kombinationen af protein og sides
        const recipes = recipeLinks[protein][sides];

        // Tøm eksisterende opskriftsbokse
        const recipeContainer = document.getElementById("recipe-links");
        recipeContainer.innerHTML = "";

        // Skab en boks for hver opskrift
        recipes.forEach(recipe => {
            const recipeBox = document.createElement("div");
            recipeBox.classList.add("recipe-box");
            recipeBox.innerHTML = `<a href="${recipe.link}" target="_blank">${recipe.name}</a>`;
            recipeContainer.appendChild(recipeBox);
        });
    } else {
        document.getElementById("recipe-links").innerHTML = "Choose BOTH a protein and sides for recipes.";
    }
}


