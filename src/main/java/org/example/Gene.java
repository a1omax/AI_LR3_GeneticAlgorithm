package org.example;


public class Gene {

    private boolean value = false; // bit

    public Gene(boolean value){
        this.value = value;
    }
    public Gene(Gene gene){
        this.value = gene.getValue();
    }


    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value?1:0);
    }
}
