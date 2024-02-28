package org.example;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


public class Evolution {

    private final int MAX_MUTATION_VALUE;

    Calculator calculator;

    private Chromosome resultChromosome;

    private List<Chromosome> chromosomeList;

    private int epoch = 1;

    public Evolution(Calculator calculator, int MAX_MUTATION_VALUE) {
        this.calculator = calculator;
        this.MAX_MUTATION_VALUE = MAX_MUTATION_VALUE;
    }

    public void runGeneticAlgorithm(int numberOfChromosomes, int numberOfGenes) {
        chromosomeList = Utils.generateChromosomeList(numberOfChromosomes, numberOfGenes, 0, calculator.d);
        resultChromosome = null;
        while (resultChromosome == null) {
            resultChromosome = runEpoch();
        }
    }

    public Chromosome getResultChromosome() {
        return resultChromosome;
    }

    public int getResultEpoch() {
        return epoch;
    }

    private Chromosome runEpoch() {

        List<Integer> listDifference = calculator.calculateListDifference(this.chromosomeList);

        Optional<Integer> optionalChromosomeResultIndex = IntStream.range(0, listDifference.size())
                .filter(i -> listDifference.get(i) == 0)
                .boxed()
                .findFirst();

        if (optionalChromosomeResultIndex.isPresent()) {
            return this.chromosomeList.get(optionalChromosomeResultIndex.get());
        }


        List<Double> adaptationCoefficientList =
                calculator.calculateAdaptationCoefficientList(calculator.calculateReversedListDifference(listDifference));


        List<Integer> sortedIndexes = IntStream.range(0, adaptationCoefficientList.size())
                .boxed()
                .sorted((i1, i2) -> Double.compare(adaptationCoefficientList.get(i2), adaptationCoefficientList.get(i1))).toList();

        System.out.println();
        System.out.println("Epoch: " + this.epoch);
        System.out.println("best: " + this.chromosomeList.get(sortedIndexes.getFirst()));
        System.out.println("adaptation coefficient: " + adaptationCoefficientList.get(sortedIndexes.getFirst()));
        System.out.println("best chromosome difference: " + listDifference.get(sortedIndexes.getFirst()));
        System.out.println();
        System.out.println("Total difference: " + listDifference.stream().mapToInt(Integer::intValue).sum());

        ArrayList<Chromosome> sortedChromosomes = new ArrayList<>();
        ArrayList<Double> sortedAdaptationCoefficients = new ArrayList<>();
        for (int index : sortedIndexes) {
            sortedChromosomes.add(this.chromosomeList.get(index));
            sortedAdaptationCoefficients.add(adaptationCoefficientList.get(index));
        }

        // copy best
        Chromosome bestChromosomeFromOldGeneration = new Chromosome(sortedChromosomes.getFirst());

        int nChildren = sortedChromosomes.size()-1;


        // sorted
        List<Chromosome> parents = getRandomRankedChromosomeList(sortedChromosomes, sortedAdaptationCoefficients, 2*nChildren);


        // Recombination
        List<Chromosome> newGeneration = Chromosome.makeRecombinations(parents, nChildren);

        // And mutate random elements  newGeneration.size()

        for (int i = 0; i < newGeneration.size(); i++) {
            Utils.randomChoice(newGeneration).mutateOneRandomGene(0, MAX_MUTATION_VALUE);
        }

        // Add best parent
        newGeneration.add(bestChromosomeFromOldGeneration);

        this.chromosomeList = newGeneration;
        this.epoch += 1;
        return null;
    }


    public List<Chromosome> getRandomRankedChromosomeList(List<Chromosome> chromosomeArrayList, List<Double> adaptationCoefficients, int numberOfChromosomes) {

        List<Chromosome> randomChromosomeList = new ArrayList<>();

        for (int i = 0; i < numberOfChromosomes; i++) {

            randomChromosomeList.add(selectRandomChromosome(chromosomeArrayList, adaptationCoefficients));
        }

        return randomChromosomeList;
    }

    private static Chromosome selectRandomChromosome(List<Chromosome> chromosomeArrayList, List<Double> adaptationCoefficients) {
        double randomValue = App.RANDOM.nextDouble();
        double cumulativeWeight = 0.0;
        for (int i = 0; i < adaptationCoefficients.size(); i++) {
            cumulativeWeight += adaptationCoefficients.get(i);
            if (randomValue < cumulativeWeight) {
                return chromosomeArrayList.get(i);
            }
        }
        return null;
    }


}
