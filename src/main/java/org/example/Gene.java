package org.example;


public class Gene {

    private int value = 0;

    public Gene(int value){
        this.value = value;
    }
    public Gene(Gene gene){
        this.value = gene.getValue();
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
