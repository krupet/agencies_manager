# Agencies manager

This is a simple web page (for now UI is very basic) that manges a set of Agencies,
build using Angular and Spring Boot.

## How to run the project?

Got the th `local` folder and run `docker compose` to launch project locally.
Unfortunately, as for now we need to build all components from scratch 
(it will take some time as we need to pull all dependencies and build projects). 

```shell
$ cd local
$ docker-compose up
```

Then got to [http://localhost:8888/](http://localhost:8888/).

> NOTICE: Sometimes after the new build of images Spring boot service struggles to connect to the database.
> in this case please hit `Ctrl + C` and run `docker-compose up` command again.
