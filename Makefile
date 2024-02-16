# 백그라운드 실행, 강제 재생성
db-up: # db-up : 컨테이너 생성, 실행 / -d : 백그라운드 실행 / --force-recreate : 반드시 컨테이너를 지우고 새로 만든다.(docker-compose 파일을 수정했다면 이 옵션을 줘야 한다.)
	docker-compose up -d --force-recreate

# volume 삭제
db-down: # 컨테이너 정지, 삭제(볼륨도) / -v : 볼륨 /
	docker-compose down -v
