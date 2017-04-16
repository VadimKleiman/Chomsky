package ru.spbau.mit;


import java.io.*;
import java.util.*;


class Pair {
    private LinkedHashMap<String, String> set = new LinkedHashMap<>();
    public Set<String> getKeys() {
        return set.keySet();
    }
    public void add(String rule, String tree) {
        set.put(rule, tree);
    }

    public String  getTree(String rule) {
        return set.get(rule);
    }
}
public class CYK {
    private Map<String, ArrayList<String>> gramm = new HashMap<>();
    private String word;
    private String st;
    private Pair R[][];
    public void importFile(String name) throws IOException {
        File f = new File(name);
        BufferedReader fin = new BufferedReader(new FileReader(f));
        word = fin.readLine();
    }
    public void setStart(String start) {
        st = start;
    }
    public void addGrammar(Map<String, Rule> grammar) {
        Set<Map.Entry<String, Rule>> entries = grammar.entrySet();
        for (Map.Entry<String, Rule> i : entries) {
            ArrayList<ArrayList<String>> arrayLists = i.getValue().get();
            for (ArrayList<String> j : arrayLists) {
                StringBuilder str = new StringBuilder();
                for (String k : j) {
                    str.append(k);
                }
                if (!gramm.containsKey(str.toString())) {
                    gramm.put(str.toString(), new ArrayList<String>());
                }
                gramm.get(str.toString()).add(i.getKey());
            }
        }
    }
    public boolean start()
    {
        if (word == null) {
            if (gramm.containsKey("eps")) {
                return true;
            }
            return false;
        }
        Pair[][] T = new Pair[word.length()][word.length()];
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                T[i][j] = new Pair();
            }
        }
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (gramm.containsKey("\'" + word.charAt(i) + "\'")) {
                ArrayList<String> strings = gramm.get("\'" + word.charAt(i) + "\'");
                for (String j : strings) {
                    T[i][i].add(j, "\"" + j + "[" + count + "]\"  -- \"" + word.charAt(i) + " [" + (count + 1) + "]\"" + ";");
                    count += 2;
                }
            }
        }
        for (int k = 1; k < word.length(); k++) {
            for (int i = 0; i < word.length() - k; i++) {
                for (int j = i; j < i + k; j++) {
                    Set<String> keys_l = T[i][j].getKeys();
                    Set<String> keys_r = T[j + 1][i + k].getKeys();
                    for (String l : keys_l) {
                        for (String r : keys_r) {
                            if (gramm.containsKey(l + r)) {
                                String tree_l = T[i][j].getTree(l);
                                String tree_r = T[j + 1][i + k].getTree(r);
                                ArrayList<String> strings = gramm.get(l + r);
                                for (String t : strings) {
                                    StringBuilder out = new StringBuilder();
                                    out.append("\"").append(t).append(" [").append(count).append("]").append("\"").append(" -- ");
                                    out.append(tree_l).append("\n");
                                    out.append("\"").append(t).append(" [").append(count).append("]").append("\"").append(" -- ");
                                    out.append(tree_r).append("\n");
                                    T[i][i + k].add(t, out.toString());
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (T[0][word.length() - 1].getKeys().isEmpty())
            return false;
        Set<String> keys = T[0][word.length() - 1].getKeys();
        if (keys.contains(st)) {
            R = T;
            return true;
        }
        return false;
    }
    public String getWord() {
        return word;
    }
    public String getTree() {
        return R[0][word.length() - 1].getTree(st);
    }
    public Pair[][] getTable() {
        return R;
    }
}
