# 디비 스키마 관리
`/db/migration` 에 flyway versioning 방식으로 db scheme 변경을 관리한다.  
변경사항을 적용한 최종 scheme 은 `/db/live/scheme-live.sql` 에 적용하여 관리한다.

![how_to_versioning](./img/version.png)
