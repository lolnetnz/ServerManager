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

package io.github.lxgaming.servermanager.bungee.listener;

import com.google.gson.JsonObject;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import io.github.lxgaming.servermanager.api.ServerManager;
import io.github.lxgaming.servermanager.api.data.User;
import io.github.lxgaming.servermanager.api.network.packet.UserPacket;
import io.github.lxgaming.servermanager.bungee.ServerManagerImpl;
import io.github.lxgaming.servermanager.common.manager.PacketManager;
import io.github.lxgaming.servermanager.common.util.Toolbox;

import java.net.InetSocketAddress;

public class BungeeListener implements Listener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPubSubMessage(PubSubMessageEvent event) {
        if (ServerManagerImpl.getInstance().getRedisService().getChannels().contains(event.getChannel())) {
            Toolbox.parseJson(event.getMessage(), JsonObject.class).ifPresent(PacketManager::process);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = new User(player.getName(), player.getUniqueId());
        if (player.getSocketAddress() instanceof InetSocketAddress) {
            user.setAddress(((InetSocketAddress) player.getSocketAddress()).getHostString());
        }
        
        ServerManager.getInstance().sendResponse(new UserPacket.Add(user));
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        User user = new User(player.getName(), player.getUniqueId());
        if (player.getSocketAddress() instanceof InetSocketAddress) {
            user.setAddress(((InetSocketAddress) player.getSocketAddress()).getHostString());
        }
        
        ServerManager.getInstance().sendResponse(new UserPacket.Remove(user));
    }
}