# Hotel reserve

Данный проект представляет собой REST сервис на языке Java для бронирования отелей 
с автоматической отправкой сообщений на электронную почту и поддержкой интернационализации

## Стек используемых технологий

* [Spring](https://spring.io/) — фреймворк для разработки на Java
* [Gradle](https://gradle.org) — инструмент для автоматизации сборки проекта
* [Lombok](https://projectlombok.org/) — библиотека для генерации boilerplate кода
* [PostgreSQL](https://postgresql.org) — реляционная база данных
* [JUnit 5](https://github.com/junit-team/junit5) — фреймворк для модульного тестирования на Java
* [Swagger](https://springdoc.org) – инструмент для интерактивной документации API
* [ModelMapper](https://modelmapper.org) – библиотека для автоматического преобразования объектов
* [Liquibase](https://liquibase.org) – система управления версиями базы данных

## Запуск

1. Настроить подключение к PostgreSQL или запустить локально
```shell
docker run --name hotel-database -e POSTGRES_PASSWORD=pass -p 5432:5432 -d postgres:15.3-alpine
```
2. Скомпилировать и запустить приложение
```shell
./gradlew bootRun
```
3. Перейти на страницу с интерактивной документацией `localhost:8080/swagger-ui.html`

4. Пройти аутентификацию c username `swift@gmail.com` и password `1234`
<img src="https://github.com/aojona/hotel-reserve/blob/main/data/auth.svg" width="140">

## База данных

![diagram](https://github.com/aojona/hotel-reserve/blob/main/data/diagram.svg)

### Эндпоинты

<table>
	<thead>
		<tr>
			<th>METHOD</th>
			<th>PATH</th>
			<th>DESCRIPTION</th>
		</tr>
	</thead>
	<tbody>
		<tr>
      <td colspan="3" align="center"> <b>USER</b></td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>users/</td>
			<td>Найти всех пользователей</td>
		</tr>
		<tr>
			<td><strong>POST</strong></td>
			<td>users/</td>
			<td>Зарегестрировать пользователя</td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>users/{id}</td>
			<td>Найти пользователя по id</td>
		</tr>
        <tr>
			<td><strong>PUT</strong></td>
			<td>users/{id}</td>
			<td>Обновить пользователя по id</td>
		</tr>
		<tr>
			<td><strong>DELETE</strong></td>
			<td>users/{id}</td>
			<td>Удалить пользователя по id</td>
		</tr>
		<tr>
      <td colspan="3" align="center"><b>HOTEL</b></td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>hotels/</td>
			<td>Найти все отели</td>
		</tr>
		<tr>
			<td><strong>POST</strong></td>
			<td>hotels/</td>
			<td>Добавить отель</td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>hotels/{id}</td>
			<td>Найти отель по id</td>
		</tr>
        <tr>
			<td><strong>PUT</strong></td>
			<td>hotels/{id}</td>
			<td>Обновить данные отеля по id</td>
		</tr>
		<tr>
			<td><strong>DELETE</strong></td>
			<td>hotels/{id}</td>
			<td>Удалить отель по id</td>
		</tr>
        <tr>
			<td><strong>GET</strong></td>
			<td>hotels/available</td>
			<td>Узнать количество свободных номеров в отеле с параметром 'name'</td>
		</tr>
		<tr>
      <td colspan="3" align="center"><b>ROOM</b></td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>rooms/</td>
			<td>Найти все номера</td>
		</tr>
		<tr>
			<td><strong>POST</strong></td>
			<td>rooms/</td>
			<td>Добавить номер</td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>rooms/{id}</td>
			<td>Найти номер по id</td>
		</tr>
        <tr>
			<td><strong>PUT</strong></td>
			<td>rooms/{id}</td>
			<td>Обновить номер по id</td>
		</tr>
		<tr>
			<td><strong>DELETE</strong></td>
			<td>rooms/{id}</td>
			<td>Удалить номер по id</td>
		</tr>
		<tr>
      <td colspan="3" align="center"><b>RESERVATION</b></td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>reservations/</td>
			<td>Найти все бронирования</td>
		</tr>
		<tr>
			<td><strong>POST</strong></td>
			<td>reservations/</td>
			<td>Забронировать номер</td>
		</tr>
		<tr>
			<td><strong>GET</strong></td>
			<td>reservations/{id}</td>
			<td>Найти номер по id</td>
		</tr>
        <tr>
			<td><strong>PUT</strong></td>
			<td>reservations/{id}</td>
			<td>Обновить номер по id</td>
		</tr>
		<tr>
			<td><strong>DELETE</strong></td>
			<td>reservations/{id}</td>
			<td>Удалить номер по id</td>
		</tr>
<tr>
			<td><strong>POST</strong></td>
			<td>reservations/reserve</td>
			<td>Забронировать любой свободный номер</td>
		</tr>
	</tbody>
</table>

Prefix `api/v1` is required for REST API

### Примеры запросов
Request
```http
GET /api/v1/users?page=0&size=2
```
Response
```json
{
  "data": [
    {
      "firstName": "Jonathan",
      "lastName": "Swift",
      "email": "bangOf4@gmail.com",
      "role": "ADMIN"
    },
    {
      "firstName": "Rob",
      "lastName": "Schneider",
      "email": "animal@ya.ru",
      "role": "USER"
    }
  ]
}
```
____
Request
```http
POST /api/v1/reservations?lang=ru
```
Response
```json
{
    "userEmail": "odd8@ya.ru",
    "hotelName": "Plaza",
    "roomNumber": 35
}
```
Email notification

<img src="https://github.com/aojona/hotel-reserve/blob/main/data/reservation.png" width="440">
