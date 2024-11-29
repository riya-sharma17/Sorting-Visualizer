import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortingVisualizer extends JPanel {

    private static final int SCREEN_WIDTH = 910;
    private static final int SCREEN_HEIGHT = 750;
    private static final int ARR_SIZE = 130;
    private static final int RECT_SIZE = 7;

    private int[] arr = new int[ARR_SIZE];
    private int[] baseArr = new int[ARR_SIZE];
    private boolean complete = false;

    public SortingVisualizer() {
        randomizeArray();
        loadArray();
    }

    // Generate random array
    private void randomizeArray() {
        Random rand = new Random();
        for (int i = 0; i < ARR_SIZE; i++) {
            baseArr[i] = rand.nextInt(SCREEN_HEIGHT);
        }
    }

    // Load array from base
    private void loadArray() {
        System.arraycopy(baseArr, 0, arr, 0, ARR_SIZE);
        complete = false;
    }

    // Visualization logic
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        for (int i = 0; i < ARR_SIZE; i++) {
            int height = arr[i];
            int x = i * RECT_SIZE;
            int y = SCREEN_HEIGHT - height;

            if (complete) {
                g.setColor(new Color(100, 180, 100));
            } else {
                g.setColor(new Color(170, 183, 184));
            }
            g.fillRect(x, y, RECT_SIZE, height);
        }
    }

    // Sorting Algorithms
    public void selectionSort() {
        for (int i = 0; i < ARR_SIZE - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < ARR_SIZE; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(i, minIndex);
            repaintWithDelay();
        }
        complete = true;
        repaint();
    }

    public void bubbleSort() {
        for (int i = 0; i < ARR_SIZE - 1; i++) {
            for (int j = 0; j < ARR_SIZE - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                    repaintWithDelay();
                }
            }
        }
        complete = true;
        repaint();
    }

    public void insertionSort() {
        for (int i = 1; i < ARR_SIZE; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                repaintWithDelay();
            }
            arr[j + 1] = key;
        }
        complete = true;
        repaint();
    }

    // Utility function to swap elements
    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Delay for visualization
    private void repaintWithDelay() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    // Main UI setup
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer visualizer = new SortingVisualizer();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.add(visualizer);
        frame.setVisible(true);

        // Command menu
        while (true) {
            String input = JOptionPane.showInputDialog(
                    "Choose Sorting Algorithm:\n" +
                            "1: Selection Sort\n" +
                            "2: Bubble Sort\n" +
                            "3: Insertion Sort\n" +
                            "0: Randomize Array\n" +
                            "q: Quit"
            );

            if (input == null || input.equalsIgnoreCase("q")) {
                System.exit(0);
            }

            switch (input) {
                case "1":
                    visualizer.loadArray();
                    visualizer.selectionSort();
                    break;
                case "2":
                    visualizer.loadArray();
                    visualizer.bubbleSort();
                    break;
                case "3":
                    visualizer.loadArray();
                    visualizer.insertionSort();
                    break;
                case "0":
                    visualizer.randomizeArray();
                    visualizer.loadArray();
                    visualizer.repaint();
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Invalid choice, try again.");
            }
        }
    }
}

