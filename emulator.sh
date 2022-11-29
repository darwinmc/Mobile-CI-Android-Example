#!/bin/bash

# Download an ARM system image to create an ARM emulator.
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-23;default;armeabi-v7a"

# Launch the emulator in the background
# Create an ARM AVD emulator, with a 100 MB SD card storage space. Echo "no"
# because it will ask if you want to use a custom hardware profile, and you don't.
# https://medium.com/@AndreSand/android-emulator-on-docker-container-f20c49b129ef
echo no | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --force --sdcard 100M --name test -k "system-images;android-23;default;armeabi-v7a"
$ANDROID_HOME/emulator/emulator -avd test -no-window &

## Wait for Android to finish booting
wget --quiet --output-document=android-wait-for-emulator https://raw.githubusercontent.com/travis-ci/travis-cookbooks/master/community-cookbooks/android-sdk/files/default/android-wait-for-emulator
chmod +x android-wait-for-emulator
./android-wait-for-emulator
## Unlock the Lock Screen
adb shell input keyevent 82

#start tests
export ADB_INSTALL_TIMEOUT=10
