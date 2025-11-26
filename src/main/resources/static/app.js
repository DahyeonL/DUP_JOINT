const statusText = document.querySelector("#status");
const form = document.querySelector("#chat-form");
const nameInput = document.querySelector("#name");
const messageInput = document.querySelector("#message");
const messagesContainer = document.querySelector("#messages");

const protocol = window.location.protocol === "https:" ? "wss" : "ws";
const socketUrl = `${protocol}://${window.location.host}/chat`;

let socket;

function connect() {
  socket = new WebSocket(socketUrl);

  socket.addEventListener("open", () => {
    updateStatus("Connected", true);
  });

  socket.addEventListener("close", () => {
    updateStatus("Disconnected", false);
    setTimeout(connect, 1000);
  });

  socket.addEventListener("message", (event) => {
    try {
      const payload = JSON.parse(event.data);
      addMessage(payload);
    } catch (err) {
      console.error("Failed to parse message", err);
    }
  });
}

function updateStatus(text, isOnline) {
  statusText.textContent = text;
  const dot = document.querySelector(".status-dot");
  dot.style.background = isOnline ? "#10b981" : "#ef4444";
  dot.style.boxShadow = isOnline
    ? "0 0 0 4px rgba(16, 185, 129, 0.15)"
    : "0 0 0 4px rgba(239, 68, 68, 0.2)";
}

function addMessage({ sender, message, timestamp }) {
  const item = document.createElement("div");
  item.className = `message ${sender === "System" ? "system" : ""}`.trim();

  const meta = document.createElement("div");
  meta.className = "meta";
  const time = new Date(timestamp).toLocaleTimeString();
  meta.innerHTML = `<span class="sender">${sender}</span><span>${time}</span>`;

  const text = document.createElement("div");
  text.className = "text";
  text.textContent = message;

  item.appendChild(meta);
  item.appendChild(text);
  messagesContainer.appendChild(item);
  messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

form.addEventListener("submit", (event) => {
  event.preventDefault();
  if (!socket || socket.readyState !== WebSocket.OPEN) {
    return;
  }
  const payload = {
    sender: nameInput.value.trim() || "Guest",
    message: messageInput.value.trim(),
  };

  if (!payload.message) return;

  socket.send(JSON.stringify(payload));
  messageInput.value = "";
  messageInput.focus();
});

connect();
