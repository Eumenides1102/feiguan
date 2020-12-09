package com.bjtu.redis;

import com.bjtu.redis.actions.Action;
import com.bjtu.redis.jedis.JedisUtil;
import com.bjtu.redis.jsonhelpers.ActionJsonHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 *  SpringBootApplication
 * 用于代替 @SpringBootConfiguration（@Configuration）、 @EnableAutoConfiguration 、 @ComponentScan。
 * <p>
 * SpringBootConfiguration（Configuration） 注明为IoC容器的配置类，基于java config
 * EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
 * ComponentScan 自动扫描并加载符合条件的组件
 */
@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
        initStr();
        System.out.println("************  Redis Homework ************");

        int num=1;
        while(true) {
            System.out.println(num+ ". Please Choose Your Next Action:");
            System.out.println("\t A. plus count \t B. read count \t C.read freq \t D.read log \t E.project info \t F.Get all count incremented distinct timestamps");
            System.out.print("choice>>");
            Scanner ms = new Scanner(System.in);
            String choice = ms.nextLine();
            if(!choice.equals("A")&&!choice.equals("B")&&!choice.equals("C")&&!choice.equals("D")&&!choice.equals("E")&&!choice.equals("F")){
                System.out.println("\t Please input one of A,B,C,D,E,F");
            }else{
                switch (choice){
                    case "A":
                        System.out.println("Action:pluscount");
                        ActionJsonHelper jsh1 = new ActionJsonHelper(new Action("plus count"));
                        DoAction da1 = new DoAction(jsh1);
                        da1.Do();
                        break;
                    case "B":
                        System.out.println("Action:readcount");
                        ActionJsonHelper jsh2 = new ActionJsonHelper(new Action("read count"));
                        DoAction da2 = new DoAction(jsh2);
                        da2.Do();
                        break;
                    case "C":
                        System.out.println("Action:readfreq");
                        ActionJsonHelper jsh3 = new ActionJsonHelper(new Action("read freq"));
                        DoAction da3 = new DoAction(jsh3);
                        da3.Do();
                        break;
                    case "D":
                        System.out.println("Action:readlog");
                        ActionJsonHelper jsh4 = new ActionJsonHelper(new Action("read log"));
                        DoAction da4 = new DoAction(jsh4);
                        da4.Do();
                        break;
                    case "E":
                        System.out.println("Action:readSTR");
                        ActionJsonHelper jsh5 = new ActionJsonHelper(new Action("read STR"));
                        DoAction da5 = new DoAction(jsh5);
                        da5.Do();
                        break;
                    case "F":
                        System.out.println("Action:readSet");
                        ActionJsonHelper jsh6 = new ActionJsonHelper(new Action("read Set"));
                        DoAction da6 = new DoAction(jsh6);
                        da6.Do();
                        break;
                    default:
                        break;
                }
            }

            System.out.println("Will You Exit?");
            System.out.println("\t A. yes \t B. no");
            System.out.print("choice>>");
            Scanner sc = new Scanner(System.in);
            String ex = ms.nextLine();
            if(ex.equals("yes")||ex.equals("YES")||ex.equals("A")){
                break;
            }
            num++;
            System.out.println("**************************************");
        }
        System.out.println("************ Thanks ************");
    }

    private static void initStr(){
        String str = "*****************************************************\n" +
                "Using incr method of key string to realize accumulation counter\n" +
                "Get counter value and introduction by using key string get method\n" +
                "Set STR with key string\n" +
                "Use key hash to realize freq\n" +
                "Using key list and FIFO to implement log\n" +
                "The key set is used to get the non repeated time points of all counter operations by using the de duplication of the set\n" +
                "*****************************************************";
        JedisUtil.setStr("str",str);
    }
}
