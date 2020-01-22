/*

    Description: This class holds the name and value of a trait of a creature
 */

package gameEngine.ADT;

public class Trait implements Comparable<Trait>{
    private String name;
    private float value;
    private float minTraitValue, maxTraitValue, defaultTraitValue;

    public Trait(String name, float value){
        this.name = name;
        this.value = value;
    }

    public Trait(String name, float min, float max, float defaultVal) {
        this.name = name;
        this.minTraitValue = min;
        this.maxTraitValue = max;
        this.defaultTraitValue = defaultVal;
        this.value = defaultVal;
    }
    public float getMinTraitValue() {
        return minTraitValue;
    }

    public void setMinTraitValue(float minTraitValue) {
        this.minTraitValue = minTraitValue;
    }

    public float getMaxTraitValue() {
        return maxTraitValue;
    }

    public void setMaxTraitValue(float maxTraitValue) {
        this.maxTraitValue = maxTraitValue;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(float value) {
        this.value = value;
    }

    //compares two traits lexicographically by their names
    public int compareTo(Trait trait) {
        //TODO could be sorting in decending order
        return this.getName().compareTo(trait.getName());
    }
}