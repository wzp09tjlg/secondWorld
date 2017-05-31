package com.example.wuzp.secondworld.network;

import android.content.Context;
import android.text.TextUtils;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.network.parse.User;
import com.example.wuzp.secondworld.utils.AppUtil;
import com.example.wuzp.secondworld.utils.CommonHelper;
import com.example.wuzp.secondworld.utils.MD5Helper;
import com.example.wuzp.secondworld.utils.Night;
import com.example.wuzp.secondworld.utils.Tool;
import com.example.wuzp.secondworld.utils.UUID;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tiny on 3/14/2017.
 */
@SuppressWarnings("All")
public class Net {
    /**
     * 请求前置参数
     * <p>
     * 请求公参, 所有的网络接口通过这个方法创建map参数
     *
     * @return
     */
    public static HashMap<String, String> newHashMap() {
        HashMap<String, String> params = new HashMap<String, String>();
        Context context = HApplication.getContext();
        User user = HApplication.getUser();
        params.put("app_key", ApiStores.HS_APP_KEY);
        params.put("app_version", AppUtil.getVersionName(context));
        params.put("channel", Tool.getChannel(context));
        params.put("theme", Night.getInstance().isNight() ? "black" : "normal");
        params.put("app_secret", ApiStores.HS_APP_SECRET);

        if (!TextUtils.isEmpty(user.getUid())) {
            params.put("app_uid", user.getUid());
        }

        String uuid = UUID.getInstance(context).getUUID();
        params.put("app_uuid", uuid);


        String access_token = user.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            params.put("access_token", access_token);
        }
        return params;
    }

    public static String getSign(Map<String, String> params) {
        Map<String, String> mSortMap = new TreeMap<String, String>(new MapKeyComparator());

        for (Map.Entry<String, String> entry : params.entrySet()) {
            mSortMap.put(entry.getKey(), entry.getValue());
        }

        String paramStr = CommonHelper.paramstoString(mSortMap, false);

        String md5Sign = MD5Helper.getMd5(paramStr);
        return md5Sign;
    }

    private static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}
