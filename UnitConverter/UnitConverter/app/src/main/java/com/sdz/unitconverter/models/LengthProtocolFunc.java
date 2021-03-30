package com.sdz.unitconverter.models;

public class LengthProtocolFunc implements protocolFunc {

    public double Convert(String from, String to, double input) {

        if (from.equals(to)) {
            return input;
        }
        if ((from.equals(Units.METER)) && to.equals(Units.CENTIMETER)) {
            return  100 * input;
        }

        if ((from.equals(Units.METER)) && to.equals(Units.INCH)) {
            return 100 * input / 2.54;
        }

        if ((from.equals(Units.METER)) && to.equals(Units.FEET)) {
            return input * 3.28084;
        }


        return 0.0;
    }

    @Override
    public String getType() {
        return "length";
    }
}
