# 使用 Maven 构建镜像
FROM maven:3.8.1-openjdk-11 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 使用 OpenJDK 运行镜像
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/fund-estimate-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
