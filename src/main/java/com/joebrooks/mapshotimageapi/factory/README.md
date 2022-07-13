# factory
## 패키지 구조
### 요청 저장
![factory 요청 저장](https://user-images.githubusercontent.com/59993347/178674663-9842e906-4397-408b-bd7b-afff76d13ce6.png)
- 이미지 제작에 필요한 정보 임시 보관
```java
public class FactoryTask {
    
    // 이미지 크롤러에 요청할 uri
    private UriComponents requestUri;
    
    // 요청 반경에 따라 html에서 로딩해야 하는 지도 width 정보
    // ex) 카카오 지도, 1km 반경 요청 -> 5000
    // 가로, 세로 5000px의 지도 이미지를 생성함
    private int width;
    
    // 해당 작업을 요청한 세션 정보
    private WebSocketSession session;
}
```

### 요청 처리
![factory 요청 처리](https://user-images.githubusercontent.com/59993347/178674665-e29309ba-e7c6-4509-9db3-4c3f088e009b.png)

- FactoryExecutor에서 1초마다 처리할 작업이 있는지 확인 (@Scheduled)
- 요청한 작업이 있다면 처리 시작
- 각 패키지에 필요한 정보들을 전달