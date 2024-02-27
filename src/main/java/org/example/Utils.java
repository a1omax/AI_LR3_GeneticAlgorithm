package org.example;

import java.util.ArrayList;
import java.util.List;


public class Utils {

    public static List<Chromosome> generateChromosomeList(int numberOfChromosomes, int numberOfGenes, int minGeneValue, int maxGeneValue) {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < numberOfChromosomes; i++) {
            chromosomeList.add(createChromosome(numberOfGenes, minGeneValue, maxGeneValue));
        }

        return chromosomeList;
    }

    public static Chromosome createChromosome(int numberOfGenes, int minGeneValue, int maxGeneValue) {
        List<Gene> generatedGeneList = new ArrayList<>();
        for (int i = 0; i < numberOfGenes; i++) {
            generatedGeneList.add(new Gene(App.RANDOM.nextInt(minGeneValue, maxGeneValue + 1)));
        }
        return new Chromosome(generatedGeneList);
    }






    public static <T> T randomChoice(List<T> list){
        return list.get(App.RANDOM.nextInt(list.size()));
    }
}
