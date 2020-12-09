package com.bjtu.redis;

import com.bjtu.redis.actions.Action;
import com.bjtu.redis.counters.Counter;
import com.bjtu.redis.jedis.JedisUtil;
import com.bjtu.redis.jsonhelpers.ActionJsonHelper;
import com.bjtu.redis.jsonhelpers.CounterJsonHelper;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DoAction {

    Action action;
    private final ArrayList<String> readCounter;
    private final ArrayList<String> writeCounter;

    private final ArrayList<Counter> readCounterObj;
    private final ArrayList<Counter> writeCounterObj;

    public DoAction(ActionJsonHelper jsh){
        this.action=jsh.getAction();
        readCounter=jsh.getReadCounter();
        writeCounter=jsh.getWriteCounter();
        readCounterObj = new ArrayList<Counter>();
        writeCounterObj = new ArrayList<Counter>();
        initCounters();
    }

    private void initCounters(){
        for(String readName:readCounter){
            Counter counter = new Counter(readName);
            new CounterJsonHelper(counter);
            readCounterObj.add(counter);
        }
        for(String writeName:writeCounter){
            Counter counter = new Counter(writeName);
            new CounterJsonHelper(counter);
            writeCounterObj.add(counter);
        }
    }

    /*
    Operate according to counter array
     */
    public void Do(){
        //Read all read counters
        for(Counter counter : readCounterObj){
            switch (counter.type){
                case "num":
                    System.out.println("The Value Of "+counter.key+" Is "+JedisUtil.getValueNum(counter.key));
                    break;
                case "hash":
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
                    System.out.print("begin time>>");
                    Scanner ms = new Scanner(System.in);
                    String begin = ms.nextLine();
                    System.out.print("end time>>");
                    Scanner ms2 = new Scanner(System.in);
                    String end = ms2.nextLine();

                    try {
                        Date from = format.parse(begin);
                        Date to = format.parse(end);

                        //Get the hash table of time-valueField
                        Map<String,String> map = JedisUtil.getHashMap(counter.key);
                        Set<String> keys = map.keySet();
                        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int ret = 0;
                        for(String key:keys){
                            Date it = format2.parse(key);
                            if(it.after(from)&&it.before(to)){
                                String value = map.get(key);
                                ret+=Integer.parseInt(value);
                            }
                        }
                        System.out.println("In this period count has increased"+ret);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    break;
                case "list":
                    List<String> log = JedisUtil.getArray(counter.key);
                    for(String s:log){
                        System.out.println(s);
                    }
                    break;
                case "str":
                    System.out.println(JedisUtil.getValueNum(counter.key));
                    break;
                case "set":
                    Set<String> times = JedisUtil.getSet(counter.key);
                    for(String time : times){
                        System.out.println(time);
                    }
                    break;
                default:
                    System.out.println(counter);
                    break;
            }
        }
        //Write all write counter
        for(Counter counter : writeCounterObj){
            switch (counter.type){
                case "num":
                    JedisUtil.setIncrNum(counter.key,counter.valueField);
                    break;
                case "hash":
                    //time stamp
                    //storage structure：keyField-time-valueField
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    JedisUtil.setHashPush(counter.key,timestamp.toString(),counter.valueField+"");
                    break;
                case "list":
                    String time = new Timestamp(System.currentTimeMillis()).toString();
                    int value = counter.valueField;
                    String write = "The counter increase "+value+" at "+time;
                    JedisUtil.writeList(counter.key,write);
                    break;
                case "set":
                    String time2 = new Timestamp(System.currentTimeMillis()).toString();
                    JedisUtil.addSet(counter.key,time2);
                    break;
                default:
                    System.out.println(counter);
                    break;
            }
        }
    }


}
