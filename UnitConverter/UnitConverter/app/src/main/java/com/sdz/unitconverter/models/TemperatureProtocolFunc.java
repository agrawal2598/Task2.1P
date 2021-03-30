package com.sdz.unitconverter.models;

public class TemperatureProtocolFunc implements protocolFunc {

    public double Convert(String from, String to, double input) {

        if((from.equals(Units.CELSIUS)) && to.equals((Units.FARHENHEIT))){
            double ret = (input*9/5)+32;
            return ret;
        }

        if((from.equals(Units.CELSIUS)) && to.equals((Units.KELVIN))){
            double ret = input + 273.15;
            return ret;
        }

        if(from.equals(to)){
            return input;
        }
        return 0.0;
    }

    @Override
    public String getType() {
        return "temperature";
    }

}