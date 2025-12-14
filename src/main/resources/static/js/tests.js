function filterTests() {
    const query = document.getElementById("searchInput").value.toLowerCase();
    const cards = document.querySelectorAll(".tests-card");

    cards.forEach(card => {
        const title = card.getAttribute("data-title");
        card.style.display = title.includes(query) ? "block" : "none";
    });
}
