package oop.model.util;

import oop.model.SimulationStatistics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public class CSVWriter {
    private final UUID simulationId;
    private final SimulationStatistics statistics;

    public CSVWriter(UUID simulationId, SimulationStatistics statistics) {
        this.simulationId = simulationId;
        this.statistics = statistics;
        prepareFile();
    }

    public void prepareFile() {
        String fileName = simulationId + ".csv";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.CREATE_NEW)) {
            writer.write("day,animals,plants,freeFields,avgEnergy,avgLifespan,avgChildren");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int day) {
        String fileName = simulationId + ".csv";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), java.nio.file.StandardOpenOption.APPEND)) {
            writer.write((day + 1) + ",");
            writer.write(statistics.getAnimalCount() + ",");
            writer.write(statistics.getGrassCount() + ",");
            writer.write(statistics.freeFieldCount() + ",");
            writer.write(statistics.averageEnergy() + ",");
            writer.write(statistics.averageLifespan() + ",");
            writer.write(String.valueOf(statistics.getAverageChildren()));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
