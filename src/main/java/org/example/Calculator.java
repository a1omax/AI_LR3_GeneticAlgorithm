package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Calculator {


    public int[] functionCoefficients;
    public int d;

    public Calculator(int[] functionCoefficients, int d){
        this.functionCoefficients = functionCoefficients;
        this.d = d;
    }


    private int adaptationFunction(Chromosome chromosome) {
        List<Integer> chromosomeValues = chromosome.getGenes().stream().map(Gene::getValue).toList();

        int sum = 0;

        for (int i = 0; i < functionCoefficients.length; i++) {
            sum += functionCoefficients[i] * chromosomeValues.get(i);
        }
        return sum;
    }

    public int calculateDifference(Chromosome chromosome) {
        return Math.abs(d - adaptationFunction(chromosome));
    }

    public List<Integer> calculateListDifference(List<Chromosome> chromosomes){
        return chromosomes.stream().map(this::calculateDifference).toList();
     }


    public List<Double> calculateReversedListDifference(List<Integer> differences) {
        return differences.stream().map(d->1./d).toList();
    }

    public List<Double> calculateAdaptationCoefficientList(List<Double> reversedListDifference) {

        double reversedDifferencesSum = reversedListDifference.stream().mapToDouble(Double::doubleValue).sum();

        return reversedListDifference.stream().map(rd -> rd / reversedDifferencesSum).toList();

    }

}
