# BoreDorm Management System

BoreDorm is a modern, comprehensive dormitory management software system designed to streamline administrative operations and elevate the tenant experience. It provides features for administrators to manage rooms, monitor tenants, track billing, and review logs, while offering tenants a dedicated dashboard to view their lease details, room info, and submit maintenance requests.

---

## 📊 System UML Diagrams

The system architecture and behavioral flows are modeled below using standardized UML diagrams (Class, Use Case, Activity, and Sequence Diagrams). All diagrams are formatted with **straight, orthogonal lines** and standard UML notation. 

Click **"View Diagram"** under any section to expand and inspect the diagram.

---

### 1. 📑 Class Diagram (System Architecture & Design Patterns)

<details>
<summary><b>🔍 Click to View Class Diagram</b></summary>

<br>

```mermaid
%%{init: {'theme': 'dark', 'flowchart': {'curve': 'straight'}, 'class': {'curve': 'straight'}}}%%
classDiagram
    direction TB

    class HelloApplication {
        +start(Stage stage) void
    }

    class LoginController {
        +handleLogin(ActionEvent event) void
    }

    class DashboardController {
        +initialize(URL url, ResourceBundle rb) void
        +handleSignOut(ActionEvent event) void
    }

    class TenantDashboardController {
        +initialize() void
        +handleSubmitRequest(ActionEvent event) void
        +handleLogout(ActionEvent event) void
    }

    class MaintenanceController {
        +initialize(URL url, ResourceBundle rb) void
        +handleUpdateStatus(ActionEvent event) void
    }

    class DormitoryFacade {
        -DormitoryFacade instance$
        -UserDAO userDAO
        -MaintenanceRequestDAO maintenanceDAO
        +getInstance() DormitoryFacade$
        +authenticate(String username, String password) User
        +registerTenant(String username, String rawPassword) boolean
        +submitMaintenanceRequest(MaintenanceRequest request) boolean
        +getMyMaintenanceRequests() List~MaintenanceRequest~
        +getAllMaintenanceRequests() List~MaintenanceRequest~
        +updateMaintenanceStatus(int requestId, String newStatus, String staff) boolean
        +logout() void
        +isSessionActive() boolean
        +getActiveUser() User
        +getRoleAccessContext() RoleAccessContext
    }

    class RoleAccessContext {
        -RoleAccessStrategy strategy
        +setRole(String role) void
        +applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) void
    }

    class RoleAccessStrategy {
        <<interface>>
        +canAccessAdminFeatures() boolean
        +canModifyTenants() boolean
        +getWelcomeMessage(String username) String
        +applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) void
    }

    class AdminAccessStrategy {
        +canAccessAdminFeatures() boolean
        +canModifyTenants() boolean
        +getWelcomeMessage(String username) String
        +applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) void
    }

    class TenantAccessStrategy {
        +canAccessAdminFeatures() boolean
        +canModifyTenants() boolean
        +getWelcomeMessage(String username) String
        +applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) void
    }

    class ISessionManager {
        <<interface>>
        +saveSession(User user) void
        +loadSession() User
        +isSessionActive() boolean
        +clearSession() void
    }

    class SessionManager {
        -String SESSION_FILE$
        -SessionManager instance$
        +getInstance() SessionManager$
        +saveSession(User user) void
        +loadSession() User
        +isSessionActive() boolean
        +clearSession() void
    }

    class UserDAO {
        <<interface>>
        +getUserByUsername(String username) User
        +createUser(User user) boolean
        +getAllUsers() List~User~
    }

    class UserDAOImpl {
        +getUserByUsername(String username) User
        +createUser(User user) boolean
        +getAllUsers() List~User~
    }

    class MaintenanceRequestDAO {
        <<interface>>
        +submitRequest(MaintenanceRequest request) boolean
        +getRequestsByUserId(int tenantUserId) List~MaintenanceRequest~
        +getAllRequests() List~MaintenanceRequest~
        +updateRequestStatus(int requestId, String status, String staff) boolean
    }

    class MaintenanceRequestDAOImpl {
        +submitRequest(MaintenanceRequest request) boolean
        +getRequestsByUserId(int tenantUserId) List~MaintenanceRequest~
        +getAllRequests() List~MaintenanceRequest~
        +updateRequestStatus(int requestId, String status, String staff) boolean
    }

    class UserFactory {
        +createTenant(String username, String hashedPassword) User$
        +createAdmin(String username, String hashedPassword) User$
        +createUser(int userId, String username, String hashedPassword, String role, String roomNumber, String leaseStatus) User$
    }

    class Serializable {
        <<interface>>
    }

    class User {
        -int userId
        -String username
        -String password
        -String role
        -String roomNumber
        -String leaseStatus
    }

    class MaintenanceRequest {
        -int requestId
        -int tenantUserId
        -String tenantUsername
        -String roomNumber
        -String issueType
        -String description
        -String status
        -String dateFiled
        -String assignedStaff
    }

    HelloApplication --> DormitoryFacade
    LoginController --> DormitoryFacade
    DashboardController --> DormitoryFacade
    TenantDashboardController --> DormitoryFacade
    MaintenanceController --> DormitoryFacade

    DormitoryFacade --> SessionManager
    DormitoryFacade --> UserDAO
    DormitoryFacade --> MaintenanceRequestDAO
    DormitoryFacade --> UserFactory
    DormitoryFacade --> RoleAccessContext

    RoleAccessContext o-- RoleAccessStrategy
    RoleAccessStrategy <|.. AdminAccessStrategy
    RoleAccessStrategy <|.. TenantAccessStrategy

    ISessionManager <|.. SessionManager
    UserDAO <|.. UserDAOImpl
    MaintenanceRequestDAO <|.. MaintenanceRequestDAOImpl

    UserFactory ..> User : Creates
    Serializable <|.. User
    Serializable <|.. MaintenanceRequest
```

