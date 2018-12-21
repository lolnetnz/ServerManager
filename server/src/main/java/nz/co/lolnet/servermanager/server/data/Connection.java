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

package nz.co.lolnet.servermanager.server.data;

import nz.co.lolnet.servermanager.api.data.ServerInfo;

public class Connection {
    
    private final String channelId;
    private final String serverId;
    private boolean receiveStatuses;
    private long lastPacketTime;
    private long lastPingTime;
    private ServerInfo serverInfo;
    
    private Connection(String channelId, String serverId) {
        this.channelId = channelId;
        this.serverId = serverId;
    }
    
    public static Connection of(String channelId, String serverId) {
        return new Connection(channelId, serverId);
    }
    
    public String getChannelId() {
        return channelId;
    }
    
    public String getServerId() {
        return serverId;
    }
    
    public boolean isReceiveStatuses() {
        return receiveStatuses;
    }
    
    public void setReceiveStatuses(boolean receiveStatuses) {
        this.receiveStatuses = receiveStatuses;
    }
    
    public long getLastPacketTime() {
        return lastPacketTime;
    }
    
    public void setLastPacketTime(long lastPacketTime) {
        this.lastPacketTime = lastPacketTime;
    }
    
    public long getLastPingTime() {
        return lastPingTime;
    }
    
    public void setLastPingTime(long lastPingTime) {
        this.lastPingTime = lastPingTime;
    }
    
    public ServerInfo getServerInfo() {
        return serverInfo;
    }
    
    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }
}