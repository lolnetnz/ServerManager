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

package nz.co.lolnet.servermanager.velocity.configuration;

import nz.co.lolnet.servermanager.common.configuration.Configuration;

import java.nio.file.Path;
import java.util.Optional;

public class VelocityConfiguration extends Configuration {
    
    public VelocityConfiguration(Path path) {
        super(path);
    }
    
    @Override
    public boolean loadConfiguration() {
        Optional<VelocityConfig> config = loadFile(getPath().resolve("config.json"), VelocityConfig.class);
        if (config.isPresent()) {
            this.config = config.get();
            return true;
        }
        
        return false;
    }
    
    @Override
    public VelocityConfig getConfig() {
        return (VelocityConfig) super.getConfig();
    }
}