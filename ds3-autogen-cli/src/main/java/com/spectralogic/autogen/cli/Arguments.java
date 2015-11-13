package com.spectralogic.autogen.cli;


public class Arguments {
    private String targetDir;
    private GeneratorType type;
    private String inputSpec;
    private boolean help;

    public Arguments() { }

    public void setTargetDir(final String targetDir) {
        this.targetDir = targetDir;
    }

    public void setType(final GeneratorType type) {
        this.type = type;
    }

    public void setInputSpec(final String inputSpec) {
        this.inputSpec = inputSpec;
    }

    public void setHelp(final boolean help) {
        this.help = help;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public GeneratorType getType() {
        return type;
    }

    public boolean isHelp() {
        return help;
    }

    public String getInputSpec() {
        return inputSpec;
    }
}
