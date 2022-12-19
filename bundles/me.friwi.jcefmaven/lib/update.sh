#!/bin/bash
rm *.jar
mvn package
javac update.java
java update
