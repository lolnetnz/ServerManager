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

package nz.co.lolnet.servermanager.sponge.configuration;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import nz.co.lolnet.servermanager.common.configuration.IConfiguration;
import nz.co.lolnet.servermanager.sponge.ServerManager;

import java.io.IOException;

public class Configuration implements IConfiguration {
    
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private ObjectMapper<Config>.BoundInstance objectMapper;
    private CommentedConfigurationNode configurationNode;
    private Config config;
    
    public Configuration() {
        try {
            this.configurationLoader = HoconConfigurationLoader.builder().setPath(ServerManager.getInstance().getPath()).build();
            this.objectMapper = ObjectMapper.forClass(Config.class).bindToNew();
        } catch (Exception ex) {
            ServerManager.getInstance().getLogger().error("Encountered an error initializing {}", getClass().getSimpleName(), ex);
        }
    }
    
    @Override
    public void loadConfiguration() {
        try {
            configurationNode = getConfigurationLoader().load(ConfigurationOptions.defaults());
            config = getObjectMapper().populate(getConfigurationNode());
            ServerManager.getInstance().getLogger().info("Successfully loaded configuration file.");
        } catch (IOException | ObjectMappingException | RuntimeException ex) {
            configurationNode = getConfigurationLoader().createEmptyNode(ConfigurationOptions.defaults());
            ServerManager.getInstance().getLogger().error("Encountered an error processing {}::loadConfiguration", getClass().getSimpleName(), ex);
        }
    }
    
    @Override
    public void saveConfiguration() {
        try {
            getObjectMapper().serialize(getConfigurationNode());
            getConfigurationLoader().save(getConfigurationNode());
            ServerManager.getInstance().getLogger().info("Successfully saved configuration file.");
        } catch (IOException | ObjectMappingException | RuntimeException ex) {
            ServerManager.getInstance().getLogger().error("Encountered an error processing {}::saveConfiguration", getClass().getSimpleName(), ex);
        }
    }
    
    private ConfigurationLoader<CommentedConfigurationNode> getConfigurationLoader() {
        return configurationLoader;
    }
    
    private ObjectMapper<Config>.BoundInstance getObjectMapper() {
        return objectMapper;
    }
    
    private CommentedConfigurationNode getConfigurationNode() {
        return configurationNode;
    }
    
    public Config getConfig() {
        return config;
    }
}