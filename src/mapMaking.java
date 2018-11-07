
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.event.*;

public class mapMaking extends Application {

	// Creating a button	
	Button btnLoad = new Button ("Load");

	// Creating a canvas
	Canvas canvas = new Canvas(1000, 1200);

	public static void main(String[] args){
		launch (args);
	}
	public void start(Stage myStage){

		//		Initialising all the arrays
       
		String[] fName = new String[500];
		String[] distance = new String[500];
		int[] distanceInt = new int[500];
		int[] coorX= new int[500];
		int[] coorY= new int[500];
		int[] x1 = new int[500];		
		int[] x2= new int[500];
		int[] y1= new int[500];
		int[] y2= new int[500];
		String[] shName = new String[500];
		String[] roadFrom = new String[500];
		String[] roadTo = new String[500];
		Graph g = new Graph(false);
		myStage.setTitle("Navigation");
		Group root = new Group();
		GraphicsContext gc = canvas.getGraphicsContext2D();


		Scene myScene = new Scene(root);

		// Parsing the XML document		

		btnLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent ae){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load file");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("XML" , "*.xml*"),
						new FileChooser.ExtensionFilter("All Files" , "*.*"));
				File file = fileChooser.showOpenDialog(btnLoad.getScene().getWindow());
				if (file == null) return;
				gc.clearRect(0,0,1000,1200);

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
				try {
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(file);

					 
					
					
					NodeList CityList = doc.getElementsByTagName("CITY");
					for(int i=0; i<CityList.getLength(); i++){
						Node c = CityList.item(i);
						if(c.getNodeType() == Node.ELEMENT_NODE){ 
							Element city = (Element) c;
							String cityInf = city.getTextContent();

							// Applying the split method to divide string into parts

						String[] cp = cityInf.split(" ");
//                            String shName = cp[1];
//                            String cityInfo = cp[2];
							shName[i] = cp[1]; 
							fName[i] = cp[2];
							int x = Integer.parseInt(cp[3]);
							int y = Integer.parseInt(cp[4]);
							coorX [i] = x;
							coorY [i] = y;
							

						}
						

					}
					for (int i=0; i < shName.length; i++){
						g.insertNode(shName[i], new CityInfo(fName[i], coorX[i], coorY[i]));
					}
					
					

					
					
					NodeList RoadList = doc.getElementsByTagName("ROAD");
					for (int j=0; j < RoadList.getLength(); j++){
						Node r = RoadList.item(j);
						if (r.getNodeType() == Node.ELEMENT_NODE){
							Element road = (Element) r;
							String roadInf = road.getTextContent();

							String[] rp = roadInf.split(" ");

							roadFrom[j] = rp[1]; 
							roadTo[j] = rp[2];
							distance [j] = rp[3];
							distanceInt[j] = Integer.parseInt(distance[j]);
						}
					}
					for (int i=0; i < roadFrom.length; i++){
						g.insertEdge(roadFrom[i], roadTo[i],distanceInt[i]);
					}
					

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				//		Drawing the rectangles 

				for (int i=0; i<= coorX.length-1; i++){
					if ((coorX[i] ==0) && (coorY[i]==0)){
						break;
					}
					Rectangle r = new Rectangle(coorX[i]-5, coorY[i]-5,10,10); 	
					r.setStroke(Color.BLACK);
					r.setFill(Color.BLACK);
					r.setStrokeWidth(2);
					root.getChildren().addAll(r);
				}

				//          Providing the lines 

				for (int i=0; i < roadFrom.length;i++){
					for (int j=0; j < shName.length;j++){
						try{
							if (shName[j].equals(roadFrom[i])) {
								x1[i] = coorX[j];
								y1[i] = coorY[j];

							}

						}	
						catch(NullPointerException e){
						}
					}

				}

				for (int q=0; q < roadTo.length;q++){
					for (int k=0; k < shName.length;k++){
						try{ 
							if (shName[q].equals(roadTo[k])) {
								x2[k]= coorX[q];
								y2[k] = coorY[q];

							}	
						}
						catch(NullPointerException e)		{

						}
					}}

				for (int i=0; i< roadFrom.length; i++){
					Line line = new Line (x1[i], y1[i],x2[i], y2[i]);
					line.setStroke(Color.BLUE);
					root.getChildren().addAll(line);
				}

				//				Putting the distance and name of the cities

				for (int i=0; i< coorX.length; i++){
					Text t = new Text(coorX[i]+10,coorY[i],fName[i]);
					root.getChildren().addAll(t);
				}
				for (int i=0; i< distance.length; i++){

					Text t1 = new Text((x1[i] + x2[i])/2,(y1[i] + y2[i])/2, distance[i]);
					root.getChildren().addAll(t1);
				}

			}

		});
		root.getChildren().add(canvas);
		root.getChildren().addAll(btnLoad);
		myStage.setScene(myScene);

		myStage.show();

	}}
