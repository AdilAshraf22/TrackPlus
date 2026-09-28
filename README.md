# 🚚 TrackPlus — Real-Time Logistics Tracking & ETA Platform

TrackPlus is a full-stack logistics tracking platform designed to provide real-time shipment visibility and estimated delivery times.

The platform allows logistics operators to manage shipments and drivers while customers can track shipments using a public tracking code. Shipment status and ETA updates are delivered in real time using WebSocket communication, eliminating the need to manually refresh the tracking page.

# 📌 Overview

Traditional logistics tracking systems often depend on phone calls, spreadsheets, or manually updated shipment records.

TrackPlus provides a centralized platform where:

- Administrators can manage logistics data and users.
- Drivers can view shipments and update shipment status.
- Customers can track shipments using a tracking code.
- Shipment status changes are delivered to the customer in real time.
- Estimated delivery time is calculated dynamically based on route information and shipment progress.

The project was built as a full-stack application using Spring Boot for the backend and React for the frontend.

# 🚀 Features

## 📦 Shipment Management

- Create and manage shipments.
- Assign customers, drivers, and routes.
- Track shipment status.
- Store shipment status history.
- Generate unique shipment tracking codes.

## 📍 Public Shipment Tracking

Customers can track a shipment using its tracking code without logging in.

The public tracking page displays:

- Tracking code
- Current shipment status
- Origin
- Destination
- Estimated delivery time
- Actual delivery time when available

Sensitive customer and driver information is not exposed through the public tracking endpoint.

## ⚡ Real-Time Shipment Updates

TrackPlus uses WebSocket communication to provide real-time updates.

When a driver changes a shipment status:

```text
Driver Dashboard
       ↓
Spring Boot Backend
       ↓
WebSocket
       ↓
Customer Tracking Page
       ↓
Status + ETA Updated
```

The customer tracking page updates automatically without requiring a page refresh.

## 🔐 Authentication & Authorization

The backend uses:

- Spring Security
- JWT authentication
- Role-based authorization
- BCrypt password hashing

Supported roles include:

- ADMIN
- DRIVER
- CUSTOMER

Management APIs are protected using JWT authentication, while public shipment tracking remains accessible using the tracking code.

## 🧮 Dynamic ETA Calculation

TrackPlus calculates estimated delivery time using:

- Route distance
- Shipment progress
- Average route speed
- Shipment status
- Congestion adjustment

For shipments in active transit, a congestion multiplier is applied to account for potential delays.

The calculated ETA is returned to the frontend and updated when shipment status changes.

# 🏗️ System Architecture

```text
                    ┌─────────────────────┐
                    │   React Frontend    │
                    │      (Vite)         │
                    └──────────┬──────────┘
                               │
                     REST API / WebSocket
                               │
                               ▼
                    ┌─────────────────────┐
                    │  Spring Boot API    │
                    │                     │
                    │  Controllers        │
                    │  Services           │
                    │  Security           │
                    │  WebSocket          │
                    └──────────┬──────────┘
                               │
                              JPA
                               │
                               ▼
                    ┌─────────────────────┐
                    │       MySQL         │
                    │                     │
                    │ Users               │
                    │ Shipments           │
                    │ Routes              │
                    │ Status History      │
                    └─────────────────────┘
```

# 🛠️ Technologies Used

## Backend

- Java 17
- Spring Boot
- Spring Web / REST APIs
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- WebSocket
- Maven
- MySQL

## Frontend

- React
- Vite
- JavaScript
- Axios
- STOMP / WebSocket
- React Router
- CSS

## Development Tools

- IntelliJ IDEA
- Visual Studio Code
- MySQL Workbench
- Postman
- Git
- GitHub

# 📂 Project Structure

```text
TrackPlus/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/trackplus/trackplusbackend/
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       ├── entity/
│   │   │   │       ├── exception/
│   │   │   │       ├── repository/
│   │   │   │       ├── security/
│   │   │   │       └── service/
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── styles/
│   │   ├── App.jsx
│   │   └── main.jsx
│   │
│   ├── public/
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
└── README.md
```

# 🔐 Authentication & Security

TrackPlus uses JWT-based authentication.

## Authentication Flow

```text
User Login
    ↓
Spring Boot Authentication
    ↓
Credentials Verified
    ↓
JWT Generated
    ↓
JWT Sent to Frontend
    ↓
JWT Included in Protected Requests
    ↓
Spring Security JWT Filter
    ↓
Request Authorized
```

