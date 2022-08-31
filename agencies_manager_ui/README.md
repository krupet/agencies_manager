# AgenciesManagerUi

# how tor un the project?

```shell
$ ng serve
```

And got to [http://localhost:4200/](http://localhost:4200/)

To build image run next command:

```shell
$ docker build -t agencies-manager-ui-image .
$ docker run --name agencies-manager-ui-container -d -p 8888:80 agencies-manager-ui-image
```
