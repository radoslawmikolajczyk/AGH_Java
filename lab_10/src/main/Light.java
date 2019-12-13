package main;

import java.awt.*;

public class Light implements XmasShape {

    int x;
    int y;
    double scale;

    Color lineColor;
    GradientPaint grad;


    Light(int x,int y,double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }
    Light(int x,int y,double scale, GradientPaint grad){
        this(x,y,scale);
        this.setFillColor(grad);
    }
    Light(int x,int y,double scale,GradientPaint grad,Color lineColor){
        this(x,y,scale,grad);
        this.setLineColor(lineColor);
    }

    void setLineColor(Color color){
        this.lineColor = color;
    }
    void setFillColor(GradientPaint grad){
        this.grad = grad;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setPaint(grad);
        int [] x= new int[]{40,40, 60, 80,80};
        int [] y = new int[]{100,40, 20, 40,100};
        g2d.fillPolygon(x,y,x.length);
        g2d.setColor(this.lineColor);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}
