const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: 3000 });
let connectionCount = 0;

wss.on('connection', ws => {
    console.log('Client connected');
    connectionCount++;
    updateConnectionCount();

    ws.on('message', message => {
        console.log('Received: %s', message);
        const parsedMessage = JSON.parse(message);
        if (parsedMessage.type === 'name') {
            ws.senderName = parsedMessage.name; // Set sender's name for this WebSocket instance
        } else {
            const currentTime = new Date().toLocaleTimeString();
            const senderName = ws.senderName; // Get sender's name from WebSocket instance or set to 'Anonymous'
            const formattedMessage = {
                sender: senderName,
                content: parsedMessage.content,
                time: currentTime
            };

            // Broadcast the message to all connected clients
            wss.clients.forEach(client => {
                if (client !== ws && client.readyState === WebSocket.OPEN) { // Exclude the sender from the broadcast
                    client.send(JSON.stringify(formattedMessage));
                }
            });
        }
    });

    ws.on('close', () => {
        console.log('Client disconnected');
        connectionCount--;
        updateConnectionCount();
    });

    ws.on('error', err => {
        console.error('WebSocket error:', err.message);
    });

    const welcomeMessage = 'Welcome to the chat!';
    ws.send(welcomeMessage);
});

function updateConnectionCount() {
    const connectionCountMessage = {
        type: 'connectionCount',
        count: connectionCount
    };
    wss.clients.forEach(client => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify(connectionCountMessage));
        }
    });
}

console.log('WebSocket server is listening on port 3000');
