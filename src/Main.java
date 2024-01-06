package src;

public class Main {
    public static void main(String[] args) {
        MyFrame myframe = new MyFrame();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> myframe.close()));
    }
}