Passwords are stored using BCrypt hashing rather than plain text.

Sensitive configuration values are not stored directly in the repository.

Environment variables are used for:

```properties
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

# ⚡ Real-Time WebSocket Communication

TrackPlus uses WebSocket communication for live shipment updates.

## WebSocket Endpoint

```text
/ws
```

## Shipment Topic

```text
/topic/shipment/{shipmentId}
```

When a driver updates a shipment:

```text
Driver
  ↓
REST API
  ↓
Shipment Service
  ↓
ETA Recalculation
  ↓
WebSocket Message
  ↓
Customer Tracking Page
```

The customer receives the updated shipment status and ETA without refreshing the browser.

# 🧮 ETA Calculation

The current ETA implementation uses route and shipment information rather than machine learning.

The calculation considers:

```text
Route Distance
      ↓
Shipment Progress
      ↓
Remaining Distance
      ↓
Average Speed
      ↓
Congestion Adjustment
      ↓
Estimated Delivery Time
```

For shipments in active transit, a congestion multiplier is applied to the estimated travel time.

This provides a simple and explainable ETA calculation suitable for the current version of the platform.

# 🔌 API Overview

## Authentication

```text
POST /api/auth/login
POST /api/auth/register
```

## Shipments

```text
GET  /api/shipments
POST /api/shipments
GET  /api/shipments/track/{trackingCode}
```

## Routes

```text
GET  /api/routes
POST /api/routes
```

## Users

```text
GET /api/users
```

## Status History

```text
GET /api/status-history
```

The public tracking endpoint does not require authentication.

Management endpoints require appropriate authentication and authorization.

# ▶️ How to Run

## 1. Prerequisites

Install:

- Java 17
- MySQL 8
- Node.js
- npm
- Git

Recommended development tools:

- IntelliJ IDEA
- Visual Studio Code
- MySQL Workbench
- Postman

## 2. Clone the Repository

```bash
git clone https://github.com/AdilAshraf22/TrackPlus.git
cd TrackPlus
```

## 3. Configure MySQL

Create a MySQL database for TrackPlus.

The backend uses Spring Data JPA/Hibernate to create and manage the required tables.

Make sure the MySQL server is running before starting the backend.

## 4. Configure Environment Variables

The backend requires two environment variables:

```text
DB_PASSWORD
JWT_SECRET
```

Example on Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_mysql_password"
$env:JWT_SECRET="your_jwt_secret"
```

Do not commit actual credentials or secrets to GitHub.

## 5. Run the Backend

Navigate to:

```text
TrackPlus/backend
```

Run:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend starts on:

```text
http://localhost:8080
```

## 6. Run the Frontend

Open another terminal and navigate to:

```text
TrackPlus/frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The frontend will normally be available at:

```text
http://localhost:5173
```

# 🧪 Demo Flow

A typical TrackPlus demonstration follows this flow:

## 1. Login

An administrator or driver logs into the system.

## 2. Shipment Management

A shipment is created with:

- Customer
- Driver
- Route
- Tracking code

## 3. Driver Updates Shipment

The driver changes the shipment status.

Example shipment lifecycle:

```text
CREATED
   ↓
PICKED_UP
   ↓
IN_TRANSIT
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

## 4. ETA Recalculation

The backend recalculates the estimated delivery time based on the shipment's route and current status.

## 5. Customer Tracking

The customer opens the public tracking page using the tracking code.

## 6. Real-Time Update

When the driver changes the shipment status, the customer page receives the new status and ETA automatically through WebSocket communication.

# 📸 Screenshots

Screenshots demonstrating the following features can be added here:

- Login page
- Admin dashboard
- Driver dashboard
- Public shipment tracking
- Real-time shipment status update
- Postman API responses

# 🔮 Future Improvements

Possible future improvements include:

- Interactive map-based shipment tracking
- GPS-based driver location tracking
- More advanced ETA prediction using machine learning
- Route optimization
- Delivery analytics dashboard
- Notification system
- Cloud deployment
- Mobile application for drivers

# 👨‍💻 Author

**Adil Ashraf**

B.Tech Computer Science & Engineering

GitHub: [AdilAshraf22](https://github.com/AdilAshraf22)

# 📄 License

This project is intended primarily as an academic and portfolio project.
