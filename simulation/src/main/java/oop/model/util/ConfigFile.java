package oop.model.util;

import oop.SimulationOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ConfigFile {
    public static void saveSimulationOptions(SimulationOptions options, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }


        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)) {
            writer.write("animalsStart " + options.animalsStart() + "\n");
            writer.write("energyStart " + options.energyStart() + "\n");
            writer.write("energyConsumption " + options.energyConsumption() + "\n");
            writer.write("energyRequired " + options.energyRequired() + "\n");
            writer.write("energyReproduction " + options.energyReproduction() + "\n");
            writer.write("genomeSize " + options.genomeSize() + "\n");
            writer.write("grassPerDay " + options.grassPerDay() + "\n");
            writer.write("minMutation " + options.minMutation() + "\n");
            writer.write("maxMutation " + options.maxMutation() + "\n");
            writer.write("grassStart " + options.grassStart() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
