# CookNow Server

The CookNow Server is the server-side application for the iOS [CookNow](https://github.com/Tobisaninfo/CookNow-iOS) App.

## Installation / Requirements

- CookNow Server requires Java 8 to run
- TO build CookNow Server you need maven
- Create a java keystore fot https connections
- MySQL Database
- File Storage for resources
- To run the server use: ```java -jar CookNowServer.jar```

## Java Keystore

```
openssl pkcs12 -export -in /etc/letsencrypt/live/$1/fullchain.pem -inkey /etc/letsencrypt/live/$1/privkey.pem -out pkcs.p12 -name ALIAS -passout pass:PASSWORD
keytool -importkeystore -deststorepass PASSWORD -destkeypass PASSWORD -destkeystore keystore.jks -srckeystore pkcs.p12 -srcstoretype PKCS12 -srcstorepass PASSWORD -alias ALIAS
```

## Config File
```
download_folder=/PATH/TO/RECOUESES
db_host=localhost
keystorePassword=password
db_database=CookNow
db_username=user
db_password=password
db_port=3306
```

The ```download_folder``` contains the resources for the ingredients, recipes and supermarkets.

Types of resources
- ingredients: Images named by the id in the database
- recipes: Images named by the id in the database
- logo of supermarkets: Images named by the id in the database

## Libraries
- [Spark](https://github.com/perwendel/spark)
- [gson](https://github.com/google/gson)
- [unirest-java](https://github.com/Kong/unirest-java)
- [scala-scraper](https://github.com/ruippeixotog/scala-scraper)
- [string-similarity](https://github.com/rrice/java-string-similarity)
- mysql-connector-java