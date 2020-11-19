package com.dianping.cat.alarm.spi.sender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.alarm.sender.entity.Par;
import com.dianping.cat.alarm.sender.entity.Sender;
import com.dianping.cat.alarm.spi.AlertChannel;

import java.util.List;

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

        // 只需要一个json作为参数；
        List<Par> parList = sender.getPars();
        if (null == parList || parList.size() <= 0) {
            Cat.logError("No parameter configured for sender:" + sender, new Exception());
            return false;
        }
        String parJson = parList.get(0).getId();


        if (parJson.contains("json=")) {
            parJson = parJson.replace("json=", "");
        }

        try {
            //just for verification
            JSONObject jsonObject = JSON.parseObject(parJson);
            parJson = parJson.replace("${domain}", domain)
                    .replace("${title}", title)
                    .replace("${content}", content)
                    .replace("${type}", message.getType());
        } catch (Exception e) {
            Cat.logError(e);
        }

        return httpSend(sender.getSuccessCode(), sender.getType(), urlPrefix, parJson);
    }

    private static String extractJsonParameter(String urlQueryParameter, String parameterName) {
        String json = "{}";
        if (urlQueryParameter == null || !urlQueryParameter.contains(parameterName + "=")) {
            return json;
        }
        for (String valuePair : urlQueryParameter.split("&")) {
            if (valuePair.contains("json=")) {
                return valuePair.replace("json=", "");
            }
        }
        return json;
    }
}
