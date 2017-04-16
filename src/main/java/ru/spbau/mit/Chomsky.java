package ru.spbau.mit;

import java.io.*;
import java.util.*;

class Rule {
    private ArrayList<ArrayList<String>> listRules = new ArrayList<ArrayList<String>>();

    public ArrayList<ArrayList<String>> get() {
        return listRules;
    }
    public void addRule(String name) {
        if (name.equals("|")) {
            listRules.add(new ArrayList<String>());
            return;
        }
        if (listRules.size() == 0) {
            listRules.add(new ArrayList<String>());
        }
        listRules.get(listRules.size() - 1).add(name);
    }
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (ArrayList<String> i : listRules) {
            for (String anI : i) {
                out.append(anI).append(" ");
            }
            out.append("| ");
        }
        return out.toString().substring(0, out.toString().length() - 2);
    }
    public boolean contains(String elem) {
        for (ArrayList<String> i : listRules) {
            if (i.contains(elem)){
                return true;
            }
        }
        return false;
    }
    public boolean containsTerm(String name) {
        for (ArrayList<String> i : listRules) {
            if (i.size() == 1 && i.get(0).equals(name))
                return true;
        }
        return false;
    }
    public void insert(ArrayList<String> rules) {
        listRules.add(rules);
    }
    public ArrayList<ArrayList<String>> getRules(String name) {
        ArrayList<ArrayList<String>> out = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> i : listRules) {
            if (i.contains(name)){
                out.add(i);
            }
        }
        return out;
    }
    public void remove(ArrayList<String> rules) {
        Iterator<ArrayList<String>> iterator = listRules.iterator();
        while (iterator.hasNext()) {
            ArrayList<String> next = iterator.next();
            if (next.size() == 1) {
                if (rules.contains(next.get(0))) {
                    iterator.remove();
                }
            }
        }
    }
    public void removeUnless(String rule) {
        Iterator<ArrayList<String>> iterator = listRules.iterator();
        while (iterator.hasNext()) {
            ArrayList<String> next = iterator.next();
            if (!rule.equals("eps") && next.contains(rule)) {
                iterator.remove();
            }
        }
    }
    public void removeRule(ArrayList<String> rules) {
        listRules.remove(rules);
    }
    public ArrayList<String> getFirstRuleMore2() {
        for (ArrayList<String> i : listRules) {
            if (i.size() > 2) {
                return i;
            }
        }
        return null;
    }
    public void replaceRule(String oldVal, String newVal) {
        for (ArrayList<String> i : listRules) {
            if (i.size() > 1) {
                for (int j = 0; j < i.size(); j++) {
                    if (i.get(j).equals(oldVal)) {
                        i.set(j, newVal);
                    }
                }
            }
        }
    }
    public boolean isEmpty() {
        return listRules.isEmpty();
    }
    public int size() {
        return listRules.size();
    }
    public ArrayList<String> indexOf(int index) {
        if (index >= size()) {
            return null;
        }
        return listRules.get(index);
    }
    public void removeEps() {
        for (ArrayList<String> i : listRules) {
            i.remove("eps");
        }
    }
    public static boolean isTerminal(String elem) {
        if (elem.length() >= 3 && elem.charAt(0) == '\'' && elem.charAt(elem.length() - 1) == '\'') {
            return true;
        }
        return false;
    }
    public LinkedHashSet<String> getUnits() {
        LinkedHashSet<String> out = new LinkedHashSet<String>();
        for (ArrayList<String> i : listRules) {
            if (i.size() == 1) {
                for (String j : i) {
                    if (!isTerminal(j) && !j.equals("eps")) {
                        out.add(j);
                    }
                }
            }
        }
        return out;
    }
    public void insertRule(ArrayList<ArrayList<String>> rules) {
        listRules.addAll(rules);
    }
    private ArrayList<String> searchNotUnits(int index) {
        ArrayList<String> out = new ArrayList<String>();
        ArrayList<String> strings = listRules.get(index);
        if (strings.size() > 1 || isTerminal(strings.get(0))) {
            out = strings;
        }
        return out;
    }
    public ArrayList<ArrayList<String>> getNotUnits() {
        ArrayList<ArrayList<String>> out = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < listRules.size(); i++) {
            ArrayList<String> strings = searchNotUnits(i);
            if (strings.size() != 0) {
                out.add(strings);
            }
        }
        return out;
    }
    public void removeUnit() {
        listRules = getNotUnits();
    }
    public void shrinkFit() {
        Iterator<ArrayList<String>> iter = listRules.iterator();
        while(iter.hasNext()) {
            ArrayList<String> current = iter.next();
            if (current == null || current.size() == 0) {
                iter.remove();
            }
        }
    }
    public boolean containsFinalTerm() {
        for (ArrayList<String> i : listRules) {
            if (i.size() == 1) {
                if (isTerminal(i.get(0))) {
                    return true;
                }
            }
            if (i.size() == 2) {
                if (isTerminal(i.get(0)) && isTerminal(i.get(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getTermRule() {
        for (ArrayList<String> i : listRules) {
            if (i.size() >= 2) {
                for (String j : i) {
                    if (isTerminal(j)) {
                        return i;
                    }
                }
            }
        }
        return null;
    }
    public void removeLoop(String name) {
        Iterator<ArrayList<String>> iterator = listRules.iterator();
        while (iterator.hasNext()) {
            ArrayList<String> next = iterator.next();
            if (next.size() == 1 && next.get(0).contains(name)) {
                iterator.remove();
            }
        }
    }

    public void removeDuplicate() {
        Set<ArrayList<String>> tmp = new HashSet<ArrayList<String>>();
        tmp.addAll(listRules);
        listRules.clear();
        listRules.addAll(tmp);
    }
    public LinkedHashSet<String> getAllRule() {
        LinkedHashSet<String> out = new LinkedHashSet<String>();
        for (ArrayList<String> i : listRules) {
            for (String j : i) {
                if (!isTerminal(j)) {
                    out.add(j);
                }
            }
        }
        return out;
    }
    public boolean contains(String[] name) {
        for (ArrayList<String> i : listRules) {
            int count = 0;
            for (String j : name) {
                if (i.contains(j)) {
                    count++;
                }
            }
            if (count == i.size()) {
                return true;
            }
        }
        return false;
    }
}

class UniqueIndex {
    private String base;
    private int index = 0;
    public UniqueIndex(String name) {
        base = name;
    }
    public String next() {
        index++;
        return current();
    }
    public String current() {
        return base + Integer.toString(index);
    }
}

public class Chomsky {
    private String startRule;
    private Map<String, Rule> grammarRules = new HashMap<String, Rule>();
    private void print() {
        System.out.println("Start rule: " + startRule);
        for(Map.Entry<String, Rule> i : grammarRules.entrySet()) {
            System.out.println(i.getKey() + " = " + i.getValue().toString());
        }
    }
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(Map.Entry<String, Rule> i : grammarRules.entrySet()) {
            out.append(i.getKey()).append(" = ").append(i.getValue().toString()).append("\n");
        }
        return out.toString();
    }
    public void importFile(String fileName) throws IOException {
        File f = new File(fileName);
        BufferedReader fin = new BufferedReader(new FileReader(f));
        String line;
        line = fin.readLine();
        startRule = line;
        while ((line = fin.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (grammarRules.containsKey(tokens[0])) {
                grammarRules.get(tokens[0]).addRule("|");
                for (int j = 2; j < tokens.length; j++) {
                    grammarRules.get(tokens[0]).addRule(tokens[j]);
                }
            } else {
                Rule r = new Rule();
                for (int j = 2; j < tokens.length; j++) {
                    r.addRule(tokens[j]);
                }
                grammarRules.put(tokens[0], r);
            }
        }
//        print();
    }
    public int size() {
        return grammarRules.size();
    }
    public Map<String, Rule> getTree() {
        return grammarRules;
    }
    public String getStart() {
        return startRule;
    }
    public void eliminateRulesMore2() {
        Set<Map.Entry<String, Rule>> entries = grammarRules.entrySet();
        Map<String, Rule> newRules = new HashMap<String, Rule>();
        for (Map.Entry<String, Rule> i : entries) {
            Rule rule = i.getValue();
            ArrayList<String> rules = null;
            UniqueIndex idx = new UniqueIndex("@" + i.getKey());
            while ((rules = rule.getFirstRuleMore2()) != null) {
                rule.removeRule(rules);
                String[] name = new String[rules.size()];
                rules.toArray(name);
                if (!rule.isEmpty()) {
                    rule.addRule("|");
                }
                rule.addRule(name[0]);
                rule.addRule(idx.next());
                for (int j = 1; j < name.length; ++j) {
                    Rule tmp = new Rule();
                    String current = idx.current();
                    if (j == name.length - 2) {
                        tmp.addRule(name[j]);
                        tmp.addRule(name[j + 1]);
                        newRules.put(current, tmp);
                        break;
                    }
                    tmp.addRule(name[j]);
                    tmp.addRule(idx.next());
                    newRules.put(current, tmp);
                }
            }
        }
        grammarRules.putAll(newRules);
//        print();
    }
    public void eliminateEps() {
        HashSet<String> epsRule = new HashSet<String>();
        Set<Map.Entry<String, Rule>> entries = grammarRules.entrySet();
        for (Map.Entry<String, Rule> i : entries) {
            String name = i.getKey();
            Rule rule = i.getValue();
            if (rule.contains("eps")) {
                epsRule.add(name);
            }
        }
        if (epsRule.size() == 0) {
            return;
        }
        int prevSize = -1;
        while(prevSize != epsRule.size()) {
            prevSize = epsRule.size();
            String[] name = new String[epsRule.size()];
            epsRule.toArray(name);
            for (Map.Entry<String, Rule> j : entries) {
                if (j.getValue().contains(name)) {
                    epsRule.add(j.getKey());
                }
            }
        }
        boolean flag = false;
        if (grammarRules.get(startRule).contains("eps")) {
            flag = true;
        }
        for (Map.Entry<String, Rule> i : entries) {
            i.getValue().removeEps();
        }
        for (Map.Entry<String, Rule> i : entries) {
            i.getValue().shrinkFit();
        }
        for (Map.Entry<String, Rule> i : entries) {
            Rule rule = i.getValue();
            for (int j = 0; j < rule.size(); j++) {
                ArrayList<String> rules = rule.indexOf(j);
                if (rules.size() == 2) {
                    String[] name = new String[rules.size()];
                    rules.toArray(name);
                    if (epsRule.contains(name[0]) && epsRule.contains(name[1])) {
                        rule.addRule("|");
                        rule.addRule(name[0]);
                        rule.addRule("|");
                        rule.addRule(name[1]);
                    } else if (epsRule.contains(name[1]) && !epsRule.contains(name[0])) {
                        rule.addRule("|");
                        rule.addRule(name[0]);
                    } else if (!epsRule.contains(name[1]) && epsRule.contains(name[0])) {
                        rule.addRule("|");
                        rule.addRule(name[1]);
                    }
                }
            }
        }
        if (flag) {
            Rule n = new Rule();
            n.addRule(startRule);
            n.addRule("|");
            n.addRule("eps");
            grammarRules.put("!"+startRule, n);
            startRule = "!" + startRule;
        }
        for (Map.Entry<String, Rule> i : entries) {
            i.getValue().shrinkFit();
            i.getValue().removeDuplicate();
        }
        entries = grammarRules.entrySet();
        for (Map.Entry<String, Rule> i : entries) {
            i.getValue().removeLoop(i.getKey());
        }
//        print();
    }
    public void eliminateUnitRule() {
        Set<Map.Entry<String, Rule>> entries = grammarRules.entrySet();
        Map<String,LinkedHashSet<String>> newRules = new HashMap<String, LinkedHashSet<String>>();
        for (Map.Entry<String, Rule> i : entries) {
            LinkedHashSet<String> units = i.getValue().getUnits();
            if (units.size() != 0) {
                newRules.put(i.getKey(), units);
            }
        }
        Set<Map.Entry<String, LinkedHashSet<String>>> entries1 = newRules.entrySet();
        for (Map.Entry<String, LinkedHashSet<String>> i : entries1) {
            LinkedHashSet<String> tmp = i.getValue();
            int prevSize = -1;
            while (prevSize != tmp.size()) {
                prevSize = tmp.size();
                String[] name = new String[tmp.size()];
                tmp.toArray(name);
                for (String j : name) {
                    tmp.addAll(grammarRules.get(j).getUnits());
                }
            }
        }
        for (Map.Entry<String, LinkedHashSet<String>> i : entries1) {
            String name = i.getKey();
            ArrayList<String> t = new ArrayList<String>();
            t.addAll(i.getValue());
            grammarRules.get(name).remove(t);
            LinkedHashSet<String> tmp = i.getValue();
            for (String j : tmp) {
                grammarRules.get(name).insertRule(grammarRules.get(j).getNotUnits());
            }
        }
        for (Map.Entry<String, Rule> i : entries) {
            i.getValue().shrinkFit();
            i.getValue().removeDuplicate();
        }
//        print();
    }
    private void firstAlg() {
        LinkedHashSet<String> rule = new LinkedHashSet<String>();
        Set<Map.Entry<String, Rule>> entries = grammarRules.entrySet();
        for (Map.Entry<String, Rule> i : entries) {
            if (i.getValue().containsFinalTerm()) {
                rule.add(i.getKey());
            }
        }
        int prevSize = -1;
        while (prevSize != rule.size()) {
            prevSize = rule.size();
            String[] name = new String[rule.size()];
            rule.toArray(name);
            for (String j : name) {
                for (Map.Entry<String, Rule> i : entries) {
                    if (i.getValue().contains(j)) {
                        rule.add(i.getKey());
                    }
                }
            }
        }
        Map<String, Rule> newRule = new HashMap<String, Rule>();
        for (String i : rule) {
            if (grammarRules.containsKey(i)) {
                newRule.put(i, grammarRules.get(i));
            }
        }
        for (Map.Entry<String, Rule> i : entries) {
            String r = i.getKey();
            if (!newRule.containsKey(r)) {
                for (Map.Entry<String, Rule> j : entries) {
                    j.getValue().removeUnless(r);
                }
            }
        }
        grammarRules = newRule;
    }
    private void secondAlg() {
        LinkedHashSet<String> rule = new LinkedHashSet<String>();
        Set<Map.Entry<String, Rule>> entries = grammarRules.entrySet();
        rule.add(startRule);
        int prevSize = -1;
        while (prevSize != rule.size()) {
            prevSize = rule.size();
            String[] name = new String[rule.size()];
            rule.toArray(name);
            for (String j : name) {
                for (Map.Entry<String, Rule> i : entries) {
                    if (grammarRules.get(j).contains(i.getKey())) {
                        rule.add(i.getKey());
                    }
                }
            }
        }
        Map<String, Rule> newRule = new HashMap<String, Rule>();
        for (String i : rule) {
            if (grammarRules.containsKey(i)) {
                newRule.put(i, grammarRules.get(i));
            }
        }
        for (Map.Entry<String, Rule> i : entries) {
            String r = i.getKey();
            if (!newRule.containsKey(r)) {
                for (Map.Entry<String, Rule> j : entries) {
                    j.getValue().removeUnless(r);
                }
            }
        }
        LinkedHashSet<String> tmp = new LinkedHashSet<String>();
        for (Map.Entry<String, Rule> i : newRule.entrySet()) {
            tmp.addAll(i.getValue().getAllRule());
        }
        for (String i : tmp) {
            if (!newRule.containsKey(i)) {
                for (Map.Entry<String, Rule> j : newRule.entrySet()) {
                    j.getValue().removeUnless(i);
                }
            }
        }
        grammarRules = newRule;
    }
    public void eliminateUselessTerm() {
        firstAlg();
        secondAlg();
//        print();
    }

    public void eliminateNonsolitaryTerm() {
        Set<Map.Entry<String, Rule>> entries = grammarRules.entrySet();
        Map<String, Rule> newRules = new HashMap<String, Rule>();
        for (Map.Entry<String, Rule> i : entries) {
            Rule rule = i.getValue();
            ArrayList<String> rules = null;
            UniqueIndex idx = new UniqueIndex("@@" + i.getKey());
            while ((rules = rule.getTermRule()) != null) {
                String[] name = new String[rules.size()];
                rules.toArray(name);
                for (String j : name) {
                    if (Rule.isTerminal(j)) {
                        for (Map.Entry<String, Rule> k : entries) {
                            k.getValue().replaceRule(j, idx.next());
                            Rule a = new Rule();
                            a.addRule(j);
                            newRules.put(idx.current(), a);
                        }
                    }
                }
            }
        }
        grammarRules.putAll(newRules);
//        print();
    }
}
