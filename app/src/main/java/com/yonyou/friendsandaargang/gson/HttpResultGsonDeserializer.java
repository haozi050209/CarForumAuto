package com.yonyou.friendsandaargang.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yonyou.friendsandaargang.network.HttpResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by shibing on 18/3/15.
 */

public class HttpResultGsonDeserializer  implements JsonDeserializer<HttpResult<?>> {

    @Override
    public HttpResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonData = jsonObject.has("content") ? jsonObject.get("content") : null;

        HttpResult httpResult = new HttpResult();
        httpResult.setReturnCode(jsonObject.has("returnCode") ? jsonObject.get("returnCode").getAsInt() : 0);
        httpResult.setReturnMsg(jsonObject.has("returnMsg") ? jsonObject.get("returnMsg").getAsString() : "");

        //处理Data空串情况
        if (jsonData != null && jsonData.isJsonObject()) {
            //指定泛型具体类型
            if (type instanceof ParameterizedType) {
                Type itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
                Object item = jsonDeserializationContext.deserialize(jsonData, itemType);
                httpResult.setContent(item);
            } else {
                //未指定泛型具体类型
                httpResult.setContent(jsonData);
            }
        } else {
            httpResult.setContent(null);
        }

        return httpResult;
    }

}
