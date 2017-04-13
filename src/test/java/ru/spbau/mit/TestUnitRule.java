package ru.spbau.mit;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestUnitRule {
    @Test
    public void testElimUnitRule() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testElimUnitRule").getPath());
        a.eliminateUnitRule();
        String check = "A = D D | 'c' | 'b' | 'a' \nB = D D | 'c' | 'b' \nC = D D | 'c' \n";
        String out = a.toString();
        assertEquals(check, out);
    }
}