</details>

---

### 2. 🎯 Use Case Diagram (System Interactions & Boundaries)

<details>
<summary><b>🔍 Click to View Use Case Diagram</b></summary>

<br>

```mermaid
%%{init: {'theme': 'dark', 'flowchart': {'curve': 'straight'}}}%%
flowchart LR
    subgraph Actors["👥 System Actors"]
        Admin["👨‍💼 Administrator"]
        Tenant["👤 Tenant User"]
    end

    subgraph SystemBoundary["🏠 BoreDorm System Boundary"]
        UC1(("UC-1: Authenticate / Log In"))
        UC2(("UC-2: Register Account"))
        UC3(("UC-3: Serialize session.dat"))
        UC4(("UC-4: Manage Rooms & Rates"))
        UC5(("UC-5: Manage Tenants & Leases"))
        UC6(("UC-6: Record Invoices & Payments"))
        UC7(("UC-7: Audit Activity Logs"))
        UC8(("UC-8: View Tenant Profile"))
        UC9(("UC-9: Submit Maintenance Request"))
        UC10(("UC-10: Review & Resolve Maintenance"))
        UC11(("UC-11: Log Out & Delete Session"))
    end

    Admin ---> UC1
    Admin ---> UC2
    Admin ---> UC4
    Admin ---> UC5
    Admin ---> UC6
    Admin ---> UC7
    Admin ---> UC10
    Admin ---> UC11

    Tenant ---> UC1
    Tenant ---> UC2
    Tenant ---> UC8
    Tenant ---> UC9
    Tenant ---> UC11

    UC1 -.->|Includes| UC3
    UC11 -.->|Deletes| UC3
```

</details>

---

### 3. 🔄 Activity Diagram (Authentication, Session & Maintenance Flow)

<details>
<summary><b>🔍 Click to View Activity Diagram</b></summary>

<br>

