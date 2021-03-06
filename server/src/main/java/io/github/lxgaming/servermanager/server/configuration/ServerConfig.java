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

package io.github.lxgaming.servermanager.server.configuration;

import io.github.lxgaming.servermanager.api.configuration.Config;
import io.github.lxgaming.servermanager.common.util.Toolbox;
import io.github.lxgaming.servermanager.server.configuration.category.RedisCategory;
import io.github.lxgaming.servermanager.server.configuration.category.ServerCategory;

import java.util.List;

public class ServerConfig implements Config {
    
    private boolean debug = false;
    private String name = "";
    private String command = "bash [SCRIPT]";
    private boolean jlineOverride = true;
    private RedisCategory redisCategory = new RedisCategory();
    private List<ServerCategory> serverCategories = Toolbox.newArrayList();
    
    @Override
    public boolean isDebug() {
        return debug;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public String getCommand() {
        return command;
    }
    
    public boolean isJlineOverride() {
        return jlineOverride;
    }
    
    public RedisCategory getRedisCategory() {
        return redisCategory;
    }
    
    public List<ServerCategory> getServerCategories() {
        return serverCategories;
    }
}