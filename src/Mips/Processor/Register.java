package Mips.Processor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Register {
    private StringProperty name;
    private StringProperty value;
    private IntegerProperty code;


    public Register(String name,String code){
        this.name.set(name);
        this.code.set(Integer.parseInt(code));
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

}
