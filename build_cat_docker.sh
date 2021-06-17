#!/bin/bash

CAT_CODE_HUB="/Users/tangpeng/Documents/github/cat"
VERSION=$1

echo $1

if [ -z "$VERSION" ]
then 
	VERSION="0.0.5"
fi

pwd && mvn clean install -Dmaven.test.skip -U \
 && cp ./cat-home/target/cat-alpha-3.0.0.war ./docker \
 && cd ${CAT_CODE_HUB}/docker \
 && docker buildx build --push --platform linux/arm/v7,linux/arm64/v8,linux/amd64 --tag chrisptang/cat:latest .

cd $CAT_CODE_HUB && pwd
