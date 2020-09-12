const submitButton = document.getElementById("submit")
const principalInput = document.getElementById("principal")
const rateInput = document.getElementById("rate")
const periodInput = document.getElementById("period")

let connection =''

if (!location.host || location.hostname === "localhost" || location.hostname === "127.0.0.1"){
    connection = `http://localhost:3000/dev/`
} else{
    connection = `https://jawtd7ia9a.execute-api.us-east-1.amazonaws.com/dev/`
}

const postLambda = async ({principal, rate, period }) => {
    const settings = {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({principal, rate, period})
    };
    const endpoint = `${connection}`
    try {
    submitButton.style.visibility = 'hidden';
    console.log('Fetching....')
    const fetchResponse = await fetch(endpoint,settings)
    const response = await fetchResponse.json()
    const {interestPaid,payment,principal,rate,period} = JSON.parse(response) ;
    submitButton.style.visibility = 'visible';
    console.log({interestPaid,payment,principal,rate,period})
    } catch (error) {
        console.log(error)
    }
}

const validateAndSubmit = () => {
    const input = {principal: principalInput.value, rate: rateInput.value, period: periodInput.value }
    const isMissing = Object.keys(input).some(val => !Boolean(input[val]))
    return isMissing ? null : postLambda(input)
}

submitButton.addEventListener('click',validateAndSubmit);