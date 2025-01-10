# Food Reservation API

A Spring Boot application for managing food reservations.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
src/main/java/com/example/foodreservation/
├── config/                 # Configuration classes
├── controller/            # REST controllers
├── dto/                   # Data Transfer Objects
├── mapper/               # Object mappers
├── model/                # Domain models
│   └── enums/           # Enumerations
├── repository/           # Data access layer
├── service/             # Business logic
└── validation/          # Custom validators
```

## Getting Started

### Development

1. Clone the repository:
```bash
git clone <repository-url>
cd food-reservation
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run in development mode:
```bash
./mvnw spring-boot:run
```

The application will start on port 8080 (http://localhost:8080)

### Production

1. Build the production JAR:
```bash
./mvnw clean package
```

2. Run the JAR file:
```bash
java -jar target/food-reservation-1.0.0.jar
```

For production deployment, consider setting these environment variables:
```bash
export SPRING_PROFILES_ACTIVE=prod
export SERVER_PORT=8080
```

## API Documentation

Swagger UI is available at: http://localhost:8080/swagger-ui.html
OpenAPI specification: http://localhost:8080/api-docs

## Available Endpoints

### Foods API
- GET `/api/foods?date=2023-10-25` - Get available foods for a specific date

### Reservations API
- POST `/api/reservations` - Create a new reservation
- GET `/api/reservations/person/{personId}` - Get reservations for a person
    - Optional query parameters:
        - startDate (ISO format: YYYY-MM-DD)
        - endDate (ISO format: YYYY-MM-DD)

## Example Requests

### Create Reservation
```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "foodId": "1",
    "personId": "user123",
    "reservationDate": "2023-10-25"
  }'
```

### Get Person's Reservations
```bash
curl "http://localhost:8080/api/reservations/person/user123?startDate=2023-10-01&endDate=2023-10-31"
```

## Development Notes

- The application uses in-memory storage for demonstration purposes
- For production use, implement proper database integration
- Add appropriate logging in production environment
- Configure CORS settings based on your requirements
- Add authentication and authorization for production use

## Testing

Run tests using Maven:
```bash
./mvnw test
```

## Contributing

1. Create a feature branch
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details