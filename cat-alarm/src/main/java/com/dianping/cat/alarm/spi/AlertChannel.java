/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dianping.cat.alarm.spi;

public enum AlertChannel {

    MAIL("mail", true),

    SMS("sms", true),

    WEIXIN("weixin", true),

    //通过webhook来发送消息的接口，比如企业微信和钉钉的告警机器人；
    //不需要receiver
    WEBHOOK("webhook", false),

    DX("dx", true);

    private final String m_name;

    private final boolean needReceivers;

    AlertChannel(String name, boolean needReceivers) {
        m_name = name;
        this.needReceivers = needReceivers;
    }

    public static AlertChannel findByName(String name) {
        for (AlertChannel channel : values()) {
            if (channel.getName().equals(name)) {
                return channel;
            }
        }
        return null;
    }

    public String getName() {
        return m_name;
    }

    public boolean isNeedReceivers() {
        return needReceivers;
    }
}
