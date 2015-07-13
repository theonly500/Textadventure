import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class CreateAndModifyXML {
    private double xPos;
    private double yPos;
    private double speed;
    private double movedDistanceY;
    private double movedDistanceX;

    public CreateAndModifyXML(){
        xPos=1;
        yPos=2;
        speed=3;
        movedDistanceX=4;
        movedDistanceY=5;
        prepareWriteData(10);
        writeData(10,1234);
    }

    public static void main(String[] args) {
        new CreateAndModifyXML();
    }
//
    public void prepareWriteData(int personNumber){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Data");
            doc.appendChild(rootElement);

            // step elements
            Element step = doc.createElement("Step");
            rootElement.appendChild(step);

            // set attribute to step element
            Attr attrNumber = doc.createAttribute("Number");
            attrNumber.setValue("0");
            step.setAttributeNode(attrNumber);

            // xPos elements
            Element xPosition = doc.createElement("X-Position");
            xPosition.appendChild(doc.createTextNode(""+xPos+""));
            step.appendChild(xPosition);

            // yPos elements
            Element yPosition = doc.createElement("Y-Position");
            yPosition.appendChild(doc.createTextNode(""+yPos+""));
            step.appendChild(yPosition);

            // Speed elements
            Element possibleSpeed = doc.createElement("Speed");
            possibleSpeed.appendChild(doc.createTextNode(""+speed+""));
            step.appendChild(possibleSpeed);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("DataByPerson-"+personNumber+".xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void writeData(int personNumber,int stepId){
        try {
            String filepath = "DataByPerson-"+personNumber+".xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            // Get the root element
            Node Data = doc.getFirstChild();

            Element step=doc.createElement("Step");
            Data.appendChild(step);

            Attr attr = doc.createAttribute("Number");
            attr.setValue(""+stepId+"");
            step.setAttributeNode(attr);

            // xPos elements
            Element xPosition = doc.createElement("X-Position");
            xPosition.appendChild(doc.createTextNode(""+xPos+""));
            step.appendChild(xPosition);

            // yPos elements
            Element yPosition = doc.createElement("Y-Position");
            yPosition.appendChild(doc.createTextNode(""+yPos+""));
            step.appendChild(yPosition);

            // Delta X elements
            Element deltaX = doc.createElement("Horizontal_Movement");
            deltaX.appendChild(doc.createTextNode(""+movedDistanceX+""));
            step.appendChild(deltaX);

            // Delta Y elements
            Element deltaY = doc.createElement("Vertical_Movement");
            deltaY.appendChild(doc.createTextNode(""+movedDistanceY+""));
            step.appendChild(deltaY);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
}
