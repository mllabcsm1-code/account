# Account Vault Marketplace API Documentation

## Overview
The Account Vault Marketplace API provides comprehensive endpoints for managing a digital marketplace where users can buy and sell accounts. This RESTful API supports user management, listing management, transactions, reviews, disputes, and more.

**Base URL:** `http://localhost:8080/api/v1`

**Content-Type:** `application/json`

---

## Authentication Endpoints

### Register User
**POST** `/auth/register`

Creates a new user account.

**Request Body:**
\`\`\`json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "role": "BUYER"
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "success": true,
  "message": "User registered successfully",
  "userId": 1```md file="API_DOCUMENTATION.md"
# Account Vault Marketplace API Documentation

## Overview
The Account Vault Marketplace API provides comprehensive endpoints for managing a digital marketplace where users can buy and sell accounts. This RESTful API supports user management, listing management, transactions, reviews, disputes, and more.

**Base URL:** `http://localhost:8080/api/v1`

**Content-Type:** `application/json`

---

## Authentication Endpoints

### Register User
**POST** `/auth/register`

Creates a new user account.

**Request Body:**
\`\`\`json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "role": "BUYER"
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "success": true,
  "message": "User registered successfully",
  "userId": 1
}
\`\`\`

**Error Response (400 Bad Request):**
\`\`\`json
{
  "success": false,
  "message": "Username already exists"
}
\`\`\`

### Login User
**POST** `/auth/login`

Authenticates a user and returns user information.

**Request Body:**
\`\`\`json
{
  "username": "john_doe",
  "password": "securePassword123"
}
\`\`\`

**Response (200 OK):**
\`\`\`json
{
  "success": true,
  "message": "Login successful",
  "user": {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "role": "BUYER",
    "reputationScore": 4.5,
    "verificationLevel": "VERIFIED",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00"
  }
}
\`\`\`

### Change Password
**POST** `/auth/change-password`

Changes user password.

**Request Body:**
\`\`\`json
{
  "username": "john_doe",
  "oldPassword": "oldPassword123",
  "newPassword": "newSecurePassword456"
}
\`\`\`

**Response (200 OK):**
\`\`\`json
{
  "success": true,
  "message": "Password changed successfully"
}
\`\`\`

---

## User Management Endpoints

### Create User
**POST** `/users`

Creates a new user (admin function).

**Request Body:**
\`\`\`json
{
  "username": "jane_seller",
  "email": "jane@example.com",
  "passwordHash": "hashedPassword",
  "role": "SELLER",
  "verificationLevel": "BASIC"
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "userId": 2,
  "username": "jane_seller",
  "email": "jane@example.com",
  "role": "SELLER",
  "reputationScore": 0.0,
  "verificationLevel": "BASIC",
  "status": "ACTIVE",
  "createdAt": "2024-01-15T11:00:00",
  "updatedAt": "2024-01-15T11:00:00"
}
\`\`\`

### Get User by ID
**GET** `/users/{userId}`

Retrieves user information by ID.

**Response (200 OK):**
\`\`\`json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "role": "BUYER",
  "reputationScore": 4.5,
  "verificationLevel": "VERIFIED",
  "status": "ACTIVE",
  "lastLogin": "2024-01-15T09:30:00",
  "createdAt": "2024-01-10T14:20:00",
  "updatedAt": "2024-01-15T09:30:00"
}
\`\`\`

### Get All Users (Paginated)
**GET** `/users?page=0&size=10`

Retrieves paginated list of users.

**Response (200 OK):**
\`\`\`json
{
  "content": [
    {
      "userId": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "role": "BUYER",
      "reputationScore": 4.5,
      "verificationLevel": "VERIFIED",
      "status": "ACTIVE",
      "createdAt": "2024-01-10T14:20:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
\`\`\`

### Update User Reputation
**PUT** `/users/{userId}/reputation?score=4.8`

Updates user reputation score.

**Response (200 OK):**
\`\`\`json
{
  "userId": 1,
  "username": "john_doe",
  "reputationScore": 4.8,
  "updatedAt": "2024-01-15T12:00:00"
}
\`\`\`

---

## Listing Management Endpoints

### Create Listing
**POST** `/listings`

Creates a new marketplace listing.

**Request Body:**
\`\`\`json
{
  "seller": {
    "userId": 2
  },
  "title": "Premium Gaming Account",
  "description": "Level 100 gaming account with rare items and achievements",
  "price": 299.99,
  "currency": "USD",
  "category": "Gaming",
  "location": {
    "locationId": 1
  }
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "listingId": 1,
  "seller": {
    "userId": 2,
    "username": "jane_seller",
    "email": "jane@example.com",
    "role": "SELLER"
  },
  "title": "Premium Gaming Account",
  "description": "Level 100 gaming account with rare items and achievements",
  "price": 299.99,
  "currency": "USD",
  "category": "Gaming",
  "status": "ACTIVE",
  "viewCount": 0,
  "location": {
    "locationId": 1,
    "country": "United States",
    "city": "New York"
  },
  "createdAt": "2024-01-15T13:00:00",
  "updatedAt": "2024-01-15T13:00:00"
}
\`\`\`

### Get Listing by ID
**GET** `/listings/{listingId}`

Retrieves listing details and increments view count.

**Response (200 OK):**
\`\`\`json
{
  "listingId": 1,
  "seller": {
    "userId": 2,
    "username": "jane_seller",
    "reputationScore": 4.2
  },
  "title": "Premium Gaming Account",
  "description": "Level 100 gaming account with rare items and achievements",
  "price": 299.99,
  "currency": "USD",
  "category": "Gaming",
  "status": "ACTIVE",
  "viewCount": 15,
  "createdAt": "2024-01-15T13:00:00"
}
\`\`\`

### Search Listings
**GET** `/listings/search?keyword=gaming&page=0&size=10`

Searches listings by keyword.

**Response (200 OK):**
\`\`\`json
{
  "content": [
    {
      "listingId": 1,
      "title": "Premium Gaming Account",
      "price": 299.99,
      "category": "Gaming",
      "viewCount": 15,
      "seller": {
        "username": "jane_seller",
        "reputationScore": 4.2
      }
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}
\`\`\`

### Get Listings by Price Range
**GET** `/listings/price-range?minPrice=100&maxPrice=500&page=0&size=10`

Filters listings by price range.

### Get Active Listings (Recent)
**GET** `/listings/active/recent?page=0&size=10`

Gets recently posted active listings.

### Get Active Listings (Popular)
**GET** `/listings/active/popular?page=0&size=10`

Gets most viewed active listings.

### Get Categories
**GET** `/listings/categories`

Retrieves all available listing categories.

**Response (200 OK):**
\`\`\`json
[
  "Gaming",
  "Social Media",
  "Streaming",
  "E-commerce",
  "Educational"
]
\`\`\`

---

## Transaction Management Endpoints

### Create Transaction
**POST** `/transactions`

Creates a new transaction for purchasing a listing.

**Request Body:**
\`\`\`json
{
  "listing": {
    "listingId": 1
  },
  "buyer": {
    "userId": 1
  },
  "amount": 299.99,
  "currency": "USD",
  "paymentMethod": "Credit Card",
  "reason": "Account purchase"
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "transactionId": 1,
  "listing": {
    "listingId": 1,
    "title": "Premium Gaming Account",
    "price": 299.99
  },
  "buyer": {
    "userId": 1,
    "username": "john_doe"
  },
  "amount": 299.99,
  "currency": "USD",
  "status": "PENDING",
  "paymentMethod": "Credit Card",
  "reason": "Account purchase",
  "createdAt": "2024-01-15T14:00:00"
}
\`\`\`

### Get Transaction by ID
**GET** `/transactions/{transactionId}`

Retrieves transaction details.

**Response (200 OK):**
\`\`\`json
{
  "transactionId": 1,
  "listing": {
    "listingId": 1,
    "title": "Premium Gaming Account",
    "seller": {
      "username": "jane_seller"
    }
  },
  "buyer": {
    "userId": 1,
    "username": "john_doe"
  },
  "amount": 299.99,
  "currency": "USD",
  "status": "COMPLETED",
  "paymentMethod": "Credit Card",
  "completedAt": "2024-01-15T14:30:00",
  "createdAt": "2024-01-15T14:00:00"
}
\`\`\`

### Complete Transaction
**PUT** `/transactions/{transactionId}/complete`

Marks transaction as completed.

**Response (200 OK):**
\`\`\`json
{
  "transactionId": 1,
  "status": "COMPLETED",
  "completedAt": "2024-01-15T14:30:00"
}
\`\`\`

### Get Transactions by Buyer
**GET** `/transactions/buyer/{buyerId}?page=0&size=10`

Retrieves paginated transactions for a specific buyer.

### Get Revenue Report
**GET** `/transactions/revenue?startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59`

Gets total revenue for date range.

**Response (200 OK):**
\`\`\`json
15750.50
\`\`\`

---

## Review System Endpoints

### Create Review
**POST** `/reviews`

Creates a review for a user.

**Request Body:**
\`\`\`json
{
  "reviewer": {
    "userId": 1
  },
  "reviewee": {
    "userId": 2
  },
  "rating": 5,
  "comment": "Excellent seller! Fast delivery and great communication."
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "reviewId": 1,
  "reviewer": {
    "userId": 1,
    "username": "john_doe"
  },
  "reviewee": {
    "userId": 2,
    "username": "jane_seller"
  },
  "rating": 5,
  "comment": "Excellent seller! Fast delivery and great communication.",
  "isVerified": false,
  "createdAt": "2024-01-15T15:00:00"
}
\`\`\`

### Get Reviews for User
**GET** `/reviews/reviewee/{userId}?page=0&size=10`

Gets all reviews for a specific user.

**Response (200 OK):**
\`\`\`json
{
  "content": [
    {
      "reviewId": 1,
      "reviewer": {
        "username": "john_doe"
      },
      "rating": 5,
      "comment": "Excellent seller! Fast delivery and great communication.",
      "isVerified": true,
      "createdAt": "2024-01-15T15:00:00"
    }
  ],
  "totalElements": 1
}
\`\`\`

### Get Average Rating
**GET** `/reviews/user/{userId}/average-rating`

Gets average rating for a user.

**Response (200 OK):**
\`\`\`json
4.7
\`\`\`

### Verify Review
**PUT** `/reviews/{reviewId}/verify`

Marks review as verified (admin function).

---

## Dispute Management Endpoints

### Create Dispute
**POST** `/disputes`

Creates a dispute for a transaction.

**Request Body:**
\`\`\`json
{
  "transaction": {
    "transactionId": 1
  },
  "raisedBy": {
    "userId": 1
  },
  "reason": "Account credentials were incorrect and seller is not responding",
  "evidenceUrl": "https://example.com/evidence.pdf"
}
\`\`\`

**Response (201 Created):**
\`\`\`json
{
  "disputeId": 1,
  "transaction": {
    "transactionId": 1,
    "amount": 299.99
  },
  "raisedBy": {
    "userId": 1,
    "username": "john_doe"
  },
  "reason": "Account credentials were incorrect and seller is not responding",
  "status": "OPEN",
  "evidenceUrl": "https://example.com/evidence.pdf",
  "createdAt": "2024-01-15T16:00:00"
}
\`\`\`

### Get Dispute by ID
**GET** `/disputes/{disputeId}`

Retrieves dispute details.

### Get Disputes by Status
**GET** `/disputes/status/OPEN?page=0&size=10`

Gets disputes filtered by status.

**Response (200 OK):**
\`\`\`json
{
  "content": [
    {
      "disputeId": 1,
      "transaction": {
        "transactionId": 1,
        "listing": {
          "title": "Premium Gaming Account"
        }
      },
      "raisedBy": {
        "username": "john_doe"
      },
      "status": "OPEN",
      "createdAt": "2024-01-15T16:00:00"
    }
  ],
  "totalElements": 1
}
\`\`\`

### Resolve Dispute
**PUT** `/disputes/{disputeId}/resolve`

Marks dispute as resolved.

**Response (200 OK):**
\`\`\`json
{
  "disputeId": 1,
  "status": "RESOLVED",
  "resolvedAt": "2024-01-15T17:00:00"
}
\`\`\`

### Escalate Dispute
**PUT** `/disputes/{disputeId}/escalate`

Escalates dispute to higher authority.

---

## Error Responses

### Standard Error Format
All error responses follow this format:

\`\`\`json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2024-01-15T12:00:00"
}
\`\`\`

### Common HTTP Status Codes

- **200 OK** - Request successful
- **201 Created** - Resource created successfully
- **400 Bad Request** - Invalid request data
- **401 Unauthorized** - Authentication required
- **403 Forbidden** - Access denied
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

### Validation Error Response
\`\`\`json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "username": "Username must be between 3 and 50 characters",
    "email": "Email should be valid",
    "price": "Price must be greater than 0"
  },
  "timestamp": "2024-01-15T12:00:00"
}
\`\`\`

---

## Pagination

All list endpoints support pagination with these parameters:

- `page` - Page number (0-based, default: 0)
- `size` - Page size (default: 10, max: 100)

**Example:** `/users?page=2&size=20`

**Pagination Response Format:**
\`\`\`json
{
  "content": [...],
  "pageable": {
    "pageNumber": 2,
    "pageSize": 20
  },
  "totalElements": 150,
  "totalPages": 8,
  "first": false,
  "last": false,
  "numberOfElements": 20
}
\`\`\`

---

## Rate Limiting

- **Rate Limit:** 1000 requests per hour per IP
- **Headers:** 
  - `X-RateLimit-Limit: 1000`
  - `X-RateLimit-Remaining: 999`
  - `X-RateLimit-Reset: 1642248000`

---

## Data Models

### User Roles
- `BUYER` - Can purchase listings
- `SELLER` - Can create and manage listings
- `ADMIN` - Full system access

### User Status
- `ACTIVE` - Normal active user
- `SUSPENDED` - Temporarily suspended
- `BANNED` - Permanently banned

### Listing Status
- `ACTIVE` - Available for purchase
- `SOLD` - Successfully sold
- `REMOVED` - Removed by seller/admin
- `SUSPENDED` - Temporarily suspended

### Transaction Status
- `PENDING` - Payment processing
- `COMPLETED` - Successfully completed
- `REFUNDED` - Refunded to buyer
- `CANCELLED` - Cancelled by user/admin

### Dispute Status
- `OPEN` - Newly created dispute
- `ESCALATED` - Escalated to admin
- `RESOLVED` - Successfully resolved
- `CLOSED` - Closed without resolution

---

## Security Considerations

1. **Password Security** - All passwords are hashed using BCrypt
2. **Input Validation** - All inputs are validated and sanitized
3. **SQL Injection Protection** - Using JPA/Hibernate with parameterized queries
4. **CORS** - Configured for cross-origin requests
5. **Rate Limiting** - Implemented to prevent abuse
6. **Audit Trail** - All admin actions are logged

---

## Getting Started

1. **Setup Database** - Configure PostgreSQL connection in `application.properties`
2. **Run Application** - `mvn spring-boot:run`
3. **Test Endpoints** - Use Postman or curl to test API endpoints
4. **Monitor** - Check `/actuator/health` for application health

**Health Check Endpoint:**
\`\`\`
GET /actuator/health
\`\`\`

**Response:**
\`\`\`json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "redis": {
      "status": "UP"
    }
  }
}
