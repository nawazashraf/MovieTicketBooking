function startDummyPayment(event) {

	event.preventDefault();

	var form =
		document.getElementById(
			"dummyPaymentForm"
		);

	var button =
		document.getElementById(
			"dummyPayButton"
		);

	button.disabled = true;

	button.innerText =
		"Processing Payment...";


	var delay =
		5000 +
		Math.floor(
			Math.random() * 5001
		);


	console.log(
		"Payment will submit after " +
		(delay / 1000) +
		" seconds"
	);


	setTimeout(function() {

		form.submit();

	}, delay);

}