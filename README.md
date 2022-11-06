<p align="center">
  <img src="https://user-images.githubusercontent.com/59993347/166405369-0d610a83-68d5-4d31-8215-6eba806fba06.png" height="250">
</p>
<p align="center">
<img src="https://img.shields.io/badge/Made%20with-SpringBoot-blue">
<img src="https://img.shields.io/badge/Service%20begun%20in-2021.02-brigntgreen">
</p>
<p align="center">
  <a href="#서비스-소개">프로젝트 소개</a> •
  <a href="#기술-스택">기술 스택</a> •
  <a href="#서비스-구조도">서비스 구조도</a> •
  <a href="#관련-프로젝트">관련 프로젝트</a>
</p>

## 프로젝트 소개
맵샷 서비스의 이미지 api 서버입니다.<br>
웹소켓을 이용해 이미지 생성 과정을 공유한 후, 이미지 생성이 완료되면 <br>
유저는 uuid를 통해 자신의 이미지를 발급받습니다.

## 기술 스택
- Java 11, Spring Boot
- WebSocket, Stomp
- ChromeDriver, Selenium
- Slack API, WhaTap
- GitHub Action

## 대기열 번호 발급
- url: /topic/waiter
- type: subscribe
### 모델
|이름|설명|
|------|---|
|index|유저 대기열 순번|
|sessionId|유저 소켓 세션 ID|

```javascript
{
    index: 0,  
    sessionId : "sfedf1"
}
```


## 이미지 생성 요청
- url: /map/task
- type: wss

### 모델
|이름|설명|
|------|---|
|layerMode|도시 계획 레이어 적용 여부|
|lat|요청한 지도 이미지의 중심 위도|
|lng|요청한 지도 이미지의 중심 경도|
|level|지도 반경 값|
|type|지도 생성 타입|
|companyType|지도 이미지를 가져올 회사 타입|

```javascript
{
  layerMode: false,
  lat: 37.123,
  lng: 126.123,
  level: 2,
  type: "sattlite_base",
  companyType: "kakao"
}
```

## 이미지 UUID 발급
- url: /queue/task/{sessionId}
- type: subscribe

### 모델
|이름|설명|
|------|---|
|x|분할된 전체 지도 이미지 중 해당 지도의 x축 좌표|
|y|분할된 전체 지도 이미지 중 해당 지도의 y축 좌표|
|uuid|서버에 저장된 해당 이미지의 uuid, 키 값|
|error|에러 발생 플래그|

```javascript
{
    x: 0,
    y: 0,
    uuid: "sdff-sgds-1rdx....",
    error: false;
}
```

## 이미지 반환 요청
- url: /map/storage/{uuid}
- type: http
### 요청
|이름|설명|
|------|---|
|uuid|유저의 저장된 이미지 고유 키 값|

### 응답
jpeg Image


## 이미지 서비스 구조도
![zaaa (1) drawio (2)](https://user-images.githubusercontent.com/59993347/182296392-c8b233ff-ce33-49db-a2d7-0f833df15d47.png)

## 관련 프로젝트
- 메인 프로젝트 서버: [Mapshot_Service](https://github.com/lcw3176/Mapshot_Service)
- 클라이언트 지도 조립 라이브러리: [mapshot-lib](https://github.com/lcw3176/mapshot-lib)
