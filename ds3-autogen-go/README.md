# ds3_autogen/ds3-autogen-go

This readme specifies how to generate the Spectra Go SDK command and model files and integrate them
into the existing Spectra Go SDK. This assumes that both the Autogen project and the Spectra Go SDK
project are both on the current system.

## Install Autogen

See the Autogen [README](../README.md) for installation instructions.

## Generate Go SDK Files

To generate the go SDK files, use the following command. This assumes that the input API spec is in
the current directory and named `api_spec.xml` and it will place all generated files and directories
within `go_sdk_files/` folder.

* `ds3-autogen-cli -d go_sdk_files/ -i api_spec.xml -l go`

This will generate the command and model code in proper folders starting at the `ds3` folder.

## Integrate Generated Code Into Go SDK

Copy the generated folder `ds3` and all its contents. Go to the `ds3-go-sdk/` folder and
paste the copied `ds3` into the Go SDK. There may be warnings about overwriting the following: 

* `ds3/ds3Deletes.go`
* `ds3/ds3Gets.go`
* `ds3/ds3Heads.go` 
* `ds3/ds3Posts.go` 
* `ds3/ds3Puts`
* `ds3/models/requests.go`
* `ds3/models/responses.go`
* `ds3/models/responseModels.go`

Accept the overwrites, and continue. 

Compile the Go SDK and verify that there are no compilation errors.
