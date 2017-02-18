#!/bin/bash
set -e
set -x

git pull

cd jetson
./gradlew build


