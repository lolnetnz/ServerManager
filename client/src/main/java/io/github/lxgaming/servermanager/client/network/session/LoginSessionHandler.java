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

package io.github.lxgaming.servermanager.client.network.session;

import io.github.lxgaming.servermanager.client.ServerManagerImpl;
import io.github.lxgaming.servermanager.client.configuration.ConfigImpl;
import io.github.lxgaming.servermanager.client.configuration.category.GeneralCategoryImpl;
import io.github.lxgaming.servermanager.client.entity.ConnectionImpl;
import io.github.lxgaming.servermanager.common.manager.InstanceManager;
import io.github.lxgaming.servermanager.common.network.Packet;
import io.github.lxgaming.servermanager.common.network.SessionHandler;
import io.github.lxgaming.servermanager.common.network.StateRegistry;
import io.github.lxgaming.servermanager.common.network.packet.DisconnectPacket;
import io.github.lxgaming.servermanager.common.network.packet.HelloPacket;
import io.github.lxgaming.servermanager.common.network.packet.LoginPacket;
import io.github.lxgaming.servermanager.common.util.Toolbox;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class LoginSessionHandler implements SessionHandler {
    
    private final ConnectionImpl connection;
    
    public LoginSessionHandler(ConnectionImpl connection) {
        this.connection = connection;
    }
    
    @Override
    public void activated() {
        ServerManagerImpl.getInstance().getLogger().info("LoginSessionHandler active");
        connection.setState(StateRegistry.LOGIN);
    }
    
    @Override
    public boolean handle(DisconnectPacket packet) {
        ServerManagerImpl.getInstance().getLogger().warn("Disconnected: {}", packet.getMessage());
        connection.close();
        return true;
    }
    
    @Override
    public boolean handle(HelloPacket packet) {
        if (!Toolbox.isPrivateAddress(connection.getAddress()) && (!packet.isEncrypted() || packet.getCompressionThreshold() <= 0)) {
            ServerManagerImpl.getInstance().getLogger().warn("Secure connection cannot be established");
            connection.close();
            return true;
        }
        
        ServerManagerImpl.getInstance().getLogger().info("Sending LoginPacket Request");
        String name = ServerManagerImpl.getInstance().getConfig().map(ConfigImpl::getGeneralCategory).map(GeneralCategoryImpl::getName).orElse("Unknown");
        String path = ServerManagerImpl.getInstance().getConfig().map(ConfigImpl::getGeneralCategory).map(GeneralCategoryImpl::getPath).orElse(null);
        connection.write(new LoginPacket.Request(name, path));
        return true;
    }
    
    @Override
    public boolean handle(LoginPacket.Response packet) {
        ServerManagerImpl.getInstance().getLogger().info("Successful login");
        if (packet.getId() != null) {
            UUID id = ServerManagerImpl.getInstance().getConfig().map(ConfigImpl::getGeneralCategory).map(GeneralCategoryImpl::getId).orElse(null);
            String name = ServerManagerImpl.getInstance().getConfig().map(ConfigImpl::getGeneralCategory).map(GeneralCategoryImpl::getName).orElse("Unknown");
            
            if (!packet.getId().equals(id)) {
                ServerManagerImpl.getInstance().getLogger().warn("Id changed: {} -> {}", id, packet.getId());
                ServerManagerImpl.getInstance().getConfig().map(ConfigImpl::getGeneralCategory).ifPresent(category -> category.setId(packet.getId()));
                ServerManagerImpl.getInstance().getConfiguration().saveConfiguration();
            }
            
            connection.setAssociation(InstanceManager.getOrCreateInstance(packet.getId(), name));
            connection.setSessionHandler(new InstanceSessionHandler(connection));
        } else {
            connection.setSessionHandler(new ApplicationSessionHandler(connection));
        }
        
        return true;
    }
    
    @Override
    public void handleGeneric(Packet packet) {
        connection.close();
    }
    
    @Override
    public void handleUnknown(ByteBuf byteBuf) {
        connection.close();
    }
}