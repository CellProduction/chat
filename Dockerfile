FROM openjdk:8
COPY build/libs/chat-fat-1.0-SNAPSHOT.jar chat.jar
EXPOSE $PORT $WS_PORT

CMD ["java",  "-jar", "-Dhttp.port=$PORT", "-Dws.port=$WS_PORT", "chat.jar"]