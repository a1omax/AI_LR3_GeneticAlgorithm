package org.example;

import java.util.Random;

public class App {
    public static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main( String[] args ) {

        new App().run();

    }
    public void run(){
        int[] functionCoefficients = {2, 8, 5, 7, -6};
        int numberOfChromosomes = 10;
        int numberOfGenes = functionCoefficients.length;
        int d = 7;

        Calculator calculator = new Calculator(functionCoefficients, d);

        Evolution evolution = new Evolution(calculator, d);

        evolution.runGeneticAlgorithm(numberOfChromosomes, numberOfGenes);
        System.out.println("\n----------------------------------");
        System.out.println("\nRESULT\n");

        System.out.println("Number of epochs: " + evolution.getResultEpoch());
        System.out.println("Chromosome: ");
        System.out.println(evolution.getResultChromosome());

    }



}
