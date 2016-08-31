package com.spectralogic.autogen.cli;


public class Arguments {
    private final String targetDir;
    private final GeneratorType type;
    private final String inputSpec;
    private final boolean help;
    private final boolean generateInternal;
    private final boolean noDoc;

    public Arguments(
            final String targetDir,
            final GeneratorType type,
            final String inputSpec,
            final boolean help,
            final boolean generateInternal,
            final boolean noDoc) {
        this.targetDir = targetDir;
        this.type = type;
        this.help = help;
        this.inputSpec = inputSpec;
        this.generateInternal = generateInternal;
        this.noDoc = noDoc;
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

    public boolean generateInternal() {
        return generateInternal;
    }

    public boolean isNoDoc() {
        return noDoc;
    }
}
