# storage
## 패키지 구조
### Request, Response
![storage 이미지 반환](https://user-images.githubusercontent.com/59993347/178674659-00bbcecb-2207-4c9c-a835-80955d43a79d.png)

- 발급받은 UUID를 통한 자신의 이미지 요청
- 저장소에서 해당 이미지 반환 후 제거

### 이미지 보관
![storage 이미지 저장](https://user-images.githubusercontent.com/59993347/178674662-d1f8cd3b-0dc1-4421-b780-a452b90088f9.png)

- factory 패키지에서 이미지 ByteArrayResource 전달
- 이미지 발급 전까지 임시 보관
- 이미지는 발급과 동시에 제거


```java
public class StorageInfo {
    
    // 이미지 UUID
    private String uuid;
    
    // 이미지 바이트코드
    private ByteArrayResource byteArrayResource;
    
    // 현재 수행해야할 명령어 세트
    // CLEAR: 연결이 중간에 끊어졌거나, 작업 중 예외 발생 시 DB 클리어
    // PUT: DB에 정보 삽입
    private COMMAND command;

    public enum COMMAND {
        PUT,
        CLEAR
    }
}

```