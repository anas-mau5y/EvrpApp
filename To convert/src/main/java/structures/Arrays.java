package structures;

import java.io.IOException;

public final class Arrays {
    public static Node[] initializeWithDefaultnodeInstances(int length)
    {
        Node[] array = new Node[length];
        for (int i = 0; i < length; i++)
        {
            array[i] = new Node();
        }
        return array;
    }

    public static <T extends java.io.Closeable> void deleteArray(T[] array) throws IOException {
        for (T element : array)
        {
            if (element != null)
                element.close();
        }
    }
}
