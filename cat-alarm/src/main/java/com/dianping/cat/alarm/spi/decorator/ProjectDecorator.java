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
package com.dianping.cat.alarm.spi.decorator;

import com.dianping.cat.Cat;
import com.dianping.cat.alarm.spi.AlertEntity;
import com.dianping.cat.core.dal.Project;
import com.dianping.cat.service.ProjectService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.lookup.annotation.Inject;
import org.unidal.lookup.util.StringUtils;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class ProjectDecorator extends Decorator implements Initializable {

    @Inject
    protected ProjectService m_projectService;

    protected Configuration m_configuration;

    protected static final DateFormat M_LINK_FORMAT = new SimpleDateFormat("yyyyMMddHH");

    /**
     * specify template name: heartbeatAlert.ftl,exceptionAlert.ftl,eventAlert.ftl, etc...
     *
     * @return
     */
    protected abstract String getTemplate();

    @Override
    public final void initialize() throws InitializationException {
        m_configuration = new Configuration();
        m_configuration.setDefaultEncoding("UTF-8");
        try {
            m_configuration.setClassForTemplateLoading(this.getClass(), "/freemaker");
        } catch (Exception e) {
            Cat.logError(e);
        }
    }

    protected final String buildTemplatedContent(AlertEntity alert) {
        try {
            StringWriter sw = new StringWriter(5000);
            Map<Object, Object> dataMap = generateAlertTemplateInfoMap(alert);
            Template t = m_configuration.getTemplate(getTemplate());
            t.process(dataMap, sw);
            return sw.toString();
        } catch (Exception e) {
            Cat.logError("build exception content error:" + alert.toString(), e);
            return "build exception content error:" + alert.toString();
        }
    }

    protected final String buildContactInfo(String domainName) {
        try {
            Project project = m_projectService.findByDomain(domainName);

            if (project != null) {
                String owners = project.getOwner();
                String phones = project.getPhone();
                StringBuilder builder = new StringBuilder();

                if (!StringUtils.isEmpty(owners)) {
                    builder.append("[业务负责人: ").append(owners).append(" ]");
                }
                if (!StringUtils.isEmpty(phones)) {
                    builder.append("[负责人手机号码: ").append(phones).append(" ]");
                }

                return builder.toString();
            }
        } catch (Exception ex) {
            Cat.logError("build project contact info error for domain: " + domainName, ex);
        }

        return "";
    }

    protected final Map<Object, Object> generateAlertTemplateInfoMap(AlertEntity alert) {
        String domain = alert.getGroup();
        String contactInfo = buildContactInfo(domain);
        Map<Object, Object> map = new HashMap<Object, Object>();

        map.put("domain", domain);
        map.put("content", alert.getContent());
        map.put("level", alert.getLevel().getLevel());
        map.put("date", m_format.format(alert.getDate()));
        map.put("linkDate", M_LINK_FORMAT.format(alert.getDate()));
        map.put("contactInfo", contactInfo);

        String[] fields = alert.getMetric().split("-");

        map.put("type", fields[0]);
        if (fields.length > 1) {
            map.put("name", fields[1]);
        }

        map.put("catWebServer", System.getProperty("cat.web.server", ""));

        return map;
    }
}
