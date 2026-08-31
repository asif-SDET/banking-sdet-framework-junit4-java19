@echo off
setlocal

echo ============================================================
echo Banking SDET Framework - Windows Compatibility Check
echo ============================================================
echo.

echo [1/4] Java version
java -version
if errorlevel 1 goto :fail

echo.
echo [2/4] Maven version
mvn -v
if errorlevel 1 goto :fail

echo.
echo [3/4] Clean compile and run non-UI tests
call mvn -U clean test
if errorlevel 1 goto :fail

echo.
echo [4/4] SUCCESS
echo Core framework, JUnit 4, Cucumber, API and JDBC tests completed.
echo Next optional command: mvn clean test -Pui -Dheadless=false
exit /b 0

:fail
echo.
echo ============================================================
echo FAILED - scroll up to the FIRST [ERROR] message.
echo Copy that first error when asking for help.
echo ============================================================
exit /b 1
