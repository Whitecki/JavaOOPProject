package agh.ics.oop.presenter;

import agh.ics.oop.app.AnimalStatistics;
import agh.ics.oop.app.Statistics;
import agh.ics.oop.app.StatsSaver;
import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.map.Grass;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.visualization.MapChangeListener;
import agh.ics.oop.simulation.ConfigurationData;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.visualization.ConsoleMapDisplay;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.List;


public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Button startButton;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;
    @FXML
    private Label animalNumStats;
    @FXML
    private Label plantsNumStats;
    @FXML
    private Label freeFieldsStats;
    @FXML
    private Label averageEnergyStats;
    @FXML
    private Label averageLifeStats;
    @FXML
    private Label averageChildrenNumStats;
    @FXML
    private GridPane animalStats;
    @FXML
    private Label followLabel;
    @FXML
    private Label followedanimalEnergy;
    @FXML
    private Label followedanimalEaten;
    @FXML
    private Label followedanimalChildren;
    @FXML
    private Label followedanimalDays;
    @FXML
    private Label followedAnimalDeath;
    @FXML
    private Label followedGenom;
    @FXML
    private Label followedanimalGen;
    @FXML
    private Button stopFollowingButton;
    private ConfigurationData configurationData;
    private boolean followingAnimal = false;
    private Animal animalFollowed;
    Map map;
    private int CELL_WIDTH = 400;
    private int CELL_HEIGHT = 400;
    private int height;
    private int width;
    private int cellSize;
    private Simulation presenterSimulation;
    private Statistics statistics;
    private AnimalStatistics animalStatistics;
    private int day = 0;
    private boolean saveStats;

    public void setConfigurationData(ConfigurationData configurationData, boolean saveStats){
        this.configurationData = configurationData;
        this.height = configurationData.mapHeight();
        this.width = configurationData.mapWidth();
        this.saveStats = saveStats;
    }

    public void setWorldMap(Map map) {
        this.map = map;
    }

    public void setFollowingStats(boolean val){
        followingAnimal = val;
        followLabel.setVisible(val);
        animalStats.setVisible(val);
        stopFollowingButton.setVisible(val);
    }

    @FXML
    private void onSimulationStartClicked() throws InterruptedException {
        startButton.setVisible(false);
        infoLabel.setVisible(false);
        Simulation simulation = new Simulation(configurationData);
        presenterSimulation = simulation;
        statistics = new Statistics(simulation);
        MapChangeListener listener = new ConsoleMapDisplay();
        Map simulationMap = simulation.getMap();
        this.setWorldMap(simulationMap);
        map.subscribe(listener);
        map.subscribe(this);
        if(saveStats) {
            StatsSaver statsSaver = new StatsSaver(this);
            map.subscribe(statsSaver);
        }
        List<Simulation> simulationList = List.of(simulation);
        SimulationEngine engine = new SimulationEngine(simulationList);
        new Thread(() -> {
            engine.run();
        }).start();
    }

    @FXML
    private void onHighlightPlantsButton(ActionEvent actionEvent) {
        for(Vector2D vector: this.statistics.mostPreferedByPlants()){
            Vector2D gridVector = vectorOnGrid(vector);
            mapGrid.add(new  Circle(this.cellSize/2, Color.web("#225641")), gridVector.getX(), gridVector.getY());
        }
    }

    @FXML
    private void onSimulationStop(){
        presenterSimulation.stop();
    }

    @FXML
    private void onSimulationResume(){
        presenterSimulation.resume();
    }

    public void onStopFollowing() {
        setFollowingStats(false);
    }

    @Override
    public void mapChanged(Map worldMap, String message) {
        drawMap();
    }
    private int calculateCellSize(){
        int maxCellWidth = this.CELL_WIDTH / (width+1);
        int maxCellHeight = this.CELL_HEIGHT/ (height+1);
        return Math.min(maxCellWidth, maxCellHeight);
    }

    private Vector2D vectorOnGrid(Vector2D vector2d){
        int y = map.getUpperRightBands().getY() - vector2d.getY() + 1;
        int x = vector2d.getX()  - map.getLowerLeftBands().getX() + 1;

        return new Vector2D(x,y);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(50));
        mapGrid.getRowConstraints().add(new RowConstraints(50));
    }

    public Circle getGrassColor(){
        Circle circle = new Circle(this.cellSize/2);
        circle.setFill(Color.web("#76b474"));
        return circle;
    }

    public Circle getAnimalColor(Animal animal){
        Circle circle = new Circle(this.cellSize/2);
        int animalEnergy = animal.getEnergy();
        if(animalEnergy <= 0.2*configurationData.initialAnimalEnergy()){
            circle.setFill(Color.web("#4d2642"));
        } else if (animalEnergy <= 0.5*configurationData.initialAnimalEnergy()) {
            circle.setFill(Color.web("#800080"));
        } else if (animalEnergy <= 0.8*configurationData.initialAnimalEnergy()) {
            circle.setFill(Color.web("#b373b8"));
        }else {
            circle.setFill(Color.web("#f683c5"));
        }
        return circle;
    }

    private void drawFrame(Vector2D lowerLeft, Vector2D upperRight) {

//        int y0 = lowerLeft.getY();
        int x0 = lowerLeft.getX();
        int y1 = upperRight.getY();
//        int x1 = upperRight.getX();
//        int width = x1-x0;
//        int height = y1-y0;
        mapGrid.add(new Label("y/x"), 0, 0);
        for(int i = 1; i <= width + 1; i++){
            mapGrid.add(new Label(String.valueOf(x0)), i, 0);
            ColumnConstraints column = new ColumnConstraints(cellSize);
            mapGrid.getColumnConstraints().add(column);
            x0++;
        }

        for(int i = 1 ; i <= height + 1; i++){
            mapGrid.add(new Label(String.valueOf(y1)),0, i);
            RowConstraints row = new RowConstraints(cellSize);
            mapGrid.getRowConstraints().add(row);
            y1--;
        }
    }

    public void drawStat(){

        animalNumStats.setText(String.valueOf(statistics.allAnimalsCount()));
        averageEnergyStats.setText(String.valueOf(statistics.averageEnergyCount()));
        averageLifeStats.setText(String.valueOf(statistics.averageLifeLength()));
        plantsNumStats.setText(String.valueOf(statistics.allGrassCount()));
        freeFieldsStats.setText(String.valueOf(statistics.freeFieldCount()));
        averageChildrenNumStats.setText(String.valueOf(statistics.averageKidsCount()));
        if(followingAnimal){
            displayAnimalStats();
        }
    }

    public void drawMap() {
        day++;
        this.clearGrid();
        mapGrid.minHeight(height);
        mapGrid.minWidth(width);
        this.cellSize = this.calculateCellSize();

        drawFrame(map.getLowerLeftBands(), map.getUpperRightBands());
        HashMap<Vector2D, List<Animal>> animalHashMap = map.getAnimalHashMap();
        HashMap<Vector2D, Grass> grassHashMap = map.getGrassHashMap();

        for (Vector2D vector : grassHashMap.keySet()) {
            Vector2D gridVector = vectorOnGrid(vector);
            mapGrid.add(getGrassColor(), gridVector.getX(), gridVector.getY());
        }
        for (Vector2D vector : animalHashMap.keySet()) {
            Vector2D gridVector = vectorOnGrid(vector);
            mapGrid.add(getAnimalColor(animalHashMap.get(vector).get(0)), gridVector.getX(), gridVector.getY());
            Rectangle invisibleRect = new Rectangle(this.cellSize, this.cellSize, Color.TRANSPARENT);
            mapGrid.add(invisibleRect, gridVector.getX(), gridVector.getY());
            invisibleRect.setOnMouseClicked(event -> startToDisplayAnimalInfo(animalHashMap.get(vector).get(0)));
        }
        drawStat();
    }

    public void startToDisplayAnimalInfo(Animal animal){
        followingAnimal = true;
        animalFollowed = animal;
        animalStatistics = new AnimalStatistics(animal);
        setFollowingStats(true);
        drawStat();
    }

    public void displayAnimalStats(){
        Vector2D gridVector = vectorOnGrid(this.animalFollowed.getPosition());
        mapGrid.add(new  Circle(this.cellSize/2, Color.web("#6cb8b9")), gridVector.getX(), gridVector.getY());
        this.followedanimalEnergy.setText(this.animalStatistics.getEnergyLevel());
        this.followedanimalEaten.setText(this.animalStatistics.getPlantsEaten());
        this.followedanimalChildren.setText(this.animalStatistics.getKidsNumber());
        this.followedanimalDays.setText(this.animalStatistics.getAge(day));
        this.followedAnimalDeath.setText(this.animalStatistics.getDeathDate());
        this.followedGenom.setText(this.animalStatistics.getGenom());
        this.followedanimalGen.setText(this.animalStatistics.getActiveGenomPart());
    }

    public Statistics getStatistics() {
        return statistics;
    }
}

