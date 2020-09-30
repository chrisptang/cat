package com.dianping.cat;

public final class CatConstants {
    public static final String CAT_STATE = "cat-state";
    public static final String CAT_PAGE_URI = "cat-page-uri";
    public static final String CAT_PAGE_TYPE = "cat-page-type";
    public static final String TYPE_REMOTE_CALL = "RemoteCall";
    public static final String TYPE_URL = "URL";
    public static final String TYPE_URL_FORWARD = "URL.Forward";
    public static final String SPLIT = ";";
    public static final char BATCH_FLAG = '@';
    public static final String CAT_SYSTEM = "System";

    // 用于支持从环境变量(java启动参数或者application.properties)配置CAT client；

    /**
     * @author ptang@leqee.com
     */
    public static final String CAT_HOSTS = "cat.hosts"; // ,分割的ip组
    public static final String CAT_HTTP_PORT = "cat.http.port";
    public static final String CAT_TCP_PORT = "cat.tcp.port";
    public static final String CAT_APP_ID = "cat.app.id";
    public static final String CAT_LOG_HOME = "cat.log.home";
    public static final String CAT_LOG_MAX_DAY = "cat.log.max.day";

    private CatConstants() {
    }
}
