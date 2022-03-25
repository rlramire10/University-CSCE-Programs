::@echo off
javac Game.java View.java Controller.java Model.java Tube.java Json.java Mario.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)

