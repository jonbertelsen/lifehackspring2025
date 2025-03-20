// Når DOM er loadet kører vi denne kode
document.addEventListener("DOMContentLoaded", function () {
    // Finder alle elementer med klassen toggle-btn
    document.querySelectorAll(".toggle-btn").forEach(button => {
        // Lyt efter klik på hver knap
        button.addEventListener("click", function (e) {
            // Stop standard handling som fx at sende formularen
            e.preventDefault()
            // Find den nærmeste form for den knap der blev klikket
            const form = this.closest("form")
            // Hent værdien fra inputfeltet med navnet reminderId
            const reminderId = form.querySelector("input[name='reminderId']").value
            // Gem den nuværende knaptekst uden ekstra mellemrum
            const currentText = this.textContent.trim()
            let route = ""
            // Hvis knappen viser Tilmeld, sæt ruten til tilmeld ellers til afmeld
            if (currentText === "Tilmeld") {
                route = "/lifehackTeam3/tilmeld"
            } else {
                route = "/lifehackTeam3/afmeld"
            }
            // Lav et POST kald med reminderId som parameter
            fetch(route, {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: "reminderId=" + encodeURIComponent(reminderId)
            }).then(response => {
                // Hvis svaret er ok skift knaptekst og klasse
                if (response.ok) {
                    if (currentText === "Tilmeld") {
                        this.textContent = "Afmeld"
                        this.classList.add("afmeld-btn")
                    } else {
                        this.textContent = "Tilmeld"
                        this.classList.remove("afmeld-btn")
                    }
                } else {
                    console.error("Fejl under til eller afmelding")
                }
            }).catch(error => {
                console.error("Netværksfejl", error)
            })
        })
    })
})
