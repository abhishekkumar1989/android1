package com.abhishek_k.testapp2.app1;


public class ConverterUtil {

    public static float fahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9);
    }

    public static float celsiusToFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }

}
