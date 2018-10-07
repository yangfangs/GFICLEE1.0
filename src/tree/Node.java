package tree;

import java.util.*;


public class Node {
    Node leftChild;
    Node rightChild;
    private int data;

    private static List<Node> nodeList = null;


    public Node(int data) {
        this.leftChild = null;
        this.rightChild = null;
        this.data = data;
    }

    public Node() {

    }


    /**
     * @param idx the index of node
     * @return the node by index
     */
    public Node getNode(int idx) {
        return nodeList.get(idx);
    }


    /**
     * @return the node data
     */
    public int getData() {
        return this.data;
    }


    /**
     * @param array the int[] array for create bin tree
     * @return all node list
     */
    public List<Node> createBinTree(int[] array) {
        nodeList = new LinkedList<Node>();
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
            nodeList.add(new Node(array[nodeIndex]));
        }
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
            nodeList.get(parentIndex).leftChild = nodeList
                    .get(parentIndex * 2 + 1);
            nodeList.get(parentIndex).rightChild = nodeList
                    .get(parentIndex * 2 + 2);
        }
        int lastParentIndex = array.length / 2 - 1;
        nodeList.get(lastParentIndex).leftChild = nodeList
                .get(lastParentIndex * 2 + 1);
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList
                    .get(lastParentIndex * 2 + 2);
        }
        return nodeList;
    }

    /**
     * @param node get a root for pre order traverse
     */
    public static void preOrderTraverse(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * @param root    the root of tree
     * @param decNode the dec node
     * @param array   node list
     * @return the node list from  dec node to root
     */
    public boolean getPath(Node root, Node decNode, List<Node> array) {
        boolean findResult = false;
        if (null != root) {
            if (root == decNode) {

                array.add(root);
                findResult = true;
            }
            if (!findResult && root.leftChild != null) {
                findResult = this.getPath(root.leftChild, decNode, array);

                if (findResult) {
                    array.add(root);
                }
            }
            if (!findResult && root.rightChild != null) {
                findResult = this.getPath(root.rightChild, decNode, array);
                if (findResult) {
                    array.add(root);

                }
            }
        }

        return findResult;
    }


    /**
     * @param root the root of the tree
     * @param a    one node
     * @param b    another node
     * @return the common node of a and b;
     */
    public static Node getCommonRoot(Node root, Node a, Node b) {
        Node common = null;
        List<Node> pathA = new ArrayList<Node>();
        List<Node> pathB = new ArrayList<Node>();
        a.getPath(root, a, pathA);
        b.getPath(root, b, pathB);

        for (Node NodeA : pathA) {
            for (Node NodeB : pathB) {
                if (NodeA == NodeB) {
                    common = NodeA;
                    break;
                }
            }
            if (null != common) {
                break;
            }
        }
        return common;
    }

    /**
     * @param root the root of tree
     * @param AllNode all leaf node in this tree
     * @return the low common ancestor of all leaf node
     */
    public Node getCommonAncestor(Node root, List<Node> AllNode){
        List<List<Node>> allPath = new ArrayList<>();
        List<Integer> allData = new ArrayList<>();

//        get all path
        for (Node x: AllNode){
            List<Node> path;
            path = new ArrayList<>();
            root.getPath(root,x,path);
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
//        get tree id

        for (Node y: theIntersection){
            // remove root data

                allData.add(y.data);

        }


//        if the path not only the root


        if (allData.size() > 1) {
            allData.removeIf(x -> x.equals(root.data));

        }

//        System.out.println(allData.toString());

        if (allData.size() == 1){
            return getNode(allData.get(0));
        }else {
            return getNode(Collections.max(allData));
        }

    }

    public static List<Node> getParent(Node root, Node desc) {

        List<Node> path = new ArrayList<Node>();
        desc.getPath(root, desc, path);

        //parent = path.get(path.size()-1);
        return path;

    }

    /**
     * @param root the root of the tree
     * @param desc the node
     * @return the parent node
     */
    public static Node getParentLate(Node root, Node desc) {
        if (desc == root)
            return root;
        List<Node> path = new ArrayList<Node>();
        desc.getPath(root, desc, path);
        Node late;

        late = path.get(1);

        return late;

    }


    /**
     * @param node the node
     * @return the children of the node
     */
    public static List getChildren(Node node) {
        List<Node> tem = new ArrayList<>();
        if (node.leftChild != null)
            tem.add(node.leftChild);
        if (node.rightChild != null)
            tem.add(node.rightChild);
        if (tem.size() != 0) {
            return tem;
        } else {
            return null;
        }
    }


    public static List<Node> getLeaf(Node root) {
        List<Node> result = new LinkedList<Node>();
        Queue<Node> nodeQueue = new LinkedList<Node>();
        Node node = null;
        nodeQueue.add(root);
        while (!nodeQueue.isEmpty()) {
            node = nodeQueue.poll();
            if (node.leftChild == null) {
                if (node.rightChild == null) {
                    result.add(node);
                } else {
                    nodeQueue.add(node.rightChild);
                }
            } else {
                nodeQueue.add(node.leftChild);
                if (node.leftChild != null) {
                    nodeQueue.add(node.rightChild);
                }
            }
        }

        return result;
    }


    public static List<Node> getLongList(List<Node> list1, List<Node> list2) {
        if (list1.size() > list2.size()) {
            return list1;
        } else {
            return list2;
        }
    }

    public static int getMinRetain(List<Node> list1, List<Node> list2) {

        list1.retainAll(list2);
        int min = list1.size();
        return min;
    }


}
