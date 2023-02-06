FROM openjdk:17
COPY target/hashutil-1.0-SNAPSHOT-exec.jar /home/hashutil-1.0-SNAPSHOT-exec.jar
CMD ["java","-jar","/home/hashutil-1.0-SNAPSHOT-exec.jar","-D java.util.concurrent.ForkJoinPool.common.parallelism=16"]

