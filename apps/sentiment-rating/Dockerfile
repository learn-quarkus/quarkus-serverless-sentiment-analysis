# Stage 1: Build the application
FROM --platform=linux/amd64 registry.access.redhat.com/ubi9/openjdk-21:latest AS build

USER root

# Install Maven
RUN microdnf install -y maven && microdnf clean all

WORKDIR /app

# Copy pom.xml first
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Stage 2: Run the application
FROM --platform=linux/amd64 registry.access.redhat.com/ubi9/openjdk-21-runtime:latest

USER root

WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/quarkus-app/lib/ /app/lib/
COPY --from=build /app/target/quarkus-app/*.jar /app/
COPY --from=build /app/target/quarkus-app/app/ /app/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /app/quarkus/

# Set proper permissions and switch to non-root user
RUN chown -R 185:0 /app && chmod -R g+rwX /app

USER 185

# Set Java options for preview features and native access
ENV JAVA_OPTS="--enable-preview --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.vector"

# Expose Quarkus default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar quarkus-run.jar"]
