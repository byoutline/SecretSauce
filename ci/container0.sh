#!/usr/bin/env bash
set -e # Fail script if one of commands fails

echo " *** Build APKs *** "
date +"%T"
./gradlew assembleDebug -x lint

echo " *** Run tests *** "
date +"%T"
./gradlew check -x lint

echo " *** Run Lint *** "
date +"%T"
./gradlew :SecretSauce:lintRelease

echo " *** BUILD FINISHED *** "