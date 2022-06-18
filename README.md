# hanghae-week2-homework

# 필수 과제

<br>
## 프레임워크와 라이브러리의 차이점

- 라이브러리와 프레임워크의 차이는 **제어 흐름에 대한 주도성이 누구에게 있는가**에 따라 다르다.
- 프레임워크는 전체적인 흐름을 **스스로**가 가지고 있으며, 라이브러리는 **사용자**가 전체적인 흐름을 만든다.

## 코드를 구현할 때 예외처리를 위해 무엇을 했나요?

- ExceptionHandler을 사용하여 예외처리기를 만들었고, ControllerAdvice을 사용해 전역적으로 예외를 처리할 수 있는 클래스([ExControllerAdvice.java](https://github.com/gyunih0/hanghae-week2-homework/issues/10))를 만들어 예외를 처리하였다.

## Restful 이란?

- REST는 Representational State Transfer라는 용어의 약자로서 자원을 이름으로 구분하여 해당 자원의 상태를 주고 받는 모든 것을 의미한다.
- 즉, REST란
    1. HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고,
    2. HTTP Method(POST, GET, PUT, DELETE)를 통해
    3. 해당 자원(URI)에 대한 행위, CRUD(Create, Read, Update, Delete)를 적용하는 것을 의미합니다.
    
- Restful이란 REST의 원리를 따르는 시스템을 의미한다.

## 왜 Restful하게 설계해야할까?

- RESTful API의 목적은 성능이 아닌, **일관적인 API를 통한 이해도 및 호환성을 높이는 것**이 주 목적이며, 하나의 서버로 다양한 클라이언트(HTTP 표준 프로토콜에 따르는 모든 플랫폼)에서 사용가능하기 때문에 **확장성**이 좋아진다.

## Restful의 장/단점

### 1. 장점

- HTTP 프로토콜의 인프라를 그대로 사용하므로 REST API 사용을 위한 별도의 인프라를 구출할 필요가 없다.
- HTTP 프로토콜의 표준을 최대한 활용하여 여러 추가적인 장점을 함께 가져갈 수 있게 해 준다.
- HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용이 가능하다.
- REST API 메시지가 의도하는 바를 명확하게 나타내므로 의도하는 바를 쉽게 파악할 수 있다.
- 서버와 클라이언트의 역할을 명확하게 분리한다.

### 2. 단점

- 사용할 수 있는 메소드가 4가지밖에 없다.
- HTTP Method 형태가 제한적이다.
- 브라우저를 통해 테스트할 일이 많은 서비스라면 쉽게 고칠 수 있는 URL보다 Header 정보의 값을 처리해야 하므로 전문성이 요구된다.
- 서비스와 복잡해질수록 Over-Fetching 과 Under-Fetching 문제가 발생할 수 있다.

## Restful하게 짜기 위해 무엇을 고려했나요?

- CRUD 기능을 POST로만 처리하는게 아니라 PUT, DELETE와 같은 메서드를 사용하였다.
- URI에 자원의 형태를 명확하게 표시하였다.
- Over-Fetching 을 최소화 하도록 노력하였다.

## Entity 설계를 위해 무엇을 하였나요? 연관관계에 근거하여 설명해주세요.

- 유저(One) ↔ 게시물(Many) : 한 유저는 여러개의 게시물을 만들 수 있다.
- 유저(One) ↔ 좋아요(Many) : 한 유저는 여러개의 좋아요를 할 수 있다.
- 게시물(One) ↔ 좋아요(Many)  : 한 게시물은 여러개의 좋아요를 가질 수 있다.
- 좋아요 Entity에는 어떤 유저가 어떤 게시물에 좋아요를 했는지가 담긴다.

## 트러블슈팅

1. 게시물 한개 GET요청 들어올 때 Img_url이 null로 바뀌는 문제 발생.
- 이 요청을 받을 때 동작하는 ViewCount 증가 로직이 문제.
- 기존에는 BoardDto를 사용하여 Board를 update하면서 ViewCount를 증가시켰는데, 이렇게 되면 BoardDto 생성자에 Board의 모든 속성을 넣어줘야 함.  (Board로 BoardDto를 생성하면서 ViewCount+1을 해주고, BoardDto를 Board에 다시 업데이트)
- ViewCount를 처리하는 로직을 따로 만들어서 해결.

2. 게시물 작성시 이미지 등록 안하면 오류 발생
- AwsS3Service.java에서 multipartFile이 null일때 imgUrl을 빈 스트링으로 만들어서 해결.

3. Jasypt Encryption 오류(미해결)
- 암호화 과정에서 프로그램 실행마다 결과값이 다르게 나온 것이 문제라고 생각하였으나 암호화된 결과를 고정하여도 Aws에 key, SecretKey가 넘어가는 과정에서 불일치 발생.
- 현재는 properties를 분리 하여 깃허브에는 노출 되지 않도록 설정하였다.
