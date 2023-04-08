JAR_FILE = ecopercent-api-0.0.1-SNAPSHOT.jar

.PHONY: start
start:
	@make start_db
	@make rebuild
	@make start_api

.PHONY: stop
stop:
	@make prebuild
	@make stop_db

.PHONY: restart
restart:
	@make rebuild
	@make start_api


.PHONY: build
build:
	./gradlew build

.PHONY: prebuild
prebuild:
	rm -rf ./build

.PHONY: rebuild
rebuild:
	@make prebuild
	@make build

.PHONY: start_api
start_api:
	java -jar ./build/libs/$(JAR_FILE)

.PHONY: stop_api
stop_api:
	kill -9 `pgrep -f ${JAR_FILE}` 2> /dev/null

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
restart_db:
	brew services restart postgresql@14

