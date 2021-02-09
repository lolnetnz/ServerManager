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

package io.github.lxgaming.servermanager.common.network.packet;

import com.google.common.base.Preconditions;
import io.github.lxgaming.servermanager.common.network.Packet;
import io.github.lxgaming.servermanager.common.network.SessionHandler;
import io.github.lxgaming.servermanager.common.network.StateRegistry;
import io.github.lxgaming.servermanager.common.network.util.ProtocolUtils;
import io.netty.buffer.ByteBuf;

public class HandshakePacket implements Packet {
    
    private StateRegistry state;
    
    public HandshakePacket() {
    }
    
    public HandshakePacket(StateRegistry state) {
        this.state = state;
    }
    
    @Override
    public void decode(ByteBuf byteBuf) {
        int stateId = ProtocolUtils.readVarInt(byteBuf);
        this.state = StateRegistry.getStateRegistry(stateId);
    }
    
    @Override
    public void encode(ByteBuf byteBuf) {
        Preconditions.checkNotNull(state, "state");
        int stateId = state.getId();
        ProtocolUtils.writeVarInt(byteBuf, stateId);
    }
    
    @Override
    public boolean handle(SessionHandler handler) {
        return handler.handle(this);
    }
    
    public StateRegistry getState() {
        return state;
    }
}