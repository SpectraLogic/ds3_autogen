# ds3_autogen/ds3-autogen-net

This readme specifies how to generate the Spectra .NET SDK command and model files and integrate them
into the existing Spectra .NET SDK. This assumes that both the Autogen project and the Spectra .NET SDK
project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Prepare the .NET SDK

Go to the .NET SDK folder and delete the old commands and models. This is necessary because commands
and models can be deleted or renamed between releases, and phantom code should be removed. The following
files and folders should be removed:

Within the folder `ds3-net-sdk/Ds3/`:

  * Within the `Ds3/Calls/` folder:
    * Delete all files EXCEPT for `Ds3Request`

  * Within the `Ds3/ResponseParsers/` folder:
    * Delete all files EXCEPT for the following:
      * Do not delete `IResponseParser.cs`
      * Do not delete `ResponseParseUtilities.cs`

  * Within the `Ds3/Models/` folder:
    * Delete all files EXCEPT for the following:
      * Do not delete `ContextRange.cs`
      * Do not delete `Crc32.cs`
      * Do not delete `Ds3Object.cs`
      * Do not delete `Ds3PartialObject.cs`
      * Do not delete `Range.cs`

## Generate .NET SDK Files

To generate the .NET SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml` and it will place all generated files and directories
within `net_sdk_files/` folder.

* `ds3-autogen-cli -d net_sdk_files/ -i api_spec.xml -l net`

This will generate the command and model code in proper folders starting at the `Ds3` folder.

## Integrate Generated Code Into .NET SDK

Copy the generated folder `Ds3` and all its contents. Go to the `ds3-net-sdk` main directory and
paste the copied `Ds3` into the .NET SDK. There may be a warning about overwriting `IDs3Client.cs`
and `Ds3Client.cs`, which can be ignored.

Compile the .NET SDK and verify that there are no compilation errors. Make sure that the deleted files
are not included in the project and that all added files are included in the project. This can be done
within the `Visual Studio 2015` using the `Solution Explorer` panel.
