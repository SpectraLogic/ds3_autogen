package com.spectralogic.autogen.cli;


public class Arguments {
    private final String targetDir;
    private final GeneratorType type;
    private final String inputSpec;
    private final boolean help;
    private final boolean removeInternal;

    public Arguments(
            final String targetDir,
            final GeneratorType type,
            final String inputSpec,
            final boolean help,
            final boolean removeInternal) {
        this.targetDir = targetDir;
        this.type = type;
        this.help = help;
        this.inputSpec = inputSpec;
        this.removeInternal = removeInternal;
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

    public boolean removeInternal() {
        return removeInternal;
    }
}
