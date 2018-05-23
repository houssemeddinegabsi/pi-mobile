/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import Entity.AimerEvenement;
import Entity.Evenement;
import Entity.Participation;
import Service.AimerEvenementService;
import Service.EvenementService;
import Service.ParticipationService;
import Service.UserService;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.share.FacebookShare;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import jdk.nashorn.internal.objects.NativeString;

/**
 *
 * @author houss
 */
public class EvenementAccueil extends BaseForm {

    public EvenementAccueil(Resources res) {

        super("Accueil Evenement ", BoxLayout.y());
        System.out.println("form d'affichage des evenement begin \n");
         //Dialog ip = new InfiniteProgress().showInifiniteBlocking(); 
       removeAll();
        header(res);
         show();
        affichageevenement(res);
       // ip.dispose();

    }

    private void header(Resources res) {
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Accueil Evenement");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

       
       // addTab(res.getImage("news-item.jpg"));
        addTab(res.getImage("dog.jpg"));
        /*
 Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
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
*/
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("All", barGroup);
        all.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Populair", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("My Favorites", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, all, popular),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);

        all.addActionListener(ev -> {
            removeAll();
            header(res);
             arrow.setVisible(true);
            updateArrowPosition(all, arrow);
           Dialog ip = new InfiniteProgress().showInifiniteBlocking();  
           affichageevenement(res);
        ip.dispose();
         
        });

        popular.addActionListener(ev -> {
            removeAll();
            header(res);
             arrow.setVisible(true);
            updateArrowPosition(popular, arrow);
             Dialog ip = new InfiniteProgress().showInifiniteBlocking();  
         affichagepopulair(res);
        ip.dispose();
           

        });
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

    }

    private void affichageevenement(Resources res) {
        EvenementService es = new EvenementService();
        Evenement E = new Evenement();

        System.out.println("begin l'affichge des evnement");
        for (int i = 0; i < es.afficherAll().size(); i++) {
            System.out.println("Affichage de l'evenement : " + es.afficherAll().get(i).getId());
            addButton(res, es.afficherAll().get(i));
            show();

        }
        System.out.println("end d'affichage");
    }

    private void affichagepopulair(Resources res) {
        EvenementService es = new EvenementService();
        Evenement E = new Evenement();

        System.out.println("begin l'affichage des evenement les plus populaire");

        for (int i = 0; i < es.afficherAll().size(); i++) {
            if (es.afficherAll().get(i).getNbr_participant() > 5) {
                System.out.println("Affichage de l'evenement : " + es.afficherAll().get(i).getId_membre());
                addButtonpopulaire(res, es.afficherAll().get(i));
                show();
            }

        }
        System.out.println("end d'affichage ");
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab( Image img) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
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
        add(page1);

       
    }

