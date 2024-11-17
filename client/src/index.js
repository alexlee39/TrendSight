// handling registration form 
const registrationForm = document.getElementById('registerForm');

registrationForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const termsAccepted = document.getElementById('termsCheckbox').checked;
    if (!termsAccepted) {
        alert("terms and conditions must be checked before registering");
        return;
    }

    const user = document.getElementById('user').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const registerData = {
        user: user,
        email: email,
        password: password
    };
    
    console.log("registerData:", JSON.stringify(registerData, null, 2));
    try {
        const response = await fetch('http://localhost:5000/auth/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-ww-form-urlencoded',
            },
            body: `email=${encodeURIComponent(email)}&pass=${encodeURIComponent(password)}&user=${encodeURIComponent(user)}`, // Backend expects these keys
        });
        if (response.ok){
            const data = await response.text();
            console.log("registration - back-end message: ", data);
            alert("account created, login"); // swap to different page 
        }
        else{
            const errorText = await response.text();
            console.error("registration - errorText: ", errorText);
            alert("account not created");
        }
    } 
    catch (error){
        console.error("Error: ", error);
        alert("registration - data wasnt sent to back-end");
    }
})


// handling log in form 
const loginForm = document.getElementById('loginForm');

loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.getElementById('userID').value; // getting data from HTML input 
    const password = document.getElementById('passID').value; 

    console.log("Attempting to login with: ", {email, password});
    try {
        const response = await fetch('http://localhost:5000/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-ww-form-urlencoded',
            },
            body: `email=${encodeURIComponent(email)}&pass=${encodeURIComponent(password)}`, // Backend expects these keys
        });
        if (response.ok){
            const data = await response.text();
            console.log("login - back-end message: ", data);
            alert("logged in"); // swap to different page 
        }
        else{
            const errorText = await response.text();
            console.error("login - errorText: ", errorText);
            alert("unable to login");
        }
    } 
    catch (error){
        console.error("Error: ", error);
        alert("data wasnt sent to back-end");
    }

});

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
