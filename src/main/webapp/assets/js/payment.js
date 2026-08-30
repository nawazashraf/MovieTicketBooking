
function togglePaymentInputs() {
    var selected = document
        .querySelector('input[name="paymentMethod"]:checked').value;
    var upiSection = document.querySelector('.upi-details');
    var cardSection = document.querySelector('.card-details');

    if (selected === 'UPI') {
        upiSection.classList.add('active');
        cardSection.classList.remove('active');
    } else {
        cardSection.classList.add('active');
        upiSection.classList.remove('active');
    }
}

document.querySelectorAll('input[name="paymentMethod"]').forEach(
    function(radio) {
        radio.addEventListener('change', togglePaymentInputs);
    });

togglePaymentInputs();

// Auto-format card number: 1234 5678 9012 3456
var cardNumberInput = document.getElementById('cardNumber');
cardNumberInput.addEventListener('input', function(e) {
    var digits = e.target.value.replace(/\D/g, '').slice(0, 16);
    e.target.value = digits.replace(/(.{4})/g, '$1 ').trim();
});

// Auto-format expiry: MM/YY
var expiryInput = document.getElementById('expiryDate');
expiryInput.addEventListener('input', function(e) {
    var digits = e.target.value.replace(/\D/g, '').slice(0, 4);
    if (digits.length >= 3) {
        e.target.value = digits.slice(0, 2) + '/' + digits.slice(2);
    } else {
        e.target.value = digits;
    }
});

// CVV: digits only
var cvvInput = document.getElementById('cvv');
cvvInput.addEventListener('input', function(e) {
    e.target.value = e.target.value.replace(/\D/g, '');
});
