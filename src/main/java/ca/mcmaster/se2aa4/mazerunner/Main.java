package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        try {
            // -i argument detection

            Options options = new Options();
            options.addOption("i", true, "Path to the input maze file");
            options.addOption("p", true, "for entering maze path to verify");

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (!cmd.hasOption("i")) { throw new Exception("no command given"); }

            String entered_path = "";
            String mazeFilePath = cmd.getOptionValue("i");

            if (cmd.hasOption("p")) { entered_path = cmd.getOptionValue("p"); }


            Navigate m = new Navigate(mazeFilePath, logger, entered_path);

        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}