# ds3_autogen/ds3-autogen-go

This readme specifies how to generate the Spectra Go SDK command and model files and integrate them
into the existing Spectra Go SDK. This assumes that both the Autogen project and the Spectra Go SDK
project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Prepare the Go SDK

Go to the Go SDK folder and delete the old commands and models. This is necessary because commands
and models can be deleted or renamed between releases, and phantom code should be removed. The following
files and folders should be removed:

Within the folder `ds3-go-sdk/src/ds3/models`:

  * Delete all files in `models` EXCEPT for the following:
      * Do not delete `aggregateError.go`
      * Do not delete `badStatusCodeError.go`
      * Do not delete `enum.go`
      * Do not delete `requestPayloadUtils.go`
      * Do not delete `responseHandlingUtil.go`
      * Do not delete `responseParsingUtil.go`
      * Do not delete `xmlTree.go`

## Generate Go SDK Files

To generate the go SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml` and it will place all generated files and directories
within `go_sdk_files/` folder.

* `ds3-autogen-cli -d go_sdk_files/ -i api_spec.xml -l go`

This will generate the command and model code in proper folders starting at the `ds3` folder.

## Integrate Generated Code Into Go SDK

Copy the generated folder `ds3` and all its contents. Go to the `ds3-go-sdk/src/` folder and
paste the copied `ds3` into the Go SDK. There may be warnings about overwriting `ds3Deletes.go`,
`ds3Gets.go`, `ds3Heads.go`, `ds3Posts.go`, and `ds3Puts`. Accept the overwrites, and continue. 

Compile the Go SDK and verify that there are no compilation errors.
