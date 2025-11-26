# DUP_JOINT

Duplicate Joint SaaS

## 간단한 WebSocket 채팅

로컬에서 바로 실행할 수 있는 WebSocket 기반의 예제 채팅입니다.

### 실행 방법
1. 의존성 설치
   ```bash
   npm install
   ```
2. 서버 실행
   ```bash
   npm start
   ```
3. 브라우저에서 `http://localhost:3000`을 열어 여러 탭이나 다른 브라우저에서 메시지를 주고받을 수 있습니다.

### 구조
- `server.js`: Express로 정적 파일을 제공하고 `ws`로 WebSocket 브로드캐스트를 관리합니다.
- `public/index.html`: 채팅 레이아웃과 입력 폼을 제공합니다.
- `public/app.js`: WebSocket 클라이언트를 초기화하고 메시지를 렌더링합니다.
- `public/styles.css`: 기본 스타일 정의입니다.
