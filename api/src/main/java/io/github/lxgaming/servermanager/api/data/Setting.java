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

package io.github.lxgaming.servermanager.api.data;

import com.google.gson.annotations.Expose;

public class Setting {
    
    @Expose
    private Boolean forwardState;
    
    @Expose
    private Boolean forwardUser;
    
    @Expose
    private Long maxTickTime;
    
    public Boolean getForwardState() {
        return forwardState;
    }
    
    public void setForwardState(Boolean forwardState) {
        this.forwardState = forwardState;
    }
    
    public Boolean getForwardUser() {
        return forwardUser;
    }
    
    public void setForwardUser(Boolean forwardUser) {
        this.forwardUser = forwardUser;
    }
    
    public Long getMaxTickTime() {
        return maxTickTime;
    }
    
    public void setMaxTickTime(Long maxTickTime) {
        this.maxTickTime = maxTickTime;
    }
}