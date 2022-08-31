# AgenciesManagerServer

# how tor un the project?

Launch MongoDb instance and export its connection url: 

```shell
$ export SPRING_DATA_MONGODB_URI=mongodb://mongo:mongo@mongodb
$ ./gradlew bootRun
```

And got to [http://localhost:8080/](http://localhost:8080/)

To build image run next command:

```shell
$ docker build -t agencies-manager-server-image .
$ docker run --name agencies-manager-server-container -d -p 8080:8080 agencies-manager-server-image
```
