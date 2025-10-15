#!/bin/bash

# Build script for Crazy Eights game
echo "Building Crazy Eights game..."

# Clean up old compiled files
echo "Cleaning old files..."
rm -f *.class
rm -f CrazyEights.jar

# Compile all Java files
echo "Compiling Java files..."
javac *.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

# Create manifest file
echo "Creating manifest..."
echo "Main-Class: Eights" > manifest.txt

# Create JAR file
echo "Creating JAR file..."
jar cfm CrazyEights.jar manifest.txt *.class

# Clean up manifest
rm manifest.txt

# Check if JAR was created successfully
if [ -f "CrazyEights.jar" ]; then
    echo "Build successful!"
    echo "To run the game, use: java -jar CrazyEights.jar"
else
    echo "JAR creation failed!"
    exit 1
fi