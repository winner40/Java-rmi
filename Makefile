PWD := $(shell pwd)
all: Chat_itf.jar ChatImpl.jar ChatServer.class ChatClient.class ChatClientGUI.class

Chat_itf.jar: classes/Chat_itf.class
	cd classes && jar cvf ../lib/Chat_itf.jar Chat_itf.class

ChatImpl.jar: classes/ChatImpl.class
	cd classes && jar cvf ../lib/ChatImpl.jar ChatImpl.class

ChatServer.class: lib/Chat_itf.jar lib/ChatImpl.jar
	javac -d classes -cp .:classes:lib/Chat_itf.jar:lib/ChatImpl.jar $(PWD)/ChatServer.java

ChatClient.class: lib/Chat_itf.jar
	javac -d classes -cp .:classes:lib/Chat_itf.jar $(PWD)/ChatClient.java

ChatClientGUI.class: lib/Chat_itf.jar
	javac -d classes -cp .:classes:lib/Chat_itf.jar $(PWD)/ChatClientGUI.java

classes/Chat_itf.class: $(PWD)/Chat_itf.java
	javac -d classes -classpath .:classes $(PWD)/Chat_itf.java

classes/ChatImpl.class: $(PWD)/ChatImpl.java
	javac -d classes -classpath .:classes $(PWD)/ChatImpl.java

clean:
	rm -rf classes lib
