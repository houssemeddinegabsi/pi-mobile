/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author houss
 */
public class ProfileHome extends BaseForm {

    public ProfileHome(Resources res) {
        super("Profile Home", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile Home");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
         int height = Display.getInstance().convertToPixels(32f);
       int width = Display.getInstance().convertToPixels(32f);
        Image img= res.getImage("evenement.jpg");
        
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
      
        Label lb1=new Label("     Mes Evenemeent");
        
        Container cntEvenement= new Container(new BoxLayout(BoxLayout.Y_AXIS));
       
        cntEvenement.add(image );
        image.setVerticalAlignment(CENTER);
        cntEvenement.add(lb1);
        lb1.setVerticalAlignment(CENTER);
         
        
         image.addActionListener(ev ->new EvenementProfile(res));
         lb1.addPointerPressedListener(v ->new EvenementProfile(res));
         
        Image img2= res.getImage("animal1.jpg");
       Button image2 = new Button(img2.fill(width, height));
       image2.setUIID("Label");
      
        Label lb2=new Label("     Mes Animaux");
      
        Container cntAnimaux= new Container(new BoxLayout(BoxLayout.Y_AXIS));
         
        
         cntAnimaux.add(image2);
          cntAnimaux.add(lb2);
       
          image2.addActionListener(ev ->new AnimauxProfile(res));
         lb2.addPointerPressedListener(v ->new AnimauxProfile(res));
         
          Image img3= res.getImage("animal1.jpg");
       Button image3 = new Button(img3.fill(width, height));
       image3.setUIID("Label");
      
        Label lb3=new Label("     Mes Annonce");
      
        Container cntAnnonce= new Container(new BoxLayout(BoxLayout.Y_AXIS));
         
        cntAnnonce.add(image3 );
         cntAnnonce.add(lb3  );
         
         image3.addActionListener(ev ->new AnnonceProfile(res));
         lb3.addPointerPressedListener(v ->new AnnonceProfile(res));
        
         Image img4= res.getImage("login-user-icon.png");
       Button image4 = new Button(img4.fill(width, height));
       image4.setUIID("Label");
      
        Label lb4=new Label("     Mon Profile");
       
        Container cntProfil= new Container(new BoxLayout(BoxLayout.Y_AXIS));
         
        cntProfil.add(image4);
         cntProfil.add(lb4);
         
         image4.addActionListener(ev ->new ProfileForm(res));
         lb4.addPointerPressedListener(v ->new ProfileForm(res));
         
         
        Container Cnt1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Cnt1.add(cntEvenement);
        Cnt1.add(cntAnimaux);
        
         Container Cnt2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Cnt2.add(cntAnnonce);
        Cnt2.add(cntProfil);
        
          Container Cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Cnt.add(Cnt1);
        Cnt.add(Cnt2);
        
        add(Cnt);
        show();
        
    }
    
        
}
