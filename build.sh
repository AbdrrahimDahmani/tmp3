#!/bin/bash

echo "Building Banking Microservices..."

# Build all services
echo "Building Maven project..."
mvn clean package -DskipTests

# Build Docker images
echo "Building Docker images..."
docker-compose build

echo "Build completed successfully!"
echo ""
echo "To run the services, execute: docker-compose up -d"
echo "To stop the services, execute: docker-compose down"
