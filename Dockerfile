# Stage 1: Compile the Java code
FROM bellsoft/liberica-openjdk-alpine-musl:11 AS build
WORKDIR /usr/src/myapp
# Copy the src directory (containing keyValueStore) into the container
COPY src/keyValueStore keyValueStore
# Compile the Java files in the correct package
RUN javac keyValueStore/server/*.java keyValueStore/client/*.java

# Stage 2: Run the compiled Java program
FROM bellsoft/liberica-openjdk-alpine-musl:11
WORKDIR /usr/app
# Copy the compiled class files from the build stage
COPY --from=build /usr/src/myapp /usr/app
EXPOSE 1111/tcp
EXPOSE 5555/udp
# Use the fully qualified name for the ServerApp class
CMD ["java", "keyValueStore.server.ServerApp", "1111", "5555"]
