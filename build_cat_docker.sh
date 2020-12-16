#!/bin/bash

CAT_CODE_HUB="/Users/pengtang/Documents/github/cat"
VERSION=$1

if [ -z "$VERSION" ]
then 
	VERSION="0.0.5"
fi

pwd && mvn clean install -Dmaven.test.skip -U \
 && cp ./cat-home/target/cat-alpha-3.0.0.war ./docker \
 && cd ${CAT_CODE_HUB}/docker \
 && docker build -t chrisptang/cat:${VERSION} .

cd $CAT_CODE_HUB && pwd