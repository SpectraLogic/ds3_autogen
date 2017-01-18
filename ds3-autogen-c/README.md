# ds3_autogen/ds3-autogen-c

This readme specifies how to generate the Spectra C SDK command and model files and integrate them
into the existing Spectra C SDK. This assumes that both the Autogen project and the Spectra C SDK
project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Generate C SDK File

To generate the C SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml`.

* `ds3-autogen-cli -d src/ -i api_spec.xml -l c`

This will generate the command and model code in proper folders starting at the `src` folder.

## Integrate Generated Code Into C SDK

Copy the generated folder `src` and all its contents. Go to the `ds3-c-sdk` main directory and
paste the copied `src` into the C SDK. There may be a warning about overwriting some files, which 
can be ignored.

Compile the C SDK and verify that there are no compilation errors.
