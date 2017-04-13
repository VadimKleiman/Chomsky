package ru.spbau.mit;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestUseless {
    @Test
    public void testElimUseles() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testElimUseless").getPath());
        a.eliminateUselessTerm();
        String check = "A = 'a' \nS = A S | 's' \n";
        String out = a.toString();
//        System.out.println(out);
        assertEquals(check, out);
    }
}
