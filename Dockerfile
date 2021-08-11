FROM openjdk:11.0.4-jre-slim
VOLUME /tmp
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*"]
#docker run -p 9000:9191 image:latest --entrypoint "java -cp app:app/lib/* -Dspring.profiles.active=development -Dspring.datasource.url=jdbc:postgresql://172.19.69.95:5434/postgres -Dspring.datasource.username=postgres -Dspring.datasource.password=pass@123 package.MainClass"
#docker run -d -p 7050:7050 microsvc/static1:latest -Dapp.env.servUrl1=http://192.168.0.111:7051/microsvc-static-2  com.microsvc.static1.Static1Application