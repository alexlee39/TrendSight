import java.util.*;

public class test{
    public int smallcar;
    public test(){
        this.smallcar = 5;
    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
        test Alex = new test();
        Alex.Name();
    }
    public void Name(){
        Random dumbshit = new Random();
        for (int i = 0; i < this.smallcar; ++i) {
            int choice = dumbshit.nextInt(2);
            System.out.println("dumbshit random choice: "+ choice);
            if (choice == 0){
                System.out.print("Alex is a dumb ass for making me learn Java!\n");
            }
            else if (choice == 1){
                System.out.print("Shut the fuck up and learn Java!\n");

            };
        };
    }
}


