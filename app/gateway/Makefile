start-db:
	docker run -d \
	--rm=true \
	--name iebis-db \
	-e POSTGRES_USER=iebis \
	-e POSTGRES_PASSWORD=pass \
	-e POSTGRES_DB=iebis \
	-p 5666:5432 \
	postgres:12

stop-db:
	docker kill iebis-db