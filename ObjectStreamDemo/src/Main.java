import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Person person=new Person("Rahul",21,"rahul@email.com","12356");
//        try {
//            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("person1"));
//            oos.writeObject(person);
//        System.out.println("Person object has been serialized!");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
  Person person=null;
        try(FileInputStream fileInputStream=new FileInputStream("person1");
            ObjectInputStream ois=new ObjectInputStream(fileInputStream)
        ){ person=(Person) ois.readObject();
            System.out.println("User object has been deserialized!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Deserialized User Details:");
        System.out.println("Username: " + person.getName());
        System.out.println("Password: " + person.getPassword());


    }
}

class Person implements Serializable {
        String name;
        int age;
        String email;
        String password;

    public Person(String name, int age, String email, String password){
            this.name = name;
            this.age = age;
            this.email = email;
            this.password = password;
        }

        public String getName () {
            return name;
        }

        public void setName (String name){
            this.name = name;
        }

        public int getAge () {
            return age;
        }

        public void setAge ( int age){
            this.age = age;
        }

        public String getEmail () {
            return email;
        }

        public void setEmail (String email){
            this.email = email;
        }

        public String getPassword () {
            return password;
        }

        public void setPassword (String password){
            this.password = password;
        }

        @Override
        public String toString () {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
