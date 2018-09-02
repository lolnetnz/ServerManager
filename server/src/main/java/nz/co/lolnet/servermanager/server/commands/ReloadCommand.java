/*
 * Copyright 2018 lolnet.co.nz
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

package nz.co.lolnet.servermanager.server.commands;

import nz.co.lolnet.servermanager.server.ServerManager;

import java.util.List;

public class ReloadCommand extends AbstractCommand {
    
    public ReloadCommand() {
        addAlias("reload");
        setDescription("Reloads the application configuration");
    }
    
    @Override
    public void execute(List<String> arguments) {
        ServerManager.getInstance().getConfiguration().loadConfiguration();
        ServerManager.getInstance().reloadLogger();
        ServerManager.getInstance().getLogger().info("Reloaded");
    }
}