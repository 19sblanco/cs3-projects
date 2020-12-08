import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CuckooHashTable<E> {
    private E[] table1;
    private E[] table2;
    private String tableName;
    private final int MAX_LOOP_TIMES = 30;
    private final int INITIAL_TABLE_SIZE1 = 11;
    private final int INITIAL_TABLE_SIZE2 = 13;

    public CuckooHashTable(String tableName) {
        this.table1 = (E[]) new Object[INITIAL_TABLE_SIZE1];
        this.table2 = (E[]) new Object[INITIAL_TABLE_SIZE2];
        this.tableName = tableName;
    }

    public E[] getTable1() { return table1; }

    public E[] getTable2() { return table2; }

    public String getTableName() {
        return tableName;
    }

    public void insert(E x) {
        if (find(x)) {
            return;
        }
        for (int i = 0; i < MAX_LOOP_TIMES; i++) {
            int loc1 = hashFunction(x, table1.length);
            if (table1[loc1] == null) {
                table1[loc1] = x;
                return;
            }
            E y = table1[loc1];
            table1[loc1] = x;
            int loc2 = hashFunction(y, table2.length);
            if (table2[loc2] == null) {
                table2[loc2] = y;
                return;
            }
            x = table2[loc2];
            table2[loc2] = y;
        }
        rehash();
        insert(x);
    }
    public void insert(ArrayList<E> x) {
        for (E item: x) {
            insert(item);
        }
    }

    public int hashFunction(E data, int tableSize) {
        int hashVal = data.hashCode( );

        hashVal %= tableSize;
        if( hashVal < 0 )
            hashVal += tableSize;

        return hashVal;
    }
    /**
     * Expand the hash table
     */
    public void rehash() {
        E[] oldArray1 = table1;
        E[] oldArray2 = table2;

        // create new tables double the size
        int size1 = nextPrime(table1.length * 2);
        int size2 = nextPrime((table1.length * 2) + 1);
        E[] newArray1 = (E[]) new Object[size1];
        E[] newArray2 = (E[]) new Object[size2];
        table1 = newArray1;
        table2 = newArray2;

        // insert into new table
        for (E entry: oldArray1) {
            if (entry != null) {
                insert(entry);
            }
        }
        for (E entry: oldArray2) {
            if (entry != null) {
                insert(entry);
            }
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tableName + "\n{");
        for (E item: this.getCombinedTable()) {
            if (item != null) sb.append(item + " ");
        }
        return sb.toString() + "}\n";
    }
    /**
     * Find an item in the hash table
     * @param x item searching for
     * @return true if item is found
     */
    public boolean find(E x) {
        int loc1 = hashFunction(x, table1.length);
        int loc2 = hashFunction(x, table2.length);

        boolean val1 = false;
        boolean val2 = false;

        if (table1[loc1] != null) val1 = table1[loc1].equals(x);
        if (table2[loc2] != null) val2 = table2[loc2].equals(x);

        return val1 || val2;
    }
    public E getItem1(E x) {
        int loc1 = hashFunction(x, table1.length);
        return table1[loc1];
    }

    public E getItem2(E x) {
        int loc2 = hashFunction(x, table2.length);
        return table2[loc2];
    }

    public ArrayList<E> getCombinedTable() {
        ArrayList<E> combinedTable = new ArrayList<>();
        for (E item: table1) {
            if (item == null) { continue; } // don't follow null pointers
            combinedTable.add(item);
        }
        for (E item: table2) {
            if (item == null) { continue; }
            combinedTable.add(item);
        }
        return combinedTable;
    }


    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
}
