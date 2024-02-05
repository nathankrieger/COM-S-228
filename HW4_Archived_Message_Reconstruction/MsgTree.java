package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * This class creates and decodes a MsgTree.
 *
 * @author Nathan Krieger
 */
public class MsgTree {

    /**
     * The current payloadChar.
     */
    public char payloadChar;

    /**
     * The left node of the MsgTree.
     */
    public MsgTree left;

    /**
     * The right node of the MsgTree.
     */
    public MsgTree right;

    /*Can use a static char idx to the tree string for recursive
    solution, but it is not strictly necessary*/
    private static int staticCharIdx = 0;

    /**
     * Constructor for building the tree from a string.
     *
     * @param encodingString
     */
    public MsgTree(String encodingString) {

        // Check if the encodingString is valid
        if (encodingString == null || encodingString.length() < 2) {

            // Exit if the string is null or too short
            return;
        }

        boolean isLastStackOpperationIn = true;
        Stack<MsgTree> stack = new Stack<>();
        int index = 1;

        payloadChar = encodingString.charAt(0);

        stack.push(this);
        MsgTree currentTree = this;

        while (index < encodingString.length()) {

            char currentChar = encodingString.charAt(index);
            index++;
            MsgTree node = new MsgTree(currentChar);

            if (isLastStackOpperationIn) {
                currentTree.left = node;

            } else {
                currentTree.right = node;

            }
            if (node.payloadChar == '^') {
                // If it's '^', push it to the stack and update currentTree
                currentTree = stack.push(node);
                isLastStackOpperationIn = true;
            } else {
                // If the stack is not empty, update currentTree
                if (!stack.empty()) {
                    currentTree = stack.pop();
                }
                isLastStackOpperationIn = false;
            }

        }

    }

    /**
     * Constructor for a single node with null children.
     *
     * @param payloadChar
     */
    public MsgTree(char payloadChar) {

        this.payloadChar = payloadChar;
        left = null;
        right = null;
    }


    /**
     * Method to print characters and their binary codes.
     *
     * @param root
     * @param code
     */
    public static void printCodes(MsgTree root, String code) {

        if (root.left != null && root.right != null) {
            printCodes(root.left, code + "0");
            printCodes(root.right, code + "1");
        } else {
            char payloadChar = root.payloadChar;
            String output = String.valueOf(payloadChar);

            if (payloadChar == ' ') {
                output = "SPACE";
            } else if (payloadChar == '\n') {
                output = "NEWLINE";
            } else if (payloadChar == '\t') {
                output = "TAB";
            }

            // Formatting the output
            String tabs = "\t\t\t";
            if (output.length() > 1) {
                tabs = "\t\t";
            }

            System.out.println(output + tabs + code);
        }

    }


    /**
     * Decodes string from source.
     *
     * @param codes
     * @param msg
     */
    public void decode(MsgTree codes, String msg) {

        MsgTree currentNode = codes;
        StringBuilder decodedMessage = new StringBuilder();

        for (char c : msg.toCharArray()) {
            while (currentNode.left != null && currentNode.right != null) {
                currentNode = (c == '0') ? currentNode.left : currentNode.right;
                break;
            }
            if (currentNode.left == null && currentNode.right == null) {
                decodedMessage.append(currentNode.payloadChar);
                currentNode = codes; // Reset to the root for the next character
            }
        }

        System.out.print(decodedMessage);

    }

}
