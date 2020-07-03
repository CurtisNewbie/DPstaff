// https://blog.csdn.net/iva_brother/article/details/84037050
public class Fib {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.printf("Fib(%d) = %d\n", i, fib(i));
        }
    }

    public static int fib(int n) {
        // bottom-up
        // fib(n) = fib(n-1) + fib(n-2)
        // calculate from fib(0) -> fib(n), such that before we discover fib(n), we have
        // discovered { fib(x) | 0 <= x <= n - 1 } without undertaking repetitive
        // operations.
        if (n <= 1)
            return n;

        int prev1 = 0;
        int prev2 = 1;
        int curr = 1;
        for (int i = 2; i <= n; i++) {
            curr = prev1 + prev2;
            prev1 = prev2;
            prev2 = curr;
        }
        return curr;
    }
}