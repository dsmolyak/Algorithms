import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by dsmolyak on 6/26/14.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    Item[] items;
    int size;
    int position;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
        position = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (position == items.length) {
            expand();
        }
        items[position] = item;
        size++;
        position++;
    }

    private void expand() {
        Item[] temp = (Item[]) new Object[size*2];
        int newPos = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                temp[newPos] = items[i];
                newPos++;
            }
        }
        items = temp;
        position = size;

    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomPos = getRandomPosition();
        Item temp = items[randomPos];
        items[randomPos] = null;
        if (size <= items.length/4) {
            shrink();
        }
        size--;
        return temp;

    }

    private void shrink() {
        Item[] temp = (Item[]) new Object[size/2];
        int newPos = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                temp[newPos++] = items[i];
            }
        }
        items = temp;
        position = newPos;
    }

    // return (but do not delete) a random item
    public Item sample() {
        int randomPos = getRandomPosition();
        return items[randomPos];
    }

    private int getRandomPosition() {
        if (position == 1) {
            return 0;
        }
        int randomPos;
        do {
            randomPos = StdRandom.uniform(position);
        } while (items[randomPos] == null);
        return randomPos;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        Item[] iterables = (Item[]) new Object[size];
        int iterableSize = size;

        private int getRandomPosition() {
            int randomPos;
            do {
                randomPos = StdRandom.uniform(iterables.length);
            } while (iterables[randomPos] == null);
            return randomPos;
        }

        private RandomIterator() {
            int newPos = 0;
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    iterables[newPos++] = items[i];
                }
            }
        }

        @Override
        public boolean hasNext() {
            return (iterableSize > 0);
        }

        @Override
        public Item next() {
            if (iterableSize <= 0) {
                throw new NoSuchElementException();
            }
            int randomPos = getRandomPosition();
            Item temp = iterables[randomPos];
            iterables[randomPos] = null;
            iterableSize--;
            return temp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        assert deque.isEmpty();
        deque.enqueue("A");
        assert deque.size() == 1;
        deque.enqueue("B");
        deque.enqueue("C");
        for(String s: deque) {
            System.out.print(s + " ");
        }
        System.out.println();
        deque.dequeue();
        for(String s: deque) {
            System.out.print(s + " ");
        }
        System.out.println();
        deque.dequeue();
        for(String s: deque) {
            System.out.print(s + " ");
        }
        System.out.println();
        deque.dequeue();
        assert deque.size() == 0;
        System.out.println("blah");
    }
}