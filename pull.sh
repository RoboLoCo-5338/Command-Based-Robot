#!/bin/bash
set -e
set -x

git pull

export LD_LIBRARY_PATH=/opt/GRIP/app/:$LD_LIBRARY_PATH

cd jetson
./gradlew clean
./gradlew build
./gradlew run


