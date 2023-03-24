JAR_FILE = ecopercent-api-0.0.1-SNAPSHOT.jar

start:
	./gradlew build
	java -jar ./build/libs/$(JAR_FILE) &

stop:
	kill -9 `pgrep -f $(JAR_FILE)` 2> /dev/null
	rm -rf ./build

re:
	make stop
	make start

.PHONY: start stop re
