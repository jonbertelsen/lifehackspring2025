document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".tilmeld-btn").forEach(button => {
        button.addEventListener("click", function () {
            if (this.textContent === "Tilmeld") {
                this.textContent = "Afmeld";
                this.classList.add("afmeld-btn");
            } else {
                this.textContent = "Tilmeld";
                this.classList.remove("afmeld-btn");
            }
        });
    });
});
