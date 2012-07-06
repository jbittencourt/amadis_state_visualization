/*
 * ProjectPulse.java
 *
 * Created on 29 de Maio de 2006, 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufrgs.lec.amadis.applets.StateVisualization.ProjectPulse;

import br.ufrgs.lec.amadis.core.Project;
import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JApplet;
import javax.swing.JPanel;
import org.xml.sax.SAXException;

/**
 *
 * @author juliano
 */
public class ProjectPulse extends JApplet {
    
    
    private Pulse pulse;
    private String url;
    
    private boolean initLoadingData = false;
    private boolean initLoadedData = false;
    private boolean initUrlNotInformed = false;
    
    public ProjectPulse() {
    }
    
    public void init() {
        String param;
        
        param = this.getParameter("amadisurl");
        
        if(param==null) {
            initUrlNotInformed = true;
        } else {
            url = param;
        }
        
        this.getContentPane().add(pulse = new Pulse());
    }
    
    public void start() {
        pulse.start();
    }
    
    public void stop() {
        pulse.stop();
    }
    
    
    
    
    class Pulse extends JPanel implements Runnable,MouseListener,MouseMotionListener {
        
        private Thread thread;
        private java.util.Vector projects;
        private BufferedImage bimg;
        
        public boolean paused = false;
        public Project selectedProject=null;
        public Point2D mousePosition;
        
        private int topHits = 100;
        
        
        public Pulse() {
            setDoubleBuffered(true);
        }
        
        
        public void setProjects(java.util.Vector projs) {
            this.projects = projs;
            
            int bestHit = 0;
            int moreRecent = 0;
            int lessRecent=-1;
            
            
            for (int i=0;i<this.projects.size(); i++) {
                ProjectVisualization proj = (ProjectVisualization) this.projects.get(i);
                
                if(proj.getHits()>bestHit) bestHit = proj.getHits();
                if(proj.getRecentness()>moreRecent) moreRecent = proj.getRecentness();
                if((proj.getRecentness()<lessRecent) || (lessRecent==-1)) lessRecent = proj.getRecentness();
            }
            
            
            for (int i=0;i<this.projects.size(); i++) {
                ProjectVisualization proj = (ProjectVisualization) this.projects.get(i);
                proj.setAmplitude( proj.getHits()/bestHit );
                proj.setSpeed( (proj.getRecentness()-lessRecent)/(moreRecent-lessRecent) ); //5 is the max speed
                if(proj.getSpeed() < 0.1) proj.setSpeed( 0.06 );
                if(proj.getSpeed() > 0.4 ) proj.setSpeed( 0.4 );
                proj.setX((double)-20 * i);
            }
            
            //only accepts clicks when the projects are alredy loaded
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            repaint();
        }
        
