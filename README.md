# 공짜

## 코드 규칙
- 클래스, 인터페이스명
    - CamelCase
- 변수, 메소드, 패키지명
    - camelCase
    - 주의할점 : 전역변수에 mCamelCase 처럼 m 을붙이는 경우가 있는데 이는 C# 스타일로, 우리는 쓰지 않을거임
- xml 변수
    - camel_case_MyViewName
        - 앞 부분(camel_case)는 언더바 규칙을 쓰고 맨 뒤(뷰 이름)만 카멜 케이스 규칙을 씀
        - 예) 
    - 뷰 이름 부분의 경우 뷰의 역할(이름)과 뷰의 클래스명을 적어줌
        - 좋은 예
            - menu_chat_submit_button
            - menu_chat_submit_imageButton
        - 나쁜 예
            - menu_chat_submit
            - menu_chat_button
            
- 레이아웃 xml
    - category_name
    - 좋은 예
        - activity_main
        - dialog_chat
    - 나쁜 예
        - main_activity
        - chat_dialog
    - 안드로이드 스튜디오에서 이름 순 정렬을 해주기 때문에 카테고리를 앞에 적어줌으로써 액티비티는 액티비티끼리 모여있게된다. 

## 참고
- 안드로이드 아이콘 곳
    - https://material.io/resources/icons/?style=baseline
    - Rounded 탭에서 찾을것!
    - 다운로드 방법
        - 원하는 아이콘 클릭
        - 좌측 하단의 Selected Icon 클릭
        - 기본으로 18dp 가 선택된 것을 Android로 변경
            - Android 로 선택하면 해상도별 아이콘을 다 제공해줌
            - 이거 엄청 좋음
        - png 다운로드  
- 안드로이드 표준 디자인 이름
    - Android Material design 검색  
    - 예) Android material tab design

## 네트워크 연동 문서
- Retrofit 라이브러리 사용
    - 공식 홈페이지 = https://square.github.io/retrofit/
- 기본 사용법
    ```{.java}
    UserApi userApi = Networks.retrofit.create(UserApi.class);
    Call<User> call = userApi.signUp(MockData.getMockUser());
    
    // 1번 방법 ( 비권장 )
    // 동기식 처리
    // 이 방법을 사용하면 서버 응답이 안 올 경우 앱이 완전히 멈추게 되므로
    // 사용하지 않는것을 권장 
    // 테스트 코드에서만 사용되는 메소드임
    Response<User> response = call.execute();  
    
    // 2번 방법 ( 권장 )
    // 비동기식 처리
    // 이 방법을 사용하면 서버 응답이 오지 않더라도 앱이 멈추는 일은 없음
    // onFailure 함수에서 적절한 예외처리를 해주면 됨
    // 주의! onFailure 함수는 네트워크 처리 자체가 실패했을 때 호출됨
    // onResponse 는 네트워크 처리는 되었으나 그 결과 자체는
    // response.code() == 200 인지 확인이 필요
    // 윗 줄의 200은 컴넷 시간에 배웠던 status code 임
    // 즉 통신은 성공했으나 서버상의 오류로 200이 아닌 코드 예) 404, 500, 400 등등
    // 가 응답으로 온 경우도 onResponse 가 호출되니 onResponse 가 호출된 것이
    // 완전히 성공적인 통신을 의미하는 것은 아님
    call.enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.code == 200) {
                // 회원가입 성공
            } else {
                // 회원가입 실패
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            // 회원가입 실패
            // 서버와의 통신 실패
        }
    });
    ```
    
    - 약간의 규칙
        - ***Api 는 서버와 통신 할 수 있는 interface 를 말함
            - 즉 UserApi 는 유저 관련 기능으로 서버와 통신 하는 interface
            - ChatApi 는 채팅 관련 기능으로 서버와 통신하는 interface 임
        - Api interface 객체는 다음과 같은 방법으로 생성
            - ```UserApi userApi = Networks.retrofit.create(UserApi.class);```
            - 객체 자체는 Retrofit 이 만들어주므로 네트워크 통신을 구현한 내부 구현은 나도 모름
        -  Call 객체
            - ```Call<User> call = userApi.signUp(MockData.getMockUser());```
            - call.enqueue 로 서버와 통신
        - 기타 메소드 별 파라미터 값은 주석과 문서 하단에 명시 
            
- UserApi
    - Call<User> getUserByIdPw(String id, String password);
        - 로그인할 때 사용하는 메소드
        - id, password 를 입력하면 일치하는 정보가 있을 시 User 객체를 리턴
        - 일치하는 정보가 없다면 500 에러 발생
        
    - Call<User> signUp(User user);
        - 회원가입 할 때 사용하는 메소드
        - User 객체 내 모든 field 를 채워 넘겨야 함
        - 회원가입 성공 시 회원가입된 User 객체를 리턴

    - Call<String> findId(String name, Date birthday);
        - 아이디 찾기
        - @param name      회원가입 시 사용했던 유저 이름
        - @param birthday  회원가입 시 사용했던 유저 생일
        - @return          이름과 생일이 일치하는 유저가 있다면 해당 User id 리턴 없다면
        null 리턴
        
    - Call<Integer> getFindPasswordAuthority(String id, String name, Date birthday)
        - 비밀번호 찾기 권한 획득
        - @param id            유저 아이디
        - @param name          유저 이름
        - @param birthday      유저 생일
        - @return              세 정보 모두 일치하는 유저가 있을 시 200 리턴
        - 일치하는 유저 없으면 0 리턴
    
    - Call<Integer> getFindPasswordAuthority(String id, String name, Date birthday)
        - 비밀번호 재설정
        - @param id            재설정할 id
        - @param password      id의 비밀번호를 재설정
        - @param authorityCode 200이면 재설정 가능
        - 이외의 수는 불가능
        - @return              비밀번호 변경 성공 시 200
        - 실패 시 0
       
    - Call<AuthMail> sendAuthenticateEmail(String userId, String email)
        - 인증 메일을 보냄
        - @param userId    인증할 아이디
        - @param email     인증할 메일
        - @return AuthMail 이 null 이 아닌 경우만 인증메일 전송에 성공 
