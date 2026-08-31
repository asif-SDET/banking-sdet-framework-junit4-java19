@echo off
setlocal
call mvn clean test -Pui -Dheadless=false
if errorlevel 1 (
  echo.
  echo UI run failed. Check that Chrome is installed and review the FIRST Maven error.
  exit /b 1
)
echo.
echo UI test completed successfully.