```mermaid
%%{init: {'theme': 'dark', 'flowchart': {'curve': 'straight'}}}%%
flowchart TD
    Start([Start Application]) ---> AppLaunch[Launch HelloApplication]
    AppLaunch ---> CheckSession{Is session.dat Present?}

    CheckSession --->|Yes| LoadSession[Deserialize session.dat via DormitoryFacade]
    LoadSession ---> CheckRole{User Role?}
    CheckRole --->|Admin| RouteAdmin[Route to Dashboard.fxml]
    CheckRole --->|Tenant| RouteTenant[Route to Tenant_Dashboard.fxml]

    CheckSession --->|No| ShowLogin[Display Login Screen]
    ShowLogin ---> UserInput[User enters Username & Password]
    UserInput ---> SubmitLogin[Click Sign In / Press Enter]
    SubmitLogin ---> AuthFacade[DormitoryFacade.authenticate]
    
    AuthFacade ---> FetchDB[Query UserDAO & Validate BCrypt]
    FetchDB ---> Valid{Credentials Valid?}

    Valid --->|No| DisplayError[Show Error Alert] ---> ShowLogin
    Valid --->|Yes| SaveSession[Serialize User Object to session.dat]
    SaveSession ---> ResolveStrategy[Initialize RoleAccessContext Strategy]
    ResolveStrategy ---> RouteDashboard[Navigate to Dashboard View]

    RouteAdmin ---> AdminAction[Manage Rooms, Billing & Update Maintenance Requests]
    RouteTenant ---> TenantAction[View Profile & Submit Maintenance Request]

    AdminAction ---> UserLogout{Click Sign Out?}
    TenantAction ---> UserLogout

    UserLogout --->|No| MainLoop[Continue Operations]
    UserLogout --->|Yes| CallLogout[DormitoryFacade.logout]
    CallLogout ---> DeleteFile[Delete session.dat from disk]
    DeleteFile ---> RedirectLogin[Redirect to Login Screen]
    RedirectLogin ---> End([End Session])
```

</details>

---

### 4. ⏱️ Sequence Diagram (User Login & Session Creation Flow)

<details>
<summary><b>🔍 Click to View Sequence Diagram</b></summary>

<br>

```mermaid
sequenceDiagram
    autonumber
    actor User as User / Admin
    participant LC as LoginController
    participant DF as DormitoryFacade
    participant DAO as UserDAOImpl
    participant BC as BCrypt
    participant SM as SessionManager
    participant NV as NavigationUtil

    User->>LC: Enter Username & Password + Click "Sign In"
    LC->>DF: authenticate(username, password)
    DF->>DAO: getUserByUsername(username)
    DAO-->>DF: Return User Object
    DF->>BC: checkpw(password, user.getPassword())
    BC-->>DF: Return true (Valid Password)
    DF->>SM: saveSession(user)
    SM->>SM: Serialize user -> write to session.dat
    SM-->>DF: Session Saved Notification
    DF-->>LC: Return Authenticated User Object
    LC->>NV: navigateTo(event, dashboard.fxml)
    NV-->>User: Display Dashboard View
```

</details>

---

## 🌟 Major Features

1. **User Authentication & Authorization**: Role-based access control (RBAC) separating Admin and Tenant permissions.
2. **Interactive Room Directory**: Graphical and searchable view of dormitory rooms grouped by floors, tracking room types, rates, capacity, and current availability status.
3. **Tenant & Lease Tracking**: Managed list of all active tenants, room numbers, and lease status.
4. **Billing & Invoice Logging**: Easy entry of tenant invoices and payment posting via multiple channels (GCash, Bank Transfer, Cash, Credit Card).
5. **Tenant ↔ Admin Maintenance System**: Tenants can log into their portal to submit maintenance requests (plumbing, electrical, AC, etc.) with real-time status updates from admins.
6. **Real-time Activity Logs**: Live dashboard feed logging system audits and check-in activities.
7. **Tenant Portal**: Dedicated interface for occupants to verify their registered room profile and manage maintenance issues.

---

## 📐 Software Design Patterns Implemented

The system incorporates **Creational**, **Structural**, and **Behavioral** software design patterns to achieve clean architecture, high maintainability, and loose coupling.

### 1. 🏭 Creational Pattern: Factory Method Pattern
* **Class Involved**: `UserFactory` (`com.boredom.boredorm.Factory.UserFactory`)
* **Implementation**: Encapsulates the instantiation of `User` objects across different user roles (`Admin` vs `Tenant`). Provides specialized static factory methods like `createTenant()` and `createAdmin()` to enforce default values (e.g., initial `"Unassigned"` room numbers and `"Pending"` lease statuses for new tenants).
* **Benefits**: Centralizes object creation logic. Controllers do not need to hardcode constructor default parameters, preventing duplicate initialization logic.

