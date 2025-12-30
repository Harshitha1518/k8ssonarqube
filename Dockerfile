FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN apk update && apk add curl

ARG NEXUS_USER
ARG NEXUS_PASS
ARG NEXUS_URL
ARG VERSION

RUN curl -u ${NEXUS_USER}:${NEXUS_PASS} \
  -o utils.jar \
  ${NEXUS_URL}/repository/maven-releases/com/example/string-utils/${VERSION}/string-utils-${VERSION}.jar

ENTRYPOINT ["java", "-jar", "utils.jar"]
