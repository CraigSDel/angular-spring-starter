#!/bin/bash
# Quick fix script for Spring Security 6 backend compilation

echo "🔧 Backend Fix Script - Spring Security 6 Migration"
echo "=================================================="
echo ""

# Navigate to server directory
cd "$(dirname "$0")/server" || exit 1

# Kill any running Maven processes
echo "1️⃣ Stopping any running backend processes..."
pkill -f "mvnw" 2>/dev/null
pkill -f "spring-boot:run" 2>/dev/null
sleep 2
echo "   ✅ Processes stopped"
echo ""

# Clean build artifacts
echo "2️⃣ Cleaning Maven cache and build artifacts..."
rm -rf target/
./mvnw clean > /dev/null 2>&1
echo "   ✅ Build cache cleared"
echo ""

# Set Java 17
echo "3️⃣ Setting Java 17..."
export JAVA_HOME=/Users/craigstroberg/Library/Java/JavaVirtualMachines/corretto-17.0.12/Contents/Home
JAVA_VERSION=$($JAVA_HOME/bin/java -version 2>&1 | head -n 1)
echo "   ✅ Using: $JAVA_VERSION"
echo ""

# Compile
echo "4️⃣ Compiling application..."
./mvnw compile

if [ $? -eq 0 ]; then
    echo "   ✅ Compilation successful!"
    echo ""
    echo "5️⃣ Starting Spring Boot application..."
    echo "   (This will take 30-60 seconds)"
    echo ""
    ./mvnw spring-boot:run
else
    echo "   ❌ Compilation failed!"
    echo ""
    echo "Please check the error messages above."
    echo "Common issues:"
    echo "  - Ensure all files are saved"
    echo "  - Check Java version: java -version"
    echo "  - Review BACKEND_FIX_GUIDE.md for detailed help"
    exit 1
fi

