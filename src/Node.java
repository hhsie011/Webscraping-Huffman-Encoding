public class Node {
    
    // Class data members
    public Character c;
    public Node left;
    public Node right;

    public Node() { // Class default constructor
        this.c = null;
        this.left = null;
        this.right = null;
    }

    public Node(char input) { // Class parametrized constructor with only char passed in
        this.c = input;
        this.left = null;
        this.right = null;
    }

    public Node(Node l, Node r) { // Class parametrized constructor with only children passed in
        this.c = null;
        this.left = l;
        this.right = r;
    }

    public Node(char input, Node l, Node r) { // Class parametrized constructor
        this.c = input;
        this.left = l;
        this.right = r;
    }

}
