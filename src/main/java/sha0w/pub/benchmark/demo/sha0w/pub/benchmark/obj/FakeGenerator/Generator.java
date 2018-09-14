package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator;

import org.neo4j.cypher.internal.frontend.v2_3.ast.Or;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function.BuildCSV;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Organization;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Project;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Reward;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.User;

import java.io.IOException;
import java.util.*;

/*
    U : O : R : P = 600 : 1 : 1300 : 250
 */
public class Generator {
    private List<User> userList = new ArrayList<>();
    private List<Project> projectList = new ArrayList<>();
    private List<Reward> rewardList = new ArrayList<>();
    private List<Organization> organizationList = new ArrayList<>();
    private int one_count = 1;
    private Stack<Project> countdown = new Stack<>();
    private final static String BASE_FILE_LOCATION = "C:\\Users\\NickXin\\Desktop\\实验csv\\20000";

    private final static Random num = new Random();

    private Generator(int n, int everytime) {
        for (int i = 0 ; i < n / everytime ; i ++) {
            try {
                new Generator(everytime).entityToCsv(n);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Generator(int orgNum) {
        //1 build org
        for (int i = 0; i < orgNum ; i ++) {
            organizationList.add(new Organization());
        }
        //2 for each org insert user
        for (Organization o : organizationList) {
            Project p = new Project();
            projectList.add(p);
            //4 for each project insert 5 reward
            int rewardNum = num.nextInt(10) + 1;
            for (int j = 0; j < rewardNum ; j ++) {
                Reward r = new Reward();
                r.gain(p);
            }
            //3 for each user insert project
            for (int i = 0 ; i < 600 ; i ++) {
                User u = new User();
                userList.add(u);
                o.addUser(u);
                if (num.nextDouble() * one_count < 1) { // 项目加人
                    one_count++;
                    u.apply(p);
                } else {
                    p = new Project();
                    projectList.add(p);
                    //4 for each project insert 5 reward
                    rewardNum = num.nextInt(10) + 1;
                    for (int j = 0; j < rewardNum ; j ++) {
                        Reward r = new Reward();
                        rewardList.add(r);
                        r.gain(p);
                        p.output(r);
                    }
                    one_count = 1;
                    p.participated(u);
                }
            }
        }

        Counter.getRelaInstance().report();
    }

    public Generator getNext(int orgNum) {
        return new Generator(orgNum);
    }

    public void entityToCsv(int num) throws IOException {
    //1\store all node
        //1.1 store all user
        String schema = BuildCSV.buildSchema(BuildCSV.USER_SERVER_ID);
        String user_rela_schema_1 = User.getApplyRelationshipSchema();
        String user_rela_schema_2 = User.getWorkForRelationshipSchema();
        Set<String> tmp = new HashSet<>();
        Set<String> rela_1 = new HashSet<>(); // apply
        Set<String> rela_2 = new HashSet<>();
        for (User u : userList) {
            tmp.add(BuildCSV.buildData(u,BuildCSV.USER_SERVER_ID));
            rela_1.addAll(u.getApplyRelationship());
            rela_2.addAll(u.getWorkForRelationship());
        }
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/user_head" + num + ".csv", schema);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/user" + num + ".csv", tmp);
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/user_apply_head" + num + ".csv", user_rela_schema_1);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/user_apply" + num + ".csv", rela_1);
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/user_work_for_head" + num + ".csv", user_rela_schema_2);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/user_work_for" + num + ".csv", rela_2);

        //1.2 store all project
        String prj_rela_schema_1 = Project.getOutputFromRelationshipSchema();
        String prj_rela_schema_2 = Project.getTakePartInRelationshipSchema();
        rela_1 = new HashSet<>(); // apply
        rela_2 = new HashSet<>();
        schema = BuildCSV.buildSchema(BuildCSV.PROJECT_SERVER_ID);
        tmp = new HashSet<>();
        for (Project u : projectList) {
            tmp.add(BuildCSV.buildData(u,BuildCSV.PROJECT_SERVER_ID));
            rela_1.addAll(u.getOutputFromRelationship());
            rela_2.addAll(u.getTakePartInRelationship());
        }
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/project_head" + num + ".csv", schema);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/project" + num + ".csv", tmp);
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/project_output_head" + num + ".csv", prj_rela_schema_1);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/project_output" + num + ".csv", rela_1);
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/project_take_part_in_head" + num + ".csv", prj_rela_schema_2);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/project_take_part_in" + num + ".csv", rela_2);

        //1.3 store all organization
        String org_rela_schema_1 = Organization.getOfferJobRelationshipSchema();
        rela_1 = new HashSet<>(); // apply
        schema = BuildCSV.buildSchema(BuildCSV.ORGANIZATION_SERVER_ID);
        tmp = new HashSet<>();
        for (Organization u : organizationList) {
            rela_1.addAll(u.getOfferJobRelationship());
            tmp.add(BuildCSV.buildData(u,BuildCSV.ORGANIZATION_SERVER_ID));
        }
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/organization_head" + num + ".csv", schema);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/organization" + num + ".csv", tmp);
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/org_gain_from_head" + num + ".csv", org_rela_schema_1);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/org_gain_from_output" + num + ".csv", rela_1);

        //1.4 store all reward
        String rwd_rela_schema_1 = Reward.getGainFromRelationshipSchema();
        rela_1 = new HashSet<>();
        schema = BuildCSV.buildSchema(BuildCSV.REWARD_SERVER_ID);
        tmp = new HashSet<>();
        for (Reward u : rewardList) {
            tmp.add(BuildCSV.buildData(u,BuildCSV.REWARD_SERVER_ID));
            rela_1.addAll(u.getGainFromRelationship());
        }
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/reward_head" + num + ".csv", schema);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/reward" + num + ".csv", tmp);
        BuildCSV.CSVOut(BASE_FILE_LOCATION + "/reward_gain_from_head" + num + ".csv", rwd_rela_schema_1);
        BuildCSV.CSVSetOut(BASE_FILE_LOCATION + "/reward_gain_from" + num + ".csv", rela_1);
    }

    public static void main(String args[]) {
        Generator generator = new Generator(20000,20);
//        System.out.println(BuildCSV.buildSchema(BuildCSV.ORGANIZATION_SERVER_ID));
//        System.out.println(BuildCSV.buildData(generator.organizationList.get(0),BuildCSV.ORGANIZATION_SERVER_ID));
//        System.out.println(generator.organizationList.get(0).getOfferJobRelationship().iterator().next());
//        System.out.println(generator.projectList.get(0).getTakePartInRelationship().iterator().next());
//        System.out.println(generator.userList.get(0).getApplyRelationship().iterator().next());
//        System.out.println(generator.rewardList.get(0).getGainFromRelationship().iterator().next());
//        try {
//            generator.entityToCsv(2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
