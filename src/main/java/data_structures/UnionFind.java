package data_structures;
import java.util.stream.IntStream;
import java.util.Arrays;

//Union by Size

public class UnionFind {
    private int[] parent;
    private int[] setSize;

    public UnionFind(int n) {
        parent = IntStream.range(0, n).toArray(); // Each node is its own parent
        setSize = new int[n];
        Arrays.fill(setSize, 1); // Each set initially has size 1
    }

    // Find the root of the set containing x with path compression
    public int find(int x) {
        validate(x);
        return x == parent[x] ? x : (parent[x] = find(parent[x])); // Path compression
    }

    // Union by size
    public boolean union(int a, int b) {
        validate(a);
        validate(b);
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false; // Already in the same set
        }

        // Attach smaller tree under the larger tree
        if (setSize[rootB] > setSize[rootA]) {
            setSize[rootB] += setSize[rootA];
            parent[rootA] = rootB;
        } else {
            setSize[rootA] += setSize[rootB];
            parent[rootB] = rootA;
        }
        return true;
    }

    // Get the size of the set containing x
    public int getSize(int x) {
        return setSize[find(x)];
    }

    // Validation to ensure x is within bounds
    private void validate(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("Element out of bounds: " + x);
        }
    }

    @Override
    public String toString() {
        return "Parents: " + Arrays.toString(parent) + "\nSizes: " + Arrays.toString(setSize);
    }
}
