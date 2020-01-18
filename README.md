## How to run:

1. Launch rabbitmq in console: 
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

2. Open project in Idea and run: <br>
    - NewTask.java
    - Worker.java
    <br>or
    - Send.java
    - Receive.java