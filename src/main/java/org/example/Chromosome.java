package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chromosome {
    private final List<Gene> genes;

    public Chromosome(List<Gene> genes){
        this.genes = genes;
    }

    // Copy chromosome
    public Chromosome(Chromosome chromosome){
        List<Gene> geneList = chromosome.getGenes();
        this.genes = new ArrayList<>();

        for (Gene gene : geneList) {
            this.genes.add(new Gene(gene));
        }
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void mutateOneRandomGene(int mutationOrigin, int mutationBound){
        int randomGenePos = App.RANDOM.nextInt(this.getGenes().size());
        int randomMutationValue = App.RANDOM.nextInt(mutationOrigin, mutationBound);

        this.genes.set(randomGenePos, new Gene(randomMutationValue));
    }
    public static List<Chromosome> makeRecombinations(List<Chromosome> chromosomeList){

        List<Chromosome> recombinatedList = new ArrayList<>();

        for (int i = 0; i < chromosomeList.size(); i++) {
            Chromosome firstChromosome = Utils.randomChoice(chromosomeList);
            Chromosome secondChromosome = Utils.randomChoice(chromosomeList);

            recombinatedList.add(Chromosome.recombination(firstChromosome, secondChromosome));
        }

        return recombinatedList;
    }

    public static Chromosome recombination(Chromosome c1, Chromosome c2){
        if (c1 == null || c2 == null){
            throw new RuntimeException("Chromosome should not be null");
        }
        if (c1.getGenes().size() != c2.getGenes().size()){
            throw new RuntimeException("There should be equal amount of genes in chromosomes to create child");
        }
        if (c1.getGenes().size() < 2){
            throw new RuntimeException("There should be at least 2 genes in chromosome");
        }

        int chromosomeSize = c1.getGenes().size();

        int p1 = App.RANDOM.nextInt(1, chromosomeSize);

        List<Gene> genesC1 = c1.getGenes();
        List<Gene> genesC2 = c2.getGenes();

        List<Gene> genesC3 = Stream.concat(genesC1.stream().limit(p1), genesC2.stream().skip(p1)).collect(Collectors.toList());


        return new Chromosome(genesC3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Objects.equals(genes, that.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes);
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + genes +
                '}';
    }
}
