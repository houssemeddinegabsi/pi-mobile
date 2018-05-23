/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.AimerEvenement;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author houss
 */
public class AimerEvenementService {
    
     public void aimer(AimerEvenement a) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/projet_zanimaux/web/app_dev.php/mobile/aimer/new/" + a.getId_evenement() + "/" + a.getId_user() ;
        con.setUrl(Url);

     //   System.out.println("tt");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
          //  System.out.println(str);
//            if (str.trim().equalsIgnoreCase("OK")) {
//                f2.setTitle(tlogin.getText());
//             f2.show();
//            }
//            else{
//            Dialog.show("error", "login ou pwd invalid", "ok", null);
//            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
     public void aimerpas(AimerEvenement a) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/projet_zanimaux/web/app_dev.php/mobile/aimer/supprimer/" + a.getId_evenement() + "/" + a.getId_user() ;
        con.setUrl(Url);

     //   System.out.println("tt");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            //System.out.println(str);
//            if (str.trim().equalsIgnoreCase("OK")) {
//                f2.setTitle(tlogin.getText());
//             f2.show();
//            }
//            else{
//            Dialog.show("error", "login ou pwd invalid", "ok", null);
//            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    

    public ArrayList<AimerEvenement > getAll() {
       
        ArrayList<AimerEvenement> listParticipation = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/projet_zanimaux/web/app_dev.php/mobile/aimer/All");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    //System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> obj : list) {
                       
                        AimerEvenement p=new AimerEvenement();
  
                        p.setId((int) Float.parseFloat(obj.get("id").toString()));
                        
                        LinkedHashMap<String,Object> objEvenement= (LinkedHashMap<String,Object> ) obj.get("idEvenement");
                        p.setId_evenement((int) Float.parseFloat(objEvenement.get("id").toString()));
                        
                        LinkedHashMap<String,Object> objUser= (LinkedHashMap<String,Object> ) obj.get("idUser");
                        p.setId_user((int)Float.parseFloat(objUser.get("id").toString()));
                       
                       
                        listParticipation.add(p);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listParticipation;
    }
    
    public boolean findaimer(AimerEvenement p)
    {
        
        for (int i=0;i<getAll().size();i++)
        {
            if((p.getId_evenement()==getAll().get(i).getId_evenement()) &&(p.getId_user()==getAll().get(i).getId_user())  )
            {
                 return true;       
            }
        }
        return false;
    }
    
    public int nbraimer(int id)
    {
        int nbr=0;
        for (int i=0;i<getAll().size();i++)
        {
           if(getAll().get(i).getId_evenement()==id)
           {
               nbr++;
           }
        }
        return nbr;
    }
    
}
