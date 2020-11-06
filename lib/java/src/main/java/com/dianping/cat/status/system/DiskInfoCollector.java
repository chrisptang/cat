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
package com.dianping.cat.status.system;

import com.dianping.cat.status.AbstractCollector;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiskInfoCollector extends AbstractCollector {

    @Override
    public String getId() {
        return "system.disk";
    }

    @Override
    public Map<String, String> getProperties() {

        Map<String, String> map = new LinkedHashMap<String, String>();

        File file = new File("/");

        map.put("system.disk.total", file.getTotalSpace() + "");
        map.put("system.disk.available", file.getUsableSpace() + "");

        return map;
    }
}
