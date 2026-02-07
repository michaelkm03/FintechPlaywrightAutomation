# PowerShell wrapper to run the project's start-server.bat from the repository root
# Usage: .\start-server.ps1  (or `.










exit $LASTEXITCODE& cmd.exe /c ""$batPath""# Run the batch via cmd.exe so environment behaves like running in cmd}    exit 1    Write-Error "start-server.bat not found at $batPath"if (-not (Test-Path $batPath)) {$batPath = Join-Path $scriptDir 'start-server.bat'$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definitionun-start-server` in PowerShell)