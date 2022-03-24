
default: 
	javac *.java
	jar -cvf relativefreq.jar ./*.class
	hadoop jar relativefreq.jar relativefreq input output

clean:
	rm -rf output *.jar *.class

clobber:
	rm -rf output *.jar *.class *.java
