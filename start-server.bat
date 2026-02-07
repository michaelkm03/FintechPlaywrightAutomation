@echo off
REM WealthTech Pro - Local Development Server (Windows)
REM This script starts a local HTTP server to host the WealthTech Pro website

SET PORT=8080
SET DIRECTORY=fintech-site

echo ==========================================
echo   WealthTech Pro - Local Dev Server
echo ==========================================
echo.
echo Starting server on port %PORT%...
echo Website will be available at: http://localhost:%PORT%
echo.
echo Press Ctrl+C to stop the server
echo.

cd %DIRECTORY%

REM Check if Python 3 is available
python --version >nul 2>&1
if %errorlevel% equ 0 (
    python -m http.server %PORT%
) else (
    echo Error: Python is not installed. Please install Python to run the local server.
    pause
    exit /b 1
)
