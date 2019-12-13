package main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public interface XmasShape {
    /**
     * Przesuwa poczatek układu w zadane miejsce, skaluje, jeśli trzeba obraca
     * @param g2d Graphics2D - kontekst graficzny
     */
    void transform(Graphics2D g2d);

    /**
     * Zawiera kod, który rysuje elementy
     * @param g2d Graphics2D - kontekst graficzny
     */
    void render(Graphics2D g2d);

    /**
     * Standardowa implementacja metody
     * @param g2d
     */
    default void draw(Graphics2D g2d){
        // Get the current transform
        AffineTransform saveAT = g2d.getTransform();
        // Perform transformation
        transform(g2d);
        // Render
        render(g2d);
        // Restore original transform
        g2d.setTransform(saveAT);
    }
}


