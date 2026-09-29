# Gym Management REST API

A Java 21 and Spring Boot REST API for gym members, trainers, memberships, attendance, and payments. Hibernate creates and updates the PostgreSQL tables during development.

## Run locally

Create a PostgreSQL database named `gym_management` and set the `DB_PASSWORD` environment variable to the password for the local `postgres` user. The datasource connects to `jdbc:postgresql://localhost:5432/gym_management`. The placeholder password in `application.properties` must be replaced through the environment before running the application.

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

`spring.jpa.hibernate.ddl-auto=update` is intended for development. Do not use it as a production schema migration strategy.

## API routes

All five resources support `POST /api/{resource}`, `GET /api/{resource}`, `GET /api/{resource}/{id}`, `PUT /api/{resource}/{id}`, and `DELETE /api/{resource}/{id}`:

| Resource | Route |
|---|---|
| Members | `/api/members` |
| Trainers | `/api/trainers` |
| Memberships | `/api/memberships` |
| Attendance | `/api/attendance` |
| Payments | `/api/payments` |

Membership, attendance, and payment requests refer to an existing member using `memberId`. For example:

```json
{
  "name": "Himanshu",
  "email": "himanshu@example.com",
  "phone": "9876543210",
  "age": 19,
  "gender": "MALE",
  "joinDate": "2026-09-28"
}
```

Create requests return `201 Created`, successful reads and updates return `200 OK`, deletes return `204 No Content`, invalid input returns `400 Bad Request`, missing resources return `404 Not Found`, and duplicate or conflicting data returns `409 Conflict`.

## Packages and request flow

- `controller`: HTTP routes, request validation entry points, and response status codes.
- `service`: business rules and coordination between repositories.
- `repository`: Spring Data JPA interfaces that read and write entities.
- `entity`: persisted JPA models and enum values; memberships, attendance, and payments reference a member.
- `dto`: validated request shapes and API response shapes, keeping persistence entities out of the REST contract.
- `exception`: not-found and duplicate errors plus consistent JSON error responses.

Requests flow through `Client → Controller → Service → Repository → Database`. For example, `POST /api/members` is validated by the controller, passed to `MemberService` for email uniqueness and entity mapping, saved by `MemberRepository`, and returned as a `MemberResponse` JSON object.
