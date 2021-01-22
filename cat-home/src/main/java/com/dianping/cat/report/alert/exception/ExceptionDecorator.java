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
package com.dianping.cat.report.alert.exception;

import com.dianping.cat.Cat;
import com.dianping.cat.alarm.spi.AlertEntity;
import com.dianping.cat.alarm.spi.AlertType;
import com.dianping.cat.alarm.spi.decorator.ProjectDecorator;
import com.dianping.cat.report.alert.summary.AlertSummaryExecutor;
import org.unidal.lookup.annotation.Inject;

public class ExceptionDecorator extends ProjectDecorator {

    public static final String ID = AlertType.Exception.getName();

    @Inject
    private AlertSummaryExecutor m_executor;

    @Override
    public String generateContent(AlertEntity alert) {
        String alertContent = buildTemplatedContent(alert);
        String summaryContext = "";

        try {
            summaryContext = m_executor.execute(alert.getGroup(), alert.getDate());
        } catch (Exception e) {
            Cat.logError(alert.toString(), e);
        }

        if (summaryContext != null) {
            return alertContent + "<br/>" + summaryContext;
        } else {
            return alertContent;
        }
    }


    @Override
    public String generateTitle(AlertEntity alert) {
        StringBuilder sb = new StringBuilder();
        sb.append("[CAT异常告警] [项目: ").append(alert.getGroup()).append("]");
        return sb.toString();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected String getTemplate() {
        return "exceptionAlert.ftl";
    }
}
