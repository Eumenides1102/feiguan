package com.bjtu.redis.jsonhelpers;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjtu.redis.actions.Action;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActionJsonHelper {
    Action action;
    private final ArrayList<String> readCounter;
    private final ArrayList<String> writeCounter;


    public ActionJsonHelper(Action action){
        this.action=action;
        readCounter = new ArrayList<String>();
        writeCounter = new ArrayList<String>();
        initAction();
    }


    private void initAction(){
        try {
            String actionName = action.actionName;
            ClassLoader loader= FileUtil.class.getClassLoader();
            InputStream stream=loader.getResourceAsStream("actions.json");
            assert stream != null;
            String text = IOUtils.toString(stream,"utf8");
            JSONObject jsonObject = JSONObject.parseObject(text);
            JSONArray array = jsonObject.getJSONArray("actions");
            for (int i = 0; i < array.size(); i++){
                JSONObject jo = array.getJSONObject(i);
                String an = jo.getString("actionname");
                if(an.equals(actionName)){
                    JSONObject jo1 = jo.getJSONObject("feature_retrieve");
                    JSONObject jo2 = jo.getJSONObject("save_counter");
                    JSONArray Ja1= jo1.getJSONArray("counter");
                    JSONArray Ja2= jo2.getJSONArray("counter");

                    for(int j=0;j<Ja1.size();j++){
                        JSONObject jn = Ja1.getJSONObject(j);
                        readCounter.add(jn.getString("countname"));
                    }
                    for(int j=0;j<Ja2.size();j++){
                        JSONObject jn = Ja2.getJSONObject(j);
                        writeCounter.add(jn.getString("countname"));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Action getAction() {
        return action;
    }

    public ArrayList<String> getReadCounter() {
        return readCounter;
    }

    public ArrayList<String> getWriteCounter() {
        return writeCounter;
    }

    public int getReadCounterNum(){
        return readCounter.size();
    }

    public int getWriteCounterNum(){
        return writeCounter.size();
    }
}
