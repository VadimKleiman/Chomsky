package ru.spbau.mit;

import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertEquals;


public class TestBracketSequences {
    @Test
    public void BracketTest() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/BracketSequences").getPath());
        a.eliminateNonsolitaryTerm();
        a.eliminateRulesMore2();
        a.eliminateEps();
        a.eliminateUnitRule();
        a.eliminateUselessTerm();
        String check = "@@S1 = '(' \n" +
                "@@S2 = ')' \n" +
                "!S = S S | @@S1 @S1 | eps \n" +
                "S = S S | @@S1 @S1 \n" +
                "@S1 = S @@S2 | ')' \n";
        String out = a.toString();
        assertEquals(out, check);
    }
}
