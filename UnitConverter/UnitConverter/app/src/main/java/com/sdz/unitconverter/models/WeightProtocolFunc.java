package com.sdz.unitconverter.models;

public class WeightProtocolFunc implements protocolFunc {

    public double Convert(String from, String to, double input) {

        if(from.equals(to)){
            return input;
        }

        if((from.equals(Units.KG)) && to.equals(Units.GRAM)){
            //if((from.equals("Kg")) && (to.equals("gm"))){
            double ret = 1000*input;
            return ret;
        }
        if((from.equals(Units.KG)) && to.equals(Units.POUND)){
            //if((from.equals("Kg")) && (to.equals("lb"))){
            double ret = 2.2046*input;
            return ret;
        }

        if((from.equals(Units.KG)) && to.equals(Units.OUNCE)){
            //if((from.equals("Kg")) && (to.equals("ounce"))){
            double ret = input*35.27396;
            return ret;
        }

        return 0.0;
    }

    @Override
    public String getType() {
        return "weight";
    }

}
