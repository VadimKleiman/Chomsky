package ru.spbau.mit;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Chomsky a = new Chomsky();
        try {
            a.importFile(args[0]);
            a.eliminateNonsolitaryTerm();
            a.eliminateRulesMore2();
            a.eliminateEps();
            a.eliminateUnitRule();
            a.eliminateUselessTerm();
            System.out.println(a.toString());
        } catch (IOException e) {
            System.err.println("Error! \n" + e.getMessage());
        }
    }
}
