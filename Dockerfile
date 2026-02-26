# ===============================
# Stage 1: Build Stage
# ===============================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

# Copy pom.xml and cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests


# ===============================
# Stage 2: Runtime Stage
# ===============================
FROM eclipse-temurin:17-jre-alpine

# Install timezone data (important for production logs)
RUN apk add --no-cache tzdata

# Set timezone (India)
ENV TZ=Asia/Kolkata

# Create app user
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Create directory for uploaded images
RUN mkdir -p /app/images

# Copy jar from build stage
COPY --from=build /build/target/CollegeLibraryManagement-0.0.1-SNAPSHOT.jar app.jar

# Change ownership
RUN chown -R spring:spring /app

USER spring

# JVM Production Settings
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]