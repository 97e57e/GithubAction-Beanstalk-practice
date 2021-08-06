# 기록

## 1차 시도
jib를 이용해 Docker Build 후 이미지를 도커 허브에 푸시 한 후 ElasticBeanstalk 에서 풀 받아 Run 하려고 시도.<br>
이미지 푸시 까지는 성공 했으나 빈스톡에서 도커 이미지를 풀 받을 수 있는 플러그인을 찾지 못해 실패.<br>

<br>

더 공부 해보니 빈스톡이 기본적으로 ebextension의 스크립트를 Procfile실행으로 돌린다는것을 알게 되었음.<br>
이를 조작 하여 docker pull & run 을 할 수 있을지 추후에 시도<br>

<br>

## 2차 시도
일반적인 방법으로 시도.<br>
jar 파일을 빈스톡으로 그대로 보내 실행.<br>

workflows/deploy.yml의 32라인에서 <br>
cp target deploy/application.jar 가 디렉토리가 아니라는 에러와 함께 계속 실패<br><br>

알고보니 빌드시 -plain.jar가 함께 생성 되어 cp 명령어 실행시 두 파일이 함께 옮겨져 에러가 났던것.
<img width="764" alt="스크린샷 2021-08-07 오전 3 09 14" src="https://user-images.githubusercontent.com/41745717/128554280-a5302ce7-5879-4fb0-a855-7055a45c43c6.png">

```kotlin
tasks.getByName<Jar>("jar") {
    enabled = false
}

```
위 태스크를 gradle에 추가 하여 plain.jar 생성 안되도록 변경 후 성공
