
default: 
	javac *.java
	jar -cvf GraphSearch.jar ./*.class
	hadoop jar GraphSearch.jar GraphSearch -m 3 -r 3 -i 10

clean:
	rm -rf output-graph* *.jar *.class

clobber:
	rm -rf output-graph* *.jar *.class *.java