### 2. 🏛️ Structural Pattern: Facade Pattern
* **Class Involved**: `DormitoryFacade` (`com.boredom.boredorm.Facade.DormitoryFacade`)
* **Implementation**: Provides a unified, high-level interface to the complex subsystem of DAOs (`UserDAO`, `MaintenanceRequestDAO`), Password Hashing (`BCrypt`), Session Serialization (`SessionManager`), and Permission Strategies (`RoleAccessContext`).
* **Benefits**: Simplifies controller implementations. `LoginController`, `RegisterController`, and `TenantDashboardController` interact with a single `DormitoryFacade.getInstance()` method rather than orchestrating multi-step database and security calls manually.

### 3. 🎯 Behavioral Pattern: Strategy Pattern
* **Classes & Interfaces Involved**:
  * Interface: `RoleAccessStrategy` (`com.boredom.boredorm.Strategy.RoleAccessStrategy`)
  * Concrete Strategies: `AdminAccessStrategy` and `TenantAccessStrategy`
  * Context: `RoleAccessContext` (`com.boredom.boredorm.Strategy.RoleAccessContext`)
* **Implementation**: Encapsulates role-based access control algorithms and UI permission rules. The `RoleAccessContext` dynamically instantiates and delegates access control checks to either `AdminAccessStrategy` or `TenantAccessStrategy` based on the logged-in user's role.
* **Benefits**: Eliminates monolithic `if-else` conditional chains for permission checking across views. New roles (e.g., `StaffAccessStrategy`) can be introduced without modifying existing controller logic.

---

## 💾 Java Serialization Session Management

To validate and maintain session state across the JavaFX application, we implemented a persistent session management system using **Java Object Serialization**:

* **Session File Creation**: Upon a successful login, the user's `User` model (which implements `java.io.Serializable`) is serialized and written to a file named `session.dat` in the user's home directory.
* **Session Persistence**: While navigating through the application's screens, the controllers load and deserialize `session.dat` to confirm authentication and retrieve role/profile details dynamically.
* **Session Termination (Logout)**: Clicking the sign-out option triggers the automatic deletion of the physical `session.dat` file from the disk via `DormitoryFacade`, clearing the active session and redirecting the user back to the login screen.
* **Launch Security**: During application startup, `HelloApplication` uses `DormitoryFacade` to scan for `session.dat`. If a valid session file is found, it automatically bypasses the login screen and routes the user directly to their respective dashboard interface.

---

## 🏗️ SOLID Design Principles Applied

### 1. Single Responsibility Principle (SRP)
* **Classes Involved**: `SessionManager` (`com.boredom.boredorm.SessionManaging`) and `User` (`com.boredom.boredorm.Models`).
* **Implementation**: `SessionManager` is solely responsible for the lifecycle of the session file (`session.dat`) on disk (creating, reading, verifying, and deleting it). It does not handle database querying or page navigation. The `User` class is a pure data carrier.
* **Benefit**: High maintainability. If the session storage format changes (e.g., from local file to database or encrypted registry), only the `SessionManager` code is modified, without breaking user models or database access logic.

### 2. Dependency Inversion Principle (DIP)
* **Classes & Interfaces Involved**: `ISessionManager` / `UserDAO` / `MaintenanceRequestDAO` interfaces and their concrete implementations.
* **Implementation**: Controllers interact with data access and session mechanisms through interfaces rather than directly instantiating or depending on concrete implementations.
* **Benefit**: Weak coupling. This separates presentation concerns from data layer mechanisms.

---

## 🔑 Test Accounts (for Sir Serato)

Use these credentials to test the system:

### 👤 Admin Access
* **Username**: `JayVinceAdmin`
* **Password**: `Serato.123456`

### 👥 Tenant Access
* **Username**: `JayVinceUser`
* **Password**: `User.123456`

---
*Developed as a Capstone Project for Software Engineering and Architecture.*
