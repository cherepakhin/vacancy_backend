./gradlew bootJar
# $ ./gradlew buildDockerImage
# через wifi push сработал
# Предварительно создать репозиторий cherepakhin/vacancy_backend
docker build --build-arg JAR_FILE=build/libs/vacancy_backend-0.24.1016.1.jar -t cherepakhin/vacancy_backend:0.24.1016.1 .
docker push cherepakhin/vacancy_backend:0.24.1016.1
# test run
# docker run -p 8980:8980 -p 8988:8988  docker.io/cherepakhin/vacancy_backend:0.24.1016.1

#$ docker run -p 8980:8980 -p 8988:8988 -p 5432:5432 docker.io/cherepakhin/vacancy_backend:latest
#docker: Error response from daemon: driver failed programming external connectivity on endpoint optimistic_buck (8f3a827ad60fd111a3e72789038c77be0335936889967ff49b4a2823156d75ba): Error starting userland proxy: listen tcp4 0.0.0.0:5432: bind: address already in use.
