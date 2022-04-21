Для запуска программы нужно:
1. Открыть проект в Идее
2. Запустить постгрес, создать БД task_db
3. Настроить соединение с БД в Идее - postgres:postgres
4. Создать таблицу запросом:
   create table weather_history
   (
   id           serial primary key,
   weather_city varchar,
   weather_date timestamp with time zone,
   weather_temp int,
   weather_service varchar
   );
5. Запустить программу
6. Перейти по ссылке http://localhost:8080/weather?city=Chelyabinsk
(endpoint - это /weather)

Задача:
REST сервис хранения и предоставления информации о температуре в городах.

- перечень городов (с уточнение страны, где он находится) на англ. языке задается в настройках сервиса;
- температура для городов из заданного списка запрашивается из трех публично доступных REST сервисов погоды (конкретные сервисы-провайдеры погоды найти самостоятельно);
- значения, полученные от сервисов для одного и того города в текущий момент, усредняются и складываются в БД (можно in-memory database) с текущим timestamp;
- периодичность опроса сервисов задается в настройках приложения;
- сервис должен предоставлять REST-ендпоинт, через который, указав город/страну и дату, можно получить все имеющиеся в БД в эту дату значения температуры для данного города;
- если дата в предыдущем запросе не указана, возвращается последнее известное значение для города (ака "температура сейчас");

Результат присылать в виде репозитория git с историей коммитов (без squash и удаления веток).

На логику сервиса должны быть написаны, как минимум, юнит-тесты (JUnit, Mockito). В юнит-тестах учесть краевые сценарии.

Плюсом идет наличие интеграционных тестов.

Стек - Spring Boot, Java 8.
