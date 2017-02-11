#!/bin/bash
export http_proxy="http://localhost:3128"
export https_proxy="http://localhost:3128"
git pull
cd Java
./gradlew build

