// handling registration form 
const registrationForm = document.getElementById('registerForm');
const loginForm = document.getElementById('loginForm');


registrationForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const termsAccepted = document.getElementById('termsCheckbox').checked;
    if (!termsAccepted) {
        alert("terms and conditions must be checked before registering");
        return;
    }

    const name = document.querySelector('#username').value;
    const email = document.querySelector('#reg-email').value; 
    const password = document.querySelector('#reg-password').value;
    if(checkValidEmail(email) == false){
        notAnEmail(wrongRegDetails);
        incorrectEmailPass(wrongRegDetails, regInputBox);
        return;

    }
    // TODO
    // Check Unique username in DB?
    // Check unique email in DB ***** 
    // Check strength of PW 
        // --> (At least) 1 uppercase, 1 lwrcase, 1 number, 1 unique char(!,#,@,_,etc), 
    
    //TODO: Need to test
    try{
        const res = await fetch('localhost:5000/auth', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        })
    }
    catch(error){
        console.error('Server error!');
        alert('Internal server error');
    }

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
loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.querySelector('#login-email').value; // getting data from HTML input 
    const password = document.querySelector('#login-password').value; 
    
    //Remove if we want Server to filter unnecessary emails
    if(checkValidEmail(email) == false){
        console.log('Invalid email');
        notAnEmail(wrongLoginDetails)
        incorrectEmailPass(wrongLoginDetails, loginInputBox);
        return;
    }
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
            incorrectEmailPass(wrongLoginDetails, loginInputBox);
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

const checkValidEmail = (mail) =>{
    if(mail.includes("@")){
        console.log('Basic Validity Checked');
        return true;
    }
    else{
        console.log('Not a legit email');
        return false;
        //Invoke html to display 'invalid email' or smth like that
    }
};
////////////////////////////////////////////////////////////////////////////////////////////
// UI Functions
// swapping between register and login 
const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registerLink = document.querySelector('.register-link');
//swapping between login pop and close
const loginPopup = document.querySelector('.login-popup');
const iconClose = document.querySelector('.icon-close');
//
const loginInputBox = document.querySelectorAll('.input-box')[1];
const regInputBox = document.querySelectorAll('.input-box')[4];

const wrongCredentials = document.querySelectorAll('.wrong-credentials');
const wrongLoginDetails = wrongCredentials[0];
const wrongRegDetails = wrongCredentials[1];
//Swap between hamburger and sidebar
const hamMenu = document.querySelector('.ham-menu');
const offScreenMenu = document.querySelector('.off-screen-menu');

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

/* Helper function that displays an Invalid Email Addr
 * @param: wrongDetails(wrongLoginDetails or wrongRegDetails)
 *
*/ 
const notAnEmail = (wrongDetails) => {
    wrongDetails.textContent = 'Not a valid email address';
}

/* Helper funct that displays Incorrect Login Details OR Incorrect Register Details(user/email already exists)
 * @param: details: (wrongLoginDetails, wrongRegDetails)
 * inputBox: (loginInputBox, regInputBox)
 */
const incorrectEmailPass = (details, inputBox) =>{
    details.style.display = 'block';
    inputBox.style.marginBottom = '0px';   
}
