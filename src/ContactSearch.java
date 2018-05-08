
import java.util.ArrayList;

public class ContactSearch {
    ContactData data;
    public ContactSearch(ContactData data){
        this.data=data;
        data.initializeTrie();
    }
    
    public boolean addContact(String name, String email, String phone){
        boolean result=false;
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        String processedEmail = email.toLowerCase();
        for(String token : nameToken){
            processedName.concat(token);
        }
        data.insertIntoTrieEmail(data.rootEmail, processedEmail, processedName, phone);
        data.insertIntoTrieName(data.rootName, processedName, processedEmail, phone);
        return true;
    }
    
    public boolean deleteContact(String name, String email){
        boolean result=false;
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        String processedEmail = email.toLowerCase();
        for(String token : nameToken){
            processedName.concat(token);
        }
        result = result | data.deleteEmail(data.rootEmail, processedEmail);
        result = result | data.deleteName(data.rootName, processedName, processedEmail);
        return result;
    }
    
    public void searchByEmail(String email){
        String processedEmail = email.toLowerCase();
        NodeForEmailSearch contact = data.searchEmail(data.rootEmail, processedEmail);
        if(contact==null){
            System.out.println("No entries found!!!");
        }else{
            System.out.println(contact.getContact()+" "+contact.getName());
        }
    }
    
    public void searchByName(String name){
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        for(String token : nameToken){
            processedName.concat(token);
        }
        NodeForNameSearch contact = data.searchName(data.rootName, processedName);
        if(contact==null){
            System.out.println("No entries found!!!");
        }else{
            ArrayList<String> contactDetails = contact.getContactList();
            ArrayList<String> emailList = contact.getEmailList();
            for(int i=0;i<contactDetails.size();i++){
                System.out.println(emailList.get(i)+" "+contactDetails.get(i));
            }
        }
    }
}
