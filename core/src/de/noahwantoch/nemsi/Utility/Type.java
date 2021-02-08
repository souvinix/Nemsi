package de.noahwantoch.nemsi.Utility;

public class Type {
    private String dataType;

    public Type(int value){
        dataType = "Integer";
    }

    public Type(float value){
        dataType = "Float";
    }

    public Type(double value){
        dataType = "Double";
    }

    public Type(String value){
        dataType = "String";
    }

    public String getDataType(){ return dataType; }
}
