package ru.spbau.mit;

import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestRuleMore {
    @Test
    public void eliminateRuleMore2Test() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testRuleMore2").getPath());
        a.eliminateRulesMore2();
        String check = "@A1 = B @A2 \nA = 'a' @A1 \n@A2 = 'c' B \nB = 'd' @B1 \nS = A B \n@B1 = 'e' 'f' \n";
        String out = a.toString();
        assertEquals(check, out);
    }
}
