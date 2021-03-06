/*
 * Copyright 2019 Alex Thomson
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

package io.github.lxgaming.servermanager.server.command.connection;

import io.github.lxgaming.servermanager.api.ServerManager;
import io.github.lxgaming.servermanager.server.command.AbstractCommand;
import io.github.lxgaming.servermanager.server.data.Connection;
import io.github.lxgaming.servermanager.server.manager.ConnectionManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListConnectionCommand extends AbstractCommand {
    
    public ListConnectionCommand() {
        addAlias("list");
        setDescription("List current connections");
    }
    
    @Override
    public void execute(List<String> arguments) {
        Set<String> connections = ConnectionManager.getConnections().stream().map(Connection::getId).collect(Collectors.toSet());
        if (connections.isEmpty()) {
            ServerManager.getInstance().getLogger().warn("No connections present");
            return;
        }
        
        ServerManager.getInstance().getLogger().info("Connections: {}", String.join(", ", connections));
    }
}