#  Grocery Management System
 
A full-stack **Grocery Management System** built using **Spring Boot (Backend)** and **React.js (Frontend)**.  

This project demonstrates a real-world e-commerce workflow with authentication, product management, and cart functionality.

---

##  Features

  
###  User Features
- User registration and login system
- JWT-based authentication
- Browse all products
- Search and filter products
- Add/remove products from cart
- Place orders (basic flow)

###  Admin Features
- Secure admin login
- Add new products
- Update product details
- Delete products
- Manage inventory
- View users and orders

---

##  Tech Stack

### Frontend
- React.js
- JavaScript (ES6+)
- HTML5
- CSS3

### Backend
- Spring Boot
- Spring Security
- JWT Authentication
- RESTful APIs

### Database
- MySQL

---

## 📁 Project Structure


Grocery_Management_System/
│
├── GroceryManagement-main/ (Frontend - React.js)
├── Backend/ (Spring Boot Backend)
└── README.md


---

## ⚙️ Installation & Setup

### 🔹 Backend Setup (Spring Boot)

1. Open backend project in IntelliJ / Eclipse
2. Configure MySQL database in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/grocery_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Run Spring Boot application:
Run GroceryApplication.java
🔹 Frontend Setup (React)
cd GroceryManagement-main
npm install
npm start
 Authentication Flow
JWT token generated on login
Token stored in local storage
Secured API endpoints using Spring Security
Role-based access (Admin/User)
📸 Screenshots

<img width="1340" height="635" alt="Screenshot 2026-05-03 125316" src="https://github.com/user-attachments/assets/2161212b-8eba-4b0c-8d4a-8b361d9fd383" />

<img width="1346" height="625" alt="Screenshot 2026-05-03 125326" src="https://github.com/user-attachments/assets/4b0d1f55-b44c-4d6e-a097-858292630355" />
<img width="1344" height="643" alt="Screenshot 2026-05-03 140440" src="https://github.com/user-attachments/assets/1379a882-5cbb-46ce-a276-3b8726ae2c32" />


<img width="1335" height="648" alt="Screenshot 2026-05-03 141502" src="https://github.com/user-attachments/assets/c79188cd-251e-4e8d-96a9-53cce58d876c" />

Login Page
User Dashboard
Product Listing Page
Cart Page
Admin Panel

 Future Improvements
Payment gateway integration
Order tracking system
Email notifications
Responsive UI improvements
Docker deployment

Deployment on cloud (AWS/Render)
 Author

hrtechiecode

GitHub: https://github.com/hrtechiecode


 Project Highlights

This project demonstrates:

Full-stack development skills
REST API integration
Authentication & authorization
Real-world e-commerce logic
Clean project architecture

## 🚀 Purpose

This project was developed as a **full-stack learning and portfolio project** to strengthen my skills in real-world application development.

It demonstrates:
- Full-stack development using React.js and Spring Boot
- REST API design and integration
- Authentication and authorization using JWT
- CRUD operations and database management
- Building a real-world e-commerce (grocery) system

