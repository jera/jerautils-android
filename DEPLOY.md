##Deploying
How to deploy this repository to bintray

## Adding credentials for the deploy
Before you deploy this lib, it is necessary to add the credentials from a bintray user belonging to
the jera organization (https://bintray.com/jera)

Open (or create) a file named ```local.properties``` in root directory of the project and add the
following lines:
```
bintray.user=BINTRAY_USERNAME
bintray.apikey=BINTRAY_API_KEY
```

Repalce BINTRAY_USERNAME and BINTRAY_API_KEY by the actual username and api key from bintray.

The key can be found at:
https://bintray.com/profile/edit

## Deploying to bintray

The deploy to bintray is done using the official gradle plugin:
https://github.com/bintray/gradle-bintray-plugin

## Generating a new version:

1. Open the file build.gradle and change the value **project.version** to the desired version.
2. Open the terminal and run:

```bash
$ ./gradlew jerautils:clean jerautils:assembleRelease bintrayUpload
```