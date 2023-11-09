package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 *
 * @author Nathan Krieger
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {

    /**
     * Default number of elements that may be stored in each node.
     */
    private static final int DEFAULT_NODESIZE = 4;

    /**
     * Number of elements that can be stored in each node.
     */
    private final int nodeSize;

    /**
     * Dummy node for head.  It should be private but set to public here only
     * for grading purpose.  In practice, you should always make the head of a
     * linked list a private instance variable.
     */
    public Node head;

    /**
     * Dummy node for tail.
     */
    private Node tail;

    /**
     * Number of elements in the list.
     */
    private int size;

    /**
     * Constructs an empty list with the default node size.
     */
    public StoutList() {
        this(DEFAULT_NODESIZE);
    }

    /**
     * Constructs an empty list with the given node size.
     *
     * @param nodeSize number of elements that may be stored in each node, must be
     *                 an even number
     */
    public StoutList(int nodeSize) {

        if (nodeSize <= 0 || nodeSize % 2 != 0) {
            throw new IllegalArgumentException();
        }

        size = 0;
        // dummy nodes
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
        this.nodeSize = nodeSize;
    }

    /**
     * Constructor for grading only.  Fully implemented.
     *
     * @param head
     * @param tail
     * @param nodeSize
     * @param size
     */
    public StoutList(Node head, Node tail, int nodeSize, int size) {
        this.head = head;
        this.tail = tail;
        this.nodeSize = nodeSize;
        this.size = size;
    }

    /**
     *
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds an element ot the end of the list
     *
     * @param item element whose presence in this collection is to be ensured
     * @return
     */
    @Override
    public boolean add(E item) {

        if (item == null) throw new NullPointerException("Item cannot be null");

        if (size != 0) {
            if (tail.previous.count < nodeSize) {
                tail.previous.addItem(item);
            } else {
                Node newNode = new Node();
                newNode.previous = tail.previous;
                newNode.next = tail;
                newNode.previous.next = newNode;
                tail.previous = newNode;
                newNode.addItem(item);
            }
        } else {
            Node newNode = new Node();
            head.next = newNode;
            newNode.previous = head;
            newNode.next = tail;
            tail.previous = newNode;
            newNode.addItem(item);
        }

        size++;
        return true;
    }

    /**
     * Adds an element at position
     *
     * @param pos index at which the specified element is to be inserted
     * @param item element to be inserted
     */
    @Override
    public void add(int pos, E item) {

        if (pos < 0 || pos > size)
            throw new IndexOutOfBoundsException("Pos out of bounds.");
        if (item == null)
            throw new NullPointerException("Item is null.");

        if (head.next == tail) {
            add(item);
            return;
        }

        NodeInfo currentNodeInfo = find(pos);

        if (currentNodeInfo != null) {

            if (currentNodeInfo.offset == 0) {
                if (currentNodeInfo.node.previous.count != nodeSize && currentNodeInfo.node.previous != head) {
                    currentNodeInfo.node.previous.addItem(item);
                    size++;
                    return;
                } else if (currentNodeInfo.node == tail) {
                    add(item);
                    size++;
                    return;
                }
            }

            if (currentNodeInfo.node.count < nodeSize) {

                currentNodeInfo.node.addItem(currentNodeInfo.offset, item);
            } else {
                Node newNode = new Node();

                int count = 0;
                if (count < (nodeSize / 2)) {
                    do {
                        newNode.addItem(currentNodeInfo.node.data[nodeSize / 2]);
                        currentNodeInfo.node.removeItem(nodeSize / 2);
                        count++;
                    } while (count < nodeSize / 2);
                }

                Node nextNode = currentNodeInfo.node.next;
                currentNodeInfo.node.next = newNode;
                newNode.previous = currentNodeInfo.node;
                newNode.next = nextNode;
                nextNode.previous = newNode;

                if (currentNodeInfo.offset <= nodeSize / 2) {
                    currentNodeInfo.node.addItem(currentNodeInfo.offset, item);
                } else if (currentNodeInfo.offset > nodeSize / 2) {
                    newNode.addItem((currentNodeInfo.offset - nodeSize / 2), item);
                }
            }

            size++;

        } else {

            if (size == pos) {
                add(item);
            }
        }
    }

    /**
     * Removes an element at pos (position)
     *
     * @param pos the index of the element to be removed
     * @return element
     */
    @Override
    public E remove(int pos) {

        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException();
        }

        NodeInfo nodeInfo = find(pos);
        int offset = nodeInfo.offset;
        E nodeValue = nodeInfo.node.data[offset];
        if (nodeInfo.node.next != tail || nodeInfo.node.count != 1) {

            if (nodeInfo.node.next == tail || nodeInfo.node.count > nodeSize / 2) {
                nodeInfo.node.removeItem(offset);

            } else {
                nodeInfo.node.removeItem(offset);
                Node next = nodeInfo.node.next;

                if (next.count <= nodeSize / 2) {
                    if (next.count <= nodeSize / 2) {
                        for (E element : next.data) {
                            nodeInfo.node.addItem(element);
                        }
                        nodeInfo.node.next = next.next;
                        next.next.previous = nodeInfo.node;
                    }
                } else {
                    nodeInfo.node.addItem(next.data[0]);
                    next.removeItem(0);
                }
            }
        } else {
            Node previous = nodeInfo.node.previous;
            previous.next = nodeInfo.node.next;
            nodeInfo.node.next.previous = previous;
        }
        size--;
        return nodeValue;
    }

    /**
     * Sort all elements in the stout list in the NON-DECREASING order. You may do the following.
     * Traverse the list and copy its elements into an array, deleting every visited node along
     * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting
     * efficiency is not a concern for this project.)  Finally, copy all elements from the array
     * back to the stout list, creating new nodes for storage. After sorting, all nodes but
     * (possibly) the last one must be full of elements.
     * <p>
     * Comparator<E> must have been implemented for calling insertionSort().
     */
    public void sort() {

        // Insertion sort

        E[] newArray = (E[]) new Comparable[size];

        Node currentNode = head.next;

        if (currentNode == tail) {
            return;
        }

        ListIterator<E> listIterator = listIterator();

        int index = 0;

        while(listIterator.hasNext()) {
            newArray[index] = listIterator.next();
            index++;
        }

        head.next = tail;
        tail.previous = head;
        size = 0;

        insertionSort(newArray, new SortingComparator());

        for (E element : newArray) {
            add(element);
        }

    }

    /**
     * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
     * method.  After sorting, all but (possibly) the last nodes must be filled with elements.
     * <p>
     * Comparable<? super E> must be implemented for calling bubbleSort().
     */
    public void sortReverse() {

        // bubble sort
        E[] newArray = (E[]) new Comparable[size];

        Node n = head.next;

        if (n == tail) {
            return;
        }

        ListIterator<E> iter = listIterator();

        int count = 0;

        while(iter.hasNext()) {
            newArray[count] = iter.next();
            count++;
        }

        head.next = tail;
        tail.previous = head;
        size = 0;

        bubbleSort(newArray);

        for (E element : newArray) {
            add(element);
        }

    }

    /**
     *
     * @return StoutListIterator
     */
    @Override
    public Iterator<E> iterator() {
        return new StoutListIterator();
    }

    /**
     *
     * @return StoutListIterator
     */
    @Override
    public ListIterator<E> listIterator() {
        return new StoutListIterator();
    }

    /**
     *
     * @param index index of first element to be returned from the list
     *         iterator (by a call to the {@code next} method)
     * @return  StoutListIterator
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new StoutListIterator(index);
    }

    /**
     * Returns a string representation of this list showing
     * the internal structure of the nodes.
     */
    public String toStringInternal() {
        return toStringInternal(null);
    }

    /**
     * Returns a string representation of this list showing the internal
     * structure of the nodes and the position of the iterator.
     *
     * @param iter an iterator for this list
     */
    public String toStringInternal(ListIterator<E> iter) {
        int count = 0;
        int position = -1;
        if (iter != null) {
            position = iter.nextIndex();
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = head.next;
        while (current != tail) {
            sb.append('(');
            E data = current.data[0];
            if (data == null) {
                sb.append("-");
            } else {
                if (position == count) {
                    sb.append("| ");
                    position = -1;
                }
                sb.append(data.toString());
                ++count;
            }

            for (int i = 1; i < nodeSize; ++i) {
                sb.append(", ");
                data = current.data[i];
                if (data == null) {
                    sb.append("-");
                } else {
                    if (position == count) {
                        sb.append("| ");
                        position = -1;
                    }
                    sb.append(data.toString());
                    ++count;

                    // iterator at end
                    if (position == size && count == size) {
                        sb.append(" |");
                        position = -1;
                    }
                }
            }
            sb.append(')');
            current = current.next;
            if (current != tail) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Node type for this list.  Each node holds a maximum
     * of nodeSize elements in an array.  Empty slots
     * are null.
     */
    private class Node {

        /**
         * Array of actual data elements.
         */
        // Unchecked warning unavoidable.
        public E[] data = (E[]) new Comparable[nodeSize];

        /**
         * Link to next node.
         */
        public Node next;

        /**
         * Link to previous node;
         */
        public Node previous;

        /**
         * Index of the next available offset in this node, also
         * equal to the number of elements in this node.
         */
        public int count;

        /**
         * Adds an item to this node at the first available offset.
         * Precondition: count < nodeSize
         *
         * @param item element to be added
         */
        void addItem(E item) {
            if (count >= nodeSize) {
                return;
            }
            data[count++] = item;
            //useful for debugging
            //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
        }

        /**
         * Adds an item to this node at the indicated offset, shifting
         * elements to the right as necessary.
         * <p>
         * Precondition: count < nodeSize
         *
         * @param offset array index at which to put the new element
         * @param item   element to be added
         */
        void addItem(int offset, E item) {
            if (count >= nodeSize) {
                return;
            }
            for (int i = count - 1; i >= offset; --i) {
                data[i + 1] = data[i];
            }
            ++count;
            data[offset] = item;
            //useful for debugging
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
        }

        /**
         * Deletes an element from this node at the indicated offset,
         * shifting elements left as necessary.
         * Precondition: 0 <= offset < count
         *
         * @param offset
         */
        void removeItem(int offset) {
            E item = data[offset];
            for (int i = offset + 1; i < nodeSize; ++i) {
                data[i - 1] = data[i];
            }
            data[count - 1] = null;
            --count;
        }
    }

    /**
     * Helper class for the node offset
     */
    private class NodeInfo {

        public Node node;
        public int offset;

        public NodeInfo(Node node, int offset) {

            this.node = node;
            this.offset = offset;

        }

    }

    /**
     * A Helper method for finding elements
     *
     * @param pos
     * @return Element if one is found
     */
    private NodeInfo find(int pos) {

        Node currentNode = head.next;
        int currentPosition = 0;
        while (currentNode != tail) {

            if (currentPosition + currentNode.count <= pos) {
                currentPosition += currentNode.count;
                currentNode = currentNode.next;
                continue;
            }

            NodeInfo nodeInfo = new NodeInfo(currentNode, pos - currentPosition);

            return nodeInfo;
        }

        return null;
    }

    /**
     * Helper method to locate node info by position
     * @param pos the position
     * @return NodeInfo
     */
    private NodeInfo locateNodeInfoByPosition(int pos) {

        if (pos == size()) {
            return new NodeInfo(tail, 0);
        }

        Node currentNode = head.next;
        int nextNodeCount = currentNode.count;
        int previousOffset = 0;

        while ((pos >= nextNodeCount && currentNode.next != null)) {
            previousOffset += currentNode.count;
            currentNode = currentNode.next;
            nextNodeCount += currentNode.count;
        }
        return new NodeInfo(currentNode, pos - previousOffset);
    }

    /**
     * An iterator class made to iterate through nodes and arrays within nodes
     */
    private class StoutListIterator implements ListIterator<E> {

        /**
         * Stores the current node
         */
        private Node currentNode;

        /**
         * Stores the current position
         */
        private int currentPosition;

        /**
         * Stores a node that is meant to be removed
         */
        private Node currentNodeRemove;

        /**
         * Stores the last position
         */
        private int lastPosition;

        /**
         * Stores the current offset
         */
        private int currentOffset;

        /**
         * Stores the last offset
         */
        private int lastOffset;

        /**
         * Stores offset for removing a node
         */
        private int removableOffset;

        /**
         * Default constructor for StoutListIterator
         */
        public StoutListIterator() {
            currentNode = head.next;
            currentPosition = 0;

            currentOffset = 0;
            lastPosition = -1;
            lastOffset = -1;
        }

        /**
         * Constructs an iterator starting at a position pos
         *
         * @param pos
         */
        public StoutListIterator(int pos) {

            this.currentPosition = pos;
            this.lastPosition = pos - 1;

            NodeInfo locatedInfo = locateNodeInfoByPosition(currentPosition);

            this.currentOffset = locatedInfo.offset;
            this.currentNode = locatedInfo.node;
            if (this.currentOffset > 0) {
                lastOffset = currentOffset - 1;
            }
            if (this.currentOffset != 0) {
                return;
            }
            if (locatedInfo.node.previous != head) {
                this.lastOffset = locatedInfo.node.previous.count - 1;
            }
        }

        /**
         *
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            if (currentOffset < currentNode.count) {
                // Not at the end
                if (currentNode.data[currentOffset] != null) {
                    return true;
                }
            } else {
                if (currentNode.next != null || currentNode != tail) {
                    if (currentNode.next.count > 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Finds the next element
         * @return the next element
         */
        @Override
        public E next() {

            if (currentPosition > size) {
                throw new IllegalStateException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (currentNode.count <= currentOffset) {
                currentNode = currentNode.next;
                currentOffset = 0;
                lastPosition = currentPosition;
                currentPosition++;
                lastOffset = currentOffset;
                currentNodeRemove = currentNode;
                removableOffset = currentOffset;
                return currentNode.data[currentOffset++];
            }
            if (currentNode.count > currentOffset) {
                lastPosition = currentPosition;
                currentPosition++;
                lastOffset = currentOffset;
                currentNodeRemove = currentNode;
                removableOffset = currentOffset;
                return currentNode.data[currentOffset++];
            }
            return null;
        }

        /**
         * Removes at cursor
         */
        @Override
        public void remove() {

            if (lastPosition >= size) {
                throw new IndexOutOfBoundsException();
            }
            if (currentNodeRemove == null) {
                throw new IllegalStateException();
            }
            if (currentNodeRemove.count != 1) {
                if ((currentNodeRemove.next == tail && currentNodeRemove.previous == head) ||
                        (currentNodeRemove.count > (nodeSize / 2))) {
                    currentNodeRemove.removeItem(removableOffset);
                    size--;
                    if (removableOffset < currentOffset && currentOffset > 0) {
                        lastOffset--;
                        lastPosition--;

                        currentOffset--;
                        currentPosition--;
                    }
                    currentNodeRemove = null;
                    return;
                }
                if (currentNodeRemove.count <= (nodeSize / 2)) {
                    if (currentNodeRemove.next != null && currentNodeRemove.next != tail) {
                        if (currentNodeRemove.next.count > (nodeSize / 2)) {

                            currentNodeRemove.removeItem(removableOffset);
                            currentNodeRemove.addItem(currentNodeRemove.next.data[0]);
                            currentNodeRemove.next.removeItem(0);
                            size--;
                            if (removableOffset < currentOffset && currentOffset > 0) {
                                currentOffset--;
                                currentPosition--;
                                lastOffset--;
                                lastPosition--;
                            }
                            currentNodeRemove = null;
                            return;
                        }
                        if (currentNodeRemove.next.count <= (nodeSize / 2)) {
                            currentNodeRemove.removeItem(removableOffset);
                            size--;
                            while (currentNodeRemove.next.count > 0) {
                                currentNodeRemove.addItem(currentNodeRemove.next.data[0]);
                                currentNodeRemove.next.removeItem(0);
                            }
                            currentNodeRemove.next = currentNodeRemove.next.next;
                            currentNodeRemove.next = currentNodeRemove.next;
                            currentNodeRemove.next.previous = currentNodeRemove;
                            if (removableOffset < currentOffset && currentOffset > 0) {
                                currentOffset--;
                                currentPosition--;
                                lastOffset--;
                                lastPosition--;
                            }
                            currentNodeRemove = null;
                        }
                    } else {
                        currentNodeRemove.removeItem(removableOffset);
                        size--;
                        if (removableOffset < currentOffset && currentOffset > 0) {

                            lastOffset--;
                            lastPosition--;


                            currentOffset--;
                            currentPosition--;

                        }
                        currentNodeRemove = null;
                    }
                }
            } else {

                Node tempNode = currentNodeRemove.previous;
                currentNodeRemove = currentNodeRemove.next;
                tempNode.next = currentNodeRemove;
                currentNodeRemove.previous = tempNode;
                if (removableOffset <= currentOffset) {
                    currentOffset--;
                    lastOffset--;

                    currentPosition--;
                    lastPosition--;
                }
                size--;
                currentNodeRemove = null;
            }
        }

        /**
         *
         * @return true if there is a previous element, false otherwise
         */
        @Override
        public boolean hasPrevious() {

            if (lastOffset < 0) {
                return false;
            }
            if (lastOffset < currentNode.count && currentNode.data[lastOffset] != null) {
                return true;
            } else if (currentNode.previous != null || currentNode != tail) {
                Node temp = currentNode.previous;
                if (temp.count > 0) {
                    return true;
                }
            }

            return false;
        }

        /**
         * @return the next index of list
         */
        @Override
        public int nextIndex() {
            return currentPosition;
        }

        /**
         * Adds an item to the list.
         * @param item - the item to be added
         */
        public void add(E item) {
            if (item == null) {
                throw new NullPointerException();
            }
            if (currentPosition > size) {
                throw new IllegalArgumentException();
            }

            if (size == 0) {
                Node tempNode = new Node();
                head.next = tempNode;
                tempNode.previous = head;
                tempNode.next = tail;
                tail.previous = tempNode;
                tempNode.addItem(currentPosition, item);
                size++;
                this.lastPosition = this.currentPosition;
                this.lastOffset = this.currentOffset;
                this.currentPosition++;
                this.currentOffset++;
                currentNodeRemove = null;
                this.currentNode = tempNode;
                return;
            }

            NodeInfo nodeInfo = locateNodeInfoByPosition(currentPosition);

            if (nodeInfo.offset == 0) {
                if (nodeInfo.node.previous.count != nodeSize && nodeInfo.node.previous != head) {
                    nodeInfo.node = nodeInfo.node.previous;
                    nodeInfo.node.addItem(item);
                } else if (nodeInfo.node == tail && nodeInfo.node.previous.count == nodeSize) {
                    Node tempNode = new Node();
                    Node lastNode = nodeInfo.node.previous;
                    tempNode.previous = lastNode;
                    tempNode.next = nodeInfo.node;
                    nodeInfo.node.previous = tempNode;
                    lastNode.next = tempNode;
                    tempNode.addItem(item);
                }
            } else if (nodeInfo.node.count < nodeSize) {
                nodeInfo.node.addItem(nodeInfo.offset, item);
            } else if (nodeInfo.node.count >= nodeSize) {
                Node tempNode = new Node();
                Node tempNext = nodeInfo.node.next;
                tempNode.next = tempNext;
                nodeInfo.node.next = tempNode;
                tempNode.previous = nodeInfo.node;
                tempNext.previous = tempNode;

                while (tempNode.count != (nodeSize / 2)) {
                    tempNode.addItem(nodeInfo.node.data[nodeSize / 2]);
                    nodeInfo.node.removeItem(nodeSize / 2);
                }

                if (nodeInfo.offset <= (nodeSize / 2)) {
                    nodeInfo.node.addItem(nodeInfo.offset, item);
                    this.currentOffset = nodeInfo.offset + 1;
                    this.currentNode = nodeInfo.node;
                } else {
                    tempNode.addItem(nodeInfo.offset - (nodeSize / 2), item);
                    this.currentOffset = nodeInfo.offset - (nodeSize / 2) + 1;
                    this.currentNode = tempNode;
                }
                this.lastOffset = this.currentOffset - 1;
            }

            size++;
            this.lastPosition = currentPosition;
            this.currentPosition++;
            currentNodeRemove = null;
        }

        /**
         * @return the previous element if one exists
         */
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            else {

                if (currentNode.equals(tail) || currentOffset <= 0) {

                    currentNode = currentNode.previous;
                    currentOffset = currentNode.count;

                }

            }
            return currentNode.data[this.lastOffset--];
        }
        
        /**
         * @return the previous index
         */
        @Override
        public int previousIndex() {
            return lastPosition;
        }


        /**
         * Sets an element
         * @param item
         */
        @Override
        public void set(E item) {
            if (item == null) {
                throw new NullPointerException();
            }
            if (currentNodeRemove == null) {
                throw new IllegalStateException();
            }

            // Removes item and the replaces it
            currentNodeRemove.removeItem(removableOffset);
            currentNodeRemove.addItem(removableOffset, item);
        }

    }

    /**
     * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order.
     *
     * @param arr  array storing elements from the list
     * @param comp comparator used in sorting
     */
    private void insertionSort(E[] arr, Comparator<? super E> comp) {

        int n = arr.length;
        int i = 1;
        while (i < n) {
            E key = arr[i];
            int j = i - 1;

            // Move elements of arr[0..i-1], that are greater than key, to one position ahead of their current position
            while (j >= 0 && comp.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            ++i;
        }
    }

    /**
     * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
     * description of bubble sort please refer to Section 6.1 in the project description.
     * You must use the compareTo() method from an implementation of the Comparable
     * interface by the class E or ? super E.
     *
     * @param arr array holding elements from the list
     */
    private void bubbleSort(E[] arr) {

        int n = arr.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (arr[i].compareTo(arr[i + 1]) < 0) {

                    // Swap arr[i] and arr[i+1]
                    E temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    /**
     * Class used by sorting methods to compare elements
     * @param <E>
     */
    private class SortingComparator<E extends Comparable<E>> implements Comparator<E> {

        @Override
        public int compare(E a, E b) {
            return a.compareTo(b);
        }

    }

}