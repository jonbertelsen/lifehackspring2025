document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".toggle-btn").forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault();
            const form = this.closest("form");
            const reminderId = form.querySelector("input[name='reminderId']").value;
            const currentText = this.textContent.trim();
            let route = "";
            if (currentText === "Tilmeld") {
                route = "/lifehackTeam3/tilmeld";
            } else {
                route = "/lifehackTeam3/afmeld";
            }
            fetch(route, {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "reminderId=" + encodeURIComponent(reminderId)
            }).then(response => {
                if (response.ok) {
                    // Skift knap-tekst og -farve lokalt
                    if (currentText === "Tilmeld") {
                        this.textContent = "Afmeld";
                        this.classList.add("afmeld-btn");
                    } else {
                        this.textContent = "Tilmeld";
                        this.classList.remove("afmeld-btn");
                    }
                } else {
                    console.error("Fejl under til-/afmelding");
                }
            }).catch(error => {
                console.error("Netværksfejl:", error);
            });
        });
    });
});
