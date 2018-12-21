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

package nz.co.lolnet.servermanager.api;

import nz.co.lolnet.servermanager.api.configuration.Config;
import nz.co.lolnet.servermanager.api.configuration.Configuration;
import nz.co.lolnet.servermanager.api.data.Platform;
import nz.co.lolnet.servermanager.api.network.NetworkHandler;
import nz.co.lolnet.servermanager.api.network.Packet;
import nz.co.lolnet.servermanager.api.util.Logger;

import java.nio.file.Path;
import java.util.Optional;

public abstract class ServerManager {
    
    private static ServerManager instance;
    protected Logger logger;
    protected Path path;
    protected Configuration configuration;
    protected Platform.Type platformType;
    
    protected ServerManager() {
        instance = this;
    }
    
    protected abstract void loadServerManager();
    
    public abstract void reloadServerManager();
    
    public abstract boolean registerNetworkHandler(Class<? extends NetworkHandler> networkHandlerClass);
    
    public abstract void sendPacket(String channel, Packet packet);
    
    public abstract void sendPacket(Packet packet);
    
    public static ServerManager getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public Path getPath() {
        return path;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<? extends Config> getConfig() {
        if (getConfiguration() != null) {
            return Optional.ofNullable(getConfiguration().getConfig());
        }
        
        return Optional.empty();
    }
    
    public Platform.Type getPlatformType() {
        return platformType;
    }
}