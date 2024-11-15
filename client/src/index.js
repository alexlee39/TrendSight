var submitBtn = document.querySelector('.btn');

// handling registration form 
const registrationForm = document.getElementById('registerForm');

registrationForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const termsAccepted = document.getElementById('termsCheckbox').checked;
    if (!termsAccepted) {
        alert("terms and conditions must be checked before registering");
        return;
    }

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const registerData = {
        name: name,
        email: email,
        password: password
    };
    
    console.log("registerData:", JSON.stringify(registerData, null, 2));
})

////////////////////////////////////////////////////////////////////////////////////////////
// UI Functions
// swapping between register and login 
const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registerLink = document.querySelector('.register-link');
//swapping between login pop and close
const loginPopup = document.querySelector('.login-popup');
const iconClose = document.querySelector('.icon-close');


//clicking links adds "active" and CSS responds accordingly (sets transfromX to 400,0, and -400)
registerLink.addEventListener('click', ()=> {
    wrapper.classList.add('active');
});

loginLink.addEventListener('click', ()=> {
    wrapper.classList.remove('active');
});

//clicking links adds "active-popup" and CSS responds accordingly (simply sets scale to 0 and 1)
loginPopup.addEventListener('click', ()=> {
    wrapper.classList.add('active-popup');
});

iconClose.addEventListener('click', ()=> {
    wrapper.classList.remove('active-popup');
});
