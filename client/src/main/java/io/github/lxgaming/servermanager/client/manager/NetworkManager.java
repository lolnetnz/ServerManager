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

package io.github.lxgaming.servermanager.client.manager;

import io.github.lxgaming.servermanager.api.ServerManager;
import io.github.lxgaming.servermanager.client.ServerManagerImpl;
import io.github.lxgaming.servermanager.client.network.netty.ClientChannelInitializer;
import io.github.lxgaming.servermanager.common.configuration.Config;
import io.github.lxgaming.servermanager.common.configuration.category.NetworkCategory;
import io.github.lxgaming.servermanager.common.network.Network;
import io.github.lxgaming.servermanager.common.util.Toolbox;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.internal.SystemPropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public final class NetworkManager {
    
    public static final String NETTY_TRANSPORT_NO_NATIVE = "io.netty.transport.noNative";
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerManager.NAME);
    
    private static Network network;
    private static SocketAddress socketAddress;
    private static Channel channel;
    
    public static void prepare() {
        NetworkCategory networkCategory = ServerManagerImpl.getInstance().getConfig().map(Config::getNetworkCategory).orElseThrow(NullPointerException::new);
        if (networkCategory.getPort() <= 0 || networkCategory.getPort() > 65535) {
            LOGGER.warn("Port is out of bounds. Resetting to {}", NetworkCategory.DEFAULT_PORT);
            networkCategory.setPort(NetworkCategory.DEFAULT_PORT);
        }
        
        if (networkCategory.isNativeTransport() && SystemPropertyUtil.getBoolean(NETTY_TRANSPORT_NO_NATIVE, false)) {
            LOGGER.warn("NativeTransport is explicitly disabled");
            networkCategory.setNativeTransport(false);
        }
        
        if (networkCategory.getMaximumThreads() < 0) {
            LOGGER.warn("MaximumThreads is out of bounds. Resetting to {}", NetworkCategory.DEFAULT_MAXIMUM_THREADS);
            networkCategory.setMaximumThreads(NetworkCategory.DEFAULT_MAXIMUM_THREADS);
        }
        
        if (networkCategory.getReadTimeout() < 0) {
            LOGGER.warn("ReadTimeout is out of bounds. Resetting to {}", NetworkCategory.DEFAULT_READ_TIMEOUT);
            networkCategory.setReadTimeout(NetworkCategory.DEFAULT_READ_TIMEOUT);
        }
        
        network = Network.builder()
                .maximumThreads(networkCategory.getMaximumThreads())
                .nativeTransport(networkCategory.isNativeTransport())
                .build();
        
        socketAddress = new InetSocketAddress(networkCategory.getHost(), networkCategory.getPort());
        LOGGER.info("Connections will use {} channels", network.getTransportType().getName());
    }
    
    public static void execute() {
        Bootstrap bootstrap = network.createBootstrap();
        bootstrap.handler(new ClientChannelInitializer());
        bootstrap.connect(socketAddress).addListener((ChannelFuture future) -> {
            if (future.isSuccess()) {
                channel = future.channel();
                LOGGER.info("Connected to {}", Toolbox.getAddress(socketAddress));
            } else {
                LOGGER.info("Can't connect to {}", Toolbox.getAddress(socketAddress), future.cause());
            }
        });
    }
    
    public static void shutdown() {
        try {
            LOGGER.info("Closing endpoint {}", Toolbox.getAddress(channel.localAddress()));
            channel.close().sync();
            network.shutdown(5L, TimeUnit.SECONDS);
            LOGGER.info("Successfully terminated Netty, continuing with shutdown process...");
        } catch (Exception ex) {
            LOGGER.error("Failed to terminate Netty, continuing with shutdown process...");
        }
    }
}