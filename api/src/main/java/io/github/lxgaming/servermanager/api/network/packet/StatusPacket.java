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

package io.github.lxgaming.servermanager.api.network.packet;

import com.google.gson.annotations.Expose;
import io.github.lxgaming.servermanager.api.data.Implementation;
import io.github.lxgaming.servermanager.api.network.NetworkHandler;

public class StatusPacket extends AbstractPacket {
    
    @Expose
    private Implementation.Data data;
    
    @Override
    public void process(NetworkHandler networkHandler) {
        networkHandler.handleStatus(this);
    }
    
    public Implementation.Data getData() {
        return data;
    }
    
    public void setData(Implementation.Data data) {
        this.data = data;
    }
}