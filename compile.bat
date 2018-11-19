@echo off

rem -- Lipsion
color 1f
:menu
echo   __________________________________________________
echo  ^|                                               ^|
echo  ^|             Maven  -  �������                ^|
echo  ^|                                               ^|
echo  ^|  1 - clean package -D...skip=true             ^|
echo  ^|  2 - package -D...skip=true                   ^|
echo  ^|  3 - mvn install                              ^|
echo  ^|  4 - ���벿���                               ^|
echo  ^|  5 - mvn deploy                               ^|
echo  ^|                                               ^|
echo  ^|_______________________________________________^|
:input
set /p input=-^> ��ѡ��: 

if "%input%"== "1" goto clean-package
if "%input%"== "2" goto package
if "%input%"== "3" goto install
if "%input%"== "4" goto deploy-zip
if "%input%"== "5" goto deploy
goto end

:clean-package
echo  # �������벢��� #
mvn clean package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:package
echo  # ��� #
mvn package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:install
echo  # ��װ���زֿ� #
mvn install -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:deploy-zip
cls && call deploy.bat

:deploy
echo  # ���� #
mvn deploy -Dmaven.test.skip=true &&pause&&cls&& call compile.bat


rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo ��ǰĿ¼��:%dao_dir%
rem &&pause 
:end
echo ����
prompt
popd