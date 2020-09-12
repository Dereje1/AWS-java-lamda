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
    submitButton.disabled = true
    const fetchResponse = await fetch(endpoint,settings)
    const response = await fetchResponse.json();
    submitButton.disabled = false
    console.log(response)
    } catch (error) {
        console.log(error)
    }
}

const validateAndSubmit = () => {
    const obj = {principal: principalInput.value, rate: rateInput.value, period: periodInput.value }
    console.log(obj)
    postLambda(obj)
}

submitButton.addEventListener('click',()=> validateAndSubmit());