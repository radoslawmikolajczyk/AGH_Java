package lab_7;

public class BoundingBox {
    double xmin;
    double ymin;
    double xmax;
    double ymax;
    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y){
        if (isEmpty()){
            xmax = x;
            xmin = x;
            ymax = y;
            ymin = y;
        } else if(!contains(x,y)) {
            xmin = Math.min(xmin,x);
            xmax = Math.max(xmax,x);
            ymin = Math.min(ymin,y);
            ymax = Math.max(ymax,y);
        }
    }
    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     * @param x
     * @param y
     * @return
     */
    boolean contains(double x, double y){
        if (!isEmpty()){
            return (x <= xmax && x >= xmin) && (y <= ymax && y >= ymin);
        }
        return false;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        if (!isEmpty() && !bb.isEmpty()){
            return (bb.xmin >= this.xmin && bb.xmax <= this.xmax) && (bb.ymin >= this.ymin && bb.ymax <= this.ymax);
        }
        return false;
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        if (!isEmpty() && !bb.isEmpty()){
            return (this.xmin <= bb.xmax
                    && this.xmax >= bb.xmin
                    && this.ymin <= bb.ymax
                    && this.ymax >= bb.ymin);
        }
        return false;
    }
    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        if (isEmpty() || bb.contains(this))
            return bb;
        else if (contains(bb))
            return this;

        BoundingBox helper = new BoundingBox();
        helper.xmax = Math.max(this.xmax,bb.xmax);
        helper.xmin = Math.min(this.xmin,bb.xmin);
        helper.ymax = Math.max(this.ymax, bb.ymax);
        helper.ymin = Math.min(this.ymin,bb.ymin);
        return helper;
    }
    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    boolean isEmpty(){
        return Double.isNaN(xmax) && Double.isNaN(xmin) && Double.isNaN(ymax) && Double.isNaN(ymin);
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterX(){
        if (isEmpty())
            throw new RuntimeException("Bounding box is empty!");
        return (this.xmax + this.xmin)/2;
    }
    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterY(){
        if (isEmpty())
            throw new RuntimeException("Bounding box is empty!");
        return (this.ymax + this.ymin)/2;
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległosci euklidesowej możesz użyć wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx){
        if (isEmpty() || bbx.isEmpty())
            throw new RuntimeException("One of BoundingBox is empty.");
        final int RADIUS = 6371;

        double centerX = getCenterX();
        double centerY = getCenterY();
        double bbxCenterX = bbx.getCenterX();
        double bbxCenterY = bbx.getCenterY();

        double startLat = Math.min(centerY,bbxCenterY);
        double endLat = Math.max(centerY,bbxCenterY);
        double startLon = Math.min(centerX,bbxCenterX);
        double endLon = Math.max(centerX,bbxCenterX);

        double dLat = Math.toRadians(endLat - startLat);
        double dLon = Math.toRadians(endLon - startLon);

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.cos(startLat) * Math.cos(endLat) * Math.pow(Math.sin(dLon / 2),2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        return RADIUS*c;
    }

    boolean maxdistanceRTree(BoundingBox bbx, double maxdistance){
        xmax += maxdistance;
        ymax += maxdistance;
        ymin -= maxdistance;
        xmin -= maxdistance;

        if (intersects(bbx) || bbx.contains(this) || contains(bbx))
            return true;
        else
            return false;

    }
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("(XMAX: ").append(this.xmax)
                .append(", XMIN: ").append(this.xmin)
                .append(", YMAX: ").append(this.ymax)
                .append(", YMIN: ").append(this.ymin)
                .append(")");
        return s.toString();
    }
}
