/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import Entity.Evenement;
import Service.UserService;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author houss
 */
public class TicketForm extends BaseForm{

    public TicketForm(Resources res ,Evenement e) {
        
        setUIID("SignIn");
        
       
    
       Container c1 = new Container(BoxLayout.y());
       c1.getStyle().setMargin(70, 20, 20, 20);
      
        TextArea t1= new TextArea();
        BrowserComponent browser = new BrowserComponent();
                            browser.setURL("https://chart.googleapis.com/chart?cht=qr&chl=" +"Monsieur : "+ UserService.currentUser.getUsername() +" est invité à l'evenement: \n  "+ e.getNom()+"lieu: "+e.getLieu()+"Date: "+e.getDate() + "&chs=160x160&chld=L|0");
        
                            
                            String message="cher(ère) Monsieur(Mme) : "+UserService.currentUser.getUsername()+"\n "
                                    + "Merci d'avoir participé a l evenenement veiller consulter les informations "
                                    + "\n de l'evenement dans le QR code. ";
      t1.setSingleLineTextArea(false);
      t1.setText(message);
     t1.setEditable(false);
     t1.getStyle().setBgTransparency(100);
     Button retour= new Button("Retourner");
      c1.add(t1);
     c1.add(retour);
     c1.add( browser);
         add(c1);   
         show();
         
     retour.addActionListener((ActionEvent l)->{ 
       showBack();
    // new EvenementAccueil(res).show();
     
    });
    
    
    
}
    }
    

