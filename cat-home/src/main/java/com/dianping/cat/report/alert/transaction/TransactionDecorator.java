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
package com.dianping.cat.report.alert.transaction;

import com.dianping.cat.alarm.spi.AlertEntity;
import com.dianping.cat.alarm.spi.AlertType;
import com.dianping.cat.alarm.spi.decorator.ProjectDecorator;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TransactionDecorator extends ProjectDecorator implements Initializable {

    public static final String ID = AlertType.Transaction.getName();

    protected DateFormat m_linkFormat = new SimpleDateFormat("yyyyMMddHH");

    @Override
    public String generateContent(AlertEntity alert) {
        return buildTemplatedContent(alert);
    }

    @Override
    public String generateTitle(AlertEntity alert) {
        StringBuilder sb = new StringBuilder();

        sb.append("[CAT Transaction告警] [项目: ").append(alert.getGroup()).append("] [监控项: ").append(alert.getMetric())
                .append("]");
        return sb.toString();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected String getTemplate() {
        return "transactionAlert.ftl";
    }
}
