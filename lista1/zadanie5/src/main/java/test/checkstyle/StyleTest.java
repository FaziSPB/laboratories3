package test.checkstyle;

public class StyleTest {

    private static final int max_count = 3;
    private int Counter;

    public static void main(String args[]) {
        StyleTest t = new StyleTest();
        for(int i=0;i<max_count;i++){
            t.Add_Value(i);
        }
        long Total = 10l;
        if (Total > 5)
            System.out.println("Total: " + Total);
        System.out.println( "Counter: " + t.Counter );
    }

    public void Add_Value(int Value)
    {
        Counter += Value; Counter++;
    }
}
