package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function;

import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Entity;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class BuildCSV {
    private static final String USER_SCHEMA = "PSN_CODE:ID,NAME,NAME_EN,POSITION,PHONE_NUM,ID,ID_TYPE,USER";
    private static final String REWARD_SCHEMA = "reward_id:ID,zh_title,en_title,authors,publish_date,REWARD";
    private static final String PROJECT_SCHEMA = "PRJ_CODE:ID,ZH_TITLE,EN_TITLE,ORG_CODE,GRANT_CODE,GRANT_NAME,SUBJECT_CODE1,TOTAL_AMT,HIGH_TECH_NO,PROJNATURE,PROJECT";
    private static final String ORGANIZATION_SCHEMA = "REGID:ID,BANKORGNAME,BANKNAME,YHZH,ORGTYPE,BELONGNET,PNO,ZIPCODE,REGYEAR,REGTYPE,REGTYPE2,EN_NAME,ORG_ID,IS_CONFIRM,FAREN,TOUCHWG,CITY,PROVINCE,INTERNAL_ORG,PRINTNO,TMP_CITY,TMP,SEQ_NO,BANK_SERIAL,KYBM_ADDRESS,ORGANIZATION";

    public static final int USER_SERVER_ID = 3;
    public static final int REWARD_SERVER_ID = 4;
    public static final int PROJECT_SERVER_ID = 2;
    public static final int ORGANIZATION_SERVER_ID = 1;

    private static final String RELATIONSHIP_SCHEMA_1 = "from_id,work_in,to_id";
    private static final String RELATIONSHIP_SHCEMA_2 = "from_id,apply,to_id";

    public static String buildSchema(int ID) {
        String schema;
        switch (ID) {
            case USER_SERVER_ID : schema = USER_SCHEMA; break;
            case REWARD_SERVER_ID : schema = REWARD_SCHEMA; break;
            case PROJECT_SERVER_ID : schema = PROJECT_SCHEMA; break;
            case ORGANIZATION_SERVER_ID : schema = ORGANIZATION_SCHEMA; break;
            default: schema = "wrong num!" ; break;
        }
        return schema;
    }

    public static String getSchemaName(int ID) {
        String name;
        switch (ID) {
            case USER_SERVER_ID : name = "USER"; break;
            case REWARD_SERVER_ID : name = "REWARD"; break;
            case PROJECT_SERVER_ID : name = "PROJECT"; break;
            case ORGANIZATION_SERVER_ID : name = "ORGANIZATION"; break;
            default: name = "wrong num!" ; break;
        }
        return name;
    }
    //通过测试
    public static String buildData(Entity entity, int ID) {
        String s = buildSchema(ID);
        String[] sl = s.split(",");
        StringBuilder sb = new StringBuilder();
        Class cl = entity.getClass();
        int i = 0;
        for (String s1 : sl) {
            if (i == 0) {
                s1 = s1.split(":")[0];
                i ++;
            } else if (i < sl.length - 1) {
                sb.append(",");
                i ++;
            } else {
                break;
            }
            Field field = null;
            try {
                field = cl.getDeclaredField(s1);
                field.setAccessible(true);
                if (field.getType().getName().equals(
                        java.lang.String.class.getName())) {
                    // String type
                    try {
                        sb.append(field.get(entity));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (field.getType().getName().equals(
                        java.lang.Long.class.getName())
                        || field.getType().getName().equals("long")) {
                    // Integer type
                    try {
                        sb.append(field.getLong(entity));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return sb.append(",").append(getSchemaName(ID)).toString();
    }

    public static void CSVSetOut(String file_name, Set<String> stringSet) throws IOException {
        File f = new File(file_name);
        if (!f.exists()) f.createNewFile();
        OutputStream fos = new FileOutputStream(f,true);
        OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        for (String s : stringSet) {
            writer.append(s).append("\r\n");
        }
        writer.close();
        fos.close();
    }

    public static void CSVOut(String file_name, String s) throws IOException {
        File f = new File(file_name);
        if (!f.exists()) f.createNewFile();
        OutputStream fos = new FileOutputStream(f);
        OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        writer.append(s);
        writer.close();
        fos.close();
    }
}
