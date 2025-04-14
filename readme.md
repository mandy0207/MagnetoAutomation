# 🧪 Test Automation Framework for Web Browsers

This Automation Framework supports **cross-browser testing** across major web browsers including **Chrome, Firefox, Edge, and Safari**. It is designed to execute tests both **sequentially and in parallel**, with robust reporting and failure-handling mechanisms.

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
Test cases are grouped and executed as Journeys, each combining multiple scenarios under a single flow.

This reduces execution time and enhances test coverage.

Failures within a journey do not stop the test — all test steps continue executing with their respective validations recorded.

🔁 Retry Analyzer
A custom Retry Analyzer is configured to re-execute failed test cases automatically to reduce flaky test impact. This ensures that transient or environment-specific issues do not affect the overall test result.

📸 Reports & Screenshots
📁 Reports are generated under /test-output/ directory.

🖼 Failed test cases are captured with screenshots, embedded within the HTML Extent Report for easy debugging.





