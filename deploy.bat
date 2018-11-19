@echo off

rem -- Lipsion
color 1f
:menu
echo   ________________________________________________________________
echo  ^|                                                                ^|
echo  ^|                     Maven  -  控制面板                         ^|
echo  ^|                                                                ^|
echo  ^|  0 - clean package -D...skip=true -Plocal                      ^|
echo  ^|  1 - clean package -D...skip=true -Pdev                        ^|
echo  ^|  2 - clean package -D...skip=true -Pproduct                    ^|
echo  ^|________________________________________________________________^|
:input
set /p input=-^> 请选择打包类型: 

if "%input%"== "0" goto local
if "%input%"== "1" goto dev
if "%input%"== "2" goto product
goto end

:local
echo  # 编译本地环境包 #
mvn clean install -Dmaven.test.skip=true -Plocal&&pause&&cls&& call compile.bat

:dev
echo  # 编译测试/开发联调环境包 #
mvn clean install -Dmaven.test.skip=true -Pdev&&pause&&cls&& call compile.bat

:product
echo  # 编译生产环境包 #
mvn clean install -Dmaven.test.skip=true -Pproduct&&pause&&cls&& call compile.bat

rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo 当前目录是:%dao_dir%
rem &&pause 
:end
echo 结束
prompt
popd