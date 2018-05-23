/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;

/**
 *
 * @author houss
 */
public class UserService {

      public static User currentUser=new User();
      
    public String Login(String email, String password) {
        String msg1 = "";

        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/projet_zanimaux/web/app_dev.php/mobile/user/login"
                + "/" + email
                + "/" + password;
        con.setUrl(Url);

        
        con.addResponseListener((event) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        return Url;
    }

    public Boolean login1(String email, String password) {
        String msg1 = "";
        Label msg2=new Label();
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/projet_zanimaux/web/app_dev.php/mobile/user/login"
                + "/" + email
                + "/" + password;
        con.setUrl(Url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
               
                try {
                    Map<String, Object> event = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    // System.out.println(event);
                    //System.out.println(tasks);
                    
                    List<Map<String, Object>> list = (List<Map<String, Object>>) event.get("root");
                    for (Map<String, Object> obj : list) {
                        System.out.println("*********************obj******************************");
                        System.out.println(obj);
                        System.out.println("********************************************");
                        LinkedHashMap<String, Object> objUser = (LinkedHashMap<String, Object>) obj.get("user");

                        String msg = obj.get("message").toString();
                     
                                msg2.setText(msg);
                        if (msg.equals("connection etablie")) {
                            User u = new User();

                            u.setId((int) Float.parseFloat(objUser.get("id").toString()));
                            u.setUsername(objUser.get("username").toString());
                            u.setEmail(objUser.get("email").toString());
                            String psw = objUser.get("password").toString();
                            u.setPassword(psw);
                            u.setSexe(objUser.get("sexe").toString());
                            u.setTel((int) Float.parseFloat(objUser.get("telephone").toString()));
                            u.setImage(objUser.get("image").toString());
                            u.setVille(objUser.get("ville").toString());

                            currentUser=u;
                        }
                        
                    
                       
                        

                    }
                } catch (IOException ex) {
                }

            }

           
            
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        boolean test=false;
         msg1=msg2.getText();
        
       
        if (msg1.equals("connection etablie"))
        {
            test=true;
        }
      
        return test;
    }

}