    private void addButton(Resources res, Evenement e) {

        EvenementService es = new EvenementService();

        int height = Display.getInstance().convertToPixels(20f);
        int width = Display.getInstance().convertToPixels(30f);

        Button image = new Button(photo(width,height,  e.getImage()));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        //cnt.setLeadComponent(image);
        TextArea ttitre = new TextArea(e.getNom());
        ttitre.setUIID("NewsTopLine");
        ttitre.setEditable(false);

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

        Label lparticiper = new Label("Participer");
        lparticiper.setUIID("NewsTopLine");
        Style participerStyle = new Style(lparticiper.getUnselectedStyle());

        lparticiper.setTextPosition(RIGHT);

        Participation p = new Participation(UserService.currentUser, e);
        ParticipationService ps = new ParticipationService();

        if (ps.findparticipation(p)) {
            lparticiper.setText("Abondonner");
            participerStyle.setFgColor(0xf21f1f);
            FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK, participerStyle);
            lparticiper.setIcon(participerImage);
        } else {
            lparticiper.setText("Participer");
            participerStyle.setFgColor(0xf21f1f);
            FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK_BORDER, participerStyle);
            lparticiper.setIcon(participerImage);
        }

        lparticiper.addPointerPressedListener(ev -> {
            if (lparticiper.getText().toUpperCase().equalsIgnoreCase("PARTICIPER")) {
                lparticiper.setText("Abondonner");
                participants.setText(String.valueOf(e.getNbr_participant()+1) + " Participants  ");
                participerStyle.setFgColor(0xf21f1f);
                FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK, participerStyle);
                lparticiper.setIcon(participerImage);
                ps.participer(p);
                System.out.println("Participer");
              //  new TicketForm(res,e).show();

            } else {
                lparticiper.setText("Participer");
                  participants.setText(String.valueOf(e.getNbr_participant()-1) + " Participants  ");
                participerStyle.setFgColor(0xf21f1f);
                FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK_BORDER, participerStyle);
                lparticiper.setIcon(participerImage);
                ps.abondonner(p);
                System.out.println("Abondonner");

            }

        });

        Button laimer = new Button();
        laimer.setUIID("NewsTopLine");
        Style aimerStyle = new Style(laimer.getUnselectedStyle());

        laimer.setTextPosition(RIGHT);

        AimerEvenement ae = new AimerEvenement(UserService.currentUser.getId(), e.getId());
      

        if (aes.findaimer(ae)) {
            laimer.setText("j'aime pas");
            aimerStyle.setFgColor(0xdd1616);
            FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_DOWN, aimerStyle);
            laimer.setIcon(aimerImage);
        } else {
            laimer.setText("j'aime");
            aimerStyle.setFgColor(0x4286f4);
            FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_UP, aimerStyle);
            laimer.setIcon(aimerImage);
        }

        laimer.addActionListener(ev -> {
            if (laimer.getText().toUpperCase().equalsIgnoreCase("J'AIME")) {
                
                laimer.setText("j'aime pas");
                
                aimerStyle.setFgColor(0xdd1616);
                FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_DOWN, aimerStyle);
                laimer.setIcon(aimerImage);
               aes.aimer(ae);
                like.setText((aes.nbraimer(e.getId())+1)+" likes ");
                System.out.println("AIMER");

            } else {
                laimer.setText("j'aime");
                
                aimerStyle.setFgColor(0x4286f4);
                FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_UP, aimerStyle);
                laimer.setIcon(aimerImage);
                aes.aimerpas(ae);
                 like.setText((aes.nbraimer(e.getId())-1)+" likes ");
                System.out.println("aimer pas");

            }

        });
        
         ShareButton sb=new ShareButton();
        sb.setText("Partager");
       sb.setTextToShare("je patage avec vous ce evenement :'"+e.getNom()+"'\n Lieu : "+e.getLieu()+" \n Date : "+e.getDate());
       

        cnt.add(BorderLayout.NORTH,
                ttitre
        );
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        llieu, ldate, tdescription,sb
                ));
        
        Container cn = BorderLayout.west(BoxLayout.encloseX(like, participants));
         add(cnt);
           add(createLineSeparator(0x4286f4));
