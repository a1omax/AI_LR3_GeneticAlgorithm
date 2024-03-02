package org.example;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Utils {

    public static List<Chromosome> generateChromosomeList(int numberOfChromosomes, int numberOfGenes) {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < numberOfChromosomes; i++) {
            chromosomeList.add(createChromosome(numberOfGenes));
        }

        return chromosomeList;
    }

    public static Chromosome createChromosome(int numberOfGenes) {
        List<Gene> generatedGeneList = new ArrayList<>();
        for (int i = 0; i < numberOfGenes; i++) {
            generatedGeneList.add(new Gene(App.RANDOM.nextBoolean()));
        }
        return new Chromosome(generatedGeneList);
    }


    public static Integer booleanListToInteger(List<Boolean> booleanList){
        int result = 0;

        for (int i = booleanList.size()-1; i >= 0; i--) {
            boolean value = booleanList.get(i);
            int bitValue = value ? 1 : 0;
            result = (result << 1) + bitValue;
        }
        return result;
    }

    public double tabulatedValue(int x, double size, double from, int n) {

        double k = Math.pow(2, n);

        return size/k * x + from;
    }



    public static <T> T randomChoice(List<T> list){
        return list.get(App.RANDOM.nextInt(list.size()));
    }
}
