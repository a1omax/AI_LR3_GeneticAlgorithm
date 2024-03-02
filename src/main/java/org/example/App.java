package org.example;

import java.util.Random;

public class App {
    public static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main( String[] args ) {
        new App().run();
    }

    public void run(){
        int numberOfChromosomes = 100;
        int numberOfGenes = 16;
        double mutationChance = 0.02;
        int nEpochs = 15;

        Calculator calculator = new Calculator(-5.12, 5.12);

        Evolution evolution = new Evolution(calculator, mutationChance, nEpochs);

        evolution.runGeneticAlgorithm(numberOfChromosomes, numberOfGenes);
        System.out.println("\n----------------------------------");
        System.out.println("\nRESULT\n");

        System.out.println("Number of epochs: " + evolution.getResultEpoch());
        System.out.println("Chromosome: ");
        System.out.println(evolution.getResultChromosome());

    }



}
