
let connection =''

if (!location.host || location.hostname === "localhost" || location.hostname === "127.0.0.1"){
    connection = `http://localhost:3000/dev/`
} else{
    connection = `https://jawtd7ia9a.execute-api.us-east-1.amazonaws.com/dev/`
}

const postLambda = async () => {
    const settings = {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({principal: 30000, rate: 3, period: 10 })
    };
    const endpoint = `${connection}`
    try {
    console.log('Fetching.....')
    const fetchResponse = await fetch(endpoint,settings)
    const response = await fetchResponse.json();
    console.log(response)
    } catch (error) {
        console.log(error)
    }
}

window.onload=()=>postLambda();
//submitSquare.addEventListener('click',postLambda)