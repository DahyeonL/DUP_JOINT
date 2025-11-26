(() => {
  const statusEl = document.getElementById('status');
  const messagesEl = document.getElementById('messages');
  const form = document.getElementById('chat-form');
  const input = document.getElementById('message-input');

  const protocol = location.protocol === 'https:' ? 'wss' : 'ws';
  const socket = new WebSocket(`${protocol}://${location.host}`);

  function addMessage({ user, message, type, timestamp }) {
    const item = document.createElement('li');
    item.className = type === 'system' ? 'system' : 'chat';

    if (type === 'chat') {
      const meta = document.createElement('div');
      meta.className = 'meta';
      const time = timestamp ? new Date(timestamp) : new Date();
      meta.textContent = `${user || '익명'} · ${time.toLocaleTimeString()}`;

      const body = document.createElement('p');
      body.textContent = message;

      item.append(meta, body);
    } else {
      item.textContent = message;
    }

    messagesEl.appendChild(item);
    messagesEl.scrollTop = messagesEl.scrollHeight;
  }

  socket.addEventListener('open', () => {
    statusEl.textContent = 'WebSocket에 연결되었습니다.';
    statusEl.classList.remove('error');
  });

  socket.addEventListener('close', () => {
    statusEl.textContent = '연결이 종료되었습니다. 새로고침으로 다시 시도하세요.';
    statusEl.classList.add('error');
  });

  socket.addEventListener('message', (event) => {
    try {
      const data = JSON.parse(event.data);
      addMessage(data);
    } catch (error) {
      console.error('메시지 파싱 실패', error);
    }
  });

  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const text = input.value.trim();
    if (!text) return;

    socket.send(
      JSON.stringify({
        type: 'chat',
        message: text,
      })
    );

    input.value = '';
    input.focus();
  });
})();
