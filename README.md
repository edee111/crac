# crac

A simple Spring Boot application for testing of checkpoint/restore JDK functionality.

See 
- https://docs.spring.io/spring-framework/reference/integration/checkpoint-restore.html
- https://github.com/CRaC/org.crac
- https://foojay.io/today/springboot-3-2-crac/


### How to try
```
sdk use java 21.0.2.crac-zulu
./gradlew bootJar
java -XX:CRaCCheckpointTo=/home/etomek/Desktop/checkpoint -Dspring.context.checkpoint=onRefresh -jar build/libs/crac-0.0.1-SNAPSHOT.jar
```

#### When persistence is initialized
```
2024-01-29T16:30:34.534+01:00 ERROR 1571676 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.context.ApplicationContextException: Failed to take CRaC checkpoint on refresh
        at org.springframework.context.support.DefaultLifecycleProcessor$CracDelegate.checkpointRestore(DefaultLifecycleProcessor.java:534) ~[spring-context-6.1.3.jar!/:6.1.3]
        at org.springframework.context.support.DefaultLifecycleProcessor.onRefresh(DefaultLifecycleProcessor.java:193) ~[spring-context-6.1.3.jar!/:6.1.3]
        at org.springframework.context.support.AbstractApplicationContext.finishRefresh(AbstractApplicationContext.java:978) ~[spring-context-6.1.3.jar!/:6.1.3]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:627) ~[spring-context-6.1.3.jar!/:6.1.3]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.2.2.jar!/:3.2.2]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) ~[spring-boot-3.2.2.jar!/:3.2.2]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456) ~[spring-boot-3.2.2.jar!/:3.2.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:334) ~[spring-boot-3.2.2.jar!/:3.2.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1354) ~[spring-boot-3.2.2.jar!/:3.2.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1343) ~[spring-boot-3.2.2.jar!/:3.2.2]
        at com.example.crac.CracApplicationKt.main(CracApplication.kt:13) ~[!/:0.0.1-SNAPSHOT]
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
        at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:91) ~[crac-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
        at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:53) ~[crac-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
        at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:58) ~[crac-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
Caused by: org.crac.CheckpointException: null
        at org.crac.Core$Compat.checkpointRestore(Core.java:144) ~[crac-1.4.0.jar!/:na]
        at org.crac.Core.checkpointRestore(Core.java:237) ~[crac-1.4.0.jar!/:na]
        at org.springframework.context.support.DefaultLifecycleProcessor$CracDelegate.checkpointRestore(DefaultLifecycleProcessor.java:528) ~[spring-context-6.1.3.jar!/:6.1.3]
        ... 15 common frames omitted
        Suppressed: jdk.internal.crac.mirror.impl.CheckpointOpenSocketException: Socket[addr=localhost/127.0.0.1,port=33306,localport=33156]
                at java.base/jdk.internal.crac.JDKSocketResourceBase.lambda$beforeCheckpoint$0(JDKSocketResourceBase.java:68) ~[na:na]
                at java.base/jdk.internal.crac.mirror.Core.checkpointRestore1(Core.java:169) ~[na:na]
                at java.base/jdk.internal.crac.mirror.Core.checkpointRestore(Core.java:286) ~[na:na]
                at java.base/jdk.internal.crac.mirror.Core.checkpointRestore(Core.java:265) ~[na:na]
                at jdk.crac/jdk.crac.Core.checkpointRestore(Core.java:72) ~[jdk.crac:na]
                at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
                at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
                at org.crac.Core$Compat.checkpointRestore(Core.java:141) ~[crac-1.4.0.jar!/:na]
                ... 17 common frames omitted
        ...
```
- so it looks like the SB supports the checkpoint/restore but we need to implement `org.crac.Resource` ourselves in the current SB 3.2.2

#### Run without persistence

```
Caused by: org.crac.CheckpointException: null
        at org.crac.Core$Compat.checkpointRestore(Core.java:144) ~[crac-1.4.0.jar!/:na]
        at org.crac.Core.checkpointRestore(Core.java:237) ~[crac-1.4.0.jar!/:na]
        at org.springframework.context.support.DefaultLifecycleProcessor$CracDelegate.checkpointRestore(DefaultLifecycleProcessor.java:528) ~[spring-context-6.1.3.jar!/:6.1.3]
        ... 15 common frames omitted
        Suppressed: java.lang.RuntimeException: Native checkpoint failed.

```
- why?