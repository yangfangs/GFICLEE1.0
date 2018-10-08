package tree;

import java.util.*;


public class ParseNewickTree {

    private static int node_uuid = 0;
    private static int node_uidx = 0;
    private ArrayList<Node> nodeList = new ArrayList<>();
    private Node root;

    public static ParseNewickTree readNewickFormat(String newick) {
        return new ParseNewickTree().innerReadNewickFormat(newick);
    }


    public ArrayList<Node> getNodeList() {
        return nodeList;
    }



    public Node getNodebyLeafName(String leafname){

        Node res = null;
        for (Node node: nodeList){
//            System.out.println(node.getName());
            if (node.getName().equals(leafname)){
//                System.out.println(node);
                res =  node;

            }
        }

        return res;
    }


    public static String[] split(String s) {

        ArrayList<Integer> splitIndices = new ArrayList<>();

        int rightParenCount = 0;
        int leftParenCount = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    leftParenCount++;
                    break;
                case ')':
                    rightParenCount++;
                    break;
                case ',':
                    if (leftParenCount == rightParenCount) splitIndices.add(i);
                    break;
            }
        }

        int numSplits = splitIndices.size() + 1;
        String[] splits = new String[numSplits];

        if (numSplits == 1) {
            splits[0] = s;
        } else {

            splits[0] = s.substring(0, splitIndices.get(0));

            for (int i = 1; i < splitIndices.size(); i++) {
                splits[i] = s.substring(splitIndices.get(i - 1) + 1, splitIndices.get(i));
            }

            splits[numSplits - 1] = s.substring(splitIndices.get(splitIndices.size() - 1) + 1);
        }

        return splits;
    }



    private ParseNewickTree innerReadNewickFormat(String newick) {

        // single branch = subtree (?)
        this.root = readSubtree(newick.substring(0, newick.length() - 1));

        return this;
    }

    private Node readSubtree(String s) {

        int leftParen = s.indexOf('(');
        int rightParen = s.lastIndexOf(')');

        if (leftParen != -1 && rightParen != -1) {

            String name = s.substring(rightParen + 1);
            String[] childrenString = split(s.substring(leftParen + 1, rightParen));
            Node node = new Node(name);
            node.children = new ArrayList<>();
            for (String sub : childrenString) {
                Node child = readSubtree(sub);

                node.children.add(child);
                child.parent = node;
            }

            nodeList.add(node);
            return node;
        } else if (leftParen == rightParen) {

            Node node = new Node(s);
            nodeList.add(node);
//            System.out.println(nodeList.toString());
            return node;

        } else throw new RuntimeException("unbalanced ()'s");
    }

    public class Node {
        final String name;
        final double weight;
        boolean realName;
        ArrayList<Node> children;
        Node parent;
        public int idx;



        Node(String name) {
            this.idx = node_uidx;
            node_uidx ++;

            int colonIndex = name.indexOf(':');
            String actualNameText;
            if (colonIndex == -1) {
                actualNameText = name;
                weight = 0;
            } else {
                actualNameText = name.substring(0, colonIndex);
                weight = Float.parseFloat(name.substring(colonIndex + 1));
            }


            if (actualNameText.equals("")) {
                this.realName = true;
                this.name = Integer.toString(node_uuid);
                node_uuid++;
//                System.out.println(node_uuid);
            } else {
                this.realName = true;
                this.name = actualNameText;
            }
        }

        public double getWeight() {
            return weight;
        }

        public int getIdx() {
            return idx;
        }

        public Node getParent() {
            return parent;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }


        public String getName() {
            if (realName)
                return name;
            else
                return "";
        }

        public Set<Node> getCommonAncestor(Set<Node> allNode){

            Set<ParseNewickTree.Node> listSet = new HashSet<>();

         for(Node line:allNode){
             Node temp = line.getParent();
             if(listSet.size() !=1){
                 listSet.add(temp);
             }
         }
         if(listSet.size()==1){
             return listSet;
         }
        return getCommonAncestor(listSet);
        }



        public boolean getLeafNames(Node root, List<ParseNewickTree.Node> array){
            boolean tem = false;

            if (root != null){
                if(root.children == null){
                    array.add(root);
                    tem = true;
                }


                if(!tem && root.children != null){


                    this.getLeafNames(root.children.get(0),array);


                    this.getLeafNames(root.children.get(1),array);

                }



            }
            return tem;
        }





//        @Override
//        public int hashCode() {
//            return name.hashCode();
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (!(o instanceof Node)) return false;
//            Node other = (Node) o;
//            return this.name.equals(other.name);
//        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (children != null && children.size() > 0) {
                sb.append("(");
                for (int i = 0; i < children.size() - 1; i++) {
                    sb.append(children.get(i).toString());
                    sb.append(",");
                }
                sb.append(children.get(children.size() - 1).toString());
                sb.append(")");
            }
            if (name != null) sb.append(this.getName());
            return sb.toString();
        }


    }




    @Override
    public String toString() {
        return root.toString() + ";";
    }

}