package com.learning.javacodeexercise.EarlyExercise;

public class BinarySearchTree {

    public Node Tree;

    public Node find(int data) {
        Node p = Tree;
        while (p != null && data != p.data) {
            if (p.data < data)
            {
                p = p.left;
            }
            else
            {
                p = p.right;
            }
        }
        return p;
    }

    public void insert(int data) {
        if (Tree == null) {
            Tree = new Node(data);
            return;
        }
        Node p = Tree;
        while (p != null) {
            if (p.data < data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            } else {
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }

    public void delete(int data) {
        Node p = Tree;
        Node pp = null;
        while (p != null && p.data != data) {
            pp = p;
            if (p.data < data) {
                p = p.right;
            }
            else {
                p = p.left;
            }
        }
        if (p == null)
        {
            return;
        }

        if (p.left != null && p.right != null) {
            Node minp = p.right;
            Node minpp = p;
            while (minp.left != null) {
                minpp = minp;
                minp = minp.left;
            }
            p.data = minp.data;
            p = minp;
            pp = minpp;
        }

        Node child = null;
        if (p.left != null) {
            child = p.left;
        } else if (p.right != null) {
            child = p.right;
        }

        if (pp == null) {
            Tree = child;
        } else if (pp.left == p) {
            pp.left = child;
        } else {
            pp.right = child;
        }
    }

    public Node prdecessor(Node node) {
        Node p = null;
        if (node.left != null) {
            p = findMax(node.left);
        } else {
            p = node.parent;
            while (p != null && node == p.left) {
                node = p;
                p = p.parent;
            }
        }
        return p;
    }

    public Node successor(Node node) {
        Node p = null;
        if (node.right != null) {
            p = findMin(node.right);
        } else {
            p = node.parent;
            while (p != null && node == p.right) {
                node = p;
                p = p.parent;
            }
        }
        return p;
    }

    public Node inorderPredecessor(Node root, Node p) {
        if (root == null || p == null) {
            return null;
        }
        if (root.data <= p.data) {
            Node right = inorderPredecessor(root.right, p);
            return right == null ? root : right;
        } else {
            Node left = inorderPredecessor(root.left, p);
            return left;
        }
    }

    public Node inorderSuccessor(Node root, Node p) {
        if (root == null || p == null) {
            return null;
        }
        if (root.data <= p.data) {
            Node right = inorderSuccessor(root.right, p);
            return right;
        } else {
            Node left = inorderSuccessor(root.left, p);
            return left == null ? root : left;
        }
    }

    public Node findMax(Node node) {
        if (node == null) {
            return null;
        }
        Node p = node;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    public Node findMin(Node node) {
        if (node == null) {
            return null;
        }
        Node p = node;
        while (p.left != null ) {
            p = p.left;
        }
        return p;
    }


    public static class Node {
        private int data;
        private Node left;
        private Node right;
        private Node parent;
        public Node (int data) {
            this.data = data;
        }
    }
}