        public void loadProjects() {
            initLoadingData = true;
            java.util.Vector projects;
            
            org.w3c.dom.Document document = null;
            javax.xml.parsers.DocumentBuilder builder = null;
            
            javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            try {
                builder = builderFactory.newDocumentBuilder();
            } catch(javax.xml.parsers.ParserConfigurationException ex) {
                ex.printStackTrace();
            }
            
            try {
                document = builder.parse(new org.xml.sax.InputSource(url + "/ferramentas/projetos/projects_exportXML.php"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SAXException ex) {
                ex.printStackTrace();
            }
            ProjectsDOMScanner scanner = new ProjectsDOMScanner(document);
            projects = scanner.visitDocument();
            
            setProjects(projects);
            initLoadedData = true;
            initLoadingData = false;
        }
        
        
        public void mouseClicked(MouseEvent e) {
            boolean overBox = false;
            if(this.paused) {
                for (int i = 0; i < this.projects.size(); i++) {
                    ProjectVisualization proj = (ProjectVisualization) this.projects.get(i);
                    Rectangle2D.Float rect = proj.getRect();
                    if(rect.contains(e.getPoint())) {
                        overBox = true;
                        AppletContext context = getAppletContext();
                        URL temp = null;
                        try {
                            temp = new URL(url+"/ferramentas/projetos/projeto.php?frm_codProjeto="+String.valueOf(proj.getCodeProject()));
                        } catch (MalformedURLException ex) {
                            break;
                        }
                        context.showDocument(temp);
                        break;
                    }
                }
                if(!overBox) this.paused = false;
            } else {
                this.paused = true;
            }
        }
        
        public void mousePressed(MouseEvent e) { }
        public void mouseReleased(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
        public void mouseDragged(MouseEvent e) { }
        
        public void mouseMoved(MouseEvent e) {
            for (int i = 0; i < this.projects.size(); i++) {
                ProjectVisualization proj = (ProjectVisualization) this.projects.get(i);
                Rectangle2D.Float rect = proj.getRect();
                if(rect.contains(e.getPoint())) {
                    proj.isSelected = true;
                    this.selectedProject = proj;
                    mousePosition = e.getPoint();
                    repaint();
                } else {
                    if(proj.isSelected) {
                        proj.isSelected = false;
                        repaint();
                    } else {
                        proj.isSelected = false;
                    }
                }
            }
        }
        
        public void step(int w, int h) {
            if(initLoadedData) {
                for (int i = 0; i < this.projects.size(); i++) {
                    ((ProjectVisualization) this.projects.get(i)).step(w, h);
                }
            }
        }
        
        public Graphics2D createGraphics2D(int w, int h) {
            Graphics2D g2 = null;
            
            
            bimg = (BufferedImage) createImage(w, h);
            //reset(w, h);
            
            g2 = bimg.createGraphics();
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            return g2;
        }
        
        
        public void drawPulse(int w, int h, Graphics2D g2) {
            AffineTransform at = new AffineTransform();
            
            Color c1 = new Color(247,247,251);
            //Color c2 = new Color(255,215,238);
            Color cblock = new Color(114,203,229);
            Color cborder = new Color(221,215,238);
            Color cblockUser = new Color(102,153,204);
            Color cblockSelected = new Color(70,154,204);
            Color ctext = new Color(48,105,139);
            
            //font setup
            FontRenderContext frc = g2.getFontRenderContext();
            Font f = new Font("Helvetica",Font.PLAIN, 10);
            
            //set the background gradient
            GradientPaint gp = new GradientPaint(0,0,c1, 0, h, Color.white);
            g2.setPaint(gp);
            Rectangle2D.Float r = new Rectangle2D.Float(0,0,w,h);
            g2.fill(r);
            
            g2.setColor(cborder);
            r.width = w-1;
            r.height = h-1;
            g2.draw(r);
            
            if(!initLoadedData) {
                g2.setColor(ctext);
                if(!initUrlNotInformed) {
                    g2.drawString("Loading data", 50, 25);
                } else {
                    g2.drawString("You must inform the AMADIS Url to the applet by the PARAM Tag.", 50, 25);
                }
                
            } else {
                
                //draw the boxes
                
                if(this.projects!=null) {
                    int startX = 10;
                    
                    
                    for(int i=0; i<this.projects.size();i++) {
                        int x1,size;
                        ProjectVisualization proj = (ProjectVisualization) this.projects.get(i);
                        x1 = startX + (i*40);
                        
                        Rectangle2D.Float rect1 = proj.getRect();
                        
                        if(proj.isUserProject()) {
                            g2.setColor(cblockUser);
                        } else {
                            g2.setColor(cblock);
                        }
                        
                        if(proj.isSelected==true) {
                            g2.setColor(cblockSelected);
                            Rectangle2D.Float r2 = new Rectangle2D.Float(rect1.x-2,rect1.y-2,rect1.width+4,rect1.height+4);
                            g2.draw(r2);
                            
                            TextLayout layout = new TextLayout(selectedProject.getTitle(), f, frc);
                            Rectangle2D bounds = layout.getBounds();
                            
                            float xa = (float)mousePosition.getX()+15;
                            float ya = (float)mousePosition.getY();
                            
                            bounds.setRect(xa-3,(ya-bounds.getHeight()-3),bounds.getWidth()+6,bounds.getHeight()+6);
                            
                            g2.setColor(Color.white);
                            g2.fill(bounds);
                            g2.setColor(ctext);
                            g2.draw(bounds);
                            layout.draw(g2,xa,ya);
                        }
                        g2.fill(rect1);
                    }
                } else {
                    g2.drawString("No Projects Loaded.", 50, 25);
                }
            }
        }
        
        
        public void paint(Graphics g) {
            Dimension d = getSize();
            
            Graphics2D g2 = createGraphics2D(d.width, d.height);
            drawPulse(d.width, d.height, g2);
            g2.dispose();
            g.drawImage(bimg, 0, 0, this);
        }
        
        
        
        //THREAD METHODS;
        public void start() {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
        
        
        public synchronized void stop() {
            thread = null;
        }
        
        
        public void run() {
            Thread me = Thread.currentThread();
            while (thread == me) {
                try {
                    thread.sleep(33);
                } catch (InterruptedException e) { break; }
                if(!(initLoadedData || initLoadingData)) {
                    this.loadProjects();
                } else {
                    if(!this.paused)  {
                        Dimension d = getSize();
                        step(d.width, d.height);
                        repaint();
                    }
                }
            }
            thread = null;
        }
    }
    
}
