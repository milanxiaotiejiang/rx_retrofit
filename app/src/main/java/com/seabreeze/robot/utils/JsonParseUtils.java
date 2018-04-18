package com.seabreeze.robot.utils;

import com.seabreeze.robot.bean.AppBean;
import com.seabreeze.robot.bean.RecommendBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParseUtils {

    public static RecommendBean parseRecommendBean(String json) {
        RecommendBean recommendBean = null;
        List<String> bannerList = new ArrayList<>();
        List<RecommendBean.RecommendAppBean> recommendAppBeanList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data = jsonObject.getJSONArray("layoutData");
            //轮播图数据
            JSONArray bannerJson = data.getJSONObject(0).getJSONArray("dataList");
            for (int i = 0; i < bannerJson.length(); i++) {
                bannerList.add(bannerJson.getJSONObject(i).getString("icon"));
            }

            for (int m = 2; m < data.length(); m++) {
                if (m == 4 || m == 7) {
                    //广告
                    JSONArray dataList = data.getJSONObject(m).getJSONArray("dataList");
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < dataList.length(); i++) {
                        String icon = dataList.getJSONObject(i).getString("icon");
                        list.add(icon);
                    }
                    RecommendBean.RecommendAppBean recommendAppBean
                            = new RecommendBean.RecommendAppBean(null, list, null, 1);
                    recommendAppBeanList.add(recommendAppBean);
                } else {
                    JSONObject jsonData = data.getJSONObject(m).getJSONArray("dataList").getJSONObject(0);
                    String titleName = jsonData.getString("name");
                    JSONArray appList = jsonData.getJSONArray("list");
                    List<AppBean> recommendAppList = new ArrayList<>();
                    for (int i = 0; i < appList.length(); i++) {
                        JSONObject appJson = appList.getJSONObject(i);
                        recommendAppList.add(parseAppBean(appJson.toString()));
                    }
                    RecommendBean.RecommendAppBean recommendAppBean
                            = new RecommendBean.RecommendAppBean(titleName, null, recommendAppList, 0);
                    recommendAppBeanList.add(recommendAppBean);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        recommendBean = new RecommendBean(bannerList, recommendAppBeanList);
        return recommendBean;
    }

    public static AppBean parseAppBean(String json) {
        AppBean appBean = null;
        String appId = "";
        String appVersionName = "";
        String downCountDesc = "";
        String downurl = "";
        String icon = "";
        String intro = "";
        String memo = "";
        String name = "";
        String packageName = "";
        String sizeDesc = "";
        String stars = "";
        String aliasName = "";
        String detailId = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("appid"))
                appId = jsonObject.getString("appid");

            if (jsonObject.has("appVersionName"))
                appVersionName = jsonObject.getString("appVersionName");

            if (jsonObject.has("downCountDesc"))
                downCountDesc = jsonObject.getString("downCountDesc");

            if (jsonObject.has("downurl"))
                downurl = jsonObject.getString("downurl");

            if (jsonObject.has("icon"))
                icon = jsonObject.getString("icon");

            if (jsonObject.has("intro"))
                intro = jsonObject.getString("intro");

            if (jsonObject.has("memo"))
                memo = jsonObject.getString("memo");

            if (jsonObject.has("name"))
                name = jsonObject.getString("name");

            if (jsonObject.has("package"))
                packageName = jsonObject.getString("package");

            if (jsonObject.has("sizeDesc"))
                sizeDesc = jsonObject.getString("sizeDesc");

            if (jsonObject.has("stars"))
                stars = jsonObject.getString("stars");

            if (jsonObject.has("aliasName"))
                aliasName = jsonObject.getString("aliasName").substring(0, 1);

            if (jsonObject.has("detailId"))
                detailId = jsonObject.getString("detailId").substring(5);

            appBean = new AppBean(appId, appVersionName, downCountDesc, downurl, icon, intro, memo,
                    name, packageName, sizeDesc, stars, aliasName, detailId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appBean;
    }

}
