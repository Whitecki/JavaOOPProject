package agh.ics.oop.presenter;

import agh.ics.oop.app.SimulationApp;
import agh.ics.oop.app.StatsSaver;
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
    private ComboBox behaviorVariantBox;
    @FXML
    private Spinner heightSpinner;
    @FXML
    private Spinner widthSpinner;
    @FXML
    private Spinner animalsNumSpinner;
    @FXML
    private Spinner dailyPlantsNumSpinner;
    @FXML
    private ComboBox mapVariantBox;
    @FXML
    private Spinner animalStartEnergySpinner;
    @FXML
    private Spinner animalReproduceEnergySpinner;
    @FXML
    private Spinner animalLostToReproduceEnergySpinner;
    @FXML
    private Spinner startPlantsSpinner;
    @FXML
    private Spinner genomLengthSpinner;
    @FXML
    private Spinner minMutationSpinner;
    @FXML
    private Spinner maxMutationSpinner;
    @FXML
    private ComboBox genomVariantBox;
    @FXML
    private ComboBox plantVariantBox;
    @FXML
    private CheckBox fileSaving;
    @FXML
    private Spinner animalEatEnergySpinner;
    private boolean saveStats;

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
        if(fileSaving.isSelected()){
            saveStats = true;
        }
        else{
            saveStats = false;
        }
        presenter.setConfigurationData(configurationData, saveStats);
        simulationApp.configureStage(secondStage, viewRoot);
        secondStage.show();
    }
}

