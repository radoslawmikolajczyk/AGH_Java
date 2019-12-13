package main;

import java.awt.*;

public class Trunk implements XmasShape {

    int x;
    int y;
    double scale;

    Color lineColor;
    Color fillColor;

    Trunk(int x, int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    Trunk(int x,int y,double scale,Color fillColor){
        this(x,y,scale);
        this.setFillColor(fillColor);
    }
    Trunk(int x,int y,double scale,Color fillColor,Color lineColor){
        this(x,y,scale,fillColor);
        this.setLineColor(lineColor);
    }

    void setLineColor(Color color){
        this.lineColor = color;
    }
    void setFillColor(Color color){
        this.fillColor = color;
    }


    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(this.fillColor);
        int [] x= new int[]{30,-30,-30, 30};
        int [] y = new int[]{100,100,0,0};
        g2d.fillPolygon(x,y,x.length);
        g2d.setColor(this.lineColor);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

}
