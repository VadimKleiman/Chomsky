package ru.spbau.mit;

import java.io.*;
import java.util.Map;
import java.util.Set;

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
            CYK cyk = new CYK();
            cyk.importFile(args[1]);
            cyk.addGrammar(a.getTree());
            cyk.setStart(a.getStart());
            System.out.print(cyk.getWord() + ": ");
            boolean flag = cyk.start();
            System.out.println(flag);
            if (cyk.getWord() != null && flag) {
                String st = "graph graphname {\n";
                st = st + cyk.getTree();
                st = st + "\n }";
                try(FileWriter writer = new FileWriter(args[1] + "Tree.dot", false))
                {
                    writer.write(st);
                }catch (IOException ignored) {

                }
                try(FileWriter writer = new FileWriter(args[1] + "Result.csv", false))
                {
                    Pair out[][] = cyk.getTable();
                    for (int i = 0; i < cyk.getWord().length(); i++)
                    {
                        StringBuilder line = new StringBuilder();
                        for (int j = 0; j < cyk.getWord().length(); j++) {
                            Set<String> keys = out[i][j].getKeys();
                            line.append("[");
                            for (String k : keys) {
                                line.append(k).append(" |");
                            }
                            if (!keys.isEmpty()) {
                                line.deleteCharAt(line.length() - 1);
                            }
                            line.append("]").append(",");
                        }
                        line.deleteCharAt(line.length() - 1);
                        line.append("\n");
                        writer.write(line.toString());

                    }
                }catch (IOException ignored) {

                }
            }

        } catch (IOException e) {
            System.err.println("Error! \n" + e.getMessage());
        }
    }
}
