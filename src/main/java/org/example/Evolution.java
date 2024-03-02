package org.example;

import lombok.Getter;

import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Evolution {

    Calculator calculator;

    @Getter
    private Chromosome resultChromosome;

    private List<Chromosome> chromosomeList;

    private int epoch = 1;

    private double mutationChance;

    private int nEpochs;

    public Evolution(Calculator calculator, double mutationChance, int nEpochs) {
        this.calculator = calculator;
        this.mutationChance = mutationChance;
        this.nEpochs = nEpochs;
    }

    public void runGeneticAlgorithm(int numberOfChromosomes, int numberOfGenes) {
        chromosomeList = Utils.generateChromosomeList(numberOfChromosomes, numberOfGenes);
        resultChromosome = null;
        for (int i = 0; i < nEpochs; i++) {

            Chromosome bestChromosome = runEpoch();

            if (bestChromosome == null){
                System.out.println("No other values in epoch, breaking the loop");
                break;
            }
            else {
                resultChromosome = bestChromosome;
            }
        }
    }

    public int getResultEpoch() {
        return epoch;
    }

    private Chromosome runEpoch() {

        List<Double> listResults = calculator.calculateListResults(chromosomeList);

        List<Integer> sortedIndexes = IntStream.range(0, listResults.size())
                .boxed()
                .sorted(Comparator.comparingDouble(listResults::get).reversed()).toList();

        ArrayList<Chromosome> sortedChromosomes = new ArrayList<>();
        ArrayList<Double> sortedListResults = new ArrayList<>();
        for (int index : sortedIndexes) {
            sortedListResults.add(listResults.get(index));
            sortedChromosomes.add(this.chromosomeList.get(index));
        }

        // copy best
        Chromosome bestChromosomeFromOldGeneration = new Chromosome(sortedChromosomes.getFirst());


        System.out.println();
        System.out.println("Epoch: " + this.epoch);
        System.out.println("best: " + bestChromosomeFromOldGeneration);
        System.out.println("best chromosome value: " + listResults.get(sortedIndexes.getFirst()));
        System.out.println();
        //System.out.println("Total difference: " + listDifference.stream().mapToDouble(Double::doubleValue).sum());


        int nChildren = sortedChromosomes.size() - 1;

        // sorted // todo bad code
        List<Chromosome> parents = getRandomRankedChromosomeList(sortedChromosomes, sortedListResults, 2 * nChildren);
        if (parents.contains(null)){
            return null;
        }

        // Recombination
        List<Chromosome> newGeneration = Chromosome.makeRecombinations(parents, nChildren);

        // And mutate random elements  newGeneration.size()

        for (Chromosome chromosome : newGeneration) {
            if (App.RANDOM.nextDouble() <= this.mutationChance) {
                chromosome.mutateOneRandomGene();
            }
        }

        // Add best parent
        newGeneration.add(bestChromosomeFromOldGeneration);

        this.chromosomeList = newGeneration;
        this.epoch += 1;

        return bestChromosomeFromOldGeneration;
    }


    public List<Chromosome> getRandomRankedChromosomeList(List<Chromosome> chromosomeArrayList, List<Double> listResults, int numberOfChromosomes) {
        listResults = Calculator.subtractMinValueFromList(listResults);


        List<Chromosome> randomChromosomeList = new ArrayList<>();

        for (int i = 0; i < numberOfChromosomes; i++) {

            randomChromosomeList.add(selectRandomChromosome(chromosomeArrayList, listResults));
        }

        return randomChromosomeList;
    }

    private static Chromosome selectRandomChromosome(List<Chromosome> chromosomeArrayList, List<Double> listResults) {


        double randomValue = App.RANDOM.nextDouble() * listResults.stream().mapToDouble(Double::doubleValue).sum();
        double cumulativeWeight = 0.0;
        for (int i = 0; i < listResults.size(); i++) {
            cumulativeWeight += listResults.get(i);
            if (randomValue < cumulativeWeight) {
                return chromosomeArrayList.get(i);
            }
        }
        return null;
    }


}
