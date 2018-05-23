/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import Entity.Evenement;
import Service.EvenementService;
import Service.UserService;
import com.codename1.components.FloatingHint;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Date;
import jdk.nashorn.internal.objects.NativeString;

/**
 *
 * @author houss
 */
public class EvenementModifier extends BaseForm {

      Label im = new Label();
    public EvenementModifier(Resources res,Evenement e) {
        super("Modifier un evenement", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Accueil");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(ev -> {});
        
         TextField nom = new TextField(e.getNom(), "Nom", 20, TextField.ANY);
        TextField lieu = new TextField(e.getLieu(), "Lieu", 20, TextField.ANY);
        TextField description = new TextField(e.getDescription(), "Description", 20, TextField.ANY);
        TextField nbrmax = new TextField(String.valueOf(e.getNbr_max_participant()), "Nombre de participants", 20, TextField.ANY);
        
               
        nom.setUIID("TestField1");
          lieu.setUIID("TestField1");
            description.setUIID("TestField1");
              nbrmax.setUIID("TestField1");

        nom.setSingleLineTextArea(true);
        lieu.setSingleLineTextArea(true);
        description.setSingleLineTextArea(false);
        nbrmax.setSingleLineTextArea(true);

        Button Modifier = new Button("Modifier");

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);

        
      datePicker.setDate(e.getDate());
          datePicker.setUIID("TestField1");
          
       Label l1=new Label();
       Label l2=new Label();
          Label l3=new Label();
        Container content = BoxLayout.encloseY(
                l1,l2,l3,
                 new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(lieu),
                createLineSeparator(),
                datePicker,
                createLineSeparator(),
                new FloatingHint(nbrmax),
                createLineSeparator(),
                new FloatingHint(description),
               
                createLineSeparator(),
                l3,
                l3,
                Modifier
        );
        content.setScrollableY(true);
        
        Modifier.addActionListener(l->{
            
            System.out.println("button modifier pressed");
            
            e.setNom(nom.getText());
            e.setLieu(lieu.getText());
            e.setDescription(description.getText());
            e.setNbr_max_participant(Integer.valueOf(nbrmax.getText()));
            
              e.setDate(datePicker.getDate());
            
            
            System.out.println("**********date avant transformation : "+datePicker.getDate());
            
            String date=  TrnsformerDate(String.valueOf(datePicker.getDate()));
            System.out.println("**************date: "+date);
            
             EvenementService es=new EvenementService();     
             es.ModifierEvenement(e,date);
             System.out.println("modifier un evenement ");
             removeAll();
           new EvenementProfile(res).show();
             
        });
        add( content);
        show();
        
        
    }
    
      public String TrnsformerDate(String Date)
    { 
        String dat =null;
        
        ArrayList<String> mois=new ArrayList<>();
        mois.add("jan");
        mois.add("feb");
        mois.add("mar");
         mois.add("apr");
        mois.add("may");
        mois.add("jun");
         mois.add("jul");
        mois.add("aug");
        mois.add("sep");
         mois.add("oct");
        mois.add("nov");
        mois.add("dec");
       
       String m= NativeString.substr( Date,4, 3);
       String d= NativeString.substr( Date,8 ,2);
       String a=NativeString.substr(Date,24, 4);
       
       for (int i=0;i<mois.size();i++)
       {
           if (m.toUpperCase().equalsIgnoreCase(mois.get(i)))
           {
               m=String.valueOf(i+1);
           }
       }
       
       dat=a+"-"+m+"-"+d;
        
        return dat;
    }
}
