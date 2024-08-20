<h1>OAuth를 이용한 소셜 로그인</h1>

<h3>1. OAuth란?</h3>

- OAuth(Open Authorization)는 사용자들이 이름, 비밀번호 등의 정보를 제공하지 않고</br>한 서비스에서 다른 서비스로 "접근 권한을 부여"하기 위해 사용되는 개방형 표준이다.

<br/>

<h3>2. OAuth의 특징</h3>

- 사용자는 본인의 정보를 별도로 제공하지 않아도 되니, 회원 가입에 대한 거부감을 줄일 수 있다.
- 인증된 서비스에서 접근 권한을 얻어서 처리하므로 보안 수준을 높일 수 있다.
- 기본 정보뿐만 아니라, API를 통하여 다른 정보에도 접근이 가능하다(사진 등)

<br/>

<h3>3. OAuth Flow</h3>

![image](https://github.com/user-attachments/assets/e01a3d1d-a558-44ad-9d3a-7a93e2edb348)
>사용자가 서비스 이용 요청을 하면, 서버들은 일련의 인증 과정을 통하여 최종적으로 접근 권한을 부여함

<br/>

<h3>4. 개발</h3>

- 해당 기능으로 대표적인 3개의 서비스를 이용하여 개발(구글, 카카오, 네이버)
  - 참조 문서
    - 구글: https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko
    - 카카오: https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
    - 네이버: https://developers.naver.com/docs/login/api/api.md
  
<br/>

<h4>1. 서비스 등록</h4>

- 구글: 구글클라우드 콘솔에서 프로젝트를 생성하여 OAuth 클라이언트 ID 발급 및 Redirect URL 등록
  - https://console.cloud.google.com/welcome?hl=ko
- 카카오: 

<h4>2. 로그인 페이지 이동</h4>

- client의 웹앱에서 소셜 로그인 버튼을 누른 후의 상황
- http://localhost:8080/oauth/google[kakao,naver]/login
![image](https://github.com/user-attachments/assets/0189d503-e350-4a2c-b547-15a1134e199f)
> 사용자는 아이디와 패스워드를 입력하여 로그인을 시도한다

<br/>

<h4>3. 로그인 후</h4>



<h4>1. 구글</h4>

</br>

![image](https://github.com/user-attachments/assets/8cdfd977-432a-4a74-b80d-1c110ebf37f3)
> 구글 제공 문서(https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko)

- 구글클라우드 콘솔에서 프로젝트를 생성하여 OAuth 클라이언트 ID 발급 및 Redirect URL 등록
