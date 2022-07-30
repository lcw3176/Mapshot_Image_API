# websocket
## 패키지 구조
### Request
![소켓 요청 처리](https://user-images.githubusercontent.com/59993347/178676489-231a8515-349a-4364-95b3-6f31a9cada5f.png)

- 지도 제작 작업 전달, 세션 정보 저장

```java

public class UserMapRequest {
    
    // 도시 계획 레이어 적용 여부
    private boolean layerMode;
    
    // 요청한 지도 이미지의 중심 위도
    private double lat;

    // 요청한 지도 이미지의 중심 경도
    private double lng;
    
    // 지도 반경 값
    // ex) 1 -> 반경 1km의 지도
    private int level;
    
    // 지도 생성 타입
    // ex) satellite_base -> 위성 사진 기반의 지도
    private String type;
    
    // 지도 이미지를 가져올 회사 타입
    // ex) kakao -> 카카오 지도를 이용해서 이미지 생성
    private CompanyType companyType;
    
}
```

### Response
![소켓 응답](https://user-images.githubusercontent.com/59993347/178674655-d6c01a8e-95b4-4ab4-8d97-0682821208ed.png)

- 지도 제작 완료 시 지도 저장 정보 전달

```java

public class UserMapResponse {
    
    // 유저 대기 순번
    private int index;
    
    // 분할된 지도의 정보
    // ex) x=0, y=0 
    // -> 이 지도 이미지는 분할된 전체 이미지중 첫 번째 위치의 지도
    private int x;
    private int y;
    
    // 서버에 보관된 이미지 정보
    // storage 컨트롤러에 요청해 해당되는 이미지를 발급받음
    private String uuid;

    // 에러 발생 플래그
    private boolean error;

}
```