import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by dsmolyak on 6/26/14.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node();
        node.item = item;
        node.next = first;
        if (first == null) {
            first = node;
            last = node;
        }
        else  {
            first.previous = node;
            first = node;
        }
        size++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node();
        node.item = item;
        if (last == null) {
            first = node;
            last = node;
        }
        else {
            last.next = node;
            node.previous = last;
            last = node;
        }
        size++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result =  first.item;
        first = first.next;
        first.previous = null;
        size--;
        return result;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = last.item;
        Node node = last;
        last = node.previous;
        size--;
        return result;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {

        Item item;
        Node next;
        Node previous;

    }

    private class DequeIterator implements Iterator<Item> {

        Node nextNode = first;

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public Item next() {
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            Item result = nextNode.item;
            nextNode = nextNode.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        assert deque.isEmpty();
        deque.addFirst("A");
        assert deque.size() == 1;
        deque.addFirst("B");
        deque.addLast("C");
        for(String s: deque) {
            System.out.print(s + " ");
        }
        assert deque.removeFirst() == "B";
        assert deque.removeLast() == "C";
        assert deque.removeLast() == "A";
        assert deque.size() == 0;

    }

}