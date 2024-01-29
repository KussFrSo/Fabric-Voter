start-db:
	docker run -d \
	--rm=true \
	--name elecchain-db \
	-e POSTGRES_USER=elecchain \
	-e POSTGRES_PASSWORD=pass \
	-e POSTGRES_DB=elecchain \
	-p 5666:5432 \
	postgres:12

stop-db:
	docker kill elecchain-db

