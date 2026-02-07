#!/bin/bash

# WealthTech Pro - Local Development Server
# This script starts a local HTTP server to host the WealthTech Pro website

PORT=8080
DIRECTORY="fintech-site"

echo "=========================================="
echo "  WealthTech Pro - Local Dev Server"
echo "=========================================="
echo ""
echo "Starting server on port $PORT..."
echo "Website will be available at: http://localhost:$PORT"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

# Check if Python 3 is available
if command -v python3 &> /dev/null
then
    cd "$DIRECTORY" && python3 -m http.server $PORT
# Check if Python 2 is available
elif command -v python &> /dev/null
then
    cd "$DIRECTORY" && python -m SimpleHTTPServer $PORT
else
    echo "Error: Python is not installed. Please install Python to run the local server."
    exit 1
fi
