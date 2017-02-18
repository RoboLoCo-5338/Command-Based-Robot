#!/bin/bash
set -e
set -x

git pull

cd Java
./gradlew build


