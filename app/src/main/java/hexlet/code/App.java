package hexlet.code;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "checksum 4.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--format"}, description = "output format [default: stylish]", paramLabel = "formatName")
    private String formatName = "stylish";
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean help = false;
    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "Print version information and exit.")
    private boolean version = false;
    @CommandLine.Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filepath1;
    @CommandLine.Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filepath2;

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            var diff = Formatter.generate(filepath1, filepath2, formatName);
            System.out.println(diff);

            return 1;
        } catch (Exception e) {
            System.out.println("Error, please try again." + e.getMessage());
        }
        return 0;
    }
}
