/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author guilherme
 */
public class RunnerStartupInfo {

    private final String COMMAND_LINE = "kepler ";
    
    private final String[] args;
    private Options options;
    private CommandLine commandLine;
    private Option sourcefile;
    private Option verbose;

    public RunnerStartupInfo(String[] args)  {
        this.args = args;
    }

    public String getFileName() {
        return commandLine.getOptionValue(sourcefile.getArgName());
    }

    public boolean isVerbose() {
        return commandLine.hasOption(verbose.getOpt());
    }

    public boolean isValidOptions(boolean printHelpWhenInvalid) {

        try {
            initOptions();

            for (Option opt : options.getOptions()) {
                if (opt.isRequired() && !commandLine.hasOption(opt.getOpt())) {

                    if (printHelpWhenInvalid) {
                        showHelp();
                    }

                    return false;
                }
            }
            return true;
        } catch (ParseException e) {
            showHelp();
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showHelp() {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(COMMAND_LINE, options);
    }

    private void initOptions() throws ParseException {
        if (options != null) {
            return;
        }

        options = new Options();
        sourcefile = OptionBuilder.withArgName("file")
                .hasArg()
                .isRequired(true)
                .withDescription("execute the script from the file")
                .create("file");

        verbose = new Option("verbose", "display parsing and execution time in ms");
        Option help = new Option("help", "print this message");

        options.addOption(sourcefile);
        options.addOption(verbose);
        options.addOption(help);

        // parse the command line arguments
        // create the command line parser
        CommandLineParser parser = new DefaultParser();
        commandLine = parser.parse(options, args);
    }
}
