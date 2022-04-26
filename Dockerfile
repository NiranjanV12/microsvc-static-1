FROM openjdk:11.0.4-jdk-slim
VOLUME /tmp
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
#COPY ${DEPENDENCY}/jmx_prometheus_javaagent-0.16.1.jar /app
#COPY ${DEPENDENCY}/config.yaml /app
RUN apt-get update -y && apt-get install -y lsof tar build-essential
ADD https://github.com/jemalloc/jemalloc/releases/download/5.2.1/jemalloc-5.2.1.tar.bz2 .
RUN tar -xvf jemalloc-5.2.1.tar.bz2 
WORKDIR /jemalloc-5.2.1
RUN ./configure && make && make install
ENV LD_PRELOAD=$LD_PRELOAD:/jemalloc-5.2.1/lib/libjemalloc.so.2
WORKDIR /
ENTRYPOINT ["java","-cp","app:app/lib/*"]
#docker run -p 9000:9191 image:latest --entrypoint "java -cp app:app/lib/* -Dspring.profiles.active=development -Dspring.datasource.url=jdbc:postgresql://172.19.69.95:5434/postgres -Dspring.datasource.username=postgres -Dspring.datasource.password=pass@123 package.MainClass"
#docker run -d -p 7050:7050 microsvc/static1:latest -Dapp.env.servUrl1=http://192.168.0.111:7051/microsvc-static-2  com.microsvc.static1.Static1Application
#docker run -d -p 7050:7050 -p 8080:8080 microsvc/microsvc-static-1:latest -javaagent:./app/jmx_prometheus_javaagent-0.16.1.jar=8080:/app/config.yaml com.microsvc.static1.Static1Application
#docker run -d -p 7050:7050 microsvc/microsvc-static-1:latest com.microsvc.static1.Static1Application
#docker run -d -p 7050:7050 microsvc/microsvc-static-1:latest -Xmx1024m -XX:+PrintFlagsFinal -XX:NativeMemoryTracking=summary com.microsvc.static1.Static1Application
