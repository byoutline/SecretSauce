#!/usr/bin/env bash
./gradlew :sampleapplication:connectedAndroidTest -x lint --no-daemon -PdisablePreDex --stacktrace