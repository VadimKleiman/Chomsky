package ru.spbau.mit;

import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCYK {
    @Test
    public void TestTrue() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testCYKGrammar").getPath());
        a.eliminateNonsolitaryTerm();
        a.eliminateRulesMore2();
        a.eliminateEps();
        a.eliminateUnitRule();
        a.eliminateUselessTerm();
        CYK cyk = new CYK();
        cyk.importFile(this.getClass().getResource("/testTrueWord").getPath());
        cyk.addGrammar(a.getTree());
        cyk.setStart(a.getStart());
        assertTrue(cyk.start());
    }

    @Test
    public void TestFalse() throws IOException {
        Chomsky a = new Chomsky();
        a.importFile(this.getClass().getResource("/testCYKGrammar").getPath());
        a.eliminateNonsolitaryTerm();
        a.eliminateRulesMore2();
        a.eliminateEps();
        a.eliminateUnitRule();
        a.eliminateUselessTerm();
        CYK cyk = new CYK();
        cyk.importFile(this.getClass().getResource("/testFalseWord").getPath());
        cyk.addGrammar(a.getTree());
        cyk.setStart(a.getStart());
        assertFalse(cyk.start());
    }
}
