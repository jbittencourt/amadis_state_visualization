/*
 * File:           ProjectsDOMScanner.java
 * Generated from: projects_export.dtd
 * Date:           13 de Dezembro de 2005  17:19
 *
 * @author  juliano
 * @version generated by NetBeans XML module
 */
package br.ufrgs.lec.amadis.applets.StateVisualization.ProjectPulse;

/**
 *
 * This is a scanner of DOM tree.
 *
 * Example:
 * <pre>
 *     javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
 *     javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
 *     org.w3c.dom.Document document = builder.parse (new org.xml.sax.InputSource (???));
 *     <font color="blue">ProjectsDOMScanner scanner = new ProjectsDOMScanner (document);</font>
 *     <font color="blue">scanner.visitDocument();</font>
 * </pre>
 *
 * @see org.w3c.dom.Document
 * @see org.w3c.dom.Element
 * @see org.w3c.dom.NamedNodeMap
 */
public class ProjectsDOMScanner {
    /**
     * org.w3c.dom.Document document
     */
    org.w3c.dom.Document document;
    java.util.Vector projects;
        
    
    /**
     * Create new ProjectsDOMScanner with org.w3c.dom.Document.
     */
    public ProjectsDOMScanner(org.w3c.dom.Document document) {
        this.document = document;
    }
    
    /**
     * Scan through org.w3c.dom.Document document.
     */
    public java.util.Vector visitDocument() {
        java.util.Vector projects=null;
        
        org.w3c.dom.Element element = document.getDocumentElement();
        if ((element != null) && element.getTagName().equals("projects")) {
            projects = visitElement_projects(element);
        }
        if ((element != null) && element.getTagName().equals("project")) {
            visitElement_project(element);
        }
        if ((element != null) && element.getTagName().equals("title")) {
            visitElement_title(element);
        }
        if ((element != null) && element.getTagName().equals("hits")) {
            visitElement_hits(element);
        }
        
        
        return projects;
    }
    
    /**
     * Scan through org.w3c.dom.Element named projects.
     */
    java.util.Vector visitElement_projects(org.w3c.dom.Element element) { // <projects>
// element.getValue();
        
        java.util.Vector projects = new java.util.Vector();
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
// ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                    if (nodeElement.getTagName().equals("project")) {
                        projects.add(visitElement_project(nodeElement));
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
// ((org.w3c.dom.ProcessingInstruction)node).getTarget();
// ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
            }
        }
        
        return projects;
    }
    
    /**
     * Scan through org.w3c.dom.Element named project.
     */
    ProjectVisualization visitElement_project(org.w3c.dom.Element element) { // <project>
// element.getValue();
        ProjectVisualization project = new ProjectVisualization();
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            if (attr.getName().equals("userproject")) { 
                project.setUserProject(true);
            }
        }
        
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
// ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                    if (nodeElement.getTagName().equals("title")) {
                        project.setTitle(visitElement_title(nodeElement));
                    }
                    if (nodeElement.getTagName().equals("hits")) {
                        project.setHits(visitElement_hits(nodeElement));
                    }
                    if (nodeElement.getTagName().equals("members")) {
                        project.setNumMembers(visitElement_members(nodeElement));
                    }
                    if (nodeElement.getTagName().equals("recentness")) {
                        project.setRecentness(visitElement_recentness(nodeElement));
                    }
                    if (nodeElement.getTagName().equals("code")) {
                        project.setCodeProject(visitElement_code(nodeElement));
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
// ((org.w3c.dom.ProcessingInstruction)node).getTarget();
// ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
            }
        }
        
        return project;
    }
    
    /**
     * Scan through org.w3c.dom.Element named title.
     */
    String visitElement_title(org.w3c.dom.Element element) { // <title>
        String title = null;
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.TEXT_NODE:
                    title = ((org.w3c.dom.Text)node).getData();
                    break;
            }
        }
        
        return title; 
    }
    
    /**
     * Scan through org.w3c.dom.Element named hits.
     */
    int visitElement_hits(org.w3c.dom.Element element) { // <hits>
// element.getValue();
        int hits=0;
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.TEXT_NODE:
                    hits = Integer.parseInt( ((org.w3c.dom.Text)node).getData());
                    break;
            }
        }
        
        return hits;
    }
    
    /**
     * Scan through org.w3c.dom.Element named numMembers.
     */
    int visitElement_members(org.w3c.dom.Element element) { // <hits>
// element.getValue();
        int mem=0;
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.TEXT_NODE:
                    mem = Integer.parseInt( ((org.w3c.dom.Text)node).getData());
                    break;
            }
        }
        
        return mem;
    }

        int visitElement_recentness(org.w3c.dom.Element element) { // <hits>
// element.getValue();
        int mem=0;
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.TEXT_NODE:
                    mem = Integer.parseInt( ((org.w3c.dom.Text)node).getData());
                    break;
            }
        }
        
        return mem;
    }

    int visitElement_code(org.w3c.dom.Element element) { // <hits>
// element.getValue();
        int mem=0;
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.TEXT_NODE:
                    mem = Integer.parseInt( ((org.w3c.dom.Text)node).getData());
                    break;
            }
        }
        
        return mem;
    }
        
    
}
