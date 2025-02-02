package application;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.css.Style;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.misc.GC;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;



/**
 * 
 * 
 */
public class Controller{ 

	@FXML BorderPane bp;

	//login scene variables
	@FXML
	private JFXButton				loginButton;
	@FXML
	private ChoiceBox<String>		rosterList;
	@FXML
	private ComboBox<String>		users;
	@FXML
	private PasswordField			adminPass;


	//Player/Goalie card variables
	@FXML ComboBox GamePicker;
	@FXML ListView ClipList;
	@FXML ListView GameList;
	ArrayList<Clip> goalieClips;
	Clip currentClip;
	ArrayList<Object> gamesList;
	ArrayList<String> playersList;
	private static String currentUser;

	//key scene variables
	@FXML
	private JFXButton			keySubmit;
	@FXML
	private TextField			databaseKey;
	@FXML
	private JFXButton			exitButton;

	//Scoring Chances vars
	@FXML private Canvas AwaySCCanvas;
	@FXML private Canvas HomeSCCanvas;
	private GraphicsContext homeGC1;
	private GraphicsContext awayGC1;
	private ArrayList<DrawnObject> homeSCItems = new ArrayList<DrawnObject>();
	private ArrayList<DrawnObject> awaySCItems = new ArrayList<DrawnObject>();
	@FXML private Label homeChartFail;
	@FXML private Label homeChartSuccess;
	@FXML private Label awayChartFail;
	@FXML private Label awayChartSuccess;

	//netChart variables
	@FXML private Canvas AwayNetChartCanvas;
	@FXML private Canvas HomeNetChartCanvas;
	private Canvas netChartCanvas;
	private GraphicsContext awayGC;
	private GraphicsContext homeGC;
	private int ovalWidth;
	@FXML private Label homeNetChartFail;
	@FXML private Label homeNetChartSuccess;
	@FXML private Label awayNetChartFail;
	@FXML private Label awayNetChartSuccess;

	//passing diagram variables (passing diagram code on line 805)
	@FXML private Canvas PassingDiagramCanvas;
	@FXML private ColorPicker passCP;
	private GraphicsContext passGC;
	private DrawnObject passFrom;
	private DrawnObject passTo;
	@FXML private Label PDErrorMessage;

	@FXML TableView<PassingTable> passTable; 
	@FXML private TableColumn<PassingTable, SimpleStringProperty> periodC;
	@FXML private TableColumn<PassingTable, SimpleStringProperty> timeC;
	@FXML private TableColumn<PassingTable, SimpleStringProperty> statusC;
	@FXML private TableColumn<PassingTable, SimpleStringProperty> typeC;
	@FXML private TableColumn<PassingTable, SimpleStringProperty> teamC;
	@FXML private TableColumn<PassingTable, SimpleStringProperty> playerC;
	private ObservableList<PassingTable> passTableList = FXCollections.observableArrayList();

	@FXML private Canvas passingDiagramRink;
	@FXML ComboBox PDGamePicker;
	@FXML ComboBox<String> PDPossession;
	@FXML ComboBox<String> PDShotType;
	@FXML ComboBox<String> PDPlayer;
	private ArrayList<PassPair> passes = new ArrayList<PassPair>();
	private boolean isFrom = true;
	private int tempX;
	private int tempY;
	private DrawnObject temp;

	//possession chart variables
	@FXML private Canvas PossessionDiagramCanvas;
	@FXML private ColorPicker possCP;
	private GraphicsContext possGC;
	@FXML private ComboBox GamePickerPoss;
	@FXML private ComboBox homeCombo1;
	@FXML private ComboBox awayCombo1;
	@FXML private ComboBox awayCombo2;
	@FXML private ComboBox homeCombo2;
	@FXML private ComboBox playerNum;
	@FXML private TableView<PossTable> possTable;
	@FXML private TableColumn<PossTable, SimpleStringProperty> periodColumn;
	@FXML private TableColumn<PossTable, SimpleStringProperty> timeColumn;
	@FXML private TableColumn<PossTable, SimpleStringProperty> timeStampColumn;
	@FXML private TableColumn<PossTable, SimpleStringProperty> teamColumn;
	@FXML private TableColumn<PossTable, SimpleStringProperty> playerColumn;
	@FXML private TableColumn<PossTable, SimpleStringProperty> tempColumn;
	private ObservableList<PossTable> possTableList = FXCollections.observableArrayList();
	@FXML private JFXButton leftTop;
	@FXML private JFXButton leftBottom;
	@FXML private JFXButton middleTop;
	@FXML private JFXButton middleBottom;
	@FXML private JFXButton rightTop;
	@FXML private JFXButton rightBottom;

	//Shot Chart variables: Dropdowns
	@FXML private ComboBox<String> teamForCombo;
	@FXML private ComboBox<String> teamAgainstCombo;
	@FXML private ComboBox<String> playerCombo;
	@FXML private ComboBox<String> statusCombo;
	@FXML private ComboBox<String> shotCombo;
	@FXML private ComboBox<String> shotTypeCombo;
	@FXML private ComboBox<String> rbCombo;
	@FXML private ComboBox<String> goalieCombo;
	@FXML private ComboBox<String> extraInfoCombo;
	@FXML private ComboBox<String> playTypeCombo;
	@FXML private ComboBox<String> playerStatusCombo;
	@FXML private ComboBox<String> releaseTypeCombo;
	@FXML private ComboBox<String> pChanceCombo;
	@FXML private ComboBox<String> sChanceCombo;
	@FXML private ComboBox<String> createdByCombo;
	@FXML private ComboBox<String> resultCombo;
	@FXML private ComboBox<String> scoringChanceCombo;



	//Text Field
	@FXML private TextField urlTextField;

	//Checkboxes in shot chart
	@FXML private CheckBox heatMapHome;
	@FXML private CheckBox heatMapAway;

	//Canvases and Graphics contexts for goal diagram
	@FXML private Canvas HomeHeatMapCanvas;
	@FXML private Canvas AwayHeatMapCanvas;
	private GraphicsContext awayHeatGC;
	private GraphicsContext homeHeatGC;
	private ArrayList<DrawnObject> homeHeatItems = new ArrayList<DrawnObject>();
	private int homeHeatIndex = 0;
	private ArrayList<Integer> homeRinkMissed = new ArrayList<Integer>();
	private ArrayList<DrawnObject> awayHeatItems = new ArrayList<DrawnObject>();
	private int awayHeatIndex = 0;
	private ArrayList<Integer> awayRinkMissed = new ArrayList<Integer>();

	//Canvases and graphics contexts for Rink diagram
	@FXML private Canvas HomeRinkHeatMapCanvas;
	@FXML private Canvas AwayRinkHeatMapCanvas;
	private GraphicsContext awayRinkHeatGC;
	private GraphicsContext homeRinkHeatGC;
	private ArrayList<DrawnObject> homeRinkHeatItems = new ArrayList<DrawnObject>();
	private int homeRinkHeatIndex = 0;
	private ArrayList<DrawnObject> awayRinkHeatItems = new ArrayList<DrawnObject>();
	private int awayRinkHeatIndex = 0;


	//GOAL DIAGRAM Regions Home
	//	Top row naming convention: canvas[column][row]
	@FXML private Region region00;
	@FXML private Region region10;
	@FXML private Region region20;
	private int[][] regionCountsHome = new int[3][3];
	//	Middle Row 
	@FXML private Region region01;
	@FXML private Region region11;
	@FXML private Region region21;
	//	Bottom Row
	@FXML private Region region02;
	@FXML private Region region12;
	@FXML private Region region22;
	//Regions Away
	//	Top row naming convention: canvas[column][row]1
	@FXML private Region region001;
	@FXML private Region region101;
	@FXML private Region region201;
	private int[][] regionCountsAway = new int[3][3];
	//	Middle Row 
	@FXML private Region region011;
	@FXML private Region region111;
	@FXML private Region region211;
	//	Bottom Row
	@FXML private Region region021;
	@FXML private Region region121;
	@FXML private Region region221;

	//RINK DIARAM regions
	//home 
	@FXML private Region region0;
	@FXML private Region region1;
	@FXML private Region region2;
	@FXML private Region region3;
	@FXML private Region region4;
	@FXML private Region region5;
	@FXML private Region region6;
	private int[][] regionCountsRinkHome = new int[3][3];

	//away 
	@FXML private Region regionA0;
	@FXML private Region regionA1;
	@FXML private Region regionA2;
	@FXML private Region regionA3;
	@FXML private Region regionA4;
	@FXML private Region regionA5;
	@FXML private Region regionA6;
	private int[][]  regionCountsRinkAway= new int[3][3];

	private ArrayList<DrawnObject> homeNetChartItems = new ArrayList<DrawnObject>();
	private int homeNetChartIndex = 0;
	private ArrayList<DrawnObject> awayNetChartItems = new ArrayList<DrawnObject>();
	private int awayNetChartIndex = 0;

	//Time on Ice Variables 
	@FXML private ComboBox<String> specialSetupCombo;
	@FXML private ComboBox<String> playerCountHomeCombo;
	@FXML private ComboBox<String> playerCountAwayCombo;
	@FXML private ComboBox GamePickerIce;
	@FXML private CheckBox swapCheckBox;
	@FXML private Label multipleSwapDisplay; 

	private Label lastJersey;// Last line up button clicked 
	private JFXButton lastLineUp; 
	private String timeOnIceMode = "waiting"; // waiting, bench, line, lineChange, swap, multiSelect, or multiSwap
	private String lastTeamClicked; //home or away for bench 
	private String benchPlayerClicked;
	private String timeOff;
	private ArrayList<Label> multiPlayers = new ArrayList<Label>(5); 
	private int multiSwapCount = 0;
	private int multiKeyPressed = 0;


	@FXML private ImageView homeJersey6;
	@FXML private ImageView homeJersey5; 
	@FXML private ImageView homeJersey4; 
	@FXML private ImageView homeJersey3; 
	@FXML private ImageView homeJersey2; 
	@FXML private ImageView homeJersey1; 
	@FXML private ImageView homeJersey0;
	@FXML private Label homeJerseyLabel6;
	@FXML private Label homeJerseyLabel5; 
	@FXML private Label homeJerseyLabel4; 
	@FXML private Label homeJerseyLabel3; 
	@FXML private Label homeJerseyLabel2; 
	@FXML private Label homeJerseyLabel1;
	@FXML private Label homeJerseyLabel0; 

	@FXML private ImageView awayJersey6;
	@FXML private ImageView awayJersey5; 
	@FXML private ImageView awayJersey4; 
	@FXML private ImageView awayJersey3; 
	@FXML private ImageView awayJersey2; 
	@FXML private ImageView awayJersey1;
	@FXML private ImageView awayJersey0; 
	@FXML private Label awayJerseyLabel6;
	@FXML private Label awayJerseyLabel5; 
	@FXML private Label awayJerseyLabel4; 
	@FXML private Label awayJerseyLabel3; 
	@FXML private Label awayJerseyLabel2; 
	@FXML private Label awayJerseyLabel1;
	@FXML private Label awayJerseyLabel0; 

	@FXML private JFXButton homeLine11;
	@FXML private JFXButton homeLine12;
	@FXML private JFXButton homeLine13;
	@FXML private JFXButton homeLine21;
	@FXML private JFXButton homeLine22;
	@FXML private JFXButton homeLine23;
	@FXML private JFXButton homeLine31;
	@FXML private JFXButton homeLine32;
	@FXML private JFXButton homeLine33;
	@FXML private JFXButton homeLine41;
	@FXML private JFXButton homeLine42;
	@FXML private JFXButton homeLine43;
	@FXML private JFXButton homeLineExtra;
	@FXML private JFXButton homeLineD11;
	@FXML private JFXButton homeLineD12;
	@FXML private JFXButton homeLineD21;
	@FXML private JFXButton homeLineD22;
	@FXML private JFXButton homeLineD31;
	@FXML private JFXButton homeLineD32;

	@FXML private JFXButton awayLine11;
	@FXML private JFXButton awayLine12;
	@FXML private JFXButton awayLine13;
	@FXML private JFXButton awayLine21;
	@FXML private JFXButton awayLine22;
	@FXML private JFXButton awayLine23;
	@FXML private JFXButton awayLine31;
	@FXML private JFXButton awayLine32;
	@FXML private JFXButton awayLine33;
	@FXML private JFXButton awayLine41;
	@FXML private JFXButton awayLine42;
	@FXML private JFXButton awayLine43;
	@FXML private JFXButton awayLineExtra;
	@FXML private JFXButton awayLineD11;
	@FXML private JFXButton awayLineD12;
	@FXML private JFXButton awayLineD21;
	@FXML private JFXButton awayLineD22;
	@FXML private JFXButton awayLineD31;
	@FXML private JFXButton awayLineD32;

	//Video tab scene variables
	@FXML 
	private TextField NCHC_URL;

	//Timer variables
	@FXML private Label Time;
	private long timerStart;
	private long timerPause = 0;
	private long currentTime = 0;
	static boolean timerOn = false;	
	private static int periodIndex = 0;
	private final static String[] PERIODS = {"1st", "2nd", "3rd", "OT"};
	private ArrayList<Clip> clips = new ArrayList<Clip>();

	//TimeStamp variables
	@FXML private ComboBox<String> TimeStamps;
	@FXML private TextArea TimeStampNotes;

