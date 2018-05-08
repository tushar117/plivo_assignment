import java.util.*;
public class ContactData {
    
    NodeForEmailSearch rootEmail = null;
    NodeForNameSearch rootName = null;
    
    public ContactData(){
        initializeTrie();
    }
    
    public boolean isTrieInitialzed(){
        return rootEmail!=null && rootName!=null;
    }
    
    public void initializeTrie(){
        if(isTrieInitialzed())
            return;
        rootEmail = new NodeForEmailSearch();
        rootName = new NodeForNameSearch();
    }
    
    public void insertIntoTrieEmail(NodeForEmailSearch node, String email, String name, String contact){
        //end node
        int firstCharIndex = email.charAt(0)-'0';
        if(node.next[firstCharIndex]==null){
            node.next[firstCharIndex]= new NodeForEmailSearch();
        }
        if(email.length()==1){
            node.next[firstCharIndex].setContact(contact);
            node.next[firstCharIndex].setName(name);
        }else
            insertIntoTrieEmail(node.next[firstCharIndex], email.substring(1), name, contact);
    }
    
    public void insertIntoTrieName(NodeForNameSearch node, String name, String email, String contact){
        int firstCharIndex = name.charAt(0)-'0';
        if(node.next[firstCharIndex]==null){
            node.next[firstCharIndex] = new NodeForNameSearch();
        }
        if(name.length()==1){
            node.next[firstCharIndex].addEntry(email, contact);
        }else{
            insertIntoTrieName(node.next[firstCharIndex], name.substring(1), email, contact);
        }
    }
    
    public NodeForEmailSearch searchEmail(NodeForEmailSearch node, String email){
        NodeForEmailSearch searchResult=null;
        int firstCharIndex = email.charAt(0)-'0';
        if(node.next[firstCharIndex]==null)
            return searchResult;
        
        if(email.length()==1 && node.next[firstCharIndex]!=null && node.next[firstCharIndex].validContact()){
            searchResult = node.next[firstCharIndex];
        }else{
            searchResult = searchEmail(node.next[firstCharIndex], email.substring(1));
        }
        return searchResult;
    }
    
    public NodeForNameSearch searchName(NodeForNameSearch node, String name){
        NodeForNameSearch searchResult=null;
        int firstCharIndex = name.charAt(0)-'0';
        if(node.next[firstCharIndex]==null)
            return searchResult;
        
        if(name.length()==1 && node.next[firstCharIndex]!=null && node.next[firstCharIndex].validContact()){
            searchResult = node.next[firstCharIndex];
        }else{
            searchResult = searchName(node.next[firstCharIndex], name.substring(1));
        }
        return searchResult;
    }
    
    public boolean modifyEmail(NodeForEmailSearch node, String email, String name,String contact){
        boolean result = false;
        int firstCharIndex = email.charAt(0)-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(email.length()==1 && node.next[firstCharIndex]!=null && node.next[firstCharIndex].validContact()){
            result = result | node.next[firstCharIndex].setContact(contact);
            result = result | node.next[firstCharIndex].setName(name);
        }else{
            result = result | modifyEmail(node.next[firstCharIndex], email.substring(1), name, contact);
        }
        return result;
    }
    
    public boolean modifyName(NodeForNameSearch node, String name, String email,String contact){
        boolean result = false;
        int firstCharIndex = name.charAt(0)-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(name.length()==1 && node.next[firstCharIndex]!=null && node.next[firstCharIndex].validContact()){
            result = result | node.next[firstCharIndex].modifyEntry(email, contact);
        }else{
            result = result | modifyName(node.next[firstCharIndex], name.substring(1), email, contact);
        }
        return result;
    }
    
    public boolean deleteEmail(NodeForEmailSearch node, String email){
        boolean result=false;
        int firstCharIndex = email.charAt(0)-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(email.length()==1 && node.next[firstCharIndex]!=null && node.next[firstCharIndex].validContact()){
            node.next[firstCharIndex]=null;
            result=true;
        }else{
            result = result | deleteEmail(node.next[firstCharIndex], email.substring(1));
        }
        return result;
    }
    
    public boolean deleteName(NodeForNameSearch node, String name,String email){
        boolean result=false;
        int firstCharIndex = name.charAt(0)-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(name.length()==1 && node.next[firstCharIndex]!=null && node.next[firstCharIndex].validContact()){
            node.next[firstCharIndex].deleteEntry(email);
            result=true;
        }else{
            result = result | deleteName(node.next[firstCharIndex], name.substring(1), email);
        }
        return result;
    }
    
}

class NodeForEmailSearch{
        private String name;
        private String contactNumber;
        NodeForEmailSearch[] next;
        
        public NodeForEmailSearch(){
            this.name=null;
            this.contactNumber=null;
            next = new NodeForEmailSearch[256];
        }
        
        public NodeForEmailSearch(String name,String contact){
            this.name=name;
            this.contactNumber=contact;
            next = new NodeForEmailSearch[256];
        }
        
        public String getName(){
            return this.name;
        } 
        
        public boolean setName(String name){
            if(name.length()==0 || name==null)
                return false;
            this.name=name;
            return true;
        }
        
        public String getContact(){
            return this.contactNumber;
        }
        
        public boolean setContact(String contact){
            if(contact.length()<10)
                return false;
            this.contactNumber=contact;
            return true;
        }
        
        public boolean validContact(){
            return this.name!=null && this.contactNumber!=null;
        }
    }
    
    class NodeForNameSearch{
        private ArrayList<String> emailList;
        private ArrayList<String> contactList;
        NodeForNameSearch[] next;
        
        public NodeForNameSearch(){
            this.emailList=null;
            this.contactList=null;
            next = new NodeForNameSearch[256];
        }
        
        public NodeForNameSearch(String email,String contact){
            if(this.emailList==null)
                this.emailList = new ArrayList<>();
            if(this.contactList==null)
                this.contactList = new ArrayList<>();
            emailList.add(email);
            contactList.add(contact);
            next = new NodeForNameSearch[256];
        }
        
        public ArrayList<String> getEmailList(){
            return this.emailList;
        } 
        
        public boolean addEntry(String email,String contact){
            if(!validContact()){
                this.emailList = new ArrayList<>();
                this.contactList = new ArrayList<>();
            }
            if(email==null || email.length()==0 || contact.length()<10 || this.emailList.contains(email))
                return false;
            this.contactList.add(contact);
            this.emailList.add(email);
            return true;
        }
        
        public ArrayList<String> getContactList(){
            return this.contactList;
        }
        
        public boolean modifyEntry(String email, String contact){
            if(contact==null || contact.length()<10 || !this.validContact())
                return false;
            if(contactList.size()==emailList.size()){
                for(int i=0;i<this.emailList.size();i++){
                     if(this.emailList.get(i).equalsIgnoreCase(email)){
                         this.contactList.set(i, contact);
                         return true;
                     }
                }
            }
            return false;
        }
        
        public boolean deleteEntry(String email){
            if(!this.validContact())
                return false;
            System.out.println("in here");
            if(this.contactList.size()==this.emailList.size()){
                for(int i=0;i<this.emailList.size();i++){
                     if(this.emailList.get(i).equalsIgnoreCase(email)){
                         this.emailList.remove(i);
                         this.contactList.remove(i);
                         return true;
                     }
                }
            }
            if(this.contactList.size()==0){
                this.contactList=null;
                this.emailList=null;
            }
            return false;
        }
        
        public boolean validContact(){
            return this.contactList!=null && this.emailList!=null;
        }
    }
