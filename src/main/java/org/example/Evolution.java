package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


public class Evolution {
    //    private static double K = 0.75;

    private static final int N_MUTATIONS = 1;

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
        while (resultChromosome==null){
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

        for (int index : sortedIndexes) {
            sortedChromosomes.add(this.chromosomeList.get(index));
        }


        Chromosome bestChromosomeFromOldGeneration = new Chromosome(sortedChromosomes.getFirst());

        List<Chromosome> newGeneration = new ArrayList<>();
        for (int i = 0; i < this.chromosomeList.size() - 1; i++) {
            newGeneration.add(getRandomRankedChromosome(sortedChromosomes));
        }

        // Recombination
        newGeneration = Chromosome.makeRecombinations(newGeneration);


        // And mutate random element

        for (int i = 0; i < newGeneration.size(); i++) {
            Utils.randomChoice(newGeneration).mutateOneRandomGene(0, MAX_MUTATION_VALUE);
        }


        newGeneration.add(bestChromosomeFromOldGeneration);

        this.chromosomeList = newGeneration;
        this.epoch += 1;
        return null;
    }


    public Chromosome getRandomRankedChromosome(ArrayList<Chromosome> chromosomeArrayList) {
        int size = chromosomeArrayList.size();

        double[] weights = new double[chromosomeArrayList.size()];

        for (int i = 0; i < chromosomeArrayList.size(); i++){
            weights[i] = (double) size /(i+1);
        }

        double totalWeight = Arrays.stream(weights).sum();


        double randomValue = App.RANDOM.nextDouble()*totalWeight; // Нормалізація
        double cumulativeWeight = 0.0;
        for (int i = 0; i < size; i++) {
            cumulativeWeight += weights[i];
            if (randomValue < cumulativeWeight) {
                return chromosomeArrayList.get(i);
            }
        }

        // This should never happen if the weights are correctly set,
        return null;
    }

}
