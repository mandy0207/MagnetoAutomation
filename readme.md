# 🧪 Test Automation Framework for Web Browsers

This Automation Framework supports **cross-browser testing** across major web browsers including **Chrome, Firefox, Edge, and Safari**. It is designed to execute tests both **sequentially and in parallel**, with robust reporting and failure-handling mechanisms.

# 🚫💻 By Default: All Tests Run in **Headless Mode**
---

## 🚀 Features

- ✅ **Cross-browser support**: Chrome, Firefox, Edge, Safari
- ⏱ **Parallel & Sequential execution** with TestNG
- 🔁 **Retry Analyzer** to rerun failed test cases automatically
- 🧭 **Journey-based execution** for grouped validations
- ❌ **Non-blocking failures** — continues execution despite individual test failures
- 📊 **Extent Reports** with detailed validations and screenshots
- 💼 **Modular Page Object Model (POM)**
- 🔧 Easy integration with CI/CD tools

---

## 🧰 Prerequisites

Make sure the following are installed and configured on your machine:

- 📦 **Java 17 / JDK 17**
- 🛠 **Lombok Plugin** installed and enabled in your IDE

---

▶️ Running the Tests
Execute the following command in terminal or command prompt:

2. Navigate to the root project directory:
cd your-repo-name


## Command to run test suite
 ```bash
mvn test -PMagnetoParallel -DbrowserName=chrome
```
💡 Replace edge with chrome, firefox, or safari as needed.


🧪 Test Automation Strategy
✅ Test cases are structured into 2 core automation journeys:

Order & Returns Form Validations — covering all combinations of input validations, error messages, and dropdown-based form logic.

Signup Form Validations — covering all edge cases, mandatory fields, invalid formats, and message validations.

Each journey combines a group of test cases to simulate user flows and increase coverage.

In case of any failure, the execution continues, and the status of each test case is individually logged in the report.

This strategy improves reliability, reduces execution time, and delivers full test coverage without interruptions.

🔁 Retry Analyzer
A custom Retry Analyzer is configured to re-execute failed test cases automatically to reduce flaky test impact. This ensures that transient or environment-specific issues do not affect the overall test result.

📸 Reports & Screenshots
📁 Reports are generated under /Extent Reports directory.

🖼 Failed test cases are captured with screenshots, embedded within the HTML Extent Report for easy debugging.





