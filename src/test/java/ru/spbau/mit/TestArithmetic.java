package ru.spbau.mit;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestArithmetic {
    @Test
    public void testArithmetic() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testArithmetic").getPath());
        a.eliminateNonsolitaryTerm();
        a.eliminateRulesMore2();
        a.eliminateEps();
        a.eliminateUnitRule();
        a.eliminateUselessTerm();
        String check = "@@F3 = '(' \n" +
                "@@E2 = '+' \n" +
                "@@T1 = '*' \n" +
                "T = '9' | '8' | '7' | '6' | '5' | '4' | '3' | @@F3 @F1 | T @T1 | '2' | '1' | '0' \n" +
                "E = E @E1 | T @T1 | '9' | '8' | '7' | '6' | '5' | '4' | '3' | @@F3 @F1 | '2' | '1' | '0' \n" +
                "F = '9' | '8' | '7' | '6' | '5' | '4' | '3' | @@F3 @F1 | '2' | '1' | '0' \n" +
                "@F1 = E @@F6 \n" +
                "@E1 = @@E2 T \n" +
                "@T1 = @@T1 F \n" +
                "@@F6 = ')' \n";
        String out = a.toString();
        assertEquals(check, out);
    }
}
