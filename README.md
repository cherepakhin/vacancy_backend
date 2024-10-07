## Простой проект на kotlin и Spring Boot

### Оглавление:
[Цель](#target)<br/>
[Unit тестирование](#unit_test)<br/>
[Интеграционное тестирование работы с БД](#integration_test_database)<br/>
[Покрытие тестами](#coverage)<br/>
[Логгирование](#logging)<br/>
[Запуск](#run)<br/>
[Ручное тестирование httpie](#httpie)<br/>
[Созлание запускаемого файла](#bootJar)<br/>
[Сборка Jenkins](#jenkins)<br/>
[Publishing SpringBoot "FAT" jar](#fat)<br/>

[Анализ кода Idea Analize](#idea_analizer)<br/>
[Интеграционное тестирование RestAssured](#rest_assured_tests)<br/>

[Swagger](#swagger)<br/>
[Spring Actuator](#actuator)<br/>
[Prometheus](#prometheus)<br/>
[Docker](#docker)<br/>

[TODO](#todo)<br/>
[Примечания](#tose)<br/>
[Просмотр commits в github](#commits_github)<br/>
[Ссылки](#links)<br/>

<a id="target"></a>
### Цель

Cоздать приложение на <b>Kotlin</b> с использованием <b>Spring Boot</b> для работы с БИЗНЕС-ПРОЦЕССОМ устройства на работу. Для этого д.б. следующие элементы:

__Вакансия__ со следующими свойствами:
<ul>
<li>Название</li>
<li>Компания</li>
<li>Требования</li>
<li>Дата создания</li>
<li>Дата последнего события</li>
<li>Зарплата (если есть)</li>
<li>Источник (ссылка hh.ru, др.ссылки)</li>
<li>Контакты представителя компании (имя, почта, ТГ, ...)</li>
<li>Комментарий</li>
</ul>

__События при трудоустроустве__:
<ul>
<li>Отклик на вакансию</li>
<li>Переговоры, отклики</li>
<li>Планирование встреч</li>
<li>Встречи (переговоры с кадрами, собеседования, ...) и результаты</li>
<li>Обмен документами (отправка в отдел кадров, подписание договора)</li>
<li></li>
</ul>

__Отчеты__:
Текущее состояние ситуации.
???

В качестве базы данных использовать PostgreSQL.
Настройки базы данных:

````shell
$ export DB_VACANCY=jdbc:postgresql://192.168.1.20:5432/vacancy
````

проверка:

````shell
$ echo $DB_VACANCY
jdbc:postgresql://192.168.1.20:5432/vacancy
````

настройка user/password для базы данных:

````shell
$ export PG_USER=vasi
$ export PG_PASSWORD=pass
````

Подключение с psql:

````shell
$ psql -Uvasi -dvacancy -hv
````
-hv означает host v.perm.ru (v - это алиас для v.perm.ru)

или

````shell
psql -h192.168.1.20 -Uvasi --dbname=vacancy
````


Для версионирования БД используется [flyway](https://flywaydb.org/). Автообновление БД отключено. В application.yaml установлен флаг:

````yaml
flyway:
    enabled: false
````

Миграции в src/resources/migration. При изменении структуры вручную выполнить:

````shell
./gradlew flywayMigrate
````

или 

````shell
$ ./gradlew flywayMigrate -Dflyway.user=postgres -Dflyway.password=postgres -Dflyway.url=jdbc:postgresql://127.0.0.1:5432/vacancy
````

Для полной очистки (на пример при полной переинизиализации) выполнить:

````sql
delete from vacancy;
delete from contact;
delete from company;
delete from flyway_schema_history fsh
````
и 

````shell
./gradlew flywayMigrate
````

#### Служебные сервисы

Очистка БД:

````shell
$ http :8980/vacancy/api/init/empty_db
Ok
````

Проверка, что таблица _company_ пустая:

````shell
$ http :8980/vacancy/api/company/
[]
````

Импорт начального состояния БД:

````shell
// тест, таблица "company" пустая
$ http :8980/vacancy/api/company/
[]

// импорт начального состояния
$ http :8980/vacancy/api/init/reimport_db
Ok

// проверка, что тестовые данные импортированы в базу
$ http :8980/vacancy/api/company/
[
    {
        "n": -1,
        "name": "-"
    },
    {
        "n": 1,
        "name": "COMPANY_1"
    },
    {
        "n": 2,
        "name": "COMPANY_2"
    },
    {
        "n": 3,
        "name": "3_COMPANY"
    }
]
````


<a id="unit_test"></a>
### Unit тестирование

````java
$ ./gradlew test
````

<a id="integration_test_database"></a>
### Интеграционное тестирование работы с БД

````java
$ ./gradlew test --tests '*Integration*'
````
в [run_integr_test.sh](./run_integr_test.sh).


<a id="coverage"></a>
### Покрытие тестами
В Idea выплнить Run/Show Coverage Data (Ctrl-Alt-6). Отчет:
![doc/idea_coverage.png](doc/idea_coverage.png)

__или__ с использованием [https://www.jacoco.org/jacoco/](https://www.jacoco.org/jacoco/). (подключен плагин в build.gradle.kts: id("jacoco"))

Создание отчета о покрытии тестами:
````java
$ ./gradlew jacocoTestReport
````

Результат в build/reports/jacoco/test/html/index.html: 
![Результат](doc/jacoco_report.png)

test coverage для класса:
![coverage_class.png](doc/coverage_class.png)

<a id="logging"></a>
### Логгирование

Настройка в application.yaml

````shell
...
logging:
  level:
    root: info
  file:
    path: log/
...
````

Пример из EchoCtrl.kt:

````shell
private val logger = LoggerFactory.getLogger(this.javaClass.name)
...
        logger.info("$counter GET $mes")
...
````

<a id="run"></a>
### Запуск

````shell
$ ./gradlew bootRun
````

Запуск в режиме offline, без интернета (./run_tests_offline.sh):

````shell
$ export VACANCY_KOTLIN_IP=127.0.0.1:8980
$ ./gradlew --offline bootRun
````

Запуск jar (./run_jar.sh):

````shell
export VACANCY_KOTLIN_IP=127.0.0.1:8980
java -jar build/libs/vacancy_backend-0.24.0706.1.jar
````

<a id="httpie"></a>
### Ручное тестирование httpie

Доступ по :8980/vacancy/api

Тестовый запрос echo:

````shell
$ http :8980/vacancy/api/vacancy/echo/aaa
````
(используется программа httpie)

Другие прримеры запросов:

````shell
// get all vacancies
$ http :8980/vacancy/api/vacancy/
//get vacancy id=1
$ http :8980/vacancy/api/vacancy/1
// get all vacancies sorted by column 'n'
$ http :8980/vacancy/api/vacancy/sortByColumn/n
// get all vacancies sorted by column 'name'
$ http :8980/vacancy/api/vacancy/sortByColumn/name
// get all vacancies sorted by column 'company_n'
$ http :8980/vacancy/api/vacancy/sortByColumn/company_n

// get all companies
$ http :8980/vacancy/api/company/
// get company with id=1
$ http :8980/vacancy/api/company/1
// add new company from company_10.json
// {
//   "n": 10,
//   "name": "COMPANY_10"
// }
//
$ http POST :8980/vacancy/api/company/ < src/test/test_jsons/company_10.json
````

Для интеграционных тестов сделан служебный REST сервис по URL __:8980/vacancy/api/utils/reset__. При отправке GET запроса на этот URL данные устанавливаются в начальное состояние определенное в [./test/resources/import.sql](./test/resources/import.sql).


<a id="bootJar"></a>
### Создание запускаемого файла и его запуск

 ./gradlew bootJar
````
Собранный jar будет в build/libs/vacancy_backend-<version>.jar

Запуск:
````shell
$ java -jar build/libs/vacancy_backend-<version>.jar
````

Получение jar файла из Nexus через командную строку (или в скрипте для развертывания на сервере):

````shell
$ wget http://192.168.1.20:8082/repository/ru.perm.v/ru/perm/v/vacancy_backend/0.24.0706.1/vacancy_backend-0.24.0706.1.jar
````

или через v.perm.ru:

````shell
$ wget http://v.perm.ru:8082/repository/ru.perm.v/ru/perm/v/vacancy_backend/0.24.0706.1/vacancy_backend-0.24.0706.1.jar
````

Настроена публикации в Nexus fat jar: 

````shell
publishing {
    repositories {
        maven {
            url = uri("http://192.168.1.20:8081/repository/ru.perm.v/")
            isAllowInsecureProtocol = true
            // export NEXUS_CRED_USR=admin
            // echo $NEXUS_CRED_USR
            credentials {
                username = "admin"
                password = "pass"
            }
        }
    }
    publications {
        create<MavenPublication>("maven"){
            artifact(tasks["bootJar"])
        }
    }
}
````

Для deploy fat jar файла выполнить:

````shell
./gradlew publish
````

<a id="jenkins"></a>
### Сборка Jenkins

Сборка происходит в Jenkins, развернутом на домашнем сервере. Pipeline для Jenkins описан в файле [./Jenkinsfile](Jenkinsfile)

<a id="fat"></a>
###  Publishing SpringBoot "FAT" jar

Настройка:

````yaml
publishing {
    repositories {
        maven {
            url = uri("http://192.168.1.20:8081/repository/ru.perm.v/")
            isAllowInsecureProtocol = true
            //  publish в nexus "./gradlew publish" из ноута и Jenkins проходит
            // export NEXUS_CRED_USR=admin
            // echo $NEXUS_CRED_USR
            credentials {
                username = "admin"
                password = "pass"
            }
        }
    }
    publications {
        create("mavenJava", MavenPublication::class) {
            from(components["java"])
//            artifact(sourcesJar.get())
        }
    }
}
````

При выполнении ./gradlew publish собранный jar будет опубликован в nexus.

[https://stackoverflow.com/questions/64062905/unable-to-publish-jar-to-gitlab-package-registry-with-gradle](https://stackoverflow.com/questions/64062905/unable-to-publish-jar-to-gitlab-package-registry-with-gradle)

### Publishing source to Nexus

````yaml
val sourcesJar by tasks.registering(Jar::class)  {
  archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
  
publishing {
  ....
  publications {
    create<MavenPublication>("maven"){
      artifact(tasks["bootJar"])
      artifact(tasks["sourcesJar"])
    }
  }
  ....
}
````

<a id="idea_analizer"></a>
### Анализ кода Idea Analize

В Idea вызывается из Code ->  Inspect Code.

<a id="rest_assured_tests"></a>
### RestAssured tests<br/>

[https://github.com/cherepakhin/vacancy_backend_rest_test](https://github.com/cherepakhin/vacancy_backend_rest_test)

<a id="swagger"></a>
### Swagger

После запуска доступен по адресу [http://127.0.0.1:8980/vacancy/api/swagger-ui/](http://127.0.0.1:8980/vacancy/api/swagger-ui/)

![doc/swagger.png](doc/swagger.png)

<a id="actuator"></a>
### Spring Actuator

После запуска доступен по адресу [http://127.0.0.1:8988/vacancy/api/actuator](http://127.0.0.1:8988/vacancy/api/actuator)

````shell
$ http http://127.0.0.1:8988/vacancy/api/actuator
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/vnd.spring-boot.actuator.v3+json
Date: Sat, 22 Jun 2024 16:02:17 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "_links": {
        "beans": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/beans",
            "templated": false
        },
        "caches": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/caches",
            "templated": false
        },
        "caches-cache": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/caches/{cache}",
            "templated": true
        },
        "conditions": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/conditions",
            "templated": false
        },
        "configprops": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/configprops",
            "templated": false
        },
        "configprops-prefix": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/configprops/{prefix}",
            "templated": true
        },
        "env": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/env",
            "templated": false
        },
        "env-toMatch": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/env/{toMatch}",
            "templated": true
        },
        "health": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/health",
            "templated": false
        },
        "health-path": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/health/{*path}",
            "templated": true
        },
        "heapdump": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/heapdump",
            "templated": false
        },
        "info": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/info",
            "templated": false
        },
        "logfile": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/logfile",
            "templated": false
        },
        "loggers": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/loggers",
            "templated": false
        },
        "loggers-name": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/loggers/{name}",
            "templated": true
        },
        "mappings": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/mappings",
            "templated": false
        },
        "metrics": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/metrics",
            "templated": false
        },
        "metrics-requiredMetricName": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/metrics/{requiredMetricName}",
            "templated": true
        },
        "prometheus": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/prometheus",
            "templated": false
        },
        "scheduledtasks": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/scheduledtasks",
            "templated": false
        },
        "self": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator",
            "templated": false
        },
        "threaddump": {
            "href": "http://127.0.0.1:8988/vacancy/api/actuator/threaddump",
            "templated": false
        }
    }
}
````

<a id="prometheus"></a>
### Prometheus

Досту по адресу: http://127.0.0.1:8988/vacancy/api/actuator/prometheus

<a id="docker"></a>
### Docker

Создание Docker
[./docker_build.sh](./docker_build.sh)

Запуск образа:

````shell
$ docker run -p 8980:8980 v/vacancy_backend
````


docker ps

<a id="tose"></a>
### Примечания

Решение проблемы циклических зависимостей Spring. CompanyService зависит от VacancyService, а VacancyService зависит от CompanyServiceImpl. При поднятии сервиса возникнет ошибка. 


Определение CompanyServiceImpl: 

````java
@Service
class CompanyServiceImpl(val repository: CompanyRepository, @Lazy val vacancyService: VacancyServiceImpl) : CompanyService {...}
````

Определение VacancyServiceImpl:

````java
@Service
class VacancyServiceImpl(
    @Autowired private val repository: VacancyRepository,
    @Autowired private val companyService: CompanyService
) : VacancyService {...}
````

В CompanyService использована аннотация @Lazy для vacancyService. 

#### Что это было?

Изменения в коде не подхватываются при запуске ./gradlew bootRun. Решилось так:

````shell
./gradlew clean
./gradlew build
./gradlew bootRun
````

java -verbose:class <other args> - вывод загруженных классов

<a id="todo"></a>
### TODO
Анализ кода SonarCube<br/>
<br/>
После отладки JPA, перенести в PostgreSQL на v.perm.ru<br/>
Spring profiles<br/>
<br/>
DataJpa tests<br/>
Нагрузочное тестирование<br/>
<br/>
Prometheus<br/><br/>
Docker<br/>
Кеширование<br/>
<br/>
Camel для интеграции<br/>
jxls для отчетов<br/>

<a id="commits_github"></a>
### Просмотр commits в github

[https://github.com/cherepakhin/vacancy_backend_rest_test/commits](https://github.com/cherepakhin/vacancy_backend_rest_test/commits)

<a id="links"></a>
### Ссылки

[https://github.com/cherepakhin/shop_kotlin](https://github.com/cherepakhin/shop_kotlin)
[https://github.com/cherepakhin/shop_kotlin_restassured_test](https://github.com/cherepakhin/shop_kotlin_restassured_test)
[Kotlin + Hibernate: всё сложно](https://habr.com/ru/companies/haulmont/articles/572574/)