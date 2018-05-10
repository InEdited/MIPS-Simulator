package Mips.Processor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Register {
    private StringProperty name;
    private StringProperty value;
    private IntegerProperty code;


    public Register(String name,String code){
        this.name = new SimpleStringProperty();
        this.name.set(name);
        this.code = new SimpleIntegerProperty();
        this.code.set(Integer.parseInt(code));
        value = new SimpleStringProperty();
        this.value.set("00000000000000000000000000000000");
        if(this.name.getValue().equals("$sp")){
            this.value.set("00000111111111111111111111111111");
        }
    }


    //Getters and Setters


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        if((!this.name.get().equals("$0")) && (!this.name.get().equals("$zero")))
            this.value.set(value);
        else
            System.out.println("Error : Trying to write to the Zero register, overwriting is not allowed.");
    }

    public int getCode() {
        return code.get();
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public void printReg(){
        System.out.println("Register " + this.getName() + ": " + this.getValue());
    }

}
