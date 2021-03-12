/*
 * Copyright 2021 Alex Thomson
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

package io.github.lxgaming.servermanager.server.integration.client;

import io.github.lxgaming.servermanager.api.ServerManager;
import io.github.lxgaming.servermanager.api.entity.Instance;
import io.github.lxgaming.servermanager.client.Client;
import io.github.lxgaming.servermanager.client.configuration.ConfigImpl;
import io.github.lxgaming.servermanager.common.entity.Connection;
import io.github.lxgaming.servermanager.common.entity.InstanceImpl;
import io.github.lxgaming.servermanager.common.network.Packet;
import io.github.lxgaming.servermanager.common.util.Toolbox;
import io.github.lxgaming.servermanager.server.integration.Integration;

public class ClientIntegration extends Integration {
    
    private Instance instance;
    
    @Override
    public boolean prepare() {
        return true;
    }
    
    @Override
    public void execute() throws Exception {
        ServerManager.getInstance().getEventManager().register(new ClientListener());
        
        Client client = new Client(Toolbox.getPath());
        if (!client.prepare()) {
            return;
        }
        
        ConfigImpl config = Client.getInstance().getConfig().orElseThrow(NullPointerException::new);
        this.instance = new InstanceImpl(config.getGeneralCategory().getId(), config.getGeneralCategory().getName(), config.getGeneralCategory().getPlatform());
        
        String host = config.getNetworkCategory().getHost();
        if (host == null || host.equals("localhost") || host.equals("127.0.0.1")) {
            return;
        }
        
        client.execute();
    }
    
    @Override
    public void shutdown() {
    }
    
    public void forward(Packet packet) {
        Connection connection = Client.getInstance().getConnection();
        if (connection != null) {
            connection.write(packet);
        }
    }
    
    public Instance getInstance() {
        return instance;
    }
}