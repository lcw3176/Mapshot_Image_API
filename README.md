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
- WebSocket
- ChromeDriver, Selenium
- Slack API, WhaTap
- GitHub Action

## 전체 이미지 서비스 구조도
![구조도](https://user-images.githubusercontent.com/59993347/176987753-a7427c28-fccb-4006-905b-a2e77adc3255.png)

## Mapshot Image API 서비스 상세 구조도
![요청](https://user-images.githubusercontent.com/59993347/177678649-5d7cce8c-43db-4adf-9da7-61f2c7780e54.jpg)
![반환](https://user-images.githubusercontent.com/59993347/177678652-4dc5ed4a-737d-4f03-9036-830b3a7bc835.jpg)

## 관련 프로젝트
- 메인 프로젝트 서버: [Mapshot_Service](https://github.com/lcw3176/Mapshot_Service)
- 클라이언트 지도 조립 라이브러리: [mapshot-lib](https://github.com/lcw3176/mapshot-lib)
