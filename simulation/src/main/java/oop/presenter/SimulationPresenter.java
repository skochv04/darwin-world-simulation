package oop.presenter;

import com.sun.javafx.scene.control.IntegerField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import oop.Simulation;
import oop.SimulationEngine;
import oop.SimulationOptions;
import oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oop.model.util.ConfigFile;
import oop.model.util.ConflictResolver;
import oop.model.util.MapConfiguration;
import oop.model.util.MutationConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SimulationPresenter implements MapChangeListener {
    public Label grassStartText;
    public IntegerField grassStart;
    public IntegerField grassPerDay;
    public IntegerField animalsStart;
    public IntegerField genomeSize;
    public IntegerField energyStart;
    public IntegerField energyConsumption;
    public IntegerField energyRequired;
    public IntegerField energyReproduction;
    public Button pauseButton;
    @FXML
    public VBox optionsBox;
    @FXML
    public VBox simulationBox;
    @FXML
    private IntegerField minMutation;
    @FXML
    private IntegerField maxMutation;
    @FXML
    private Label startText;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label mapTypeText;
    @FXML
    private Label widthText;
    @FXML
    private IntegerField width;
    @FXML
    private Label heightText;
    @FXML
    private IntegerField height;
    @FXML
    private Label mutationModifierText;
    @FXML
    private ChoiceBox mutationModifier;
    @FXML
    private Label simulationText;
    @FXML
    private ChoiceBox<MapConfiguration> mapType;
    @FXML
    private Label selectionTitle;
    @FXML
    private Label statisticsOutput;
    @FXML
    private Button startButton;
    private AbstractWorldMap map;
    private Simulation simulation;
    @FXML
    private Label infoLabel;
    @FXML
    private CheckBox csvCheckbox;
    @FXML
    private VBox selectionBox;
    @FXML
    private Pane animalsPane;
    @FXML
    private Label trackingLabel;
    @FXML
    private Button stopTrackingBttn;

    private final List<Simulation> simulations = new ArrayList<>();
    private final SimulationEngine engine = new SimulationEngine(simulations);
    private Animal trackedAnimal;
    private Vector2d selectedPosition;

    @FXML
    private SimulationOptions options;
    {
        try {
            engine.runAsync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void setDefaultParametrs(){
        this.mapType.setValue(MapConfiguration.EARTH_MAP);
        this.mutationModifier.setValue(MutationConfiguration.COMPLETE_RANDOMNESS);
    }

    public synchronized void drawMap(){
        clearGrid();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
        mapGrid.getRowConstraints().add(new RowConstraints(30));
        for (int i = 0; i < map.getWidth(); i++) {
            Text obj = new Text(String.valueOf(i));
            mapGrid.add(obj,  i+1, 0, 1, 1);
            GridPane.setHalignment(obj, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
        }

        for (int j = 0; j < map.getHeight(); j++) {
            Text obj = new Text(String.valueOf(j));
            mapGrid.add(obj,  0, map.getHeight() - j, 1, 1);
            GridPane.setHalignment(obj, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(30));
        }

        for (int i = 0; i < map.getWidth(); i++){
            for (int j = 0; j < map.getHeight(); j++) {
                Vector2d currentPosition = new Vector2d(i, j);

                List<Animal> animalsOnPosition = this.map.animalsAt(currentPosition);

                Pane pane = new Pane();


                if (this.map.animalsAt(currentPosition) != null && !animalsOnPosition.isEmpty()) {
                    pane.setStyle("-fx-background-color: #fed7aa");

                    Label animalCount = new Label();
                    animalCount.setText(String.valueOf(animalsOnPosition.size()));
                    pane.getChildren().add(animalCount);

                    GridPane.setHalignment(pane, HPos.CENTER);
                } else if (this.map.grassAt(currentPosition) != null) {
                    pane.setStyle("-fx-background-color: #a7f3d0");
                }

                final int colIndex = i + 1, rowIndex = j;
                pane.setOnMouseClicked(event -> handleCellSelection(colIndex - 1, rowIndex));
                mapGrid.add(pane, colIndex, map.getHeight() - j,  1, 1);
            }
        }
    }

    public void handleCellSelection(int colIndex, int rowIndex) {
        if (simulation.isPaused()) {
            selectedPosition = new Vector2d(colIndex, rowIndex);

            selectionTitle.setText("Selected field: " + selectedPosition);

            List<Animal> animalsOnPosition = this.map.animalsAt(selectedPosition);

            animalsPane.getChildren().clear();

            if (animalsOnPosition != null && animalsOnPosition.size() > 0) {
                VBox vBox = new VBox();
                int index = 1;

                for (Animal animal: animalsOnPosition) {
                    HBox row = new HBox();

                    Label label = new Label();
                    label.setText("Animal #" + index);
                    index += 1;
                    row.getChildren().add(label);

                    Button button = new Button();
                    button.setText("Track");
                    button.setOnAction(event -> {
                        this.trackedAnimal = animal;
                        stopTrackingBttn.setDisable(false);
                    });
                    row.getChildren().add(button);

                    vBox.getChildren().add(row);
                }

                animalsPane.getChildren().add(vBox);
            } else {
                Label noAnimalsLabel = new Label();
                noAnimalsLabel.setText("There are no animals at selected field");
                animalsPane.getChildren().add(noAnimalsLabel);
            }

            System.out.println(colIndex + " " + rowIndex);
        }
    }

    public void onStopTracking(ActionEvent actionEvent) {
        stopTrackingBttn.setDisable(true);
        trackedAnimal = null;
    }

    @Override
    public void mapChanged(WorldMap worldMap) {
        Platform.runLater(() -> {
            drawMap();

            SimulationStatistics simulationStatistics = simulation.getStatistics();
            statisticsOutput.setText(
                    "Simulation statistics\n----------------\n" +
                            "Animals: " + simulationStatistics.getAnimalCount() + "\n" +
                            "Plants: " + simulationStatistics.getGrassCount() + "\n" +
                            "Average energy: " + Math.round(simulationStatistics.averageEnergy()*100.0)/100.0 + "\n" +
                            "Average lifespan: " + Math.round(simulationStatistics.averageLifespan()*100.0)/100.0 + "\n" +
                            "Average children: " + Math.round(simulationStatistics.getAverageChildren())
            );

            if (trackedAnimal != null) {
                trackingLabel.setText("Tracked animal statistics\n-----------------\n" +
                        "Genome: " + trackedAnimal.getGenome() + "\n" +
                        "Active index: " + trackedAnimal.getActiveIndex() + "\n" +
                        "Energy: " + trackedAnimal.getEnergy() + "\n" +
                        "Plants eaten: " + trackedAnimal.getStatistics().getPlantsEaten() + "\n" +
                        "Children amount: " + trackedAnimal.getStatistics().getChildrenAmount() + "\n" +
                        "Days alive: " + trackedAnimal.getStatistics().getDaysAlive() + "\n" +
                        "Death day: " + (trackedAnimal.getStatistics().getDeathDay() == 0 ? "Alive" : trackedAnimal.getStatistics().getDeathDay())
                );
            } else {
                trackingLabel.setText("No animal is tracked at the moment");
            }
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void openWindow() throws IOException {
        this.map = switch (mapType.getValue()){
            case EARTH_MAP -> new EarthMap(width.getValue(), height.getValue());
            case HELL_PORTAL_MAP -> new HellPortalMap(width.getValue(), height.getValue());
        };

        this.options = new SimulationOptions(grassStart.getValue(), grassPerDay.getValue(), animalsStart.getValue(), genomeSize.getValue(), energyStart.getValue(), energyConsumption.getValue(), energyRequired.getValue(), energyReproduction.getValue(), minMutation.getValue(), maxMutation.getValue(), csvCheckbox.isSelected());
        ConfigFile.saveSimulationOptions(this.options, "options/" + UUID.randomUUID() + ".txt");
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        loader.getController();
        map.subscribe(this);
        configureStage(stage, viewRoot);
        stage.show();
    }

    private void configureStage(Stage stage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation app");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
    public void onSimulationStartClicked(ActionEvent actionEvent) {
        try{
            if (genomeSize.getValue() == 0) {
                throw new IllegalArgumentException ("GenomeSize should be greater than 0!");
            } else if (width.getValue() <= 0 || height.getValue() <= 0) {
                throw new IllegalArgumentException ("Width and height of the map should be greater than 0!");
            } else if (animalsStart.getValue() > width.getValue() * height.getValue()) {
                throw new IllegalArgumentException("Amount of animals should not be greater than amount of fields!");
            } else if (grassStart.getValue() > width.getValue() * height.getValue() || grassPerDay.getValue() > width.getValue() * height.getValue()){
                throw new IllegalArgumentException ("Amount of grass should not be greater than amount of fields!");
            } else if(energyReproduction.getValue() > energyRequired.getValue()) {
                throw new IllegalArgumentException ("The energy spent on reproduction cannot be greater than the energy required to start reproduction!");
            } else if(maxMutation.getValue() < minMutation.getValue()) {
                throw new IllegalArgumentException ("Maximum amount of mutations should be greater or equal than minimum!");
            }
            else {
                startButton.setVisible(false);
                pauseButton.setVisible(true);
                pauseButton.setDisable(false);
                pauseButton.setText("Pause simulation");
                infoLabel.setText("");
                this.openWindow();
                Simulation simulation = new Simulation(map, (MutationConfiguration) mutationModifier.getValue(), options);
                simulations.add(simulation);
                engine.addSimulation(simulation);
                this.simulation = simulation;
                startButton.setDisable(true);
                simulationBox.setVisible(true);
                simulationBox.setManaged(true);
                optionsBox.setVisible(false);
                optionsBox.setManaged(false);
            }
        }
        catch (IllegalArgumentException e){
            infoLabel.setText("Can not start simulation. " + e.getMessage());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSimulationPauseClicked(ActionEvent actionEvent) throws InterruptedException {
        simulation.switchPause();
        pauseButton.setText(simulation.isPaused() ? "Continue simulation" : "Pause simulation");
        if (simulation.isPaused()) {
            selectedPosition = null;
            selectionBox.setVisible(true);
            selectionBox.setManaged(true);
        } else {
            selectionBox.setVisible(false);
            selectionBox.setManaged(false);
        }
    }
}
