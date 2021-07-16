# 개발환경

- LANGUAGE
    - BACK-END
        - JAVA 11
        - KOTLIN 1.4
    - FRONT-END
        - REACT
- INFRA
    - DB
        - 테스트 - H2 on MEMORY
        - 로컬 - MYSQL 5.7 on DOCKER
        - 운영 - MYSQL 5.7 on AWS EC2

# 로컬 인프라 환경 구축

`root directory` 의 `run.sh` 을 실행하여 docker container 를 실행하여 로컬 인프라 환경을 자동으로 구축한다. 종료는 `stop.sh`를 실행한다.

# 시크릿 관리

기밀로 다루어야 하는 값은 wisemeal-secret repository 에 보관한 뒤 서브모듈로 사용하여 불러와서 사용한다. 서브 모듈은 secret repository 로 관리한다. 서브 모듈은 프로젝트를
clone 이후 `git submodule init`, `git submodule update` 명령어로 초기화 한다.
