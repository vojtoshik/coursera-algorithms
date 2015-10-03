import edu.princeton.cs.algs4.StdIn;

/**
 * Write a client program Subset.java that takes a command-line integer k; reads in a sequence of N strings from
 * standard input using StdIn.readString(); and prints out exactly k of them, uniformly at random. Each item from
 * the sequence can be printed out at most once. You may assume that 0 ≤ k ≤ N, where N is the number of string on
 * standard input.
 *
 * The running time of Subset must be linear in the size of the input. You may use only a constant amount of memory plus
 * either one Deque or RandomizedQueue object of maximum size at most N, where N is the number of strings on standard
 * input. (For an extra challenge, use only one Deque or RandomizedQueue object of maximum size at most k.) It should
 * have the following API.
 */
public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please, provide number of items in subset as parameter!");
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
