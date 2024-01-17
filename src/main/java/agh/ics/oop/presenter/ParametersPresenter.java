package agh.ics.oop.presenter;

import agh.ics.oop.app.SimulationApp;
import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.edge.AllEdges;
import agh.ics.oop.model.growth.GrowthTypes;
import agh.ics.oop.model.mutation.AllMutations;
import agh.ics.oop.simulation.ConfigurationData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class ParametersPresenter {

    @FXML
    public ComboBox behaviorVariantBox;
    private int CELL_WIDTH = 40;
    private int CELL_HEIGHT = 40;

    @FXML
    public Spinner heightSpinner;

    @FXML
    public Spinner widthSpinner;

    @FXML
    public Spinner animalsNumSpinner;

    @FXML
    public Spinner plantNumSpinner;

    @FXML
    public Spinner dailyPlantsNumSpinner;

    @FXML
    public ComboBox mapVariantBox;

    @FXML
    public Spinner animalStartEnergySpinner;

    @FXML
    public Spinner animalReproduceEnergySpinner;

    @FXML
    public Spinner animalLostToReproduceEnergySpinner;

    @FXML
    public Spinner startPlantsSpinner;

    @FXML
    public Spinner genomLengthSpinner;

    @FXML
    public Spinner minMutationSpinner;

    @FXML
    public Spinner maxMutationSpinner;

    @FXML
    public ComboBox genomVariantBox;
    @FXML
    public ComboBox plantVariantBox;
    @FXML
    public CheckBox fileSaving;
    @FXML
    public Spinner animalEatEnergySpinner;

    @FXML
    private void onSimulationStartClicked() throws IOException {

        int allEdges = mapVariantBox.getSelectionModel().getSelectedIndex();
        int growthType = plantVariantBox.getSelectionModel().getSelectedIndex();
        int allMutations = genomVariantBox.getSelectionModel().getSelectedIndex();
        int allAnimalBehaviors = behaviorVariantBox.getSelectionModel().getSelectedIndex();

        AllEdges edge = AllEdges.parse(allEdges);
        AllMutations mutations = AllMutations.parse(allMutations);
        AllAnimalBehaviors behaviors = AllAnimalBehaviors.parse(allAnimalBehaviors);
        GrowthTypes growthTypes = GrowthTypes.parse(growthType);

        ConfigurationData configurationData = new ConfigurationData((int) heightSpinner.getValue(), (int) widthSpinner.getValue(), edge, (int) startPlantsSpinner.getValue(),
                (int) animalEatEnergySpinner.getValue(), (int) dailyPlantsNumSpinner.getValue(), growthTypes, (int) animalsNumSpinner.getValue(), (int) animalStartEnergySpinner.getValue(),
                (int) animalReproduceEnergySpinner.getValue(), (int) animalLostToReproduceEnergySpinner.getValue(), (int) minMutationSpinner.getValue(), (int) maxMutationSpinner.getValue(),
                mutations, (int) genomLengthSpinner.getValue(), behaviors);

        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        SimulationApp simulationApp = new SimulationApp();
        presenter.setConfigurationData(configurationData);
        simulationApp.configureStage(secondStage, viewRoot); // Wykorzystaj metodę configureStage z SimulationApp
        secondStage.show();
    }
}

