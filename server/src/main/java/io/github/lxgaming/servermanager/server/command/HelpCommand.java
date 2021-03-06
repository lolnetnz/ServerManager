/*
 * Copyright 2018 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.servermanager.server.command;

import io.github.lxgaming.servermanager.api.ServerManager;
import io.github.lxgaming.servermanager.common.util.Toolbox;
import io.github.lxgaming.servermanager.server.manager.CommandManager;

import java.util.List;

public class HelpCommand extends AbstractCommand {
    
    public HelpCommand() {
        addAlias("help");
        addAlias("?");
        setDescription("Helpful information");
        setUsage("[Command]");
    }
    
    @Override
    public void execute(List<String> arguments) {
        if (!arguments.isEmpty()) {
            AbstractCommand command = CommandManager.getCommand(arguments).orElse(null);
            if (command == null) {
                ServerManager.getInstance().getLogger().info("No help for {}", String.join(" ", arguments));
                return;
            }
            
            ServerManager.getInstance().getLogger().info("========== Help: {} ==========", command.getPrimaryAlias().orElse("Unknown"));
            ServerManager.getInstance().getLogger().info("Description: {}", command.getDescription());
            
            if (Toolbox.isNotBlank(command.getUsage())) {
                ServerManager.getInstance().getLogger().info("Usage: {}", command.getUsage());
            }
            
            if (!command.getAliases().isEmpty()) {
                ServerManager.getInstance().getLogger().info("Aliases: {}", String.join(", ", command.getAliases()));
            }
            
            if (!command.getChildren().isEmpty()) {
                ServerManager.getInstance().getLogger().info("Children:");
                
                command.getChildren().forEach(childCommand -> {
                    ServerManager.getInstance().getLogger().info("  {}: {}", childCommand.getPrimaryAlias().orElse("Unknown"), childCommand.getDescription());
                });
            }
            
            return;
        }
        
        ServerManager.getInstance().getLogger().info("========== Help: Index ==========");
        CommandManager.getCommands().forEach(command -> {
            ServerManager.getInstance().getLogger().info("{}: {}", command.getPrimaryAlias().orElse("Unknown"), command.getDescription());
        });
    }
}