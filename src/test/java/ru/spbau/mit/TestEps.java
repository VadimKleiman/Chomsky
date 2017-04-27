package ru.spbau.mit;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestEps {
    @Test
    public void testElimEps() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testElimEps").getPath());
        a.eliminateRulesMore2();
        a.eliminateEps();
        String check = "A = 'a' \nB = A | C | A C \nS = @S1 | A @S1 \nC = 'c' \n@S1 = @S2 | B @S2 \n@S2 = C 'd' | 'd' \n";
        String out = a.toString();
        assertEquals(check, out);
    }
}
