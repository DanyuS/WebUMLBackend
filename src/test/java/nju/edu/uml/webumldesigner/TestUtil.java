package nju.edu.uml.webumldesigner;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUpdate() {
        User user = userDao.findUserByUid(1);
        String fidList = user.getFidList();
        List<Integer> fList = new Gson().fromJson(fidList, List.class);
        fList.add(1);
        userDao.save(user);
    }

//    @Test
//    public void testFromJson() {
//
//        //"['1', '2']"
//        List<Integer> fList = new Gson().fromJson("[]", List.class);
//        if(fList.size()>0){
//            System.out.println("---------------" + fList.get(0));
//        }else{
//            System.out.println("---------------no");
//        }
//    }
//
//    @Test
//    public void testToJson() {
//        List<String> fList = new ArrayList<String>();
//        fList.add("1");
//        fList.add("2");
//        String str = new Gson().toJson(fList);
//        System.out.println("++++++++++++++++++++" + str);
//        //++++++++++++++++++++[1,2]integer
//        //++++++++++++++++++++["1","2"]string
//    }
}
