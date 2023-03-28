JAR_FILE = ecopercent-api-0.0.1-SNAPSHOT.jar

.PHONY: start
start:
	@make start_db
	@make start_api

.PHONY: stop
stop:
	@make stop_api
	@make stop_db

.PHONY: restart
restart:
	@make stop_api
	@make restart_db
	@make start_api

.PHONY: start_api
start_api:
	./gradlew build
	java -jar ./build/libs/$(JAR_FILE) &

.PHONY: stop_api
stop_api:
	kill -9 `pgrep -f ${JAR_FILE}` 2> /dev/null
	rm -rf ./build

.PHONY: restart_api
restart_server:
	@make stop_api
	@make start_api

.PHONY: start_db
start_db:
	brew services start postgresql@14

.PHONY: stop_db
stop_db:
	brew services stop postgresql@14

.PHONY: restart_db
	brew services restart postgresql@14



