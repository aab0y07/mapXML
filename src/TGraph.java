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
public class TGraph extends Application {

	// Creating a button	
	Button btnLoad = new Button ("Load");

	// Creating a canvas
	Canvas canvas = new Canvas(1000, 1200);

	public static void main(String[] args){
		launch (args);
	}


	public  void start(Stage myStage) {

		Graph g = new Graph(false);
		
		myStage.setTitle("Navigation");
		Group root = new Group();
		GraphicsContext gc = canvas.getGraphicsContext2D();
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

					//		g.insertNode("ORA", "Oradea");
					//		g.insertNode("ZER", "Zerind");
					//		g.insertNode("ARA", "Arad");
					//		g.insertNode("SIB", "Sibiu");
					//		
					//		g.insertEdge("ORA", "ZER", new Integer(71));
					//		g.insertEdge("ZER", "ARA", new Integer(75));
					//		g.insertEdge("ARA", "SIB", new Integer(140));
					//		g.insertEdge("ORA", "SIB", new Integer(151));
					//		g.insertEdge("ORA", "SIB", new Integer(180));     


					//		<CITY> VAS Vaslui 751 275 </CITY>
					//		<CITY> IAS Iasi 696 173 </CITY>
					//		<CITY> NEA Neamt 585 124 </CITY>
					//		<ROAD> VAS IAS 92 </ROAD>
					//		<ROAD> IAS NEA 87 </ROAD>

					NodeList CityList = doc.getElementsByTagName("CITY");
					for(int i=0; i<CityList.getLength(); i++){
						Node c = CityList.item(i);
						if(c.getNodeType() == Node.ELEMENT_NODE){ 
							Element city = (Element) c;
							String cityInf = city.getTextContent();

							// Applying the split method to divide string into parts

							String[] cp = cityInf.split(" ");
							shName[i] = cp[1]; 
							fName[i] = cp[2];
							int x = Integer.parseInt(cp[3]);
							int y = Integer.parseInt(cp[4]);
							coorX [i] = x;
							coorY [i] = y;


						}


					}
					try{
					for (int i=0; i < shName.length; i++){
						g.insertNode(shName[i],fName[i]);
					}
					}
					catch(NullPointerException e){
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
					try{
					for (int i=0; i < roadFrom.length; i++){
						g.insertEdge(roadFrom[i], roadTo[i],distanceInt[i]);
					}}
					catch(NullPointerException e){
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

//				Drawing the rectangles 

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

				
				//
//						g.insertNode("VAS", new CityInfo("Vaslui", 751, 275));
//						g.insertNode("IAS", new CityInfo("Iasi", 696, 173));
//						g.insertNode("NEA", new CityInfo("Neamt", 585, 124));
//						g.insertNode("KEA", new CityInfo("Keamt", 588, 134));
//				
//				
//						g.insertEdge("VAS", "IAS", new Integer(92));
//						g.insertEdge("IAS", "NEA", new Integer(87));
//						g.insertEdge("VAS", "KEA", new Integer(90));
//
//				Terminal.put(g.toString());
//
//
//
//				Object node;
//				while ((node = g.getNodeByNodeKey(Terminal.getString("City: "))) != null) {
//					String targets = "";
//					for (Object frog = g.getFirstEdge(node); frog != null; frog = g.getNextEdge(frog))
//						targets += ((CityInfo) g.getNodeInfo(g.getToNode(frog))).getFullName() + ", ";
//					Terminal.put("There are roads from " + 
//							((CityInfo) g.getNodeInfo(node)).getFullName() + " to " + 
//							targets.substring(0,  targets.length()-2));
//				}
			}

		}
