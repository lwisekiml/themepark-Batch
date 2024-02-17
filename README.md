잠시(?) 중단...
-----------------------
# studycafe_pass


1.  docker-compose.yml


    version: '3.8' # docker-compose 파일의 버전
    services:
      mysql:
        container_name: mysql_local
        image: mysql:8.0.30
        # command: --default-authentication-plugin=mysql_native_password # 인증 플러그인 암호화 방식(deprecated 됨)
    
        # 도커 컨테이너는 기본적으로 컨테이너가 삭제될 때 데이터가 함께 사라지게 되는데 이러한 생명주기와 상관없이 데이터를 유지할 수 있도록 하는 방법 중 하나가 volumes 이다.
        # 호스트 : 컨테이너 - 호스트가 그대로 컨테이너로 생성되어 연결된다.
        # 이 정보를 가지고 있으면 컨테이너를 내렸다 올려도 호스트에 정한 데이터가 변함없이 들어가게 된다.
        # mysql에서 필요한 이유는 호스트에서 설정 파일과 초기 실행할 쿼리들을 관리하기 위함이다.
        # 파일명에 따라 알파벳 순으로 실행되어 먼저 create가 되도록 설정해주어야 한다.

        volumes:
          - ./db/conf.d:/etc/mysql/conf.d # 우리가 설정하고 싶은 설정을 여기에 넣으면 된다.
          - ./db/initdb.d:/docker-entrypoint-initdb.d # 컨테이너가 시작되면 이 폴더 내에 있는 sh file, sql 파일을 실행한다.(create table이 추가 되면 테이블이 생성되고, insert가 추가되면 초기 데이터 설정 가능)
    ports:
      - "3307:3306"
    # restart: always # 항사 다시 시작
    environment:
      - MYSQL_DATABASE=pass_local
      - MYSQL_USER=pass_local_user
      - MYSQL_PASSWORD=pass1234
      - MYSQL_ROOT_PASSWORD=passroot1234
      - TZ=Asis/Seoul

<br/>

### custom.cnf
>[client]  
default-character-set = utf8mb4  
> 
> [mysqld]    
authentication-policy = mysql_native_password

  기본 인증 암호화 플러그인이 MySQL native password 였는데 MySQL 8번전 부터 caching sha2 패스워드로 바뀌었다.
  접속하는 client에서는 지원하지 않는 경우가 기존 암호화 방식인 MySQL native password 로 변경하였다.(호환성을 위한 설정)

<br/>

### make 설치 방법
1. PowerShell을 관리자로 실행
2. https://chocolatey.org/install#individual 에서 다운로드 명령어를 복사 후 붙여넣기
    > Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
3. 명령어로 설치 확인 
    > choco
4. 명령어로 make 설치
    > choco install make

<br/>

### Makefile
    # 백그라운드 실행, 강제 재생성
    # db-up : 컨테이너 생성, 실행 / -d : 백그라운드 실행
    # --force-recreate : 반드시 컨테이너를 지우고 새로 만든다.
    # (docker-compose 파일을 수정했다면 이 옵션을 줘야 한다.)
    db-up: 
        docker-compose up -d --force-recreate
    
    # volume 삭제
    # 컨테이너 정지, 삭제(볼륨도) / -v : 볼륨
    db-down:
        docker-compose down -v

<br/>

### DBeaver (디비버) Public Key Retrieval is not allowed 에러 해결 방법
>https://computer-science-student.tistory.com/719  

Edit Connection -> Driver properties 탭 -> allowPublicKeyRetrieval의 값을 TRUE로 변경

<br/>

---
- 한글 깨짐 현상으로 아래와 같이 파일 추가 및 수정 진행
### Dockerfile 추가
    FROM mysql:8.0.30
    COPY ./db/conf.d /etc/mysql/conf.d
    COPY ./db/initdb.d /docker-entrypoint-initdb.d
    
    RUN chmod 644 /etc/mysql/conf.d/custom.cnf
### docker-compose.yml
    version: '3.8'
    
    services:
    mysql:
    container_name: mysql_local
    build: .
    ports:
    - "3307:3306"
    environment:
      - MYSQL_DATABASE=pass_local
      - MYSQL_USER=pass_local_user
      - MYSQL_PASSWORD=pass1234
      - MYSQL_ROOT_PASSWORD=passroot1234
      - TZ=Asis/Seoul

### makefile
    db-up:
        docker-compose up -d --build --force-recreate
    
    db-down:
        docker-compose down -v
