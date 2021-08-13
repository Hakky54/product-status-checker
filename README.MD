# Product status checker
Tired of manually checking if a product is available? This application will take care of that.

**Minimum requirements:**
1. Java 11
2. Maven 3.5.0
3. A terminal
4. [Phantom JS Driver](https://phantomjs.org/download.html)

## Usage
During the initial run execute only once to prepare the program:
```text
mvn clean install
```

Afterwords check the product status with the following command:
```text
mvn exec:java -Dexec.args="https://www.decathlon.nl/p/halterset-krachttraining-20-kg-met-schroefdraad/_/R-p-7449?mc=8018574&c=ZWART stock-infos 'Online Beschikbaar' /path/to/phantomjs 30"
```
`-Dexec.args` expects 5 arguments:

0. URL
1. HTML element by class name of the product status indicator
2. Text to contain in the HTML element when the product is available
3. Absolute path to phantom js driver
4. Retry after n amount of time in seconds

## Demo
![alt text](https://github.com/Hakky54/product-status-checker/blob/master/images/demo.gif)