	//RinkDiagram variables
	@FXML private Canvas RinkCanvas;
	@FXML private ColorPicker RinkCP;
	@FXML private Slider RinkSlider;
	@FXML private ToggleGroup RinkGroup;
	@FXML private TextArea RinkDiagramText;
	@FXML private ListView<String> PlayerList;
	private GraphicsContext rinkGC;
	private ArrayList<DrawnObject> drawList = new ArrayList<DrawnObject>();
	private DrawnObject line;
	@FXML private Label 				diagramSuccess;
	@FXML private Label 				titleAndLinkSuccess;
	@FXML private Label 				clipsSuccess;
	@FXML private Label					selectGameFirst;

	//instance variables
	private Scene					scene;
	private Stage					primaryStage;
	private FXMLLoader				fxmlLoader;
	private Parent					parent;

	private boolean	isLogin		= true;

	//Page variables
	private final String	LOGIN_SCENE				= "/View/SPAnalytics-Login.fxml";
	private final String	PLAYER_HOME				= "/View/PlayerHome.fxml";
	private final String	GOALIE_HOME				= "/View/GoalieHome.fxml";
	private final String	TEAM_PROFILE			= "/View/TeamProfile.fxml";
	private final String	PLAYER_CARD				= "/View/SPAnalytics-playerCard.FXML";
	private final String	GOALIE_CARD				= "/View/SPAnalytics-goalieCard.fxml";
	private final String	GOALIE_CARD_PERCENT		= "/View/SPAnalytics-goalieCardPercent.fxml";
	private final String	ADMIN_SCORINGCHANCES	= "/View/Admin_ScoringChances.fxml";
	private final String	ADMIN_RINKDIAGRAM		= "/View/Admin_RinkDiagram.fxml";
	private final String	ADMIN_POSSESSIONDIAGRAM	= "/View/Admin_PossessionDiagram.fxml";
	private final String	ADMIN_PASSINGDIAGRAM	= "/View/Admin_PassingDiagram.fxml";
	private final String	ADMIN_NETCHART			= "/View/Admin_NetChart.fxml";
	private final String	ADMIN_HOME				= "/View/AdminHome.fxml";
	private final String	ADMIN_ADD				= "/View/ADMIN_ADD.fxml";
	private final String	KEY						= "/View/Admin_Key.fxml";
	private final String	CSS						= "/View/application.css";
	private final String    SHOT_CHART              = "/View/ShotChart.fxml";
	private final String    TIME_ON_ICE				= "/View/Admin_TimeOnIce.fxml";


	//add player to database variables
	@FXML
	private JFXTextField		fullName;
	@FXML
	private JFXTextField		birthDate;
	@FXML
	private JFXTextField		height;
	@FXML
	private JFXTextField		weight;
	@FXML
	private JFXTextField		homeTown;
	@FXML
	private JFXTextField		addJerseyNumber;
	@FXML
	private JFXButton			addPlayer;
	@FXML 
	private Label 				playerSuccess;
	@FXML 
	private Label 				playerRemoved;
	@FXML 
	private Label 				gameSuccess;
	@FXML private CheckBox		GoalieCheckBox;


	//remove player to database variables
	@FXML
	private JFXTextField		removeJerseyNumber;
	@FXML
	private JFXButton			removePlayer;


	//add a game to database variables
	@FXML
	private JFXTextField		opponent;
	@FXML
	private JFXTextField		date;
	@FXML
	private JFXButton			addGame;

	//Model Variable
	Model model = new Model();

	/**
	 * This is the method that will allow this Controller class to
	 * load new FXML files. 
	 * @param inStage
	 */
	public void setPrimaryStage(Stage inStage) {
		primaryStage = inStage;

		//Read the users keypath if it exists and populate the text field with the path
		try {
			Scanner f = new Scanner(new File("Key.txt"));
			String key = f.nextLine();
			databaseKey.setText(key);
			f.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method that will close the application when the Exit button is clicked 
	 * @param event
	 */
	@FXML
	public void closeApplication(ActionEvent event) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Helper method that will load scene
	 * @param newScene
	 */
	private void loadScene(String newScene) {
//		try{  
//			Class.forName("com.mysql.jdbc.Driver");  
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//			Statement stmt=con.createStatement();
//			ResultSet rs=stmt.executeQuery("select teamID from team;");
//			while(rs.next())  
//				System.out.println(rs.getInt(1));  
//			con.close();  
//		}
//		catch(Exception e){
//			System.out.println(e);
//		} 

		if (newScene.equals(LOGIN_SCENE)) {
			isLogin = true;
		} else {
			isLogin = false;
		}

		try {
			// Switch to player card scene
			fxmlLoader = new FXMLLoader(
					getClass().getResource(newScene));

			// To keep the states of everything in this controller
			fxmlLoader.setController(this);


			// Loading the new FXML file
			parent = fxmlLoader.load();

			playersList = model.playerNames();
			users.getItems().add("ADMIN");
			users.getItems().addAll(playersList);
			System.out.println(users.getItems().size());

			//Load the proper player

			JFXTextArea textArea = (JFXTextArea) parent.lookup("#playerInfo");


			String jerseyNo = null;

			//Handle the scene for goalie/player cards
			if (newScene.equals(GOALIE_CARD) || newScene.equals(PLAYER_CARD)) {

				if (newScene.equals(GOALIE_CARD)) {
					try {
						jerseyNo = model.getJerseyNo(currentUser);
						Object check = model.getPlayerStats(jerseyNo);

						if (check != null) {
							//make the observable list
							HashMap<String, HashMap> map = (HashMap<String, HashMap>) check;
							ObservableList<GoalieModel> data = makeGoalieTable(map);
							//find the table needed to be added to
							TableView<GoalieModel> tbData = (TableView<GoalieModel>) parent.lookup("#tbData");
							//add the items to be updated
							tbData.setItems(data);
							makeGoalieCols(tbData); //create the columns
						}						
					} catch(Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("No Player Info Found");
						alert.setHeaderText("Some information not loaded");
						alert.setContentText("Please check with the database admins");
						alert.show();						
					}

				} else if (newScene.equals(PLAYER_CARD)) {
					try {
						jerseyNo = model.getJerseyNo(currentUser);
						//Get the player stats
						Object check = model.getPlayerStats(jerseyNo);

						if (check != null) {
							HashMap<String, HashMap> map = (HashMap<String, HashMap>) check;
							//make the observable list
							ObservableList<MemberModel> data = makeMemberTable(map);
							//find the table needed to be added to
							TableView<MemberModel> tbData = (TableView<MemberModel>) parent.lookup("#tbData");
							//add the items to be updated
							tbData.setItems(data);
							makeMemberCols(tbData); //create the columns
						}
					} catch(Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("No Player Info Found");
						alert.setHeaderText("Some information not loaded");
						alert.setContentText("Please check with the database admins");
						alert.show();
					}

				}

				//Set up all canvases
				rinkGC = RinkCanvas.getGraphicsContext2D();
				homeGC = HomeNetChartCanvas.getGraphicsContext2D();
				homeGC.setLineWidth(7);
				awayGC = AwayNetChartCanvas.getGraphicsContext2D();
				awayGC.setLineWidth(7);
				homeGC1 = HomeSCCanvas.getGraphicsContext2D();
				homeGC1.setLineWidth(7);
				awayGC1 = AwaySCCanvas.getGraphicsContext2D();
				awayGC1.setLineWidth(7);

				//Populate the games list with all games for Miami
				gamesList = model.getGameStats();
				for(Object s : gamesList) {
					GameList.getItems().add(s.toString());
				}

				//Allow the game list to have multiple choices at once, for aggregate charts
				GameList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

				//Load charts and information based on selected game
				GameList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						//clear out pre-existing data from charts
						homeGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						homeGC1.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						awayGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						awayGC1.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());

						ClipList.getItems().clear();

						//get all games selected by user, populate the associated charts
						Object[] games = GameList.getSelectionModel().getSelectedItems().toArray();
						for(int i = 0; i < games.length; i++) {
							String game = games[i].toString();

							goalieClips = model.getClips(game); 
							System.out.println(game);

							homeNetChartItems = model.getChart("offense", game, "netChart");
							awayNetChartItems = model.getChart("defense", game, "netChart");
							homeSCItems = model.getChart("offense", game, "scoringChart");
							awaySCItems = model.getChart("defense", game, "scoringChart");

							drawOvals(homeNetChartItems, homeGC);
							drawOvals(awayNetChartItems, awayGC);
							drawOvals(homeSCItems, homeGC1);
							drawOvals(awaySCItems, awayGC1);						

							//add the clips matching the user and current game
							for(Clip c : goalieClips) {
								if(c.getPlayers().contains(currentUser)) {
									String clipText = "Clip: " + c.getTime() + "\n" + "Description: " + c.getTitle();
									ClipList.getItems().add(clipText);
								}
							}
						}


					}

				});

			}

			//Populate player bio information
			if (textArea != null && jerseyNo != null) {

				HashMap playerInfo = model.getPlayer(jerseyNo); //based on jersey no
				Object birthDate = playerInfo.get("birthDate");
				Object name = playerInfo.get("name");
				Object height = playerInfo.get("height");
				Object weight = playerInfo.get("weight");
				Object homeTown = playerInfo.get("homeTown");
				Object position = model.getPosition(jerseyNo); //based on jersey no
				textArea.setText("#"+jerseyNo+" "+name+" "+position+" Height: "+height+"\nWeight: "+weight+" Born: "+birthDate+"\n"+homeTown);
			}
			scene = new Scene(parent, 600, 400);
			Pane root = (Pane) parent;
			scene = new Scene(new Group(root), 600, 400);

			//setting scaling
			Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
			double width = resolution.getWidth();
			double height = resolution.getHeight();
			double w = width/bp.getPrefWidth();
			double h = height/bp.getPrefHeight();
			Scale scale = new Scale(w,h,0,0);
			root.getTransforms().add(scale);

