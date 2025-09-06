
# Asset Management REST API

A Spring Boot based REST API for managing company assets such as laptops, keyboards, furniture, and other items.  
This application keeps track of asset categories, asset details, their condition, and assignment to employees.  

## 🚀 Tech Stack
- **Java 17+**
- **Spring Boot 2.4**
- **Spring Data JPA**
- **H2 Database (in-memory)**
- **Maven**

---

## 📂 Features
- Manage **Asset Categories** (Add, Update, List)
- Manage **Assets** (Add, Update, Search, List, Assign, Recover, Delete)
- Ensure assets cannot be deleted while assigned
- Employee data assumed to be pre-existing in DB
- DTO-based request/response for cleaner API design

---

## 🗂 Data Models

### Category
```json
{
  "id": 1,
  "name": "Electronics",
  "description": "Laptops, keyboards, monitors"
}
````

### Employee

```json
{
  "id": 3,
  "fullName": "Charlie Brown",
  "designation": "Designer"
}
```

### Asset

```json
{
  "id": 1,
  "name": "Dell Laptop",
  "purchaseDate": "2025-09-06",
  "conditionNotes": "16GB RAM, 512GB SSD",
  "category": {
    "id": 1,
    "name": "Electronics",
    "description": "Laptops, keyboards, monitors"
  },
  "assignTo": {
    "id": 3,
    "fullName": "Charlie Brown",
    "designation": "Designer"
  },
  "assignStatus": "Assigned"
}
```

---

## 📌 API Endpoints

### Category APIs

| Method | Endpoint                | Description           |
| ------ | ----------------------- | --------------------- |
| `POST` | `/category/create`      | Create a new category |
| `GET`  | `/category/all`         | Get all categories    |
| `PUT`  | `/category/update/{id}` | Update category by ID |

#### Example – Create Category

```bash
curl -X POST http://localhost:8080/category/create \
-H "Content-Type: application/json" \
-d '{
  "name": "Electronics",
  "description": "Laptops, keyboards, monitors"
}'
```

---

### Asset APIs

| Method   | Endpoint                       | Description                         |
| -------- | ------------------------------ | ----------------------------------- |
| `POST`   | `/asset/create`                | Add new asset                       |
| `GET`    | `/asset/getAll`                | Get all assets                      |
| `GET`    | `/asset/search?name={keyword}` | Search assets by name               |
| `PUT`    | `/asset/update/{id}`           | Update asset by ID                  |
| `PUT`    | `/asset/assign/{id}`           | Assign asset to employee            |
| `PUT`    | `/asset/recover/{id}`          | Recover asset from employee         |
| `DELETE` | `/asset/delete/{id}`           | Delete asset (only if not assigned) |

#### Example – Create Asset

```bash
curl -X POST http://localhost:8080/asset/create \
-H "Content-Type: application/json" \
-d '{
  "name": "Dell Laptop",
  "purchaseDate": "2025-09-06",
  "notes": "16GB RAM, 512GB SSD",
  "categoryId": 1,
  "employeeId": 3,
  "status": "Assigned"
}'
```

#### Example – Assign Asset

```bash
curl -X PUT http://localhost:8080/asset/assign/1 \
-H "Content-Type: application/json" \
-d '{
  "employeeId": 3
}'
```

#### Example – Recover Asset

```bash
curl -X PUT http://localhost:8080/asset/recover/1
```

#### Example – Delete Asset

```bash
curl -X DELETE http://localhost:8080/asset/delete/1
```

---

## ⚖ Business Rules

* Asset can be **Assigned** to exactly one employee.
* Asset can be **Recovered** (status changes to `Recovered`, `assignTo` set to `null`).
* Asset **cannot be deleted** if its status is `Assigned`.
* Categories must exist before creating an asset.

---

## ▶️ Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/anoushka-10/asset-management.git
   ```

2. Build and run:

   ```bash
   ./mvnw spring-boot:run
   ```

3. Access API at:

   ```
   http://localhost:8080
   ```

4. H2 Database Console (for debugging):

   ```
   http://localhost:8080/h2-console
   ```

---

## ✅ Assignment Requirements Coverage

* [x] Add / Update / List Categories
* [x] Add / Update / List / Search Assets
* [x] Assign Asset to Employee
* [x] Recover Asset
* [x] Delete Asset with business rules
* [x] Employee pre-seeded in DB
* [x] H2 Database + Spring Boot + JPA

---

## 📖 Notes

* Used **DTOs** (`CategoryDTO`, `AssetDTO`, `EmployeeDTO`) for request/response mapping.
* Basic exception handling implemented (asset not found, deletion restriction, etc.).
* In production, `@ControllerAdvice` and custom exceptions would improve error handling.

---


