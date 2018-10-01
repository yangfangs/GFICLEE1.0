package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Node {
    Node leftChild;
    Node rightChild;
    public int data;

    private static List<Node> nodeList = null;


    public Node(int data) {
        this.leftChild = null;
        this.rightChild = null;
        this.data = data;
    }

    public Node() {

    }



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

    public static void preOrderTraverse(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

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

    public static Node getCommonRoot(Node root, Node a, Node b) {
        Node common = null;
        List<Node> pathA = new ArrayList<Node>();
        List<Node> pahtB = new ArrayList<Node>();
        a.getPath(root, a, pathA);
        b.getPath(root, b, pahtB);

        for (Node NodeA : pathA) {
            for (Node NodeB : pahtB) {
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


    public static List<Node> getParent(Node root, Node desc) {

        List<Node> path = new ArrayList<Node>();
        desc.getPath(root, desc, path);

        //parent = path.get(path.size()-1);
        return path;

    }

    public static Node getParentLate(Node root, Node desc) {
        if (desc == root)
            return root;
        List<Node> path = new ArrayList<Node>();
        desc.getPath(root, desc, path);
        Node late;

        late = path.get(1);

        return late;

    }

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
