import java.util.HashMap;
import java.util.Map;

public class Phonebook {
    private String surname;
    private Long number;

    Map<Long, String> phonebook = new HashMap<>();

    public void add(Long number,String surname){
        phonebook.put(number,surname);
    }
    public void get(String surname){
        System.out.println(surname + " имеет номера: ");
        for(Map.Entry<Long,String> o: phonebook.entrySet()){
            String str = o.getValue();
            if(surname.equals(str))
                System.out.println(o.getKey());
        }
    }
}
