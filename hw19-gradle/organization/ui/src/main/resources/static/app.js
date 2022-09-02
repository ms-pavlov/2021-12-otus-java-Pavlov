let stompClient = null;

const infoElementId = "info";
const formElementId = "form";

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        stompClient.subscribe(topic, (message) => showMessage(message.body));
    });
}

const send = () => {
    console.log(JSON.stringify(verify()));
    stompClient.send(url, {}, JSON.stringify(verify()))
}

const showMessage = (message) => {
    console.log(message);
    const infoLine = document.getElementById(infoElementId);
    let newText = document.createTextNode(message);
    infoLine.appendChild(newText);
}