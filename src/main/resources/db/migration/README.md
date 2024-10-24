`/db/migration` 하위 파일은 DB migration 라이브러리 `flyway`를 추가하기 위해 추가했습니다.
그러나 해당 라이브러리를 추가하고 배포할 때 에러가 발생하는 문제가 있어서 빠른 기능 배포를 위해 **`flyway`는 프로젝트에서 삭제**했습니다.

따라서 `flyway`와 이 폴더에 있는 migration SQL은 작동하지 않는 상태입니다. 그러나 수동으로 마이그레이션하거나 추후 `flyway` 설치 시에 참고하실 수 있도록 작성한 `.sql` 파일은 남겨
놓겠습니다.