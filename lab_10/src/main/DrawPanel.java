package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    Image img = Toolkit.getDefaultToolkit().getImage("choinka.jpg");
    List<XmasShape> shapes = new ArrayList<>();


    DrawPanel(){
        addTrunk();
        addBranches();
        addBubbles();
        addLights();
        addStars();
        addChains();
    }

    public void addBranches(){

        shapes.add(new Branch(500,340,1.2,true,new Color(4, 65, 4),Color.black));
        shapes.add(new Branch(500,340,1.2,false,new Color(4, 65, 4),Color.black));

        shapes.add(new Branch(500,280,1,true,new Color(4, 65, 4),Color.black));
        shapes.add(new Branch(500,280,1,false,new Color(4, 65, 4),Color.black));

        shapes.add(new Branch(500,220,0.8,true,new Color(4, 65, 4),Color.black));
        shapes.add(new Branch(500,220,0.8,false,new Color(4, 65, 4),Color.black));

        shapes.add(new Branch(500,180,0.6,true,new Color(4, 65, 4),Color.black));
        shapes.add(new Branch(500,180,0.6,false,new Color(4, 65, 4),Color.black));

        shapes.add(new Branch(500,150,0.4,true,new Color(4, 65, 4),Color.black));
        shapes.add(new Branch(500,150,0.4,false,new Color(4, 65, 4),Color.black));
    }

    public void addTrunk(){
        shapes.add(new Trunk(500,420,1,new Color(65, 31, 3),Color.black));
    }

    public void addBubbles(){
        shapes.add(new Bubble(400,200,0.5,new Color(255, 44, 90),Color.black));
        shapes.add(new Bubble(250,200,0.5,new Color(255, 44, 90),Color.black));
        shapes.add(new Bubble(270,160,0.5,new Color(69, 192, 255),Color.black));
        shapes.add(new Bubble(380,160,0.5,new Color(69, 192, 255),Color.black));
        shapes.add(new Bubble(420,253,0.5,new Color(255, 0, 20),Color.black));
        shapes.add(new Bubble(230,253,0.5,new Color(255, 0, 20),Color.black));
        shapes.add(new Bubble(210,306,0.5,new Color(255, 248, 5),Color.black));
        shapes.add(new Bubble(440,306,0.5,new Color(255, 248, 5),Color.black));
    }

    public void addLights(){
        shapes.add(new Light(400,400,0.3,new GradientPaint(0,0, new Color(255, 8,0),0,100, new Color(240,255,76)),Color.black));
        shapes.add(new Light(570,400,0.3,new GradientPaint(0,0, new Color(255,8,0),0,100, new Color(240,255,76)),Color.black));
        shapes.add(new Light(550,330,0.3,new GradientPaint(0,0, new Color(255,8,0),0,100, new Color(240,255,76)),Color.black));
        shapes.add(new Light(420,330,0.3,new GradientPaint(0,0, new Color(255,8,0),0,100, new Color(240,255,76)),Color.black));
        shapes.add(new Light(435,255,0.3,new GradientPaint(0,0, new Color(255,8,0),0,100, new Color(240,255,76)),Color.black));
        shapes.add(new Light(530,255,0.3,new GradientPaint(0,0, new Color(255,8,0),0,100, new Color(240,255,76)),Color.black));
    }

    public void addStars(){
        shapes.add(new Star(463,100,0.7,new Color(230, 255, 0),Color.black));
        for(int i = 0; i < 69; i++) {
            shapes.add(new Star((int)(Math.random()*1000), (int)(Math.random()*1000), 0.15, new Color(240, 255, 76), Color.black));
        }
    }

    public void addChains(){
        shapes.add(new Chain(458,310,0,0,100,70,1,new GradientPaint(0,0,new Color(5, 24, 255),0,100, new Color(255, 245, 62))));
        shapes.add(new Chain(440,380,0,0,100,70,1.1,new GradientPaint(0,0,new Color(5, 24, 255),0,100, new Color(255, 245, 62))));
        shapes.add(new Chain(465,244,0,0,100,70,0.8,new GradientPaint(0,0,new Color(5, 24, 255),0,100, new Color(255, 245, 62))));
        shapes.add(new Chain(475,196,0,0,100,70,0.6,new GradientPaint(0,0,new Color(5, 24, 255),0,100, new Color(255, 245, 62))));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.setColor(Color.RED);
        g.drawString("Merry Christmas", 380, 550);
        for (XmasShape s : shapes) {
            s.draw((Graphics2D) g);
        }
    }
}
