$ docker build --build-arg JAR_FILE=build/libs/vacancy_backend-0.24.0706.1.jar -t v/vacancy_backend .

# test run
$ docker run -p 8980:8980 -p 8988:8988  docker.io/v/vacancy_backend:latest

#$ docker run -p 8980:8980 -p 8988:8988 -p 5432:5432 docker.io/v/vacancy_backend:latest
#docker: Error response from daemon: driver failed programming external connectivity on endpoint optimistic_buck (8f3a827ad60fd111a3e72789038c77be0335936889967ff49b4a2823156d75ba): Error starting userland proxy: listen tcp4 0.0.0.0:5432: bind: address already in use.
