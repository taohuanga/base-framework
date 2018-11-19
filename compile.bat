@echo off

rem -- Lipsion
color 1f
:menu
echo   __________________________________________________
echo  ^|                                               ^|
echo  ^|             Maven  -  控制面板                ^|
echo  ^|                                               ^|
echo  ^|  1 - clean package -D...skip=true             ^|
echo  ^|  2 - package -D...skip=true                   ^|
echo  ^|  3 - mvn install                              ^|
echo  ^|  4 - 编译部署包                               ^|
echo  ^|  5 - mvn deploy                               ^|
echo  ^|                                               ^|
echo  ^|_______________________________________________^|
:input
set /p input=-^> 请选择: 

if "%input%"== "1" goto clean-package
if "%input%"== "2" goto package
if "%input%"== "3" goto install
if "%input%"== "4" goto deploy-zip
if "%input%"== "5" goto deploy
goto end

:clean-package
echo  # 消除编译并打包 #
mvn clean package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:package
echo  # 打包 #
mvn package -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:install
echo  # 安装本地仓库 #
mvn install -Dmaven.test.skip=true &&pause&&cls&& call compile.bat

:deploy-zip
cls && call deploy.bat

:deploy
echo  # 发布 #
mvn deploy -Dmaven.test.skip=true &&pause&&cls&& call compile.bat


rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo 当前目录是:%dao_dir%
rem &&pause 
:end
echo 结束
prompt
popd