#!/bin/bash

cd ~

sudo apt update

sudo apt install default-jdk

sudo apt install maven

cd ~/geni-passwordcraker/master

mvn  package

cd target/

java -jar master-1.0-SNAPSHOT-jar-with-dependencies.jar
