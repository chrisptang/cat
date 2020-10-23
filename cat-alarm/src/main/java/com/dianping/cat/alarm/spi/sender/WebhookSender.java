package com.dianping.cat.alarm.spi.sender;

import com.dianping.cat.Cat;
import com.dianping.cat.alarm.sender.entity.Sender;
import com.dianping.cat.alarm.spi.AlertChannel;

public class WebhookSender extends AbstractSender {

    public static final String ID = AlertChannel.WEBHOOK.getName();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean send(SendMessageEntity message) {
        Sender sender = querySender();

        return sendWebhook(message, sender);
    }

    private boolean sendWebhook(SendMessageEntity message, Sender sender) {
        String domain = message.getGroup();
        String title = message.getTitle().replaceAll(",", " ");
        String content = message.getContent().replaceAll(",", " ").replaceAll("<a href.*(?=</a>)</a>", "");
        String urlPrefix = sender.getUrl();
        String urlPars = m_senderConfigManager.queryParString(sender);

        if (!urlPars.contains("json=")) {
            Cat.logError("No parameter 'json' found, please check AlertChannel:Webhook's configuration", new Exception());
            return false;
        }

        String json = extractJsonParameter(urlPars, "json");

        try {
            json = json.replace("${domain}", domain)
                    .replace("${title}", title)
                    .replace("${content}", content);
        } catch (Exception e) {
            Cat.logError(e);
        }

        return httpSend(sender.getSuccessCode(), sender.getType(), urlPrefix, json);
    }

    private static String extractJsonParameter(String urlQueryParameter, String parameterName) {
        String json = "{}";
        if (urlQueryParameter == null || !urlQueryParameter.contains(parameterName + "=")) {
            return json;
        }
        for (String valuePair : urlQueryParameter.split("&")) {
            if (valuePair.contains("json=")) {
                json = valuePair.split("=")[1];
                break;
            }
        }
        return json;
    }
}
