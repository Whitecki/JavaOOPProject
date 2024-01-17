package agh.ics.oop.app;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.visualization.MapChangeListener;
import agh.ics.oop.presenter.SimulationPresenter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatsSaver implements MapChangeListener {
    private String filePath;
    private SimulationPresenter presenter;

    public StatsSaver(SimulationPresenter presenter) {
        this.presenter = presenter;
        String currentDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
        this.filePath = currentDirectory + "/statistics.csv";
        writeHeader();
    }

    @Override
    public void mapChanged(Map worldMap, String message) {
        saveStatistics();
    }

    private void saveStatistics() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.format("%d,%d,%d,%d,%d,%d\n",
                    presenter.getStatistics().allAnimalsCount(),
                    presenter.getStatistics().averageEnergyCount(),
                    presenter.getStatistics().averageLifeLength(),
                    presenter.getStatistics().allGrassCount(),
                    presenter.getStatistics().freeFieldCount(),
                    presenter.getStatistics().averageKidsCount(),
//Liczba zwierząt, Średnia energia zwierząt, Średnia długość życia, Liczba roślin, Wolne pola, Średnia ilość dzieci
                    0, 0, 0, 0, "N/A", "N/A", "N/A"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHeader() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            writer.write("New Simulation started on " + formattedDateTime + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
