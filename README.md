# JOINT (MVP 스켈레톤)

JOINT는 **B2B SaaS 기반 외부 사용자 관리 및 협업 플랫폼**으로, 우리 조직의 팀원과 거래처/파트너를 한 공간에서 초대하고 대화할 수 있습니다. 이 저장소는 Kotlin + Spring Boot 기반의 백엔드 목업 API와 향후 Next.js 프론트엔드 개발을 위한 기본 가이드를 제공합니다.

## 구현 현황
- Spring Boot 3 + Kotlin으로 작성된 REST 엔드포인트
- 인메모리 샘플 데이터로 팀/거래처/대화방/메시지/대시보드 지표를 조회할 수 있는 API
- 프론트엔드(Next.js + React Query)는 아직 포함되지 않았으며, API 스펙에 맞춰 추가할 수 있습니다.

## 실행 방법
```bash
gradle bootRun
```
서버가 `http://localhost:8080` 에서 실행되며, 주요 엔드포인트는 `/api` 하위에 노출됩니다.

## 주요 엔드포인트 (목업)
- `GET /api/teams` — 워크스페이스(team) 정보 및 내부 멤버 목록 조회
- `GET /api/partners` — 거래처(외부 조직) 목록 조회
- `GET /api/conversations` — 거래처별 대화방 리스트와 최근 메시지 요약
- `GET /api/conversations/{id}/messages` — 지정된 대화방의 메시지 타임라인
- `GET /api/dashboard` — 거래처/대화방/메시지 건수 요약 통계

## 도메인 모델 스케치
- **Team**: 워크스페이스, 내부 사용자 목록 포함
- **Partner**: 외부 조직(거래처), 메모/주요 연락처 정보 포함
- **Conversation**: Team + Partner가 참여하는 대화방, 공개/비공개 여부
- **Message**: 작성자/본문/이모지 리액션/중요 여부(참여 필요)
- **DashboardStats**: 거래처, 대화방, 메시지 집계

## 차후 확장 가이드
- JPA + MySQL + Redis로 영속성과 캐시를 추가하고, 도메인별 Repository/Service 레이어를 분리합니다.
- Next.js + React Query + Radix UI를 사용해 메시지/거래처/팀 관리 탭을 구현하고, 위 API를 기반으로 화면을 구성합니다.
- 파일 업로드, 멘션, 초대(이메일 토큰) 등의 고급 기능을 컨트롤러와 서비스 레벨에서 확장합니다.
