# BankCore 
> A production-inspired banking system built in pure Java No frameworks, no shortcuts.

Built as a deliberate exercise in software engineering fundamentals before moving to Spring Boot.
Every design decision in this project has a reason behind it.

---

## Why this project exists

Most Java tutorials teach you *what* to type. This project was built to understand **_why_**   encapsulation protects data, _**why**_ services shouldn't know about repositories, _**why**_ Optional exists, why checked exceptions force you to handle business failures.

No Spring Boot. Just pure Java and engineering thinking.

---

## Architecture
```
com.bankcore
├── model/          → domain objects (Account, Transaction)
├── interfaces/     → capabilities (Interestable, Taxable)  
├── exceptions/     → business exceptions (InsufficientFundsException)
├── repository/     → memory storage layer
├── service/        → business logic layer
└── BankCoreApplication.java → interactive console entry point
```

**Each layer has one responsibility and one only.**
The service layer doesn't store data. The repository layer doesn't apply business rules.

---

## Features

- Create **Savings** and **Checking** accounts with different behaviors
- **Deposit**, **withdraw**, **transfer** between accounts
- **Interest calculation** on savings accounts
- **Tax application** on savings accounts
- **Transaction history** per account with full audit trail
- **Overdraft support** with configurable limit on checking accounts
- **Transaction fees** per withdrawal on checking accounts
- Input validation with **3 attempt retry logic** on every operation

---

## Concepts demonstrated

| Concept | Where                                                            |
|---|------------------------------------------------------------------|
| Abstract classes | `Account`  shared base with abstract `withdraw()`                |
| Interfaces | `Interestable`, `Taxable`  capabilities not identity             |
| Polymorphism | `Account` references holding `SavingsAccount`/`CheckingAccount`  |
| Encapsulation | Private fields, validated setters, no direct balance access      |
| Generics | `Optional<Account>`, `List<Transaction>`, `Map<String, List<T>>` |
| Collections | `HashMap` for O(1) account lookup, `ArrayList` for history       |
| Streams & Lambdas | Transaction processing and filtering                             |
| Checked exceptions | `InsufficientFundsException`, `AccountNotFoundException`         |
| Optional | `findById()` returns `Optional<Account>`  no null returns        |
| Static | Auto generated account/transaction IDs, shared counters          |
| Dependency Injection | `BankService` receives `AccountRepository` via constructor       |

---

## Design decisions

**Why is `transfer` in `BankService` and not in `Account`?**
A transfer involves two accounts. An `Account` object should not know about other accounts  that violates Single Responsibility. The service layer orchestrates operations between objects.

**Why `Optional` instead of returning `null`?**
`null` is a silent failure. `Optional` forces the caller to handle the "not found" case explicitly.

**Why checked exceptions for `InsufficientFundsException`?**
Insufficient funds is an expected business scenario, not a crash. 

---

## How to run
```bash
git clone https://github.com/haytamRaba/bankCore.git
cd bankcore
mvn compile
mvn exec:java -Dexec.mainClass="com.bankcore.BankCoreApplication"
```

**Generate documentation:**
```bash
mvn javadoc:javadoc
# open target/site/apidocs/index.html
```

---


## Author

**Haytam Raba** — 4th year Software Engineering Student   
[LinkedIn](https://www.linkedin.com/in/haytam-raba-798663261) · [GitHub](https://github.com/haytamRaba)