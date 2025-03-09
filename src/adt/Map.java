package adt;

public class Map<K, V> implements MapInterface<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private TableEntry<K, V>[] hashTable;
    private int numOfValues;
    private int numOfIndexUsed;

    public Map() {
        this.hashTable = new TableEntry[DEFAULT_SIZE];
        numOfValues = 0;
        numOfIndexUsed = 0;
    }

    @Override
    public Boolean containsKey(K key) {
        int index = getHashIndex(key);

        TableEntry<K, V> entry = hashTable[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }

        return false;
    }

    @Override
    public Boolean containsValue(V value) {
        for (int i = 0; i < hashTable.length; i++) {
            TableEntry<K, V> entry = hashTable[i];

            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }

        return false;
    }

    @Override
    public void add(K key, V value) {
        int index = getHashIndex(key);
        boolean isRepeatedKey = false;

        TableEntry<K, V> existingElement = hashTable[index];

        for (; existingElement != null; existingElement = existingElement.next) {

            if (existingElement.key.equals(key)) {

                existingElement.value = value;
                return;
            } else {

                isRepeatedKey = true;
            }
        }

        TableEntry<K, V> entryInOldBucket = new TableEntry(key, value);
        entryInOldBucket.next = hashTable[index];
        hashTable[index] = entryInOldBucket;
        numOfValues++;

        if (!isRepeatedKey) {
            numOfIndexUsed++;
        }
    }

    @Override
    public V get(K key) {
        int index = getHashIndex(key);

        TableEntry existingElement = hashTable[index];

        // if bucket is found then traverse through the linked list and
        // see if element is present
        while (existingElement != null) {

            if (existingElement.key.equals(key)) {

                return (V) existingElement.value;
            }
            existingElement = existingElement.next;
        }

        // if nothing is found then return null
        return null;
    }

    @Override
    public Integer getNumOfValues() {
        return numOfValues;
    }

    @Override
    public Integer getNumOfIndexUsed() {
        return numOfIndexUsed;
    }

    @Override
    public Boolean isEmpty() {
        return numOfValues == 0 && numOfIndexUsed == 0;
    }

    @Override
    public Boolean isIndexFull() {
        return numOfIndexUsed == hashTable.length;
    }

    @Override
    public void clear() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
    }

    @Override
    public V remove(K key) {
        int index = getHashIndex(key);
        TableEntry<K, V> entry = hashTable[index];
        TableEntry<K, V> prevEntry = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prevEntry != null) {
                    prevEntry.next = entry.next; // Update previous entry's next pointer
                } else {
                    hashTable[index] = entry.next; // Update bucket's head if the first entry is removed
                }
                numOfValues--; // Decrease the size of the hash table
                return entry.value; // Return the value of the removed entry
            }
            prevEntry = entry;
            entry = entry.next;
        }

        return null; // Key not found
    }

    @Override
    public ListInterface<K> getKeys() {
        ListInterface<K> keysList = new List<>();

        for (int i = 0; i < hashTable.length; i++) {
            TableEntry<K, V> entry = hashTable[i];
            while (entry != null) {
                keysList.add(entry.key);
                entry = entry.next;
            }
        }

        return keysList;
    }

    @Override
    public ListInterface<V> getValues() {
        ListInterface<V> valuesList = new List<>();

        for (int i = 0; i < hashTable.length; i++) {
            TableEntry<K, V> entry = hashTable[i];
            while (entry != null) {
                valuesList.add(entry.value);
                entry = entry.next;
            }
        }

        return valuesList;
    }

    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % hashTable.length;
        if (hashIndex < 0) {
            hashIndex = hashIndex + hashTable.length;
        }
        return hashIndex;
    }

    private class TableEntry<K, V> {

        private K key;
        private V value;
        private TableEntry next;

        private TableEntry(K searchKey, V dataValue) {
            key = searchKey;
            value = dataValue;
        }

        private K getKey() {
            return key;
        }

        private void setKey(K newKey) {
            key = newKey;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V newValue) {
            value = newValue;
        }

        private void setToRemoved() {
            key = null;
            value = null;
        }
    }

}
