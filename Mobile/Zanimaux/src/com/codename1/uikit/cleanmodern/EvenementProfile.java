/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import Entity.AimerEvenement;
import Entity.Evenement;
import Entity.Participation;
import Entity.User;
import Service.AimerEvenementService;
import Service.EvenementService;
import Service.ParticipationService;
import Service.UserService;
import com.codename1.components.FloatingHint;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
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
public class EvenementProfile extends BaseForm {

    Label im = new Label();

    public EvenementProfile(Resources res) {

        super("Mes Evenement", BoxLayout.y());
        System.out.println("form d'affichage begin \n");
        header(res);
        show();

    }

    private void header(Resources res) {
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Mes Evenement");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
       
        addTab(swipe, res.getImage("dog.jpg"), spacer2, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();

        RadioButton all = RadioButton.createToggle("Mes Evenement", barGroup);
        all.setUIID("SelectBar");
        RadioButton ajout = RadioButton.createToggle("Ajouter ", barGroup);
        ajout.setUIID("SelectBar");
        RadioButton participation = RadioButton.createToggle("Mes Participation", barGroup);
        participation.setUIID("SelectBar");

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, all, participation, ajout),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });

        all.addActionListener(ev -> {
            removeAll();
            header(res);
            affichagemesevent(res);
        });

        participation.addActionListener(ev -> {
            removeAll();
            header(res);
            affichageparticipation(res);

        });

        ajout.addActionListener(ev -> {
            removeAll();
            header(res);
            affichageajout(res);
        });

        bindButtonSelection(all, arrow);
        bindButtonSelection(ajout, arrow);
        bindButtonSelection(participation, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        show();
    }

    private void affichageparticipation(Resources res) {
        EvenementService es = new EvenementService();
        Evenement E = new Evenement();
        ParticipationService ps = new ParticipationService();
        int ip = 0;
        System.out.println("begin Affichage mes participation");
        for (int i = 0; i < ps.getAll().size(); i++) {

            if (ps.getAll().get(i).getUser().getId() == UserService.currentUser.getId()) {
                
                ip++;
                
                System.out.println("affiche participation" + ip);

                addButtonparticipation(res, ps.getAll().get(i).getEvenement());
                show();

            }

        }
    }

    private void affichagemesevent(Resources res) {
        EvenementService es = new EvenementService();
        Evenement E = new Evenement();
        System.out.println("begin Affichage mes evenement");

        for (int i = 0; i < es.afficherAll().size(); i++) {
            if (es.afficherAll().get(i).getId_membre().getId() == UserService.currentUser.getId()) {
                System.out.println("Affichage de l'evenement : " +es.afficherAll().get(i).getId() );
                addButton(res, es.afficherAll().get(i));
                show();
            }

        }
    }

    private void affichageajout(Resources res) {
        TextField nom = new TextField("", "Nom", 20, TextField.ANY);
        TextField lieu = new TextField("", "Lieu", 20, TextField.ANY);
        TextField description = new TextField("", "Description", 20, TextField.ANY);
        TextField nbrmax = new TextField("", "Nombre de participants", 20, TextField.ANY);

        nom.setUIID("TestField1");
        lieu.setUIID("TestField1");
        description.setUIID("TestField1");
        nbrmax.setUIID("TestField1");

        nom.setSingleLineTextArea(true);
        lieu.setSingleLineTextArea(true);
        description.setSingleLineTextArea(false);
        nbrmax.setSingleLineTextArea(true);

        Button ajouter = new Button("ajouter");

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);

        datePicker.setDate(new Date());
        datePicker.setUIID("TestField1");

        Button imga = new Button("Choisir Image");
        imga.setUIID("TestField1");
        Style imgtStyle = new Style(imga.getUnselectedStyle());
        imgtStyle.setFgColor(0x4286f4);
        FontImage imgImage = FontImage.createMaterial(FontImage.MATERIAL_IMAGE, imgtStyle);

        imga.setIcon(imgImage);
        imga.setTextPosition(RIGHT);

        imga.addActionListener(l -> {
            ActionListener callback = e -> {
                if (e != null && e.getSource() != null) {
                    String filePath = (String) e.getSource();
                    System.out.println(filePath);

                    im.setText(filePath);
                    //  Now do something with this file
                }
            };

            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }

        });

        
        Label l2=new Label("");
         Label l3=new Label("");
          Label l4=new Label("");
           Label l5=new Label("");
        
        Label l1=new Label();
        Container content = BoxLayout.encloseY(
                l1,l2,
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
                imga,
                createLineSeparator(),
                l4,l5,
                ajouter
        );
        content.setScrollableY(true);
        ajouter.addActionListener(l -> {

            System.out.println("button ajout pressed");

            Evenement e = new Evenement(UserService.currentUser, nom.getText(), lieu.getText(), description.getText(), Integer.valueOf(nbrmax.getText()), im.getText());
            
            e.setDate(datePicker.getDate());
            
            
            System.out.println("**********date avant transformation : "+datePicker.getDate());
            
            String date=  TrnsformerDate(String.valueOf(datePicker.getDate()));
            System.out.println("**************date: "+date);
            
                     
            EvenementService es = new EvenementService();
            es.AjouterEvenement(e,date);
            System.out.println("ajouter un evenement ");
            removeAll();
            header(res);
            affichagemesevent(res);

        });
        add(content);
        show();
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image
                );

        swipe.addTab("", page1);
    }

    private void addButton(Resources res, Evenement e) {
        EvenementService es = new EvenementService();

        int height = Display.getInstance().convertToPixels(14f);
        int width = Display.getInstance().convertToPixels(20f);

        Button image = new Button(photo(height, width, e.getImage()));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        //cnt.setLeadComponent(image);
        TextArea ttitre = new TextArea(e.getNom());
        ttitre.setUIID("NewsTopLine");
        ttitre.setEditable(false);

        TextArea tlieu = new TextArea(e.getLieu());
        tlieu.setUIID("NewsTopLine");
        tlieu.setEditable(false);

        Label llieu = new Label(String.valueOf(e.getLieu()));
        llieu.setUIID("NewsTopLine");
        Style lieutStyle = new Style(llieu.getUnselectedStyle());
        lieutStyle.setFgColor(0x4286f4);
        FontImage lieuImage = FontImage.createMaterial(FontImage.MATERIAL_ROOM, lieutStyle);
        llieu.setIcon(lieuImage);
        llieu.setTextPosition(RIGHT);

        Label ldate = new Label(AffichageDate(String.valueOf(e.getDate())));
        ldate.setUIID("NewsTopLine");
        Style datetStyle = new Style(ldate.getUnselectedStyle());
        datetStyle.setFgColor(0x4286f4);
        FontImage dateImage = FontImage.createMaterial(FontImage.MATERIAL_DATE_RANGE, datetStyle);
        ldate.setIcon(dateImage);
        ldate.setTextPosition(RIGHT);

        TextArea tdescription = new TextArea(e.getDescription());
        tdescription.setUIID("NewsTopLine");
        tdescription.setEditable(false);

        Label participants = new Label(String.valueOf(e.getNbr_participant()) + " Participants  ", "NewsBottomLine");
            participants.setUIID("NewsTopLine");
         AimerEvenementService aes=new AimerEvenementService();

        Label like = new Label(aes.nbraimer(e.getId())+" likes ");
        Style heartStyle = new Style(like.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        like.setIcon(heartImage);
        like.setTextPosition(RIGHT);

        Label lsupprimer = new Label("   ");
        lsupprimer.setUIID("NewsTopLine");
        Style supprimertStyle = new Style(lsupprimer.getUnselectedStyle());
        supprimertStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimertStyle);
        lsupprimer.setIcon(supprimerImage);
        lsupprimer.setTextPosition(RIGHT);

        lsupprimer.addPointerPressedListener(l -> {
            es.SupprimerEvenement(e.getId());
            removeAll();
            header(res);
            affichagemesevent(res);
        });

        Label lmodifier = new Label("   ");
        lmodifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lmodifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        FontImage modifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lmodifier.setIcon(modifierImage);
        lmodifier.setTextPosition(RIGHT);

        lmodifier.addPointerPressedListener(l -> {
            new EvenementModifier(res, e);
        });

        cnt.add(BorderLayout.NORTH,
                ttitre
        );
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        llieu, ldate, tdescription
                ));
        Container cn = BorderLayout.west(BoxLayout.encloseX(like, participants));

        cn.add(BorderLayout.EAST,
                BoxLayout.encloseX(
                        lmodifier, lsupprimer
                ));
        add(cnt);
        add(cn);
        image.addActionListener(ev -> ToastBar.showMessage(e.getNom(), FontImage.MATERIAL_INFO));
    }

   
    private void addButtonparticipation(Resources res, Evenement e) {
        EvenementService es = new EvenementService();
        
        int height = Display.getInstance().convertToPixels(14f);
        int width = Display.getInstance().convertToPixels(20f);
        
        Button image = new Button(photo(height, width, e.getImage()));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        //cnt.setLeadComponent(image);
        TextArea ttitre = new TextArea(e.getNom());
        ttitre.setUIID("NewsTopLine");
        ttitre.setEditable(false);

        TextArea tlieu = new TextArea(e.getLieu());
        tlieu.setUIID("NewsTopLine");
        tlieu.setEditable(false);

        Label llieu = new Label(String.valueOf(e.getLieu()));
        llieu.setUIID("NewsTopLine");
        Style lieutStyle = new Style(llieu.getUnselectedStyle());
        lieutStyle.setFgColor(0x4286f4);
        FontImage lieuImage = FontImage.createMaterial(FontImage.MATERIAL_ROOM, lieutStyle);
        llieu.setIcon(lieuImage);
        llieu.setTextPosition(RIGHT);

        Label ldate = new Label(String.valueOf(e.getDate()));
        ldate.setUIID("NewsTopLine");
        Style datetStyle = new Style(ldate.getUnselectedStyle());
        datetStyle.setFgColor(0x4286f4);
        FontImage dateImage = FontImage.createMaterial(FontImage.MATERIAL_DATE_RANGE, datetStyle);
        ldate.setIcon(dateImage);
        ldate.setTextPosition(RIGHT);

        TextArea tdescription = new TextArea(e.getDescription());
        tdescription.setUIID("NewsTopLine");
        tdescription.setEditable(false);

        Label participants = new Label(String.valueOf(e.getNbr_participant()) + " Participants  ", "NewsBottomLine");

       participants.setUIID("NewsTopLine");
         AimerEvenementService aes=new AimerEvenementService();

        Label like = new Label(aes.nbraimer(e.getId())+" likes ");
        Style heartStyle = new Style(like.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        like.setIcon(heartImage);
        like.setTextPosition(RIGHT);

        Label lparticiper = new Label("   ");
        lparticiper.setUIID("NewsTopLine");
        Style participerStyle = new Style(lparticiper.getUnselectedStyle());
        participerStyle.setFgColor(0xf21f1f);
        
        FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK, participerStyle);
        lparticiper.setIcon(participerImage);
        lparticiper.setTextPosition(RIGHT);

        lparticiper.addPointerPressedListener(l -> {

            removeAll();
            header(res);
            affichageparticipation(res);
            
        });
        
        
        

        Label laimer = new Label("   ");
        laimer.setUIID("NewsTopLine");
        Style aimerStyle = new Style(laimer.getUnselectedStyle());
        aimerStyle.setFgColor(0xf7ad02);
        FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_UP, aimerStyle);
        laimer.setIcon(aimerImage);
        laimer.setTextPosition(RIGHT);

        laimer.addPointerPressedListener(l -> {

        });

        cnt.add(BorderLayout.NORTH,
                ttitre
        );
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        llieu, ldate, tdescription
                ));
        Container cn = BorderLayout.west(BoxLayout.encloseX(like, participants));

        cn.add(BorderLayout.EAST,
                BoxLayout.encloseX(
                        laimer, lparticiper
                ));
        add(cnt);
        add(cn);
        image.addActionListener(ev -> ToastBar.showMessage(e.getNom(), FontImage.MATERIAL_INFO));
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    public URLImage photo(int w, int h, String pa) {
        URLImage photo2 = URLImage.createToStorage(EncodedImage.createFromImage(Image.createImage(w, h, 0xffff0000), true), pa,
                "http://localhost/projet_zanimaux/web/images/evenement" + pa
        );
        photo2.fetch();
        return photo2;
    }

    public String AffichageDate(String Date)
    { 
        String dat =new String("");
        
       String day=NativeString.substr( Date,0, 3);
       String m= NativeString.substr( Date,4, 3);
       String d= NativeString.substr( Date,8 ,2);
       String a=NativeString.substr(Date,24, 4);
       dat=day+" "+d+" "+m+" "+a;
        
        return dat;
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
