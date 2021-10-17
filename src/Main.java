import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        List<String> list = Arrays.asList("one", "two", "tree", "two", "tree", "tree");
        Set<String> set = new HashSet<>();
        for (String el : list)
            set.add(el);
        Map<String, Integer> map = new HashMap<>();
        for (String el : set)
            map.put(el, 0);
        for (Map.Entry<String, Integer> o : map.entrySet()) {
            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String str = iter.next();
                if (str.equals(o.getKey()))
                    o.setValue(o.getValue() + 1);
            }
        }
        for (Map.Entry<String, Integer> o : map.entrySet())
            System.out.println(o.getKey() + ": " + o.getValue());
    }


}
