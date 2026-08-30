var selectedSeats = [];
var total = 0;


/* =====================================================
   SELECT / DESELECT
===================================================== */

function selectSeat(button) {

	var seatId = button.id;

	var seatName = button.getAttribute("data-seat");

	var price = parseFloat(
		button.getAttribute("data-price")
	);


	/* ================= DESELECT ================= */

	if (button.classList.contains("selected")) {

		button.classList.remove("selected");

		button.classList.add("available");


		for (var i = 0; i < selectedSeats.length; i++) {

			if (selectedSeats[i].id == seatId) {

				total = total - selectedSeats[i].price;

				selectedSeats.splice(i, 1);

				break;
			}
		}
	}


	/* ================= SELECT ================= */

	else {

		button.classList.remove("available");

		button.classList.add("selected");


		selectedSeats.push({

			id: seatId,

			name: seatName,

			price: price
		});


		total = total + price;
	}


	updateSummary();
}


/* =====================================================
   UPDATE SUMMARY
===================================================== */

function updateSummary() {

	var names = "";

	var ids = "";


	for (var i = 0; i < selectedSeats.length; i++) {

		names += selectedSeats[i].name;

		ids += selectedSeats[i].id;


		if (i < selectedSeats.length - 1) {

			names += ", ";

			ids += ",";
		}
	}


	if (names == "") {

		names = "No seats selected";
	}


	document.getElementById(
		"selectedText"
	).innerHTML = names;


	document.getElementById(
		"seatCount"
	).innerHTML = selectedSeats.length;


	document.getElementById(
		"total"
	).innerHTML = total.toFixed(0);


	document.getElementById(
		"selectedSeatsInput"
	).value = ids;


	/* ================= CONTINUE ================= */

	var continueButton =
		document.getElementById("continueButton");

	var continueMessage =
		document.getElementById("continueMessage");


	if (selectedSeats.length > 0) {

		continueButton.disabled = false;

		continueButton.classList.add("active");

		continueMessage.innerHTML =
			"Click Continue to proceed";
	}

	else {

		continueButton.disabled = true;

		continueButton.classList.remove("active");

		continueMessage.innerHTML =
			"Select your seats to continue";
	}
}


/* =====================================================
   FORM VALIDATION
===================================================== */

function checkSeats() {

	if (selectedSeats.length == 0) {

		alert("Please select at least one seat.");

		return false;
	}

	return true;
}