const coin = document.getElementById('coin');
const flipButton = document.getElementById('flipButton');
const resultDisplay = document.getElementById('result');
const scoreDisplay = document.getElementById('score');
flipButton.addEventListener('click', () => {
    let outcome = Math.random() < 0.5 ? 'heads' : 'tails';

    let rotation = 1080 + Math.random() * 720; // Spins multiple times

    coin.style.transition = 'transform 1.5s ease-out';
    coin.style.transform = `translate(-50%, -50%) rotateY(${rotation}deg)`;  // Keep translate fixed

    setTimeout(() => {
        coin.style.transition = 'none';
        coin.style.transform = `translate(-50%, -50%) ${outcome === 'heads' ? 'rotateY(0deg)' : 'rotateY(180deg)'}`;
        if (outcome === 'heads') {
            currentStreak++;  // Increase streak if heads
        } else {
            currentStreak = 0; // Reset streak if tails
        }

        resultDisplay.textContent = `It's ${outcome}!`;  // Display result
        scoreDisplay.textContent = `${currentStreak}`;
    }, 1500); // Match transition duration
});