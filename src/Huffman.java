import java.io.*;
import java.util.*;
import javafx.util.Pair;


public class Huffman {

    // Class data memebers
    private String txt;
    private HashMap<Character, Integer> charFreq = new HashMap<>();
    protected HashMap<Character, String> encodingMap = new HashMap<>();
    private PriorityQueue<Pair<Integer, Node>> pqFreq = new PriorityQueue<Pair<Integer, Node>>((a, b) -> a.getKey() - b.getKey());
    protected Trie prefixTree;

    public Huffman() { // Class constructor
        this.txt = "";
    }

    protected void readFile(String filename) { // Read lines of text from text file into a string
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String txtLine = br.readLine();
            while (txtLine != null) {
                this.txt += txtLine;
                br.readLine();
            }
            br.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void getFreq() { // Calculate the frequency of each character
        int len = this.txt.length();
        for (int i = 0; i < len; i++) {
            if (this.charFreq.containsKey(this.txt.charAt(i))) { // If character has already been seen and hashed
                this.charFreq.put(this.txt.charAt(i), 1);
                continue;
            }
            this.charFreq.put(this.txt.charAt(i), this.charFreq.get(this.txt.charAt(i)) + 1); // If character has not been seen and hashed
        }
    }

    protected void buildTrie() { // Construct prefix tree
        Iterator it = this.charFreq.entrySet().iterator();
        while (it.hasNext()) { // Form pairs with each character and its frequency and store them into a priority queue
            Map.Entry entry = (Map.Entry)it.next();
            Node n = new Node((char)entry.getKey());
            Pair<Integer, Node> p = new Pair<Integer, Node>((Integer)entry.getValue(), n);
            this.pqFreq.add(p);
        }
        while (pqFreq.size() > 1) { // Greedy algorithm: pop the characters with the lowest frequencies so they are deeper in the trie
            int freq = (int)this.pqFreq.peek().getKey();
            Node n1 = this.pqFreq.poll().getValue();
            freq += (int)this.pqFreq.peek().getKey();
            Node n2 = this.pqFreq.poll().getValue();
            Node newNode = new Node(n1, n2);
            Pair<Integer, Node> newPair = new Pair<Integer, Node>((Integer)freq, newNode);
            this.pqFreq.add(newPair);
        }
        Trie t = new Trie(pqFreq.poll().getValue());
        this.buildTrieRecur(t.root, "");
    }

    private void buildTrieRecur(Node curr, String encoding) { // Traverse through the prefix tree and copy the encoding corresponding to each character
        if (curr == null) {
            return;
        }
        if (curr.c != null) {
            this.encodingMap.put(curr.c, encoding);
        }
        encoding += "0";
        buildTrieRecur(curr.left, encoding);
        encoding += "1";
        buildTrieRecur(curr.right, encoding);
    }
    
}