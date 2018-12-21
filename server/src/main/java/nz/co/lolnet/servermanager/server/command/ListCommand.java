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

package nz.co.lolnet.servermanager.server.command;

import nz.co.lolnet.servermanager.api.ServerManager;
import nz.co.lolnet.servermanager.common.util.Toolbox;
import nz.co.lolnet.servermanager.server.manager.ConnectionManager;

import java.util.List;
import java.util.Set;

public class ListCommand extends AbstractCommand {
    
    public ListCommand() {
        addAlias("list");
        setDescription("Show a list of connections");
    }
    
    @Override
    public void execute(List<String> arguments) {
        Set<String> connections = Toolbox.newHashSet();
        ConnectionManager.getConnections().forEach(connection -> connections.add(connection.getServerId()));
        if (connections.isEmpty()) {
            ServerManager.getInstance().getLogger().info("No connections present");
            return;
        }
        
        ServerManager.getInstance().getLogger().info("Connections: {}", String.join(", ", connections));
    }
}