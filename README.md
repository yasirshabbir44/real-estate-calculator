# Real Estate App

A comprehensive real estate application that helps users compare properties, calculate mortgage payments, and make informed real estate decisions.

## Features

- Property listing and details
- Property comparison
- Mortgage calculator
- Cost breakdown
- Document checklist
- Service charge estimates

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- H2 Database
- Lombok
- PDFBox

### Frontend
- React 18
- React Router 6
- Axios
- Tailwind CSS

## Project Structure

```
real-estate-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ae/smartdubai/iid/realestateapp/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── RealEstateAppApplication.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── frontend/
│   │       ├── public/
│   │       ├── src/
│   │       │   ├── components/
│   │       │   ├── pages/
│   │       │   ├── App.js
│   │       │   └── index.js
│   │       ├── package.json
│   │       └── tailwind.config.js
│   └── test/
│       └── java/
│           └── ae/smartdubai/iid/realestateapp/
└── build.gradle
```

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- npm 8 or higher
- Gradle 7.6 or higher

## Building the Application

The application uses Gradle to build both the backend and frontend. The build process will automatically:

1. Install npm dependencies
2. Build the React frontend
3. Copy the built frontend to the Spring Boot static resources directory
4. Build the Spring Boot application

To build the application, run:

```bash
./gradlew build
```

## Running the Application

After building the application, you can run it using:

```bash
./gradlew bootRun
```

Or you can run the JAR file directly:

```bash
java -jar build/libs/real-estate-app-0.0.1-SNAPSHOT.jar
```

The application will be available at http://localhost:8080

## Development

### Backend Development

For backend development, you can run the Spring Boot application using:

```bash
./gradlew bootRun
```

### Default Data

The application initializes the database with default data when it starts up:

- **Properties**: Sample properties of different types (apartments, villas, townhouses) in various Dubai communities
- **Document Checklists**: Standard document requirements for different buyer types (salaried, self-employed, investor)
- **Service Charge Estimates**: Typical service charge rates for different communities and property types

This default data helps users get started with the application without having to enter data manually.

### Frontend Development

For frontend development with hot-reloading, you can run the React development server:

```bash
cd src/main/frontend
npm start
```

The React development server will be available at http://localhost:3000 and will proxy API requests to the backend at http://localhost:8080.

## API Documentation

The application provides RESTful APIs for various features:

- `/api/properties` - Property management
- `/api/property-comparisons` - Property comparison
- `/api/loan-calculations` - Mortgage calculations
- `/api/cost-breakdowns` - Cost breakdown
- `/api/document-checklists` - Document checklist
- `/api/service-charge-estimates` - Service charge estimates
- `/api/pdf-reports` - PDF report generation

## License

This project is licensed under the MIT License - see the LICENSE file for details.
