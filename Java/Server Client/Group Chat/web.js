
const socket = new WebSocket('ws://localhost:3000');
const messagesDiv = document.getElementById('messages');
const userCountSpan = document.getElementById('userCount');
const statusIndicator = document.getElementById('statusIndicator');
const nameInput = document.getElementById('nameInput'); // Reference to the name input field

socket.onopen = () => {
    console.log('Connected to server');
    statusIndicator.style.backgroundColor = 'green';
};


socket.onmessage = event => {
    const message = JSON.parse(event.data);
    if (message.type === 'connectionCount') {
        userCountSpan.textContent = message.count;
    } else {
        displayMessage(message);
    }
};

socket.onclose = () => {
    console.log('Disconnected from server');
    statusIndicator.style.backgroundColor = 'red';
};

function sendMessage() {
    const input = document.getElementById('messageInput');
    const messageContent = input.value.trim();
    socket.send(JSON.stringify({ type: 'name', name: nameInput.value.trim() }));
    if (messageContent !== '') {
        const message = {
            content: messageContent,
            time: new Date().toLocaleTimeString()
        };
        displayMessage({ sender: nameInput.value.trim(), ...message }); // Display the message immediately
        socket.send(JSON.stringify({ sender: nameInput.value.trim(), ...message })); // Send the message to the server
        input.value = '';
    }
}

function displayMessage(message) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message');
    const senderSpan = document.createElement('span');
    senderSpan.textContent = message.sender ? message.sender + ': ' : '';
    const contentSpan = document.createElement('span');
    contentSpan.textContent = message.content;
    const timeSpan = document.createElement('span');
    timeSpan.textContent = message.time;
    timeSpan.classList.add('time');
    messageDiv.appendChild(senderSpan);
    messageDiv.appendChild(contentSpan);
    messageDiv.appendChild(timeSpan);
    messagesDiv.appendChild(messageDiv);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

function toggleCustomizePanel() {
    var customizePanel = document.getElementById('customizePanel');
    if (customizePanel.style.display === 'none') {
        customizePanel.style.display = 'block';
    } else {
        customizePanel.style.display = 'none';
    }
}

function applyCustomizations() {
    var backgroundColor = $('#backgroundColor').spectrum('get').toHexString();
    var messageColor = $('#messageColor').spectrum('get').toHexString();
    var buttonColor = $('#buttonColor').spectrum('get').toHexString();
    var borderColor = $('#borderColor').spectrum('get').toHexString();
    var borderRadius = $('#borderRadius').val() + 'px';

    $('#chatContainer').css({
        'background-color': backgroundColor,
        'border-color': borderColor,
        'border-radius': borderRadius
    });
    $('#messages').css('color', messageColor);
    $('#messageInput, #nameInput').css('border-color', borderColor);
    $('button').css({
        'background-color': buttonColor,
        'border-color': buttonColor
    });
}