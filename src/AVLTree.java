import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AVLTree {
    Node root;

    class Node{
        Node left;
        Node right;
        int val;
        int height;


        Node(int i){
            val = i;
            height = 1;
        }

    }

    Node rightRotate(Node n){
        Node newRoot = n.left;
        Node temp = newRoot.right;

        newRoot.right = n;
        n.left = temp;

        n.height = Math.max((n.left!=null)?n.left.height:0, (n.right != null)? n.right.height:0) + 1;
        newRoot.height = Math.max((newRoot.left!= null)?newRoot.left.height:0, newRoot.right.height ) + 1;


        return newRoot;
    }

    Node leftRotate(Node n){
        Node newRoot = n.right;
        Node temp = newRoot.left;

        newRoot.left = n;
        n.right = temp;

        n.height = Math.max((n.left!=null)?n.left.height:0, (n.right != null)? n.right.height:0) + 1;
        newRoot.height = Math.max(newRoot.left.height, (newRoot.right!= null)?newRoot.right.height:0 ) + 1;

        return newRoot;
    }

    void serialize(Node n){
        int height = n.height;
        StringBuilder sb = new StringBuilder();
        Map.Entry<Integer, Node> e = new AbstractMap.SimpleEntry<>(1,n);
        Queue<Map.Entry<Integer, Node>> q = new LinkedList<>();
        q.add(e);
        while(!q.isEmpty()){
            Map.Entry<Integer, Node> entry = q.poll();
            int curHeight = entry.getKey();
            Node curNode = entry.getValue();

            if(curNode != null){
                sb.append(curNode.val+",");
            }else if(curHeight <= height)
                sb.append("null,");

            if(curHeight+1 <= height) {
                Map.Entry<Integer, Node> leftEntry = new AbstractMap.SimpleEntry<>(curHeight + 1, curNode.left);
                q.add(leftEntry);
                Map.Entry<Integer, Node> rightEntry = new AbstractMap.SimpleEntry<>(curHeight + 1, curNode.right);
                q.add(rightEntry);
            }

        }
        System.out.println(sb.toString());
    }


    Node insert(int key){
        return insertHelper(root, key);
    }

    int getBalance(Node n){
        if(n == null) return 0;

        return ((n.left != null)?n.left.height:0) - ((n.right != null)? n.right.height:0);
    }


    Node insertHelper(Node r, int key){
        if(r == null) return new Node(key);

        if(key< r.val){
            r.left = insertHelper(r.left, key);
        }else if(key > r.val){
            r.right = insertHelper(r.right, key);
        }else{
            return r;
        }

        // Note about the null check here
        r.height = Math.max((r.left!= null)? r.left.height:0, (r.right != null)? r.right.height:0) + 1;

        int balFactor = getBalance(r);
        if(balFactor > 1 && key > r.left.val){
            return rightRotate(r);
        }

        if(balFactor > 1 && key < r.left.val){
            r.left = leftRotate(r.left);
            return rightRotate(r);
        }

        if(balFactor < -1 && key > r.right.val){
            return leftRotate(r);
        }

        if(balFactor < -1 && key < r.right.val){
            r.right = rightRotate(r.right);
            return leftRotate(r);
        }
        return r;
    }

    public static void main(String[] args) {
        AVLTree avl = new AVLTree();

        /* Constructing tree given in the above figure */
        avl.root = avl.insert(10);
        //if(avl.root == null) System.out.println("check null");
        avl.root = avl.insert(20);
        avl.root = avl.insert(30);
        avl.root = avl.insert(40);
        avl.root = avl.insert(50);
        avl.root = avl.insert(25);

        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        avl.serialize(avl.root);
    }
}