			//Setting a Scene KeyListener, allows user to add clip with the S key
			scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
					case S: 
						getTime();
						break;
					}
				}
			});

			scene.getStylesheets().add(getClass().getResource(CSS).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("SP Analytics");
			primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
			primaryStage.show();

		} catch (Exception err) {
			System.out.println(err);
		}

		//Setting up the net/scoring chances charts
		if(newScene.equals(ADMIN_NETCHART) || newScene.equals(ADMIN_SCORINGCHANCES)) {
			try {
				//chart graphics settings
				netChartCanvas = HomeNetChartCanvas;

				homeGC = HomeNetChartCanvas.getGraphicsContext2D();
				homeGC.setStroke(Color.color(.77, .13, .2));
				homeGC.setLineWidth(7);
				homeNetChartItems = new ArrayList<DrawnObject>();
				homeNetChartIndex = 0;

				awayGC = AwayNetChartCanvas.getGraphicsContext2D();
				awayGC.setStroke(Color.color(.77, .13, .2));
				awayGC.setLineWidth(7);
				awayNetChartItems = new ArrayList<DrawnObject>();
				awayNetChartIndex = 0;

				if(newScene.equals(ADMIN_NETCHART)) {
					ovalWidth = 40;
				} else if(newScene.equals(ADMIN_SCORINGCHANCES)) {
					ovalWidth = 20;
				}

				//add games to picker dropdown
				GamePicker.getItems().addAll(model.getGameStats());

			} catch(Exception e) {}
		}

		if(newScene.equals(ADMIN_RINKDIAGRAM)) {
			try {
				//setting up rink diagram graphics
				RinkCP.setValue(Color.BLACK);
				rinkGC = RinkCanvas.getGraphicsContext2D();
				rinkGC.setStroke(RinkCP.getValue());
				rinkGC.setFill(RinkCP.getValue());
				rinkGC.setLineWidth(RinkSlider.getValue());
				rinkGC.setFont(new Font("Verdana", 24));

				//draws associated diagram and populates information for a selected timestamp
				TimeStamps.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						int index = TimeStamps.getSelectionModel().getSelectedIndex();
						TimeStampNotes.setText(clips.get(index).getTitle());
						NCHC_URL.setText(clips.get(index).getURL());
						copyDrawList(clips.get(index).getRinkDiagram());
						rinkGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						drawLinesAndNumbers(drawList, rinkGC);
						PlayerList.getSelectionModel().clearSelection();
						for(String player : clips.get(index).getPlayers()) {
							PlayerList.getSelectionModel().select(player);
						}

					}

				});

				//gets the clips for a specific game selected
				GamePicker.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						String game = GamePicker.getSelectionModel().getSelectedItem().toString();
						clips = model.getClips(game);
						System.out.println(clips.size());
						TimeStamps.getItems().clear();
						for(Clip c : clips) {
							TimeStamps.getItems().add(c.getTime());
						}
					}
				});

				//populate gamepicker
				gamesList = model.getGameStats();
				for(Object s : gamesList) {
					GamePicker.getItems().add(s.toString());
				}

				//allow multiple selection, add players to list
				PlayerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				PlayerList.getItems().addAll(model.playerNames());
			} catch(Exception e) {}
		}

		if(newScene.equals(ADMIN_PASSINGDIAGRAM)) {
			// Clear the list so that each time you open it's fresh
			passTableList.clear();
			//passingDiagramRinkClicked
			PDGamePicker.getItems().addAll(model.getGameStats());
			fillShotTypes();
			fillTeams();
			fillPlayers();
			passGC = passingDiagramRink.getGraphicsContext2D();
			passGC.setStroke(Color.color(.77, .13, .2));
			passGC.setLineWidth(7);
			passGC.setStroke(Color.WHITE);
			passGC.setFont(new Font("Verdana", 24));

			initializePassTable();// initializes the table for the passing diagram
		}

		if(newScene.equals(ADMIN_POSSESSIONDIAGRAM)) {
			try {
				// Clears the list so that the table is fresh every time
				possTableList.clear();
				//add games to picker dropdown
				initializePossTable();
				initializePossDropdowns();

				//set up graphics
				possCP.setValue(Color.BLACK);
				possGC = PossessionDiagramCanvas.getGraphicsContext2D();
				possGC.setLineWidth(7);
				possGC.setStroke(possCP.getValue());
				possGC.setFill(possCP.getValue());
				possGC.setFont(new Font("Verdana", 24));

			} catch(Exception e) {

			}

		}

		if(newScene.equals(SHOT_CHART)) {

			//gets all the info for each combo box
			netChartCanvas = HomeNetChartCanvas;

			//Needed to draw the circles of the shot chart
			homeHeatGC = HomeHeatMapCanvas.getGraphicsContext2D();
			homeHeatGC.setStroke(Color.color(.77, .13, .2));
			homeHeatGC.setLineWidth(7);
			homeHeatItems = new ArrayList<DrawnObject>();
			homeHeatIndex = 0;

			awayHeatGC = AwayHeatMapCanvas.getGraphicsContext2D();
			awayHeatGC.setStroke(Color.color(.77, .13, .2));
			awayHeatGC.setLineWidth(7);
			awayHeatItems = new ArrayList<DrawnObject>();
			awayHeatIndex = 0;

			homeRinkHeatGC = HomeRinkHeatMapCanvas.getGraphicsContext2D();
			homeRinkHeatGC.setStroke(Color.color(.77, .13, .2));
			homeRinkHeatGC.setLineWidth(7);
			homeRinkHeatItems = new ArrayList<DrawnObject>();
			homeRinkHeatIndex = 0;

			awayRinkHeatGC = AwayRinkHeatMapCanvas.getGraphicsContext2D();
			awayRinkHeatGC.setStroke(Color.color(.77, .13, .2));
			awayRinkHeatGC.setLineWidth(7);
			awayRinkHeatItems = new ArrayList<DrawnObject>();
			awayRinkHeatIndex = 0;

			ovalWidth = 30;
			initializeDropdowns(); // puts necessary data in the dropdowns
		}

		if(newScene.equals(TIME_ON_ICE)) {

			GamePickerIce.getItems().addAll(model.getGameStats());

			specialSetupCombo.getItems().addAll(
					"Even Strength",
					"Power Play",
					"Penalty Kill Home",
					"Penalty Kill Away",
					"Extra Attacker Home",
					"Extra Attacker Away"
					);
			playerCountHomeCombo.getItems().addAll(
					"3",
					"4",
					"5",
					"6"
					);
			playerCountAwayCombo.getItems().addAll(
					"3",
					"4",
					"5",
					"6"
					);

			//Keyboard listener for multiswap functionality 
			scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
					case DIGIT2: 
						swapMultiple(2);
						break;
					case DIGIT3: 
						swapMultiple(3);
						break;
					case DIGIT4: 
						swapMultiple(4);
						break;
					case DIGIT5: 
						swapMultiple(5);
						break;
					}
				}
			});
		}

	}// end of loadScene Method 

	//	**************** Passing Diagram Methods **********************

	/**
	 * This is the method that is called when the passing diagram is clicked.
	 * it will draw ovals on the ice showing where different types of passes stop and start.
	 * It will also display all the data to a table so that the user can see what's going on
	 *  
	 * @param e the mouse event of the click on the ice
	 */
	public void passingDiagramRinkClicked(MouseEvent e) {
		// Variables used to add data to the table
		String passPeriod, passTime, playType, status, passTeam, passPlayer;
		//		ADD COLOR TO THE PEN AND ADD PEN FUNCTIONALITY TO DRAW LINES
		if(PDShotType.getValue() == null) {
			PDErrorMessage.setText("Please Select A Shot Type First");
		} else {
			if(isFrom) {
				PDErrorMessage.setText("");
				if (PDShotType.getValue().contentEquals("Completed Pass")) {
					//if complete, then green
					passGC.setStroke(Color.FORESTGREEN);
				} else if (PDShotType.getValue().contentEquals("Incomplete Pass")) {
					//if incomplete, then red
					passGC.setStroke(Color.RED);
				} else if (PDShotType.getValue().contentEquals("Turnover")) {
					//if turnover, then yellow
					passGC.setStroke(Color.YELLOW);
				} else if (PDShotType.getValue().contentEquals("Successful Clear")) {
					//if successful, then blue
					passGC.setStroke(Color.BLUE);
				} else if (PDShotType.getValue().contentEquals("Unsuccessful Clear")) {
					//if unsuccessful, then orange
					passGC.setStroke(Color.ORANGE);
				} else if (PDShotType.getValue().contentEquals("Hit")) {
					//if hit, then black and no second click
					passGC.setStroke(Color.BLACK);
				}

			} else {
				ovalWidth = 8;
				//draw the circle
				Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), passGC.getStroke());
				passGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
			}
			ovalWidth = 20;
			//draw the circle
			Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), passGC.getStroke());
			passGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
			DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);

			if(isFrom) {
				tempX = (int)e.getX();
				tempY = (int)e.getY();
				if(PDShotType.getValue().contentEquals("Hit")) {
					passes.add(new PassPair(oval, null));
					// Add the hit to the table
					passPeriod = PERIODS[periodIndex];
					passTime = (String) formatTimeShort(currentTime);
					playType = selectPlayType(PDShotType.getValue());
					status = selectPlayStatus(PDShotType.getValue(), PDPossession.getValue());
					passTeam = PDPossession.getValue();
					passPlayer = PDPlayer.getValue();
					passTable.getItems().add(new PassingTable(passPeriod, passTime, playType, status, passTeam, passPlayer)); 
//					try{
//						//TODO
//						//Fall 2020 group: This should be made only once 
//						Class.forName("com.mysql.jdbc.Driver");  
//						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//						Statement stmt=con.createStatement();
//
//						String query="INSERT INTO hit (playerNumber, passPeriod, passTime, playType, status, passTeam, x, y) VALUES ('"+passPlayer+"','"+passPeriod+"','"+passTime+"','"+playType+"','"+status+"','"+passTeam+"','"+tempX+"','"+tempY+"');";
//						System.out.println(query);
//						int result=stmt.executeUpdate(query);
//					}
//					catch(Exception e1){
//						System.out.println(e1);
//					} 

				} else {
					temp = oval;
					isFrom=false;
				}

			} else {
				passGC.setLineWidth(2);
				passGC.strokeLine(tempX, tempY, (int)e.getX(), (int)e.getY());
				passGC.setLineWidth(7);
				passes.add(new PassPair(temp, oval));
				// Add the new pair to the table
				passPeriod = PERIODS[periodIndex];
				passTime = (String) formatTimeShort(currentTime);
				playType = selectPlayType(PDShotType.getValue());
				status = selectPlayStatus(PDShotType.getValue(), PDPossession.getValue());
				passTeam = PDPossession.getValue();
				passPlayer = PDPlayer.getValue();
				passTable.getItems().add(new PassingTable(passPeriod, passTime, playType, status, passTeam, passPlayer)); 

				temp = null;
				isFrom=true;
//				try{  
//					Class.forName("com.mysql.jdbc.Driver");  
//					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//					Statement stmt=con.createStatement();
//
//					String query="INSERT INTO pass (playerNumber, passPeriod, passTime, playType, status, passTeam, x, y,x2,y2) VALUES ('"+passPlayer+"','"+passPeriod+"','"+passTime+"','"+playType+"','"+status+"','"+passTeam+"','"+tempX+"','"+tempY+"','"+(int)e.getX()+"','"+(int)e.getY()+"');";
//					System.out.println(query);
//					int result=stmt.executeUpdate(query);
//				}
//				catch(Exception e1){
//					System.out.println(e1);
//				}
			}
		}
	}

	/**
	 * Helper method to return the status for the play table
	 * 
	 * This will probably need to be updated depending how the front end for the page changes
	 * 
	 * @param shotType
	 * @param passTeam
	 * @return The status
	 */
	private String selectPlayStatus(String shotType, String passTeam) {
		// if (passTeam.equals("Miami")) {
		if (shotType.equals("Completed Pass") || shotType.equals("Successful Clear"))
			return "Successful";
		else if (shotType.equals("Incomplete Pass") || shotType.equals("Unsuccessful Clear"))
			return "Unsuccessful";
		else if (shotType.equals("Turnover"))
			return "Turn";
		else
			return "Hit";
		//}
	}

	/**
	 * Helper method to return the play type
	 * @param shotType
	 * @return The play type
	 */
	private String selectPlayType(String shotType) {
		if (shotType.equals("Completed Pass") || shotType.equals("Incomplete Pass")) {
			return "Pass";
		} else if (shotType.equals("Successful Clear") || shotType.equals("Unsuccessful Clear")) {
			return "Clear";
		} else if (shotType.equals("Turnover")) {
			return "Turnover";
		} else {
			return "Hit";
		}
	}

	/**
	 * Fills in the shot types 
	 */
	public void fillShotTypes() {
		PDShotType.getItems().addAll(
				"Completed Pass",
				"Incomplete Pass",
				"Turnover",
				"Successful Clear",
				"Unsuccessful Clear",
				"Hit");
	}

	/**
	 * fills in the players, this will need to be made into a SQL call
	 * when the new database is hooked up
	 */
	public void fillPlayers() {
		PDPlayer.getItems().addAll(
				"0",
				"1",
				"2",
				"3"
				);
	}

	/**
	 * Fills the teams, this will need to be made into a SQL call
	 * when the new database is hooked up
	 */
	public void fillTeams() {
		PDPossession.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other"
				);

	}

	/**
	 * This initializes the table and adds the items to it for the passing diagram
	 */
	public void initializePassTable() {

		//To make sure these work, make sure the variable inside the quotation marks, in this case "period" matches what is in the TableData class
		periodC.setCellValueFactory(new PropertyValueFactory<PassingTable, SimpleStringProperty>("period")); 
		timeC.setCellValueFactory(new PropertyValueFactory<PassingTable, SimpleStringProperty>("time"));
		typeC.setCellValueFactory(new PropertyValueFactory<PassingTable, SimpleStringProperty>("playType"));
		statusC.setCellValueFactory(new PropertyValueFactory<PassingTable, SimpleStringProperty>("status"));
		teamC.setCellValueFactory(new PropertyValueFactory<PassingTable, SimpleStringProperty>("team"));
		playerC.setCellValueFactory(new PropertyValueFactory<PassingTable, SimpleStringProperty>("player"));	

		passTable.setItems(passTableList);
	}

	/**
	 * Helper class for passing diagram
	 * @author Nicole Roark
	 */
	private class PassPair {
		DrawnObject passFrom;
		DrawnObject passTo;

		PassPair(DrawnObject a, DrawnObject b) {
			passFrom = a;
			passTo = b;
		}
	}


	//	************** End Passing Diagram Methods *********************

	//*********************Shot chart Methods *************************

	/**
	 * Methhod called when the home net is clicked
	 * @param e - mouse event of the click
	 */
	public void homeHeatMapClicked(MouseEvent e) {

		//If the shot missed the net, don't draw it. 
		while(homeRinkMissed.contains(homeHeatIndex)) {
			homeHeatIndex++; 
		}

		Color col = Color.WHITE;
		int count = regionCountsHome[0][0];

		//Determines the color of the oval based on goal status 
		if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("Goal")) {
			homeHeatGC.setStroke(Color.GREEN);
		} else if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("No Goal")) {
			homeHeatGC.setStroke(Color.RED);
		} else {
			//If the stauts is missing, it misses the Net, and is not drawn on the net, and the method stops 
			return;
		}

		//draw the circle
		Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), homeHeatGC.getStroke());
		homeHeatGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
		DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
		homeHeatItems.add(oval);

		//draw the number of the shot (with offset to be in center of circle
		int xOff = 2*(int)(Math.log10(Math.max(1, homeHeatIndex))+1);
		Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, homeHeatGC.getFill());
		homeHeatGC.fillText(""+ ++homeHeatIndex, p2.getX(), p2.getY());
		DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+homeHeatIndex);
		homeHeatItems.add(number);

		double xCoord = e.getSceneX(), yCoord = e.getSceneY();

		//Logic to determine the region of the heat map clicked 
		//Top Row
		if (xCoord < 1151.0 && yCoord < 678.0) {
			regionCountsHome[0][0]++;
		}
		if (xCoord > 1151.0 && xCoord < 1436.0 && yCoord < 678.0) {
			regionCountsHome[1][0]++;
		}
		if (xCoord > 1436.0 && yCoord < 678.0) {
			regionCountsHome[2][0]++;
		}

		//Middle Row
		if (xCoord < 1151.0 && yCoord > 678.0 && yCoord < 851.0) {
			regionCountsHome[0][1]++;
		}
		if (xCoord > 1151.0 && xCoord < 1436.0 && yCoord > 678.0 && yCoord < 851.0) {
			regionCountsHome[1][1]++;
		}
		if (xCoord >1436.0 && yCoord > 678.0 && yCoord < 851.0) {
			regionCountsHome[2][1]++;
		}

		//Bottom Row
		if (xCoord < 1151.0 && yCoord > 851.0) {
			regionCountsHome[0][2]++;
		}
		if (xCoord > 1151.0 && xCoord < 1436.0 && yCoord > 851.0) {
			regionCountsHome[1][2]++;
		}
		if (xCoord > 1436.0 && yCoord > 851.0) {
			regionCountsHome[2][2]++;
		}

	}

	/**
	 * Exactly the same as the homeHeatMapClicked method above, but for the away tab 
	 * @param e - Mouse event of the click
	 */
	public void awayHeatMapClicked(MouseEvent e) {

		while(awayRinkMissed.contains(awayHeatIndex)) {
			awayHeatIndex++; 
		}

		Color col = Color.WHITE;
		int count = regionCountsAway[0][0];

		//Determines the color of the oval based on goal status 
		if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("Goal")) {
			awayHeatGC.setStroke(Color.GREEN);
		} else if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("No Goal")) {
			awayHeatGC.setStroke(Color.RED);
		} else {
			//If the stauts is missing, it misses the Net, and is not drawn on the net, and the method stops 
			return; 
		}

		//draw the circle
		Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), awayHeatGC.getStroke());
		awayHeatGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
		DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
		awayHeatItems.add(oval);

		//draw the number of the shot (with offset to be in center of circle
		int xOff = 2*(int)(Math.log10(Math.max(1, awayHeatIndex))+1);
		Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, awayHeatGC.getFill());
		awayHeatGC.fillText(""+ ++awayHeatIndex, p2.getX(), p2.getY());
		DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+awayHeatIndex);
		awayHeatItems.add(number);

		double xCoord = e.getSceneX(), yCoord = e.getSceneY();

		//Top Row
		if (xCoord < 1151.0 && yCoord < 678.0) {
			regionCountsAway[0][0]++;
		}
		if (xCoord > 1151.0 && xCoord < 1436.0 && yCoord < 678.0) {
			regionCountsAway[1][0]++;
		}
		if (xCoord > 1436.0 && yCoord < 678.0) {
			regionCountsAway[2][0]++;
		}

		//Middle Row
		if (xCoord < 1151.0 && yCoord > 678.0 && yCoord < 851.0) {
			regionCountsAway[0][1]++;
		}
		if (xCoord > 1151.0 && xCoord < 1436.0 && yCoord > 678.0 && yCoord < 851.0) {
			regionCountsAway[1][1]++;
		}
		if (xCoord >1436.0 && yCoord > 678.0 && yCoord < 851.0) {
			regionCountsAway[2][1]++;
		}

		//Bottom Row
		if (xCoord < 1151.0 && yCoord > 851.0) {
			regionCountsAway[0][2]++;
		}
		if (xCoord > 1151.0 && xCoord < 1436.0 && yCoord > 851.0) {
			regionCountsAway[1][2]++;
		}
		if (xCoord > 1436.0 && yCoord > 851.0) {
			regionCountsAway[2][2]++;
		}

	}

	/**
	 * Draws the oval on the rink when clicked 
	 * @param e - MouseEvent of the Click
	 */
	public void homeRinkHeatMapClicked(MouseEvent e) {
		System.out.println("CANVAS ACTIVATES");
		System.out.println(e.getSceneX() + ", " + e.getSceneY());

		Color col = Color.WHITE;
		int count = regionCountsRinkHome[0][0];

		if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("Goal")) {
			homeRinkHeatGC.setStroke(Color.GREEN);
		} else if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("No Goal")) {
			homeRinkHeatGC.setStroke(Color.RED);
		} else {
			homeRinkHeatGC.setStroke(Color.BLACK);
			homeRinkMissed.add(homeRinkHeatIndex); // keeps track of missed shots, so the net chart can make accommodations
		}

		ovalWidth = 20;

		//draw the circle
		Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), homeRinkHeatGC.getStroke());
		homeRinkHeatGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);		
		DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
		homeRinkHeatItems.add(oval);

		ovalWidth = 30;

		//draw the number of the shot (with offset to be in center of circle
		int xOff = 2*(int)(Math.log10(Math.max(1, homeRinkHeatIndex))+1);
		Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, homeRinkHeatGC.getFill());
		homeRinkHeatGC.fillText(""+ ++homeRinkHeatIndex, p2.getX(), p2.getY());
		DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+homeRinkHeatIndex);
		homeRinkHeatItems.add(number);

		double xCoord = e.getSceneX(), yCoord = e.getSceneY();

		//Region 0
		if (yCoord < 573.0) {
			regionCountsRinkHome[0][0]++;
		}

		//Region 1
		if (xCoord < 365.0 && yCoord > 573.0) {
			regionCountsRinkHome[1][0]++;
		}

		//Region 2
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 573.0 && yCoord < 701.0) {
			regionCountsRinkHome[2][0]++;
		}

		//Region 3
		if (xCoord > 663.0 && yCoord > 573.0) {
			regionCountsRinkHome[0][1]++;
		}

		//Region 4
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 701.0 && yCoord < 803.0) {
			regionCountsRinkHome[1][1]++;
		}

		//Region 5
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 803.0 && yCoord < 905.0) {
			regionCountsRinkHome[2][1]++;
		}

		//Region 6
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 905.0 && yCoord < 942.0) {
			regionCountsRinkHome[0][2]++;
		}

	}

	/**
	 * Same as homeRinkHeatMapClicked 
	 * @param e - MouseEvent for click
	 */
	public void awayRinkHeatMapClicked(MouseEvent e) {
		System.out.println("CANVAS ACTIVATES");
		System.out.println(e.getSceneX() + ", " + e.getSceneY());

		Color col = Color.WHITE;
		int count = regionCountsRinkAway[0][0];

		if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("Goal")) {
			awayRinkHeatGC.setStroke(Color.GREEN);
		} else if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("No Goal")) {
			awayRinkHeatGC.setStroke(Color.RED);
		} else {
			awayRinkHeatGC.setStroke(Color.BLACK);
			awayRinkMissed.add(awayRinkHeatIndex);
		}

		ovalWidth = 20;

		//draw the circle
		Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), awayRinkHeatGC.getStroke());
		awayRinkHeatGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
		DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
		awayRinkHeatItems.add(oval);

		ovalWidth = 30; 

		//draw the number of the shot (with offset to be in center of circle
		int xOff = 2*(int)(Math.log10(Math.max(1, awayRinkHeatIndex))+1);
		Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, awayRinkHeatGC.getFill());
		awayRinkHeatGC.fillText(""+ ++awayRinkHeatIndex, p2.getX(), p2.getY());
		DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+awayRinkHeatIndex);
		awayRinkHeatItems.add(number);

		double xCoord = e.getSceneX(), yCoord = e.getSceneY();

		//Region 0
		if (yCoord < 573.0) {
			regionCountsRinkAway[0][0]++;
		}

		//Region 1
		if (xCoord < 365.0 && yCoord > 573.0) {
			regionCountsRinkAway[1][0]++;
		}

		//Region 2
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 573.0 && yCoord < 701.0) {
			regionCountsRinkAway[2][0]++;
		}

		//Region 3
		if (xCoord > 663.0 && yCoord > 573.0) {
			regionCountsRinkAway[0][1]++;
		}

		//Region 4
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 701.0 && yCoord < 803.0) {
			regionCountsRinkAway[1][1]++;
		}

		//Region 5
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 803.0 && yCoord < 905.0) {
			regionCountsRinkAway[2][1]++;
		}

		//Region 6
		if (xCoord > 365.0 && xCoord < 663.0 && yCoord > 905.0 && yCoord < 942.0) {
			regionCountsRinkAway[0][2]++;
		}

	}

	/**
	 * Eventually some of these will all be changed to SQL queries so that data is pulled out
	 * of the database
	 */
	public void initializeDropdowns() {
		//gets all the info for each combo box
		GamePicker.getItems().addAll(model.getGameStats());
		playTypeCombo.getItems().addAll();   	//DEFINED IN DATABASE
		releaseTypeCombo.getItems().addAll(
				"One-timer",
				"Catch-Shoot");
		pChanceCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		sChanceCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		createdByCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		resultCombo.getItems().addAll(
				"First Goal",
				"Down >2",
				"Down 2",
				"Down 1",
				"Tied",
				"Up 1",
				"Up 2",
				"Up >2",
				"Empty Net"
				);
		teamForCombo.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		teamAgainstCombo.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		playerCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		playerStatusCombo.getItems().addAll(
				"Stationary",
				"Moving Forward",
				"Moving Across");
		shotCombo.getItems().addAll(
				"1","2","3","4","5","6","7","8","9","10",
				"11","12","13","14","15","16","17","18","19","20",
				"21","22","23","24","25","26","27","28","29","30",
				"31","32","33","34","35","36","37","38","39","40","41","42","43","44",
				"45","46","47","48","49","50","51","52","53","54","55","56",
				"57","58","59","60","61","62","63","64","65","66","67","68","69","70",
				"71","72","73","74","75","76","77","78","79","80","81","82","83","84","85",
				"86","87","88","89","90","91","92","93","94","95","96","97","98","99","100");
		shotTypeCombo.getItems().addAll(
				"Wrist shot" 
				,"Slapshot"
				,"Backhand");
		rbCombo.getItems().addAll(
				"Yes",
				"No");
		goalieCombo.getItems().addAll(
				"Moving",
				"Stationary");
		extraInfoCombo.getItems().addAll(
				"5v5", "4v4","3v3",	"5v4","4v5",
				"5v3","3v5","4v3","3v4","6v5","5v6","6v4","4v6","6v3","3v6");
		statusCombo.getItems().addAll(
				"Goal",
				"No Goal",
				"Misses");
		scoringChanceCombo.getItems().addAll(
				"Yes",
				"No");

	}//end of initializeDropdowns()

	/**
	 * Generates the heat map by coloring different section of the net image 
	 * @param e the event that triggered the method
	 */
	public void generateHeatMapHome(MouseEvent e) {

		if (!heatMapHome.isSelected()) {
			HomeHeatMapCanvas.setVisible(false);
			Color col = Color.WHITE;
			int count = regionCountsHome[0][0];

			region00.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[1][0];
			region10.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[2][0];
			region20.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[0][1];
			region01.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[1][1];
			region11.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[2][1];
			region21.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[0][2];
			region02.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[1][2];
			region12.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsHome[2][2];
			region22.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");


			//Rink Diagram
			HomeRinkHeatMapCanvas.setVisible(false);
			//0
			count = regionCountsRinkHome[0][0];		
			region0.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//1
			count = regionCountsRinkHome[1][0];
			region1.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//2
			count = regionCountsRinkHome[2][0];
			region2.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//3
			count = regionCountsRinkHome[0][1];
			region3.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//4
			count = regionCountsRinkHome[1][1];
			region4.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//5
			count = regionCountsRinkHome[2][1];
			region5.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//6
			count = regionCountsRinkHome[0][2];
			region6.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

		} else {
			HomeHeatMapCanvas.setVisible(true);
			Color col = Color.WHITE;
			int count = regionCountsHome[0][0];
			region00.setStyle("; -fx-opacity: .0;");
			region10.setStyle("; -fx-opacity: .0;");
			region20.setStyle("; -fx-opacity: .0;");
			region01.setStyle("; -fx-opacity: .0;");
			region11.setStyle("; -fx-opacity: .0;");
			region21.setStyle("; -fx-opacity: .0;");
			region02.setStyle("; -fx-opacity: .0;");
			region12.setStyle("; -fx-opacity: .0;");
			region22.setStyle("; -fx-opacity: .0;");

			//Rink
			HomeRinkHeatMapCanvas.setVisible(true);
			region0.setStyle("; -fx-opacity: .0;");
			region1.setStyle("; -fx-opacity: .0;");
			region2.setStyle("; -fx-opacity: .0;");
			region3.setStyle("; -fx-opacity: .0;");
			region4.setStyle("; -fx-opacity: .0;");
			region5.setStyle("; -fx-opacity: .0;");
			region6.setStyle("; -fx-opacity: .0;");
		}
	}

	/**
	 * Generates the heat map for the Away tab Net image 
	 * @param e the event that triggered the method
	 */
	public void generateHeatMapAway(MouseEvent e) {

		if (!heatMapAway.isSelected()) {
			AwayHeatMapCanvas.setVisible(false);
			Color col = Color.WHITE;

			//Goal Diagram
			int count = regionCountsAway[0][0];				
			region001.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[1][0];
			region101.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[2][0];
			region201.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[0][1];
			region011.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[1][1];
			region111.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[2][1];
			region211.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[0][2];
			region021.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[1][2];
			region121.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			count = regionCountsAway[2][2];
			region221.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//Rink Diagram
			AwayRinkHeatMapCanvas.setVisible(false);
			//0
			count = regionCountsRinkAway[0][0];		
			regionA0.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//1
			count = regionCountsRinkAway[1][0];
			regionA1.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//2
			count = regionCountsRinkAway[2][0];
			regionA2.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//3
			count = regionCountsRinkAway[0][1];
			regionA3.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//4
			count = regionCountsRinkAway[1][1];
			regionA4.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//5
			count = regionCountsRinkAway[2][1];
			regionA5.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

			//6
			count = regionCountsRinkAway[0][2];
			regionA6.setStyle("-fx-background-color: " + setRegionColors(count) + "; -fx-opacity: .3;");

		} else {
			//Goal
			AwayHeatMapCanvas.setVisible(true);
			region001.setStyle("; -fx-opacity: .0;");
			region101.setStyle("; -fx-opacity: .0;");
			region201.setStyle("; -fx-opacity: .0;");
			region011.setStyle("; -fx-opacity: .0;");
			region111.setStyle("; -fx-opacity: .0;");
			region211.setStyle("; -fx-opacity: .0;");
			region021.setStyle("; -fx-opacity: .0;");
			region121.setStyle("; -fx-opacity: .0;");
			region221.setStyle("; -fx-opacity: .0;");

			//Rink
			AwayRinkHeatMapCanvas.setVisible(true);
			regionA0.setStyle("; -fx-opacity: .0;");
			regionA1.setStyle("; -fx-opacity: .0;");
			regionA2.setStyle("; -fx-opacity: .0;");
			regionA3.setStyle("; -fx-opacity: .0;");
			regionA4.setStyle("; -fx-opacity: .0;");
			regionA5.setStyle("; -fx-opacity: .0;");
			regionA6.setStyle("; -fx-opacity: .0;");
		}
	}

	/**
	 * Gets the color for the region based on a number of shots 
	 * 
	 * @param count - number of shots in that region
	 * @return - Color name 
	 */
	private String setRegionColors(int count) {

		if(6 >= count && count >= 1)
			return "GREEN";
		if(12 >= count && count > 6)
			return "GREENYELLOW";
		if(18 >= count && count > 12)
			return "YELLOW";
		if(24 >= count && count > 18)
			return "ORANGE";
		if(count > 24)
			return "RED";
		return "WHITE";

	}

	/**
	 * This is the method that will draw circles Net chart when mouse released
	 */
	@FXML
	public void drawCircle(MouseEvent e) {
		//set a home circle	

		if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("Goal")) {
			homeGC.setStroke(Color.GREEN);
			awayGC.setStroke(Color.GREEN);
		} else if(statusCombo.getValue() != null && statusCombo.getValue().contentEquals("No Goal")) {
			homeGC.setStroke(Color.RED);
			awayGC.setStroke(Color.RED);
		} else {
			homeGC.setStroke(Color.BLACK);
			awayGC.setStroke(Color.BLACK);
		}

		String region = ((Node) e.getSource()).getId();

		if (region.equals("region00")) {
			regionCountsHome[0][0]++;
		}
		if (region.equals("region10")) {
			regionCountsHome[1][0]++;
		}
		if (region.equals("region20")) {
			regionCountsHome[2][0]++;
		}
		if (region.equals("region01")) {
			regionCountsHome[0][1]++;
		}
		if (region.equals("region11")) {
			regionCountsHome[1][1]++;
		}
		if (region.equals("region21")) {
			regionCountsHome[2][1]++;
		}
		if (region.equals("region02")) {
			regionCountsHome[0][2]++;
		}
		if (region.equals("region12")) {
			regionCountsHome[1][2]++;
		}
		if (region.equals("region22")) {
			regionCountsHome[2][2]++;
		}
		if (region.equals("region001")) {
			regionCountsAway[0][0]++;
		}
		if (region.equals("region101")) {
			regionCountsAway[1][0]++;
		}
		if (region.equals("region201")) {
			regionCountsAway[2][0]++;
		}
		if (region.equals("region011")) {
			regionCountsAway[0][1]++;
		}
		if (region.equals("region111")) {
			regionCountsAway[1][1]++;
		}
		if (region.equals("region211")) {
			regionCountsAway[2][1]++;
		}
		if (region.equals("region021")) {
			regionCountsAway[0][2]++;
		}
		if (region.equals("region121")) {
			regionCountsAway[1][2]++;
		}
		if (region.equals("region221")) {
			regionCountsAway[2][2]++;
		}

		if(netChartCanvas == HomeNetChartCanvas) {

			System.out.println(e.getY());
			//System.out.println("HOME GC WHATEVER: " + homeGC.toString());// This gives me the error
			System.out.println("OVAL WIDTH" +  ovalWidth); //ovalWidth is 0 

			//draw the circle
			Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), homeGC.getStroke());
			homeGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
			DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
			homeNetChartItems.add(oval);

			//draw the number of the shot (with offset to be in center of circle
			int xOff = 2*(int)(Math.log10(Math.max(1, homeNetChartIndex))+1);
			Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, homeGC.getFill());
			homeGC.fillText(""+ ++homeNetChartIndex, p2.getX(), p2.getY());
			DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+homeNetChartIndex);
			homeNetChartItems.add(number);

		} else if(netChartCanvas == AwayNetChartCanvas) { //away circle

			//draw circle
			Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), awayGC.getStroke());
			awayGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
			DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
			awayNetChartItems.add(oval);

			//draw number w/ offset
			int xOff = 2*(int)(Math.log10(Math.max(1, awayNetChartIndex))+1);
			Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, awayGC.getFill());
			awayGC.fillText(""+ ++awayNetChartIndex, p2.getX(), p2.getY());
			DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+awayNetChartIndex);
			awayNetChartItems.add(number);
		}

	}

	//*********************************End shot chart methods *************************************

	//**********************************Time on Ice Methods ***************************************

	/**
	 * Changes the set up of the home side of the rink in the Time on Ice Page
	 * 
	 * Notes from Matthew Murch of Phase 2 group:
	 * 
	 * - The Bench is not implemented, should pull the players from the teams and put them into the list 
	 * - The page works by setting different modes, and acting based on what the mode is 
	 * - Everything else front end wise is done, other than the bench
	 * - All thats left is to record the times the players leave the ice and save it to the database 
	 * - I have put comments of where Times should be recorded 
	 * 
	 */
	public void changeHomePlayers() {

		String value = playerCountHomeCombo.getValue();

		//Pulled goalie 
		if(value.equals("6")) {
			homeJersey0.setStyle("-fx-opacity: .0;");
			homeJersey6.setStyle("-fx-opacity: 1.0;");
			homeJersey5.setStyle("-fx-opacity: 1.0;");
			homeJersey4.setStyle("-fx-opacity: 1.0;");

			homeJerseyLabel0.setStyle("-fx-opacity: .0;");
			homeJerseyLabel6.setStyle("-fx-opacity: 1.0;");
			homeJerseyLabel5.setStyle("-fx-opacity: 1.0;");
			homeJerseyLabel4.setStyle("-fx-opacity: 1.0;");
		}
		if(value.equals("5")) {
			homeJersey0.setStyle("-fx-opacity: 1.0;");
			homeJersey6.setStyle("-fx-opacity: .0;");
			homeJersey5.setStyle("-fx-opacity: 1.0;");
			homeJersey4.setStyle("-fx-opacity: 1.0;");		

			homeJerseyLabel0.setStyle("-fx-opacity: 1.0;");
			homeJerseyLabel6.setStyle("-fx-opacity: .0;");
			homeJerseyLabel5.setStyle("-fx-opacity: 1.0;");
			homeJerseyLabel4.setStyle("-fx-opacity: 1.0;");
		}
		if(value.equals("4")) {
			homeJersey0.setStyle("-fx-opacity: 1.0;");
			homeJersey6.setStyle("-fx-opacity: .0;");
			homeJersey5.setStyle("-fx-opacity: .0;");
			homeJersey4.setStyle("-fx-opacity: 1.0;");

			homeJerseyLabel0.setStyle("-fx-opacity: 1.0;");
			homeJerseyLabel6.setStyle("-fx-opacity: .0;");
			homeJerseyLabel5.setStyle("-fx-opacity: .0;");
			homeJerseyLabel4.setStyle("-fx-opacity: 1.0;");
		}
		if(value.equals("3")) {
			homeJersey0.setStyle("-fx-opacity: 1.0;");
			homeJersey6.setStyle("-fx-opacity: .0;");
			homeJersey5.setStyle("-fx-opacity: .0;");
			homeJersey4.setStyle("-fx-opacity: .0;");

			homeJerseyLabel0.setStyle("-fx-opacity: 1.0;");
			homeJerseyLabel6.setStyle("-fx-opacity: .0;");
			homeJerseyLabel5.setStyle("-fx-opacity: .0;");
			homeJerseyLabel4.setStyle("-fx-opacity: .0;");
		}

	}

	/**
	 * Changes the set up of the away side of the rink in the Time on Ice Page
	 */
	public void changeAwayPlayers() {

		String value = playerCountAwayCombo.getValue();

		//Pulled goalie
		if(value.equals("6")) {
			awayJersey0.setStyle("-fx-opacity: .0;");
			awayJersey6.setStyle("-fx-opacity: 1.0;");
			awayJersey5.setStyle("-fx-opacity: 1.0;");
			awayJersey4.setStyle("-fx-opacity: 1.0;");

			awayJerseyLabel0.setStyle("-fx-opacity: .0;");
			awayJerseyLabel6.setStyle("-fx-opacity: 1.0;");
			awayJerseyLabel5.setStyle("-fx-opacity: 1.0;");
			awayJerseyLabel4.setStyle("-fx-opacity: 1.0;");
		}
		if(value.equals("5")) {
			awayJersey0.setStyle("-fx-opacity: 1.0;");
			awayJersey6.setStyle("-fx-opacity: .0;");
			awayJersey5.setStyle("-fx-opacity: 1.0;");
			awayJersey4.setStyle("-fx-opacity: 1.0;");

			awayJerseyLabel0.setStyle("-fx-opacity: 1.0;");
			awayJerseyLabel6.setStyle("-fx-opacity: .0;");
			awayJerseyLabel5.setStyle("-fx-opacity: 1.0;");
			awayJerseyLabel4.setStyle("-fx-opacity: 1.0;");

		}
		if(value.equals("4")) {
			awayJersey0.setStyle("-fx-opacity: 1.0;");
			awayJersey6.setStyle("-fx-opacity: .0;");
			awayJersey5.setStyle("-fx-opacity: .0;");
			awayJersey4.setStyle("-fx-opacity: 1.0;");

			awayJerseyLabel0.setStyle("-fx-opacity: 1.0;");
			awayJerseyLabel6.setStyle("-fx-opacity: .0;");
			awayJerseyLabel5.setStyle("-fx-opacity: .0;");
			awayJerseyLabel4.setStyle("-fx-opacity: 1.0;");
		}
		if(value.equals("3")) {
			awayJersey0.setStyle("-fx-opacity: 1.0;");
			awayJersey6.setStyle("-fx-opacity: .0;");
			awayJersey5.setStyle("-fx-opacity: .0;");
			awayJersey4.setStyle("-fx-opacity: .0;");

			awayJerseyLabel0.setStyle("-fx-opacity: 1.0;");
			awayJerseyLabel6.setStyle("-fx-opacity: .0;");
			awayJerseyLabel5.setStyle("-fx-opacity: .0;");
			awayJerseyLabel4.setStyle("-fx-opacity: .0;");
		}

	}

	/**
	 * Determines what to do based on Time On Ice mode 
	 * @param e - ActionEvent, which will be a button clicked 
	 */
	public void lineUpClicked(ActionEvent e) {

		JFXButton jerseyNumber = (JFXButton) e.getSource();

		//Line means that a jersey has been clicked 
		if(timeOnIceMode.equals("line")) {
			String lastSide = lastJersey.getId().substring(0, 4);
			String thisSide = jerseyNumber.getId().substring(0, 4); // home/away of this jersey

			if(lastSide.equals(thisSide) && jerseyNumber.getOpacity() != 0.0) {

				timeOnIceMode = "waiting"; //setting the mode
				lastJersey.setText(jerseyNumber.getText());
			} 
		} else if(timeOnIceMode.equals("lineChange")){ // lineChange means that a line up button was clicked last 

			String lastSide = lastLineUp.getId().substring(0, 4);
			String thisSide = jerseyNumber.getId().substring(0, 4); // home/away of this jersey

			if(lastSide.equals(thisSide) && jerseyNumber.getOpacity() != 0.0) {

				timeOnIceMode = "waiting";

				//swap the numbers 
				String temp = jerseyNumber.getText();
				jerseyNumber.setText(lastLineUp.getText());
				lastLineUp.setText(temp);

				lastLineUp.setStyle("-fx-background-color: #C42134"); // set it back to normal color 
			}

		} else if(timeOnIceMode.equals("waiting")) { // in waiting mode, clicking the line up first will let you swap in the line up

			timeOnIceMode = "lineChange";			

			lastLineUp = jerseyNumber;//record the last line up clicked

			lastLineUp.setStyle("-fx-background-color: #FF0000"); //highlight it

		} else if(timeOnIceMode.equals("multiSwap")){ // In multiswap mode, that means that multiple jerseys have been clicked, and are ready to be replaced 

			//Line up numbers are moved to the ice in the order that the jersey's were clicked 
			if(multiSwapCount < multiKeyPressed) {
				multiPlayers.get(multiSwapCount).setText(jerseyNumber.getText());;
				multiSwapCount++;
			}
			if(multiSwapCount == multiKeyPressed) {
				multipleSwapDisplay.setText("");
				timeOnIceMode = "waiting";
				multiSwapCount = 0;
				multiPlayers = new ArrayList<Label>(5);
			}
		}
//		try{  
//			Class.forName("com.mysql.jdbc.Driver");  
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//			Statement stmt=con.createStatement();
//		
//			String query="INSERT INTO shift (playerNumber, shiftPeriod, shiftTime, shiftTeam) VALUES ('"+lastJersey.getText()+"','"+PERIODS[periodIndex]+"','"+timeOff+"','"+lastTeamClicked+"');";
//			System.out.println(query);
//			int result=stmt.executeUpdate(query);
//		}
//	catch(Exception e1){
//			System.out.println(e1);
//		} 
	}

	/**
	 * Determines what to do based on Time On Ice mode 
	 * @param e - Mouse event of clicking the Label of the number 
	 * @throws IOException
	 */
	public void jerseyClicked(MouseEvent e) throws IOException {

		//HERE IS WHERE TIME SHOULD BE RECORDED 
		//Should only be used if youre switching witht he lineup 
		timeOff = Time.getText(); //recording the time, Dont do anything with this right now, but will once we finally use the database 

		if(!swapCheckBox.isSelected()) { //if the swap box is NOT selected

			lastJersey = (Label) e.getSource(); 

			if(timeOnIceMode.equals("line")) {

				lastJersey = (Label) e.getSource(); //Then set the button 

			} else if(timeOnIceMode.equals("waiting") || timeOnIceMode.equals("swap")) {

				timeOnIceMode = "line"; //setting the mode

			} else if (timeOnIceMode.equals("bench")) {

				lastJersey.setText(benchPlayerClicked); //Set the text of the button to the appropriate number

			} else if(timeOnIceMode.equals("multiSelect")){ 

				if(multiSwapCount < multiKeyPressed && !multiPlayers.contains(lastJersey)) {
					multiSwapCount++;
					multiPlayers.add(lastJersey);
				}
				if(multiSwapCount == multiKeyPressed) {
					multipleSwapDisplay.setText("Select " + multiKeyPressed + " players to swap in");
					timeOnIceMode = "multiSwap";
					multiSwapCount = 0;
				}

			}

		} else { //if the swap box is selected
			if(timeOnIceMode.equals("swap")) { // swaps the jersey clicked with the last jersey clicked 

				//swap positions
				String temp = lastJersey.getText();
				lastJersey.setText(((Label) e.getSource()).getText());
				((Label) e.getSource()).setText(temp);	

				timeOnIceMode = "waiting";
			} else { // if in any other mode than waiting, goes into swap mode and sets the lastJersey

				timeOnIceMode = "swap";
				lastJersey = (Label) e.getSource();

			}

		}
	}

	/**
	 * This Method will be used in a similar way the previous methods worked,
	 * for moving a player from the bench to the line up 
	 * 
	 * We were not able to get to this point. For any questions on vision, feel free to contact Matthew Murch from the Phase 2 group
	 */
	public void benchClicked(ActionEvent e){
		//Store the selected number in benchPlayerClicked and change mode to bench
	}

	/**
	 * This method sets the number of jerseys that will be swapped out 
	 * @param num - number of jerseys to swap out 
	 */
	public void swapMultiple(int num) {

		if(timeOnIceMode.equals("waiting")) {
			timeOnIceMode = "multiSelect"; //Sets the mode to multiselect so the only thing the user can do is click a jersey
			multipleSwapDisplay.setText("Select " + num + " players to swap out");
			multiKeyPressed = num; 
		} 
	}

	//************************Ends Time on Ice Methods ***************************************


	// **********************Start possession diagram button functionality ***********************************************


	/**
	 * This is the method that is called when one of the buttons on the possession diagram rink 
	 * are clicked. This will fill the table with the necessary information as the game is ongoing.
	 * 
	 * There are a few keyboard shortcuts that will need added with time.
	 * 
	 * Suggestions from Colin to next years group:
	 * - To change the page to just a home and away dropdown rather than having all four
	 * of the dropdowns for who has possession. 
	 * - Also, maybe add the zone that it was clicked to the
	 * table.
	 * 
	 * @param e the event that is the clicking of the button
	 */
	public void buttonClicked(ActionEvent e) {
		// So you know where the click came from
		JFXButton b = (JFXButton) e.getSource();
		String id = b.getId();
		String team;

		// The logic below gets the team value to add to the table
		if (id.equals("leftTop") || id.equals("middleTop") || id.equals("rightTop")) {
			team = "Miami";
		} else if (id.equals("leftBottom") && (periodIndex == 0 || periodIndex == 2)){
			team = (String) awayCombo1.getValue();
		} else if (id.equals("leftBottom") && periodIndex == 1) {
			team = (String) homeCombo1.getValue();
		} else if (id.equals("rightBottom") && (periodIndex == 0 || periodIndex == 2)) {
			team = (String) homeCombo2.getValue();
		} else if (id.equals("rightBottom") && periodIndex == 1) {
			team = (String) awayCombo2.getValue();
		} else if (id.equals("middleBottom") && (periodIndex == 0 || periodIndex == 2)) {
			team = (String) awayCombo1.getValue();
		} else if (id.equals("middleBottom") && periodIndex == 1) {
			team = (String) homeCombo1.getValue();
		} else {
			team = "Away";
		}

		// Get the information that needs to be displayed within the table
		String player = (String) playerNum.getValue();
		String time = (String) formatTimeShort(currentTime);
		String period = PERIODS[periodIndex];

		// add the new set of data to the tablelist
		possTable.getItems().add(new PossTable(period, time, team, player));
//		try{  
//			Class.forName("com.mysql.jdbc.Driver");  
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//			Statement stmt=con.createStatement();
//
//			String query="INSERT INTO possession( game, period, gameTime, team, player) VALUES ('"+GamePickerPoss.getValue()+"','"+PERIODS[periodIndex]+"','"+time+"','"+team+"','"+player+"');";
//			System.out.println(query);
//			int result=stmt.executeUpdate(query);
//		}
//		catch(Exception e1){
//			System.out.println(e1);
//		} 

		// System.out.println(period + " " + time + " " + team + " " + player);

	}

	/**
	 * Initializes the table so that data can be put in
	 */
	public void initializePossTable() {

		periodColumn.setCellValueFactory(new PropertyValueFactory<PossTable, SimpleStringProperty>("period"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<PossTable, SimpleStringProperty>("time"));
		teamColumn.setCellValueFactory(new PropertyValueFactory<PossTable, SimpleStringProperty>("team"));
		playerColumn.setCellValueFactory(new PropertyValueFactory<PossTable, SimpleStringProperty>("player"));	

		possTable.setItems(possTableList);
	}

	/**
	 * Eventually these will all be changed to SQL queries so that data is pulled out
	 * of the databas
	 */
	public void initializePossDropdowns() {
		GamePickerPoss.getItems().addAll(model.getGameStats());

		homeCombo1.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		awayCombo1.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		homeCombo2.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		awayCombo2.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		playerNum.getItems().addAll(
				"0",
				"1",
				"2",
				"3");
	}

	// end possession diagram button functionality ***********************************************



	/**
	 * 
	 * The rest of the methods in this class were created by Phase 1 group 
	 * 
	 */

	/**
	 * Helper method to copy rink diagram from Clip object to the drawList
	 */
	private void copyDrawList(List<DrawnObject> list) {
		drawList = new ArrayList<DrawnObject>();
		drawList.addAll(list);
	}

	/**
	 * Helper method that converts milliseconds to a stopwatch time format
	 * example output is 19:03. Used to fill the tables with the timestamp
	 */
	private static String formatTimeShort(final long l)
	{
		final long time;

		if(periodIndex <= 2) {
			time = TimeUnit.MINUTES.toMillis(20) - l;
		} else {
			time = TimeUnit.MINUTES.toMillis(5) - l;
		}
		if (time < 0) {
			return String.format("%02d:%02d %s", 0, 0, PERIODS[periodIndex]);
		}
		final long min = TimeUnit.MILLISECONDS.toMinutes(time);
		final long sec = TimeUnit.MILLISECONDS.toSeconds(time - TimeUnit.MINUTES.toMillis(min));
		return String.format("%02d:%02d", min, sec);
	}

	/**
	 * Helper method that converts milliseconds to a stopwatch time format
	 * example output is 19:03 1st
	 */
	private static String formatTime(final long l)
	{
		final long time;

		if(periodIndex <= 2) {
			time = TimeUnit.MINUTES.toMillis(20) - l;
		} else {
			time = TimeUnit.MINUTES.toMillis(5) - l;
		}
		if (time < 0) {
			return String.format("%02d:%02d %s", 0, 0, PERIODS[periodIndex]);
		}
		final long min = TimeUnit.MILLISECONDS.toMinutes(time);
		final long sec = TimeUnit.MILLISECONDS.toSeconds(time - TimeUnit.MINUTES.toMillis(min));
		return String.format("%02d:%02d %s", min, sec, PERIODS[periodIndex]);
	}

	/**
	 * creates a new clip with the current time on the timer, range of 15 seconds
	 */
	private void getTime() {
		long sTime = currentTime - TimeUnit.SECONDS.toMillis(15);
		String start = formatTime(Math.max(sTime, 0));
		String end = formatTime(currentTime);
		//TimeStamps.appendText(start + " - " + end + "\n");
		Clip c = new Clip(start + " - " + end, "Untitled");
		TimeStamps.getItems().add(c.getTime());
		clips.add(c);
	}

	/**
	 * Opens provided link when clicked
	 */
	@FXML
	public void OpenGameButtonClicked() {

		if(currentClip != null) {
			System.out.println(currentClip.getURL());
			try {
				Desktop.getDesktop().browse(new URL(currentClip.getURL()).toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method Draws selected Clip's diagram
	 */
	@FXML
	public void ClipListSelectionChanged() {

		if(ClipList.getSelectionModel().getSelectedItem() == null) return;
		String clipName = ClipList.getSelectionModel().getSelectedItem().toString();
		System.out.println(clipName);
		for(int i = 0; i < goalieClips.size(); i++) {
			Clip c = goalieClips.get(i);
			String clipMsg = "Clip: " + c.getTime() + "\n" + "Description: " + c.getTitle();
			if(clipMsg.equals(clipName)) {
				currentClip = c;
				break;
			}
		}

		//System.out.println(c.getRinkDiagram().get(0).getText());
		rinkGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
		drawLinesAndNumbers(currentClip.getRinkDiagram(), rinkGC);
	}

	/**
	 * Checks the database connection before login
	 */
	@FXML
	public void submitKey() {
		primaryStage.getScene().setCursor(Cursor.WAIT);
		String keyVal = databaseKey.getText();

		//Create a task to connect to database, async call
		Task<Boolean> task = new Task<Boolean>() {
			@Override
			public Boolean call() {
				String key = databaseKey.getText();
				boolean authenticated = model.makeDatabase(key);
				Boolean result = Boolean.valueOf(authenticated);
				return result ;
			}
		};

		//get the result of db connection
		//if success -> go to login page
		//if fail -> alert error to user
		task.setOnSucceeded(e -> {
			boolean authenticated = task.getValue().booleanValue();
			primaryStage.getScene().setCursor(Cursor.DEFAULT);
			if(authenticated == true) {
				try {
					//Write the user's choice for future use
					File keyFile = new File("Key.txt");					
					PrintWriter pw = new PrintWriter(keyFile);
					pw.println(keyVal);
					pw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				loadScene(LOGIN_SCENE);		
			} else {
				String msg = "Error on getting key";
				Alert err = new Alert(AlertType.CONFIRMATION, msg);
				err.show();
			}
		});

		new Thread(task).start();


	}

	/**
	 * This is the method that will switch to the home screen once login is clicked
	 * If player --> player home screen.
	 * If goalie --> goalie home screen
	 * if admin --> admin home screen
	 */
	@FXML
	public void loginButtonClicked() {
		currentUser = users.getValue();
		String number = model.getJerseyNo(currentUser);
		String position = model.getPosition(number);
		if(position.equals("goalie")) {
			loadScene(GOALIE_CARD);
		}else if(currentUser.equals("ADMIN")) {
			adminPass.setVisible(true);
			if(adminPass.getText().equals("test")) {
				loadScene(ADMIN_HOME);
			}		
		}
		else {	
			loadScene(PLAYER_CARD);
		}
	}
	//Admin card button functionalities
	/**
	 * This is the method that will go back to the previous scene.
	 */
	@FXML
	public void goBack() {
		loadScene(ADMIN_HOME);
	}

	/**
	 * This is the method that will go to the admin database.
	 */
	@FXML
	public void databaseClicked() {
		loadScene(ADMIN_ADD);
	}

	/**
	 * This is the method that will add a player to the database.
	 */
	@FXML
	public void addPlayer() {
		Player player = new Player(Integer.parseInt(addJerseyNumber.getText()), fullName.getText(), 
				height.getText(), weight.getText(), birthDate.getText(), homeTown.getText());
		String position;
		if(GoalieCheckBox.isSelected()) {
			position = "goalie";
		} else {
			position = "player";
		}
		model.addPlayerWithPosition(player, position);
		//Displays success text for 2 seconds
		playerSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(
				Duration.seconds(2)
				);
		visiblePause.setOnFinished(
				event -> playerSuccess.setVisible(false)
				);
		visiblePause.play();	
	}

	/**
	 * This is the method that will remove player from the database.
	 */
	@FXML
	public void removePlayer() {
		Player Removeplayer = new Player(Integer.parseInt(removeJerseyNumber.getText()));
		model.deletePlayer(Removeplayer);
		playerRemoved.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(
				Duration.seconds(2)
				);
		visiblePause.setOnFinished(
				event -> playerRemoved.setVisible(false)
				);
		visiblePause.play();	
	}


	/**
	 * This is the method that will add a game to the database.
	 */
	@FXML
	public void addGame() {
		String opp = opponent.getText();
		String day = date.getText();
		String name = opp + " " + day;
		//System.out.println(name);
		Game game = new Game(name);
		model.addGame(opp, day);

		gameSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(
				Duration.seconds(2)
				);
		visiblePause.setOnFinished(
				event -> gameSuccess.setVisible(false)
				);
		visiblePause.play();	
	}

	/**
	 * This is the method that will go to the possession diagram scene.
	 */
	@FXML
	public void PossessionDiagramClicked() {
		loadScene(ADMIN_POSSESSIONDIAGRAM);
	}

	/**
	 * This is the method that will go to the passing diagram scene.
	 */
	@FXML
	public void PassingDiagramClicked() {
		loadScene(ADMIN_PASSINGDIAGRAM);
	}

	/**
	 * This is the method that will go to the rink diagram scene.
	 */
	@FXML
	public void RinkDiagramClicked() {
		loadScene(ADMIN_RINKDIAGRAM);
	}


	// player card button functionalities 
	/**
	 * This is the method that will go to the player card scene.
	 */
	@FXML
	public void PlayerCardClicked() {
		System.out.print("player card clicked");
		loadScene(PLAYER_CARD);

	}

	/**
	 * This is the method that will go back the home scene.
	 */
	@FXML
	public void PlayerHomeButtonClicked() {
		loadScene(PLAYER_HOME);
	}

	/**
	 * This is the method that will logout and go back the login scene.
	 */
	@FXML
	public void PlayerLogoutButtonClicked() {
		loadScene(LOGIN_SCENE);
	}


	// goalie card button functionalities
	/**
	 * This is the method that will go to the player card scene.
	 */
	@FXML
	public void GoalieCardClicked() {
		loadScene(GOALIE_CARD);
	}

	/**
	 * This is the method that will go to the player card scene.
	 */
	@FXML
	public void GoalieTeamProfileClicked() {
		loadScene(TEAM_PROFILE);
	}


	/**
	 * This is the method that will go back the home scene.
	 */
	@FXML
	public void GoalieHomeButtonClicked() {
		loadScene(GOALIE_HOME);
	}

	/**
	 * This is the method that will logout and go back the login scene.
	 */
	@FXML
	public void GoalieLogoutButtonClicked() {
		loadScene(LOGIN_SCENE);
	}
	/**
	 * This method loads the Shot Chart page 
	 */
	@FXML
	public void shotChartClicked() {
		loadScene(SHOT_CHART);
	}

	/**
	 * This method loads the Shot Chart page 
	 */
	@FXML
	public void timeOnIceClicked() {
		loadScene(TIME_ON_ICE);
	}

	/**
	 * This is the method that will change the color of the circle to red
	 */
	@FXML
	public void setColorRed() {
		homeGC.setStroke(Color.color(.77, .13, .2));
		awayGC.setStroke(Color.color(.77, .13, .2));
	}

	/**
	 * This is the method that will change the color of the circle to green
	 */
	@FXML
	public void setColorGreen() {
		homeGC.setStroke(Color.GREEN);
		awayGC.setStroke(Color.GREEN);
	}



	/**
	 * Method changes highlighted canvas to home
	 */
	@FXML
	public void NetChartHomeSelected() {
		netChartCanvas = HomeNetChartCanvas;
	}

	/**
	 * Method changes highlighted canvas to away
	 */
	@FXML
	public void NetChartAwaySelected() {
		netChartCanvas = AwayNetChartCanvas;
	}

	/**
	 * Method undos an item on the NetChartScene
	 */
	@FXML
	public void NetChartUndoPressed() {
		//removes the last circle, redraws chart
		if(netChartCanvas == HomeNetChartCanvas) {
			Color original = (Color) homeGC.getStroke();
			homeGC.clearRect(0, 0, netChartCanvas.getWidth(), netChartCanvas.getHeight());
			if(homeNetChartItems.size() < 2) return;

			//have to remove two (one for circle, one for text)
			homeNetChartItems.remove(homeNetChartItems.size()-1);
			homeNetChartItems.remove(homeNetChartItems.size()-1);
			drawOvals(homeNetChartItems, homeGC);
			homeNetChartIndex--;
			homeGC.setStroke(original);
		} else if(netChartCanvas == AwayNetChartCanvas) {
			Color original = (Color) awayGC.getStroke();
			awayGC.clearRect(0, 0, netChartCanvas.getWidth(), netChartCanvas.getHeight());
			if(awayNetChartItems.size() < 2) return;

			//have to remove two (one for circle, one for text)
			awayNetChartItems.remove(awayNetChartItems.size()-1);
			awayNetChartItems.remove(awayNetChartItems.size()-1);
			drawOvals(awayNetChartItems, awayGC);
			awayNetChartIndex--;
			awayGC.setStroke(original);
		}


	}

	/**
	 * Helper drawing method for any canvas using ovals
	 */
	private static void drawOvals(ArrayList<DrawnObject> d, GraphicsContext gc) {
		for(int i = 0; i < d.size(); i++) {
			DrawnObject obj = d.get(i);
			System.out.println("[" + obj.getLastPoint().getX() + ", " + obj.getLastPoint().getY() + "]");
			System.out.println(obj.getWidth());

			//draw either the circle or the text inside
			if(obj.getText() == null) {
				Point p = obj.getLastPoint();
				gc.setStroke(p.getColor());
				gc.strokeOval(p.getX(), p.getY(), obj.getWidth(), obj.getWidth());
			} else {
				System.out.println("text: " + obj.getText());
				Point p = obj.getLastPoint();
				gc.fillText(obj.getText(), p.getX(), p.getY());
			}
		}
	}

	/**
	 * Helper method to draw lines and numbers
	 */
	private static void drawLinesAndNumbers(ArrayList<DrawnObject> d, GraphicsContext gc) {
		for(int i = 0; i < d.size(); i++) {
			DrawnObject obj = d.get(i);
			System.out.println("Points size: " + obj.getPoints().size());
			if(obj.getText() == null) {
				//start the line
				gc.beginPath();
				gc.setLineWidth(obj.getWidth());

				//draw all points on line
				for(int xy = 0; xy < obj.size(); xy++) {
					Point p = obj.getPoint(xy);
					gc.setStroke(p.getColor());
					gc.lineTo(p.getX(), p.getY());
					gc.stroke();
				}
			} else {
				//draw the text
				Point p = obj.getPoint(obj.size()-1);
				gc.setFill(p.getColor());
				gc.fillText(obj.getText(), p.getX(), p.getY());
			}
		}
	}

	@FXML
	/**
	 * Saving the home scoring chart
	 */
	public void ScoringChartSaveHomePressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			homeChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> homeChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		model.addChart("offense", GamePicker.getSelectionModel().getSelectedItem().toString(), "scoringChart", homeNetChartItems);
		homeChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> homeChartSuccess.setVisible(false));
		visiblePause.play();
		return;
	}

	@FXML
	/**
	 * Saving the home scoring chart
	 */
	public void ScoringChartSaveAwayPressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			awayChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> awayChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		model.addChart("defense", GamePicker.getSelectionModel().getSelectedItem().toString(), "scoringChart", awayNetChartItems);
		awayChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> awayChartSuccess.setVisible(false));
		visiblePause.play();	
	}

	@FXML
	/**
	 * Method saves the home chart
	 */
	public void NetChartSaveHomePressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			homeNetChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> homeNetChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		model.addChart("offense", GamePicker.getSelectionModel().getSelectedItem().toString(), "netChart", homeNetChartItems);
		homeNetChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> homeNetChartSuccess.setVisible(false));
		visiblePause.play();
//		try{  
//			Class.forName("com.mysql.jdbc.Driver");  
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//			Statement stmt=con.createStatement();
//			int shotID =6;
//			String link=null;
//			String shotnumber="0";
//			String query="INSERT INTO shot( 'teamFor', 'teamAgainst', 'link', 'player', 'shotnumber', 'shotType', 'playtype', 'playerStatus', 'releaseType', 'pChance', 'sChance', 'createdBy', 'result', 'scoringChance', 'rb', 'goalie', 'extra', 'statusCombo') VALUES (\""+teamForCombo.getValue()+"\",\""+teamAgainstCombo.getValue()+"\",\""+urlTextField.getText()+"\",\""+playerCombo.getValue()+"\",\""+shotTypeCombo.getValue()+"\",\""+playTypeCombo.getValue()+"\",\""+playerStatusCombo.getValue()+"\",\""+releaseTypeCombo.getValue()+"\",\""+pChanceCombo.getValue()+"\",\""+sChanceCombo.getValue()+"\",\""+createdByCombo.getValue()+"\",\""+resultCombo.getValue()+"\",\""+scoringChanceCombo.getValue()+"\",\""+rbCombo.getValue()+"\",\""+goalieCombo.getValue()+"\",\""+createdByCombo.getValue()+"\",\""+statusCombo.getValue()+"\");";
//			System.out.println(query);
//			int result=stmt.executeUpdate(query);
//		}
//		catch(Exception e){
//			System.out.println(e);
//		} 

	}


	@FXML
	/**
	 * Method saves the away chart
	 */
	public void NetChartSaveAwayPressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			awayNetChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> awayNetChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		model.addChart("defense", GamePicker.getSelectionModel().getSelectedItem().toString(), "netChart", awayNetChartItems);
		awayNetChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> awayNetChartSuccess.setVisible(false));
		visiblePause.play();
