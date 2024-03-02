package org.example;

import java.util.Collections;
import java.util.List;


public class Calculator {

    private final double xMin;
    private final double xMax;

    private final double size;

    public Calculator(double xMin, double xMax) {
        if (xMin > xMax) {
            throw new RuntimeException("xMin can not be more than xMax");
        }

        this.xMax = xMax;
        this.xMin = xMin;
        this.size = xMax - xMin;
    }


    private double function(Chromosome chromosome) {
        List<Boolean> chromosomeValues = chromosome.getGenes().stream().map(Gene::getValue).toList();


        int center = chromosomeValues.size() / 2;

        // todo test
        int intValue1 = Utils.booleanListToInteger(chromosomeValues.subList(0, center));
        int intValue2 = Utils.booleanListToInteger(chromosomeValues.subList(center, chromosomeValues.size()));

        double result = f(Utils.tabulatedValue(intValue1, this.size, this.xMin, center),
                Utils.tabulatedValue(intValue2, this.size, this.xMin, center));

        return result;
    }


    private double f(double x1, double x2) {
        return 20 + x1 * x1 + x2 * x2 - 10 * Math.cos(2 * Math.PI * x1) - 10 * Math.cos(2 * Math.PI * x2);
    }

    public List<Double> calculateListResults(List<Chromosome> chromosomeList) {
        return chromosomeList.stream().map(this::function).toList();
    }


    public static List<Double> subtractMinValueFromList(List<Double> list){
        Double minValue = Collections.min(list);

        return list.stream().map(d->d-minValue).toList();
    }



}
