const express = require('express');
const http = require('http');
const path = require('path');
const { WebSocketServer } = require('ws');

const app = express();
const server = http.createServer(app);

app.use(express.static(path.join(__dirname, 'public')));

const wss = new WebSocketServer({ server });
let userCount = 1;

function broadcast(data, exclude) {
  const message = JSON.stringify(data);
  wss.clients.forEach((client) => {
    if (client.readyState === 1 && client !== exclude) {
      client.send(message);
    }
  });
}

wss.on('connection', (ws) => {
  const username = `사용자${userCount++}`;
  ws.send(JSON.stringify({ type: 'system', message: `${username}로 연결되었습니다.` }));
  broadcast({ type: 'system', message: `${username}님이 입장했습니다.` }, ws);

  ws.on('message', (raw) => {
    let payload;
    try {
      payload = JSON.parse(raw.toString());
    } catch (error) {
      return;
    }

    if (payload.type === 'chat' && typeof payload.message === 'string') {
      const trimmed = payload.message.trim();
      if (trimmed.length === 0) return;

      const chatMessage = {
        type: 'chat',
        user: username,
        message: trimmed,
        timestamp: new Date().toISOString(),
      };

      broadcast(chatMessage);
    }
  });

  ws.on('close', () => {
    broadcast({ type: 'system', message: `${username}님이 나갔습니다.` }, ws);
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`Chat server running at http://localhost:${PORT}`);
});