if (e.getId_membre().getId()!=UserService.currentUser.getId())
{
        cn.add(BorderLayout.EAST,
                BoxLayout.encloseX(
                        laimer, lparticiper
 
                ));
         add(cn);
}
       add(createLineSeparator(0x4286f4));
         add(createLineSeparator(0x4286f4));
           add(createLineSeparator(0x4286f4));
             add(createLineSeparator(0x4286f4));
               add(createLineSeparator(0x4286f4));
       
        image.addActionListener(ev -> ToastBar.showMessage(e.getNom(), FontImage.MATERIAL_INFO));
    }

    private void addButtonpopulaire(Resources res, Evenement e) {
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

        Label lparticiper = new Label("Participer");
        lparticiper.setUIID("NewsTopLine");
        Style participerStyle = new Style(lparticiper.getUnselectedStyle());

        lparticiper.setTextPosition(RIGHT);

        Participation p = new Participation(UserService.currentUser, e);
        ParticipationService ps = new ParticipationService();

        if (ps.findparticipation(p)) {
            lparticiper.setText("Abondonner");
            participerStyle.setFgColor(0xf21f1f);
            FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK, participerStyle);
            lparticiper.setIcon(participerImage);
        } else {
            lparticiper.setText("Participer");
            participerStyle.setFgColor(0xf21f1f);
            FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK_BORDER, participerStyle);
            lparticiper.setIcon(participerImage);
        }

        lparticiper.addPointerPressedListener(ev -> {
            if (lparticiper.getText().toUpperCase().equalsIgnoreCase("PARTICIPER")) {
                lparticiper.setText("Abondonner");
                participerStyle.setFgColor(0xf21f1f);
                FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK, participerStyle);
                lparticiper.setIcon(participerImage);
                ps.participer(p);
                System.out.println("Participer");

                System.out.println(p.toString());

            } else {
                lparticiper.setText("Participer");
                participerStyle.setFgColor(0xf21f1f);
                FontImage participerImage = FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK_BORDER, participerStyle);
                lparticiper.setIcon(participerImage);
                ps.abondonner(p);
                System.out.println("Abondonner");

            }

            removeAll();
            header(res);
            affichagepopulair(res);
        });

          Label laimer = new Label();
        laimer.setUIID("NewsTopLine");
        Style aimerStyle = new Style(laimer.getUnselectedStyle());

        laimer.setTextPosition(RIGHT);

        AimerEvenement ae = new AimerEvenement(UserService.currentUser.getId(), e.getId());
     

        if (aes.findaimer(ae)) {
            laimer.setText("j'aime pas");
            aimerStyle.setFgColor(0xdd1616);
            FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_DOWN, aimerStyle);
            laimer.setIcon(aimerImage);
        } else {
            laimer.setText("j'aime");
            aimerStyle.setFgColor(0x4286f4);
            FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_UP, aimerStyle);
            laimer.setIcon(aimerImage);
        }

        laimer.addPointerPressedListener(ev -> {
            if (lparticiper.getText().toUpperCase().equalsIgnoreCase("J'AIME")) {
                laimer.setText("j'aime pas");
                aimerStyle.setFgColor(0xdd1616);
                FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_DOWN, aimerStyle);
                laimer.setIcon(aimerImage);
                ps.participer(p);
                System.out.println("AIMER");

            } else {
                laimer.setText("j'aime");
                aimerStyle.setFgColor(0x4286f4);
                FontImage aimerImage = FontImage.createMaterial(FontImage.MATERIAL_THUMB_UP, aimerStyle);
                laimer.setIcon(aimerImage);
                ps.abondonner(p);
                System.out.println("aimer pas");

            }

        });
        
        ShareButton sb=new ShareButton();
        sb.setText("Partager");
       sb.setTextToShare("je patage avec vous ce evenement :'"+e.getNom()+"'\n Lieu : "+e.getLieu()+" \n Date : "+e.getDate());
        
       
        cnt.add(BorderLayout.NORTH,
                ttitre
        );
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        llieu, ldate, tdescription,sb
                ));
        Container cn = BorderLayout.west(BoxLayout.encloseX(like, participants));

    add(cnt);
     add(createLineSeparator(0x4286f4));
         add(createLineSeparator(0x4286f4));
if (e.getId_membre().getId()!=UserService.currentUser.getId())
{
        cn.add(BorderLayout.EAST,
                BoxLayout.encloseX(
                        laimer, lparticiper
 
                ));
         add(createLineSeparator(0x4286f4));
         add(createLineSeparator(0x4286f4));
         add(cn);
         
} add(createLineSeparator(0x4286f4));
         add(createLineSeparator(0x4286f4));
       add(createLineSeparator(0x4286f4));
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
                "http://localhost/projet_zanimaux/web/images/evenement/" + pa
        );
        photo2.fetch();
        return photo2;
    }

    public String AffichageDate(String Date) {
        String dat = null;

        String day = NativeString.substr(Date, 0, 3);
        String m = NativeString.substr(Date, 4, 3);
        String d = NativeString.substr(Date, 8, 2);
        String a = NativeString.substr(Date, 24, 4);
        dat = day + " " + d + " " + m + " " + a;

        return dat;
    }
    
    

}