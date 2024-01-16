package agh.ics.oop.presenter;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.map.Grass;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.visualization.MapChangeListener;
import agh.ics.oop.simulation.ConfigurationData;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.visualization.ConsoleMapDisplay;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label bounds;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;
    private ConfigurationData configurationData;
    Map map;
    private int CELL_WIDTH = 400;
    private int CELL_HEIGHT = 400;
    private int height;
    private int width;
    private int cellSize;

    public void setConfigurationData(ConfigurationData configurationData){
        this.configurationData = configurationData;
        this.height = configurationData.mapHeight();
        this.width = configurationData.mapWidth();
    }
    public void setWorldMap(Map map) {
        this.map = map;
    }

    private int calculateCellSize(){
        int maxCellWidth = this.CELL_WIDTH / (width);
        int maxCellHeight = this.CELL_HEIGHT/ (height);
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
        circle.setFill(Color.GREEN);
        return circle;
    }

    public Circle getAnimalColor(Animal animal){
        Circle circle = new Circle(this.cellSize/2);
        int animalEnergy = animal.getEnergy();
        if(animalEnergy <= 0.2*configurationData.initialAnimalEnergy()){
            circle.setFill(Color.VIOLET.darker());
        } else if (animalEnergy <= 0.5*configurationData.initialAnimalEnergy()) {
            circle.setFill(Color.VIOLET);
        } else if (animalEnergy <= 0.8*configurationData.initialAnimalEnergy()) {
            circle.setFill(Color.PINK.darker());
        }else {
            circle.setFill(Color.PINK);
        }
        return circle;
    }
    public void drawMap2() {
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
        }
    }




    void drawMap() {
        clearGrid();
        draw(map.getLowerLeftBands(), map.getUpperRightBands());
    };

    @Override
    public void mapChanged(Map worldMap, String message) {
        drawMap2();
    }

    @FXML
    private void onSimulationStartClicked() throws InterruptedException {
        infoLabel.setVisible(false);
        Simulation simulation = new Simulation(configurationData);
        MapChangeListener listener = new ConsoleMapDisplay();
        Map simulationMap = simulation.getMap();
        this.setWorldMap(simulationMap);
        map.subscribe(listener);
        map.subscribe(this);
        List<Simulation> simulationList = List.of(simulation);
        SimulationEngine engine = new SimulationEngine(simulationList);
        new Thread(() -> {
            engine.run();
            Platform.runLater(() -> {
                drawMap2();
            });
        }).start();
    }

    private void draw(Vector2D lowerLeft, Vector2D upperRight) {
        drawFrame(lowerLeft, upperRight);
        int y0 = lowerLeft.getY();
        int x0 = lowerLeft.getX();
        int y1 = upperRight.getY();
        int x1 = upperRight.getX();
        for(int i = x0; i < x1 + 1; i++){
            for(int j = y0; j < y1 + 1; j++){
                Object object = this.map.objectAt(new Vector2D(i, j));
                if (object != null) {

                    String mapObject = object.toString();
                    Label a = new Label(mapObject);
                    mapGrid.add(a, i - x0 + 1, y1 - j + 1);
                }
            }
        }
    }

    private void drawFrame(Vector2D lowerLeft, Vector2D upperRight) {

        int y0 = lowerLeft.getY();
        int x0 = lowerLeft.getX();
        int y1 = upperRight.getY();
        int x1 = upperRight.getX();
        int width = x1-x0;
        int height = y1-y0;
        mapGrid.add(new Label("y/x"), 0, 0);
        for(int i = 1; i <= width + 1; i++){
            ColumnConstraints column = new ColumnConstraints(cellSize);
            mapGrid.getColumnConstraints().add(column);
            mapGrid.add(new Label(String.valueOf(x0)), i, 0);
            x0++;
        }

        for(int i = 1 ; i <= height + 1; i++){
            RowConstraints row = new RowConstraints(cellSize);
            mapGrid.getRowConstraints().add(row);
            mapGrid.add(new Label(String.valueOf(y1)),0, i);
            y1--;
        }
    }

}

