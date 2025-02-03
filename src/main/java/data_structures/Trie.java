package data_structures;
import java.util.HashMap;
import java.util.Map;
public class Trie {
    TrieNode root;
    class TrieNode {
        Map<Character,TrieNode> arcs;
        boolean isEnd = true;
        TrieNode(){
            this.arcs = new HashMap<>();
            this.isEnd = false;
        }
    }
    Trie(){
        root = new TrieNode();
    }
    Trie(String word){
        this();
        insert(word);
    }
    void insert(String word){
        TrieNode node = this.root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!node.arcs.containsKey(c)){
                node.arcs.put(c, new TrieNode());
            }
            node = node.arcs.get(c);
        }
        node.isEnd = true;
    }
    boolean search(String word){
        TrieNode node = this.root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!node.arcs.containsKey(c)){
                return false;
            }
            node = node.arcs.get(c);
        }
        return node.isEnd;
    }
}
