package nju.edu.uml.webumldesigner.util;

import com.google.gson.Gson;

public class JsonUtil {
    //    private Gson gson = new Gson();
    public String toJson(Object object) {
        String str = new Gson().toJson(object);
        return str;
    }

    public Object fromJson(String str) {
        Object object = new Gson().fromJson(str, Object.class);
        return object;
    }
}
