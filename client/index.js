const submitButton = document.getElementById("submit")
const principalInput = document.getElementById("principal")
const rateInput = document.getElementById("rate")
const periodInput = document.getElementById("period")
const output = document.getElementById("output-holder")

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
    output.style.visibility = 'visible';
    output.innerHTML = `<div class="spin"></div>`;
    const fetchResponse = await fetch(endpoint,settings)
    const response = await fetchResponse.json()
    const {interestPaid,payment,principal,rate,period} = JSON.parse(response) ;
    submitButton.style.visibility = 'visible';
    updateOutPut({interestPaid,payment,principal,rate,period})
    } catch (error) {
        console.log(error)
    }
}


const validateAndSubmit = () => {
    const input = {principal: principalInput.value.replace(/[$,]/g,''), rate: rateInput.value, period: periodInput.value }
    const isMissing = Object.keys(input).some(val => !Boolean(input[val]))
    if(isMissing) {
        output.style.visibility = 'hidden';
        return null;
    }
    if(input.principal < 1000){
        principalInput.style.background = '#ff00002e'
        output.style.visibility = 'hidden';
        return null;
    }

    if(input.rate < 0 || input.rate > 30){
        rateInput.style.background = '#ff00002e'
        output.style.visibility = 'hidden';
        return null;
    }

    if(input.period < 1 || input.period > 30){
        periodInput.style.background = '#ff00002e'
        output.style.visibility = 'hidden';
        return null;
    }
    principalInput.style.background = 'none'
    rateInput.style.background = 'none'
    periodInput.style.background = 'none'

    return postLambda(input)
}

const toCurrency = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 0
});

const updateOutPut = ({interestPaid,payment,principal,rate,period}) => {

    // Create our number formatter
  
    const html = `
    <span class="output-style"> Principal: ${toCurrency.format(principal)}</span>
    <span class="output-style"> Interest Rate: ${rate.toFixed(2)}%</span>
    <span class="output-style"> Period: ${period} years</span>
    <hr/>
    <span class="output-style-results"> Monthly Payment: ${toCurrency.format(payment)}</span>
    <span class="output-style-results"> Interest Paid: ${toCurrency.format(interestPaid)}</span>
    `
    output.innerHTML = html;
}

const validatePrinicipal = ({target:{value}}) => {
    const replacedCurrency = Number(value.replace(/[$,]/g,''))
    if(!replacedCurrency || replacedCurrency > 5000000) {
        principalInput.value = value.slice(0,value.length-1)
        return
    };
    const currencyFormat = toCurrency.format(replacedCurrency.toFixed(0))
    principalInput.value = currencyFormat
}

submitButton.addEventListener('click',validateAndSubmit);

[principalInput, rateInput, periodInput].forEach(inputType => {
    inputType.addEventListener('input', (e) => {
        inputType.style.background = 'none'
        if(inputType.id === 'principal') validatePrinicipal(e)
    })
})
