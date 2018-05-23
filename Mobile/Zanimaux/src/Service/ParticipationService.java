/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Evenement;
import Entity.Participation;
import Entity.User;
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
public class ParticipationService {
    
    public void participer(Participation p) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/projet_zanimaux/web/app_dev.php/mobile/participation/new/" + p.getEvenement().getId() + "/" + p.getUser().getId();
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
    
     public void abondonner(Participation p) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/projet_zanimaux/web/app_dev.php/mobile/participation/supprimer/" + p.getEvenement().getId() + "/" + p.getUser().getId();
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
    

    public ArrayList<Participation > getAll() {
       
        ArrayList<Participation> listParticipation = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/projet_zanimaux/web/app_dev.php/mobile/participation/All");
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
                       
                        Participation p=new Participation();
  
                        p.setId((int) Float.parseFloat(obj.get("id").toString()));
                        
                        LinkedHashMap<String,Object> objEvenement= (LinkedHashMap<String,Object> ) obj.get("idEvenement");
                      
                        Evenement e = new Evenement();

                        LinkedHashMap<String, Object> objUser = (LinkedHashMap<String, Object>) objEvenement.get("idMembre");

                        User u = new User();

                        u.setId((int) Float.parseFloat(objUser.get("id").toString()));
                        u.setUsername(objUser.get("username").toString());

                        e.setId_membre((User) u);

                        float id = Float.parseFloat(objEvenement.get("id").toString());
                        e.setId((int) id);

                        e.setNom(objEvenement.get("nom").toString());
                        e.setLieu(objEvenement.get("lieu").toString());
                        e.setDescription(objEvenement.get("description").toString());

                        float nbrmax = Float.parseFloat(objEvenement.get("nbrMaxParticipant").toString());
                        e.setNbr_max_participant((int) nbrmax);

                        float nbr = Float.parseFloat(objEvenement.get("nbrParticipant").toString());
                        e.setNbr_participant((int) nbr);

                        e.setImage((String) objEvenement.get("image").toString());

                        LinkedHashMap<String,Object> date = (LinkedHashMap<String,Object>) objEvenement.get("date"); 
                        
                        /*
                        double t = (double) date.get("timestamp");
                        long x = (long) (t * 1000L);
                         e.setDate(new Date(x));
*/
                        p.setEvenement(e);
                        
                        
                        
                        LinkedHashMap<String,Object> objUs= (LinkedHashMap<String,Object> ) obj.get("idUser");
                        User user1=new User();
                         user1.setId((int) Float.parseFloat(objUs.get("id").toString()));
                        user1.setUsername(objUs.get("username").toString());
                       
                        p.setUser(user1);
                        
                        listParticipation.add(p);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listParticipation;
    }
    
    public boolean findparticipation(Participation p)
    {
        
        for (int i=0;i<getAll().size();i++)
        {
            if((p.getEvenement().getId()==getAll().get(i).getEvenement().getId()) &&(p.getUser().getId()==getAll().get(i).getUser().getId())  )
            {
                 return true;       
            }
        }
        return false;
    }
    
}
