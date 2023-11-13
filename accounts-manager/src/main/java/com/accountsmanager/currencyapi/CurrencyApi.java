package com.accountsmanager.currencyapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class CurrencyApi {
    double eur;
    double usd;
    double pln;
    double gbp;

    public double getCurrency(final String to) {
        switch (to){
            case "eur" -> {return eur;}
            case "usd" -> {return usd;}
            case "pln" -> {return pln;}
        }
        return gbp;
    }
}
