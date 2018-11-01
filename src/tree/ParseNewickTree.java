package tree;

import java.util.*;

import static utils.Utils.isDouble;


public class ParseNewickTree {

    private static int node_uuid = 0;
    private static int node_uidx = 0;
    private ArrayList<Node> nodeList = new ArrayList<>();
    private ArrayList<Node> leafNodeList = new ArrayList<>();
    private ArrayList<String> realName = new ArrayList<>();
    private Node root;

    public static ParseNewickTree readNewickFormat(String newick) {
        return new ParseNewickTree().innerReadNewickFormat(newick);
    }


    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public Node getRoot() {
        return root;
    }

    public Node getNodeByLeafName(String leafName) {
        Node res = null;
        int length = leafNodeList.size();
        for (int i = 0; i < length; i++) {
            if(realName.get(i).equals(leafName))
                res = leafNodeList.get(i);
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
            if (node.realName) {
                realName.add(node.getName());
                leafNodeList.add(node);
            }

            return node;
        } else if (leftParen == rightParen) {

            Node node = new Node(s);
            nodeList.add(node);
            if (node.realName) {
                realName.add(node.getName());
                leafNodeList.add(node);
            }
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
        private int status = 0;


        Node(String name) {
            this.idx = node_uidx;
            node_uidx++;

            int colonIndex = name.indexOf(':');
            String actualNameText;
            if (colonIndex == -1) {
                actualNameText = name;
                weight = 0;
            } else {
                actualNameText = name.substring(0, colonIndex);
                weight = Float.parseFloat(name.substring(colonIndex + 1));
            }
//            if the names are not string
            boolean isNum = isDouble(actualNameText);

            if (actualNameText.equals("") || isNum) {
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


        public boolean getPath(Node node, List<Node> array) {
            if (node.parent != null) {
                array.add(node);
            }
            if (node == root) {
                return true;
            }
            return getPath(node.parent, array);
        }

        public int high(Node node) {
            int len = 0;
            for (; node != null; node = node.parent) {
                len++;
            }
            return len;
        }


        public Node getCommonAncestor(List<Node> AllNode) {

            List<List<Node>> allPath = new ArrayList<>();
            List<Node> allData = new ArrayList<>();
            List<Integer> allHigh = new ArrayList<>();

//        get all path
            for (Node x : AllNode) {
                List<Node> path;
                path = new ArrayList<>();
                getPath(x, path);
                allPath.add(path);
            }


//        System.out.println(allPath.toString());

//      get all list intersection
            List<Node> theIntersection = allPath.stream()
                    .reduce((List<Node> x1, List<Node> y1) -> {
                        x1.retainAll(y1);
                        return x1;
                    })
                    .orElse(Collections.emptyList());


//        get tree high


            for (Node y : theIntersection) {
                // remove root data

                allHigh.add(high(y));

            }
            if (allHigh.size() == 0) {
                return root;
            }
            Node resNode = theIntersection.get(allHigh.indexOf(Collections.max(allHigh)));

            return resNode;

//        if the path not only the root


//            if (allData.size() > 1) {
//                allData.removeIf(x -> x.equals(root);
//
//            }

//        System.out.println(allData.toString());

//            if (allData.size() == 1){
//                return getNode(allData.get(0));
//            }else {
//                return getNode(Collections.max(allData));
//            }


        }


        public boolean getLeafNames(Node root, List<ParseNewickTree.Node> array) {
            boolean tem = false;

            if (root != null) {
                if (root.children == null) {
                    array.add(root);
                    tem = true;
                }


                if (!tem && root.children != null) {


                    this.getLeafNames(root.children.get(0), array);


                    this.getLeafNames(root.children.get(1), array);

                }


            }
            return tem;
        }


        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node)) return false;
            Node other = (Node) o;
            return this.name.equals(other.name);
        }

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


        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }


    @Override
    public String toString() {
        return root.toString() + ";";
    }

}