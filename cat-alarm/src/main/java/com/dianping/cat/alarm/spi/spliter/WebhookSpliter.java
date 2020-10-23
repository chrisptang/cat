package com.dianping.cat.alarm.spi.spliter;

import com.dianping.cat.alarm.spi.AlertChannel;

import java.util.regex.Pattern;

public class WebhookSpliter implements Spliter {

    public static final String ID = AlertChannel.WEBHOOK.getName();

    @Override
    public String process(String content) {
        String contentToSend = content.replaceAll("<br/>", "\n");
        contentToSend = Pattern.compile("<div.*(?=</div>)</div>", Pattern.DOTALL).matcher(contentToSend).replaceAll("");
        contentToSend = Pattern.compile("<table.*(?=</table>)</table>", Pattern.DOTALL).matcher(contentToSend).replaceAll("");

        return contentToSend;
    }

    @Override
    public String getID() {
        return ID;
    }
}
