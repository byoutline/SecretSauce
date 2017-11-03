#!/usr/bin/env bash
set -e # Fail script if one of commands fails
echo " *** UI TESTS *** "

./gradlew --stop
EMU=testing16
echo "launching $EMU"
emulator -avd $EMU -no-audio -no-window -no-boot-anim -sdcard mysdcard.img &

echo " *** Ensure that emulator booted *** "
circle-android wait-for-boot
sleep 30

echo " *** Disable animations and screen unlock *** "
adb devices
ci/disableAnimations.sh
adb shell input keyevent 82
adb logcat -d

echo " *** Run instrumented tests with retry *** "
ci/run_instrumented_tests.sh || ci/run_instrumented_tests.sh

echo " *** BUILD FINISHED *** "

