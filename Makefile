#Constants
JAVAC = javac
JAVA = java 
BIN = bin
SRC = server/src/*.java
SERVER_SRC = server.src.Server

#This allows for all:(default: 'make') to be placed anywhere in makefile
.PHONY:
default: all

# Compile all java files(Default: ie. [make])
all: compile run

#Compile Java Files
compile:
	$(JAVAC) -d $(BIN) $(SRC)

#Run Java Server File
run: 
	$(JAVA)-cp $(BIN) $(SERVER_SRC)

#Make Clean(Remove all .class files in /bin)
clean: 
	rm -rf $(BIN)/*
