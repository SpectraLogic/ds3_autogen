# ds3_autogen/ds3-autogen-python

This readme specifies how to generate the Spectra Python SDK ds3.py file and integrate it
into the existing Spectra Python SDK. This assumes that both the Autogen project and the 
Spectra Python SDK project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Generate Python SDK File

To generate the Python SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml` and it will place all generated files and directories
within `python_sdk_files/` folder.

* `ds3-autogen-cli -d python_sdk_files/ -i api_spec.xml -l python`

This will generate the command and model code in proper folders starting at the `ds3` folder.

## Integrate Generated Code Into Python SDK

Copy the generated folder `ds3` and all its contents. Go to the `ds3-python-sdk` main directory and
paste the copied `ds3` into the Python SDK. There may be a warning about overwriting `ds3.py`, which 
can be ignored.

Run the Python SDK tests and verify that there are no runtime errors.