//		try{  
//			Class.forName("com.mysql.jdbc.Driver");  
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sPAnalytics","root","root");  
//			Statement stmt=con.createStatement();
//			int shotID =6;
//			String link=null;
//			String shotnumber="0";
//			String query="INSERT INTO shot( 'teamFor', 'teamAgainst', 'link', 'player', 'shotnumber', 'shotType', 'playtype', 'playerStatus', 'releaseType', 'pChance', 'sChance', 'createdBy', 'result', 'scoringChance', 'rb', 'goalie', 'extra', 'statusCombo') VALUES (\""+teamForCombo.getValue()+"\",\""+teamAgainstCombo.getValue()+"\",\""+urlTextField.getText()+"\",\""+playerCombo.getValue()+"\",\""+shotTypeCombo.getValue()+"\",\""+playTypeCombo.getValue()+"\",\""+playerStatusCombo.getValue()+"\",\""+releaseTypeCombo.getValue()+"\",\""+pChanceCombo.getValue()+"\",\""+sChanceCombo.getValue()+"\",\""+createdByCombo.getValue()+"\",\""+resultCombo.getValue()+"\",\""+scoringChanceCombo.getValue()+"\",\""+rbCombo.getValue()+"\",\""+goalieCombo.getValue()+"\",\""+createdByCombo.getValue()+"\",\""+statusCombo.getValue()+"\");";			System.out.println(query);
//			int result=stmt.executeUpdate(query);
//		}
//		catch(Exception e){
//			System.out.println(e);
//		} 
	}

	/**
	 * Method opens NCHC link on default browser
	 */
	@FXML
	public void NCHCOpenButtonClicked() {
		try {
			Desktop.getDesktop().browse(new URL(NCHC_URL.getText()).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method increments the period
	 */
	@FXML
	public void NextPeriodClicked() {
		periodIndex = (periodIndex+1) % PERIODS.length;
		timerStart = System.nanoTime();
		timerPause = 0;
		currentTime = 0;
		Time.setText(formatTime(currentTime));
	}

	/**
	 * Method starts timer
	 */
	@FXML
	public void StartTimerClicked() {
		timerOn = true;
		timerStart = System.nanoTime();

		//run stopwatch on thread to keep app from locking
		Thread t = new Thread() {
			public void run() {
				while(timerOn) {
					try {
						currentTime = (System.nanoTime() - timerStart) / 1000000 + timerPause;
						String formattedTime = formatTime(currentTime);

						Thread.sleep(10);
						//update the application thread to show time
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Time.setText(formattedTime);
							}
						});

					} catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}	
			}
		};
		t.start();
	}


	/**
	 * Method stops timer
	 */
	@FXML
	public void StopTimerClicked() {
		timerOn = false;
		timerPause += (System.nanoTime() - timerStart) / 1000000;
	}

	/**
	 * Method restarts timer
	 */
	@FXML
	public void ResetTimerClicked() {
		timerStart = System.nanoTime();
		timerPause = 0;
		currentTime = 0;
		periodIndex = 0;
		Time.setText("20:00 1st");
	}

	/**
	 * Method saves all clips to the database
	 */
	@FXML
	public void SaveClipsToDBClicked() {
		if(GamePicker.getSelectionModel().getSelectedItem()	 == null) {
			selectGameFirst.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> selectGameFirst.setVisible(false));
			visiblePause.play();
		} else {
			String game = GamePicker.getSelectionModel().getSelectedItem().toString();
			for(int i = 0; i < clips.size(); i++) {
				clips.get(i).setGame(game);
				model.addClip(clips.get(i));
			}
		}
		clipsSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> clipsSuccess.setVisible(false));
		visiblePause.play();
	}

	/**
	 * Method saves diagram to current clip
	 */
	@FXML
	public void SaveRinkClicked() {
		int index = TimeStamps.getSelectionModel().getSelectedIndex();
		if (index == -1) return;
		clips.get(index).setRinkDiagram(drawList);
		ObservableList<String> players = PlayerList.getSelectionModel().getSelectedItems();
		ArrayList<String> playersAL = new ArrayList<String>();
		for(String player : players) {
			playersAL.add(player);
		}
		clips.get(index).addPlayer(playersAL);

		diagramSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> diagramSuccess.setVisible(false));
		visiblePause.play();	
	}

	/**
	 * Method Saves the title of selected clip
	 */
	@FXML
	public void SaveNotesButtonClicked() {
		int index = TimeStamps.getSelectionModel().getSelectedIndex();
		if (index == -1) return;
		clips.get(index).setTitle(TimeStampNotes.getText());
		clips.get(index).setURL(NCHC_URL.getText());
		titleAndLinkSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> titleAndLinkSuccess.setVisible(false));
		visiblePause.play();
	}

	/**
	 * Method begins drawing on canvas when mouse pressed
	 */
	@FXML
	public void CanvasMousePressed(MouseEvent e) {
		RadioButton selected = (RadioButton) RinkGroup.getSelectedToggle();
		if(selected.getText().equals("Line")) {
			line = new DrawnObject(e.getX(), e.getY(), rinkGC.getStroke(), rinkGC.getLineWidth());
			rinkGC.beginPath();
			rinkGC.lineTo(line.getLastPoint().getX(), line.getLastPoint().getY());
			rinkGC.stroke();
			drawList.add(line);
		} else if(selected.getText().equals("Text")) {
			DrawnObject text = new DrawnObject(e.getX()-12, e.getY()+12, rinkGC.getFill(), 0, RinkDiagramText.getText());
			rinkGC.fillText(text.getText(), text.getLastPoint().getX(), text.getLastPoint().getY());
			drawList.add(text);
		}
	}

	/**
	 * Method draws on canvas with mouse movement
	 */
	@FXML
	public void CanvasMouseDragged(MouseEvent e) {
		RadioButton selected = (RadioButton) RinkGroup.getSelectedToggle();
		if(selected.getText().equals("Line")) {
			line.addPoint(e.getX(), e.getY(), rinkGC.getStroke());
			rinkGC.lineTo(line.getLastPoint().getX(), line.getLastPoint().getY());
			rinkGC.stroke();
		}
	}

	/**
	 * Method undos the last drawing action
	 */
	@FXML
	public void UndoRinkClicked() {
		rinkGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
		if(drawList.size() < 1) return;

		//delete last object, redraw
		drawList.remove(drawList.size()-1);
		drawLinesAndNumbers(drawList, rinkGC);

		rinkGC.setStroke(RinkCP.getValue());
		rinkGC.setFill(RinkCP.getValue());
		rinkGC.setLineWidth(RinkSlider.getValue());
		//drawList.clear();
	}

	/**
	 * Method changes selected color
	 */
	@FXML
	public void RinkCPColorChange() {
		rinkGC.setStroke(RinkCP.getValue());
		rinkGC.setFill(RinkCP.getValue());
	}

	/**
	 * Method updates line width
	 */
	@FXML
	public void RinkSliderDropped() {
		rinkGC.setLineWidth(RinkSlider.getValue());
	}

	/**
	 * Method to initialize table for a player
	 */
	@FXML
	public ObservableList<MemberModel> makeMemberTable(HashMap<String, HashMap> playerData) {
		ObservableList<MemberModel> data = FXCollections.observableArrayList();;

		Iterator it = playerData.entrySet().iterator();
		while (it.hasNext()) {
			MemberModel model = new MemberModel();
			Map.Entry pair = (Map.Entry)it.next();
			HashMap values = (HashMap) pair.getValue();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			Iterator stats = values.entrySet().iterator();
			//each game would be a new row
			while (stats.hasNext()) {
				Map.Entry details = (Map.Entry)stats.next();
				System.out.println(details.getKey() + " = " + details.getValue());
				//check what value it is and make the correct value
				if (details.getKey() == "PPA") {
					model.setPPA(details.getValue());
				}
				else if (details.getKey() == "A") {
					model.setA(details.getValue());
				}
				else if (details.getKey() == "PPG") {
					model.setPPG(details.getValue());
				}
				else if (details.getKey() == "G") {
					model.setG(details.getValue());
				}
				else if (details.getKey() == "GP") {
					model.setGP(details.getValue());
				}
				else if (details.getKey() == "SOG") {
					model.setSOG(details.getValue());
				}
				else if (details.getKey() == "percent") {
					model.setPercent(details.getValue());
				}
				else if (details.getKey() == "PTS") {
					model.setPTS(details.getValue());
				}
				else if (details.getKey() == "PROD") {
					model.setPROD(details.getValue());
				}
				else if (details.getKey() == "SHG") {
					model.setSHG(details.getValue());
				}
				else if (details.getKey() == "GWG") {
					model.setGWG(details.getValue());
				}
				else if (details.getKey() == "winsOrLosses") {
					model.setPlusMinus(details.getValue());
				}
				else if (details.getKey() == "season") {
					model.setSeason(details.getValue());
				}
				else if (details.getKey() == "GTG") {
					model.setGTG(details.getValue());
				}
				else if (details.getKey() == "TOIG") {
					model.setTOI(details.getValue());
				}
				stats.remove();
			}
			it.remove(); // avoids a ConcurrentModificationException
			data.add(model);

			//here model goes out of scope
		}
		return data;
	}
	/**
	 * Method to initialize table for a player
	 */
	@FXML
	public ObservableList<GoalieModel> makeGoalieTable(HashMap<String, HashMap> playerData) {
		ObservableList<GoalieModel> data = FXCollections.observableArrayList();

		Iterator it = playerData.entrySet().iterator();
		while (it.hasNext()) {
			GoalieModel model = new GoalieModel();
			Map.Entry pair = (Map.Entry)it.next();
			HashMap values = (HashMap) pair.getValue();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			Iterator stats = values.entrySet().iterator();
			//each game would be a new row
			while (stats.hasNext()) {
				Map.Entry details = (Map.Entry)stats.next();
				System.out.println(details.getKey() + " = " + details.getValue());
				//check what value it is and make the correct value
				if (details.getKey() == "GP") {
					TableColumn<GoalieModel,String> nameColumn=new TableColumn<>("GP");
					model.setGP(details.getValue());
				}
				else if (details.getKey() == "season") {
					model.setSeason(details.getValue());
				}
				else if (details.getKey() == "SV") {
					model.setSV(details.getValue());
				}
				else if (details.getKey() == "T") {
					model.setT(details.getValue());
				}
				else if (details.getKey() == "GAA" ) {
					model.setGAA(details.getValue());
				}
				else if (details.getKey() == "W") {
					model.setW(details.getValue());
				}
				else if (details.getKey() == "GA") {
					model.setGA(details.getValue());
				}
				else if (details.getKey() == "SA") {
					model.setSA(details.getValue());
				}
				else if (details.getKey() == "SVpercent") {
					model.setSVpercent(details.getValue());
				}
				else if (details.getKey() == "L") {
					model.setL(details.getValue());
				}
				else if (details.getKey() == "SO") {
					model.setSO(details.getValue());
				}

				stats.remove();
			}
			it.remove(); // avoids a ConcurrentModificationException
			data.add(model);

			//here model goes out of scope
		}
		return data;


	}

	//creates a table with goalie stats
	public void makeGoalieCols(TableView<GoalieModel> tbData) {

		TableColumn<GoalieModel, String> season = new TableColumn<GoalieModel, String>("Season");
		season.setCellValueFactory(new PropertyValueFactory("season"));
		TableColumn<GoalieModel, String> GP = new TableColumn<GoalieModel, String>("GP");
		GP.setCellValueFactory(new PropertyValueFactory("GP"));
		TableColumn<GoalieModel, String> W = new TableColumn<GoalieModel, String>("W");
		W.setCellValueFactory(new PropertyValueFactory("W"));
		TableColumn<GoalieModel, String> L = new TableColumn<GoalieModel, String>("L");
		L.setCellValueFactory(new PropertyValueFactory("L"));
		TableColumn<GoalieModel, String> T = new TableColumn<GoalieModel, String>("T");
		T.setCellValueFactory(new PropertyValueFactory("T"));
		TableColumn<GoalieModel, String> GA = new TableColumn<GoalieModel, String>("GA");
		GA.setCellValueFactory(new PropertyValueFactory("GA"));
		TableColumn<GoalieModel, String> GAA = new TableColumn<GoalieModel, String>("GAA");
		GAA.setCellValueFactory(new PropertyValueFactory("GAA"));
		TableColumn<GoalieModel, String> SA = new TableColumn<GoalieModel, String>("SA");
		SA.setCellValueFactory(new PropertyValueFactory("SA"));
		TableColumn<GoalieModel, String> SV = new TableColumn<GoalieModel, String>("SV");
		SV.setCellValueFactory(new PropertyValueFactory("SV"));
		TableColumn<GoalieModel, String> SVpercent = new TableColumn<GoalieModel, String>("SV%");
		SVpercent.setCellValueFactory(new PropertyValueFactory("SVpercent"));
		TableColumn<GoalieModel, String> SO = new TableColumn<GoalieModel, String>("SO");
		SO.setCellValueFactory(new PropertyValueFactory("SO"));

		tbData.getColumns().setAll(season,GP,W,L,T,GA,GAA,SA,SV,SVpercent,SO);
	}

	//create table with player stats
	public void makeMemberCols(TableView<MemberModel> tbData) {

		TableColumn<MemberModel, String> season = new TableColumn<MemberModel, String>("Season");
		season.setCellValueFactory(new PropertyValueFactory("season"));
		TableColumn<MemberModel, String> GP = new TableColumn<MemberModel, String>("GP");
		GP.setCellValueFactory(new PropertyValueFactory("GP"));
		TableColumn<MemberModel, String> A = new TableColumn<MemberModel, String>("A");
		A.setCellValueFactory(new PropertyValueFactory("A"));
		TableColumn<MemberModel, String> PPG = new TableColumn<MemberModel, String>("PPG");
		PPG.setCellValueFactory(new PropertyValueFactory("PPG"));
		TableColumn<MemberModel, String> G = new TableColumn<MemberModel, String>("G");
		G.setCellValueFactory(new PropertyValueFactory("G"));
		TableColumn<MemberModel, String> SOG = new TableColumn<MemberModel, String>("SOG");
		SOG.setCellValueFactory(new PropertyValueFactory("SOG"));
		TableColumn<MemberModel, String> percent = new TableColumn<MemberModel, String>("\'%");
		percent.setCellValueFactory(new PropertyValueFactory("percent"));
		TableColumn<MemberModel, String> PTS = new TableColumn<MemberModel, String>("PTS");
		PTS.setCellValueFactory(new PropertyValueFactory("PTS"));
		TableColumn<MemberModel, String> PROD = new TableColumn<MemberModel, String>("PROD");
		PROD.setCellValueFactory(new PropertyValueFactory("PROD"));
		TableColumn<MemberModel, String> SHG = new TableColumn<MemberModel, String>("SHG");
		SHG.setCellValueFactory(new PropertyValueFactory("SHG"));
		TableColumn<MemberModel, String> GWG = new TableColumn<MemberModel, String>("GWG");
		GWG.setCellValueFactory(new PropertyValueFactory("GWG"));
		TableColumn<MemberModel, String> winsOrLosses = new TableColumn<MemberModel, String>("+/-");
		winsOrLosses.setCellValueFactory(new PropertyValueFactory("plusMinus"));
		TableColumn<MemberModel, String> GTG = new TableColumn<MemberModel, String>("GTG");
		GTG.setCellValueFactory(new PropertyValueFactory("GTG"));
		TableColumn<MemberModel, String> TOIG = new TableColumn<MemberModel, String>("TOI/G");
		TOIG.setCellValueFactory(new PropertyValueFactory("TOI"));
		tbData.getColumns().setAll(season,GP,A,PPG,G,SOG,percent,PTS,PROD,SHG,GWG, winsOrLosses, GTG, TOIG);
	}

	public  HashMap<String, HashMap<String, Object>> readMemberCols(TableView<MemberModel> tbData) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		HashMap<String, HashMap<String, Object>> allValues = new  HashMap<String, HashMap<String, Object>>();
		ObservableList<TableColumn<MemberModel, ?>> columns = tbData.getColumns();

		for (Object row : tbData.getItems()) {

			for (TableColumn column : columns) {
				System.out.println(column.getText());
				values.put(column.getText(), (Object) column.
						getCellObservableValue(row).
						getValue());

			}
			Object season = values.get("Season");
			allValues.put(season.toString(), values);
		}

		Iterator stats = allValues.entrySet().iterator();
		//each game would be a new row
		while (stats.hasNext()) {
			Map.Entry details = (Map.Entry)stats.next();
			System.out.println(details.getKey() + " = " + details.getValue());

		}
		return allValues;

	}
	//get
	public  HashMap<String, HashMap<String, Object>> readGoalieCols(TableView<GoalieModel> tbData) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		HashMap<String, HashMap<String, Object>> allValues = new  HashMap<String, HashMap<String, Object>>();
		ObservableList<TableColumn<GoalieModel, ?>> columns = tbData.getColumns();

		for (Object row : tbData.getItems()) {

			for (TableColumn column : columns) {
				System.out.println(column.getText());
				values.put(column.getText(), (Object) column.
						getCellObservableValue(row).
						getValue());

			}
			Object season = values.get("Season");
			allValues.put(season.toString(), values);
		}

		Iterator stats = allValues.entrySet().iterator();
		//each game would be a new row
		while (stats.hasNext()) {
			Map.Entry details = (Map.Entry)stats.next();
			System.out.println(details.getKey() + " = " + details.getValue());

		}
		return allValues;
	}
	//delete player
	public void deletePlayer(int jerseyNo, TableView<?> tbData,JFXTextArea textArea) {
		tbData.getItems().clear();
		textArea.clear();
		model.deletePlayerByJerseyNo(jerseyNo);

	}

}
