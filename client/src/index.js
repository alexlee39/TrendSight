// handling registration form 
const registrationForm = document.getElementById('registerForm');
const loginForm = document.getElementById('loginForm');


registrationForm.addEventListener('submit', (event) => {
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
        return;
    }
    // TODO
    // Check Unique username in DB?
    // Check unique email in DB ***** 
    // Check strength of PW 
        // --> (At least) 1 uppercase, 1 lwrcase, 1 number, 1 unique char(!,#,@,_,etc), 

    const registerData = {
        name: name,
        email: email,
        password: password
    };
    
    console.log("registerData:", JSON.stringify(registerData, null, 2));
})


// handling log in form 
loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.querySelector('#login-email').value; // getting data from HTML input 
    const password = document.querySelector('#login-password').value; 
    
    //Remove if we want Server to filter unnecessary emails
    if(checkValidEmail(email) == false){
        console.log('Invalid email');
        wrongEmailOrPass();
        return;
    }
    console.log("Attempting to login with: ", {email, password});
    try {
        const response = await fetch('http://localhost:5000/auth', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-ww-form-urlencoded',
            },
            body: `userID=${encodeURIComponent(email)}&passID=${encodeURIComponent(password)}`, // Backend expects these keys
        });
        if (response.ok){
            const data = await response.text();
            console.log("data: ", data);
            alert("correct input"); // swap to different page 
        }
        else{
            wrongEmailOrPass();
            const errorText = await response.text();
            console.error("errorText: ", errorText);
            alert("wrong input");
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
const wrongDetails = document.querySelector('.wrong-credentials');
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

// hamMenu.addEventListener('click', () => {
//     hamMenu.classList.toggle('active');
//     offScreenMenu.classList.toggle('active');
// })
// inputBox.style.display = 'block';
const wrongEmailOrPass = () =>{
    console.log('this ran!');
    wrongDetails.style.display = 'block';
    // inputBox.style.margin = '30px 0px 0px';
    // console.log(inputBox);
    loginInputBox.style.marginBottom = '0px';
}
