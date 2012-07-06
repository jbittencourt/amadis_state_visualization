/*
 * ProjectVisualization.java
 *
 * Created on 13 de Dezembro de 2005, 17:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufrgs.lec.amadis.applets.StateVisualization.ProjectPulse;

import br.ufrgs.lec.amadis.core.Project;
import java.awt.geom.Rectangle2D;

/**
 * ProjectPulse.ProjectVisualization is the graphical representation of the
 * br.ufrgs.lec.amadis.core.ProjectVisualization. Is contains the data needed
 * by ProjectPulse to render the small box that represent the
 * project in the applet canvas. Its receveis a series os properties
 * from it setters and store some in the project propertie or in
 * it's own properties.
 * 
 * @author juliano
 */
public class ProjectVisualization extends br.ufrgs.lec.amadis.core.Project {

    
    //projetct data
    private int numMembers;
    private int recentness;
        
    //data for visualizatio7n
    private double speed;
    private double amplitude;
    private double length = 20.0;
    private boolean userProject = false;
    private double x;
    private double y;
    
    public boolean isSelected = false;
    
    private Rectangle2D.Float rect;
    
    /**
     * Creates a new instance of ProjectVisualization
     */
    public ProjectVisualization() {

    }

    
    /**
     * The above getter and setters are used to properties
     * of the own project
     **/
    
    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public int getRecentness() {
        return recentness;
    }

    public void setRecentness(int recentness) {
        this.recentness = recentness;
    }

   
    /**
     * The above functions are used to render the the project in the canvas
     **/
    
    
    private void createRect() {
        int size = 10+this.getNumMembers();
        if(size>25) size = 25;
        this.setRect(new Rectangle2D.Float(0, 0, size, size));
    }
    
    
    public Rectangle2D.Float getRect() {
        if(this.rect==null) {
            createRect();
        }
        
        return this.rect;
    }
    
    
    /*  Function:
     *    y(x,t) = ym sen[k(x + vt)] = ym sen[kvt]
     *  where:
     *    ym  é a amplitude da onda
     *    k comprimento de onda
     *    
     */
    
    public void step(int maxX, int maxY) {
        //int max = y - 10;
        //int min = 10;
        double amp = getAmplitude() * (double) (maxY-30)/2;
        
        if(this.getX()>maxX) this.setX(0);
           
        this.setX(this.getX() + getSpeed());
        
        /**
         * The math formula is a simple senoidal function
         * f(x) = A * sin(pi * x / c )
         * where:
         *  A = amplitude of the wave
         *  c = length of the wave
         *  
         **/
        this.setY(amp * Math.sin(Math.PI * this.getX() / 20.0));
    
        if(this.getRect()==null) {
            createRect();
        }
        this.getRect().x = (float) this.getX();
        this.getRect().y = (float) this.getY()+(maxY/2);
    }

    public boolean isUserProject() {
        return userProject;
    }

    public void setUserProject(boolean value) {
        this.userProject=value;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRect(Rectangle2D.Float rect) {
        this.rect = rect;
    }
    
    
}



