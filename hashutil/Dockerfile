FROM openjdk:17
COPY target/hashutil-0.0.1-SNAPSHOT.jar /home/hashutil-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/home/hashutil-0.0.1-SNAPSHOT.jar","-D java.util.concurrent.ForkJoinPool.common.parallelism=8"]

