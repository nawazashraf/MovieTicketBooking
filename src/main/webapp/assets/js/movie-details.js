const dateButtons = document.querySelectorAll(".date-btn");
const showDates = document.querySelectorAll(".show-date");
const showTimeButtons = document.querySelectorAll(".show-time-btn")

dateButtons.forEach(button => {

    button.addEventListener("click", () => {

        dateButtons.forEach(btn => {
            btn.classList.remove("active");
        });

        button.classList.add("active");

        const selectedDate = button.dataset.date;

        showDates.forEach(section => {

            if (selectedDate === "undefined") {
                section.style.display = "block";
            }
            else if (section.dataset.date === selectedDate) {
                section.style.display = "block";
            }
            else {
                section.style.display = "none";
            }

        });

    });

});

showTimeButtons.forEach(button => {

    button.addEventListener("click", function () {

        showTimeButtons.forEach(btn => {
            btn.classList.remove("selected");
        });

        this.classList.add("selected");

        const showId = this.dataset.showId;
        const showDate = this.dataset.showDate;
        const mallName = this.dataset.mallName;
        const showTime = this.dataset.showTime;

        selectedShow.textContent =
            mallName + " • " + showDate + " • " + showTime;

        proceedButton.disabled = false;
        proceedButton.dataset.showId = showId;

    });

});

proceedButton.addEventListener("click", function() {

    const showId = this.dataset.showId;

    window.location.href =
        contextPath + "/booking/seats?showId=" + encodeURIComponent(showId);

});