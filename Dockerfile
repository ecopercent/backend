FROM amazoncorretto:17

ARG APP_DIR=/opt/app/api/classes/

WORKDIR ${APP_DIR}

CMD [ "java", "-Dspring.profiles.active=${BUILD_PROFILE}", "-cp", ".:./*", "sudols.ecopercent.EcopercentApplication" ]