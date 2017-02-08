# ds3_autogen/ds3-autogen-python3

This readme specifies how to generate the Spectra Python3 SDK ds3.py file and integrate it
into the existing Spectra Python3 SDK. This assumes that both the Autogen project and the 
Spectra Python3 SDK project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Generate Python3 SDK File

To generate the Python3 SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml` and it will place all generated files and directories
within `python3_sdk_files/` folder.

* `ds3-autogen-cli -d python3_sdk_files/ -i api_spec.xml -l python3`

This will generate the command and model code in proper folders starting at the `ds3` folder.

## Integrate Generated Code Into Python3 SDK

Copy the generated folder `ds3` and all its contents. Go to the `ds3-python3-sdk` main directory and
paste the copied `ds3` into the Python3 SDK. There may be a warning about overwriting `ds3.py`, which 
can be ignored.

Run the Python3 SDK tests and verify that there are no runtime errors.
