# ds3_autogen/ds3-autogen-java

This readme specifies how to generate the Spectra Java SDK command and model files and integrate them
into the existing Spectra Java SDK. This assumes that both the Autogen project and the Spectra Java SDK
project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Prepare the Java SDK

Go to the Java SDK folder and delete the old commands and models. This is necessary because commands
and models can be deleted or renamed between releases, and phantom code should be removed. The following
files and folders should be removed:

Within the folder `ds3-java-sdk/ds3-sdk/src/main/java/com/spectralogic/ds3client/`:

  * Within the `ds3client/commands/` folder:
    * DO NOT DELETE sub-folders `/commands/parsers/` or `/commands/interfaces/`
    * Delete all files (non-recursive) EXCEPT do not delete the `package-info` file
    * Delete the sub-folder `commands/spectrads3/` and all its contents.
    * Within the subfolder `commands/parsers/`:
      * DO NOT DELETE any sub-folders or any of their contents.
      * Delete all files (non-recursive)

  * Within the `ds3client/models/` folder:
    * DO NOT DELETE any sub-folders or any of their contents.
    * Delete all files (non-recursive)

## Generate Java SDK Files

To generate the Java SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml` and it will place all generated files and directories
within `java_sdk_files/` folder.

* `ds3-autogen-cli -d java_sdk_files/ -i api_spec.xml -l java`

This will generate the command and model code in proper folders starting at the `ds3-sdk` folder.

## Integrate Generated Code Into Java SDK

Copy the generated folder `ds3-sdk` and all its contents. Go to the `ds3-java-sdk` main directory and
paste the copied `ds3-sdk` into the Java SDK. There may be a warning about overwriting `Ds3Client.java`
and `Ds3ClientImpl.java`, which can be ignored.

Compile the Java SDK and verify that there are no compilation errors.
