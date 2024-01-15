package agh.ics.oop.presenter;

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

import java.util.List;

import static agh.ics.oop.model.behavior.AllAnimalBehaviors.FullPredestination;
import static agh.ics.oop.model.edge.AllEdges.GlobeEdgeBehavior;
import static agh.ics.oop.model.growth.GrowthTypes.NearToGrassGrowth;
import static agh.ics.oop.model.mutation.AllMutations.RandomMutation;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label bounds;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label moveDescriptionLabel;
    @FXML
    private Label infoLabel;

    @FXML
    private Label mapLabel;

    @FXML
    private TextField moveList;
    Map map;
    public void setWorldMap(Map map) {
        this.map = map;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(50));
        mapGrid.getRowConstraints().add(new RowConstraints(50));
    }

    private void drawMap() {
        clearGrid();
        draw(map.getLowerLeftBands(), map.getUpperRightBands());
    };

    @Override
    public void mapChanged(Map worldMap, String message) {
        drawMap();
        moveDescriptionLabel.setText(message);
        bounds.setText(map.getLowerLeftBands().toString() + map.getUpperRightBands().toString());

    }

    @FXML
    private void onSimulationStartClicked() throws InterruptedException {
        infoLabel.setVisible(false);
        ConfigurationData config = new ConfigurationData(10, 10, GlobeEdgeBehavior , 6,
                2, 2, NearToGrassGrowth, 5, 12, 2,
                1, 1, 3, RandomMutation, 8,
                FullPredestination);
        Simulation simulation = new Simulation(config);
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
                moveDescriptionLabel.setText("Simulation ended");
                drawMap();
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
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(50);
            mapGrid.getColumnConstraints().add(column);
            mapGrid.add(new Label(String.valueOf(x0)), i, 0);
            x0++;
        }

        for(int i = 1 ; i <= height + 1; i++){
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(50);
            mapGrid.getRowConstraints().add(row);
            mapGrid.add(new Label(String.valueOf(y1)),0, i);
            y1--;
        }
    }

}

