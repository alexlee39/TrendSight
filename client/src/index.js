var submitBtn = document.querySelector('.btn');

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
