
import java.util.ArrayList;

public class ContactBook {
    public static void main(String[] args){
        ContactData data = new ContactData();
        ContactSearch query = new ContactSearch(data);
        query.addContact("tushar", "tushar", "7759856956");
        //query.searchByName("tushar");
    }
}
