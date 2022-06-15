let stompClient = null;

const infoElementId = "info";
const formElementId = "form";

const nameElementId = "name";
const orderColumnElementId = "orderColumn";

const setConnected = (connected) => {
    const connectBtn = document.getElementById("connect");
    const disconnectBtn = document.getElementById("disconnect");

    connectBtn.disabled = connected;
    disconnectBtn.disabled = !connected;
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        stompClient.subscribe('/topic/clients/create', (message) => showMessage(message.body));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const create = () => {
    const name = document.getElementById(nameElementId).value;
    const orderColumn = document.getElementById(orderColumnElementId).value;
    let client = {"name": document.getElementById(nameElementId).value,
            "orderColumn": document.getElementById(orderColumnElementId).value};
    console.log(JSON.stringify(client));
    stompClient.send("/app/clients/create", {}, JSON.stringify(client))
}

const showMessage = (message) => {
    console.log(message);
    const infoLine = document.getElementById(infoElementId);
    let newText = document.createTextNode(message);
    infoLine.appendChild(newText);
}