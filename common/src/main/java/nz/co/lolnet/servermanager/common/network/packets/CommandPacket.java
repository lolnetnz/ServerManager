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

package nz.co.lolnet.servermanager.common.network.packets;

import nz.co.lolnet.servermanager.common.AbstractServerManager;
import nz.co.lolnet.servermanager.common.data.User;

public class CommandPacket extends AbstractPacket {
    
    private String command;
    private User user;
    
    private CommandPacket(String command, User user) {
        this.command = command;
        this.user = user;
    }
    
    @Override
    public void process() {
        AbstractServerManager.getInstance().getNetworkHandler().handleCommand(this);
    }
    
    public static CommandPacket of(String command, User user) {
        return new CommandPacket(command, user);
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}