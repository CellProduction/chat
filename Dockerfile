FROM openjdk:8
ADD build/libs/chat-fat-1.0-SNAPSHOT.jar chat.jar
EXPOSE 8088 8089

ENV WS_PORT 8088
ENV REST_PORT 8089

CMD ["java -jar chat.jar"]