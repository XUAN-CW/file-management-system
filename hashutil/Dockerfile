FROM openjdk:17
COPY target/hashutil-1.0-SNAPSHOT.jar /home/hashutil-1.0-SNAPSHOT.jar
CMD ["java","-jar","/home/hashutil-1.0-SNAPSHOT.jar","-D java.util.concurrent.ForkJoinPool.common.parallelism=16"]

