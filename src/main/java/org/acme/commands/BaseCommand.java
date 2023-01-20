package org.acme.commands;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true,
        subcommands = {ListCommand.class, CountCommand.class,
                UploadCommand.class, DownloadCommand.class, InfoCommand.class,
                DeleteCommand.class})
public class BaseCommand {


}