package adt;

public interface MapInterface<K, V> {

    public Boolean containsKey(K key);

    public Boolean containsValue(V value);

    public void add(K key, V value);

    public V get(K key);

    public Integer getNumOfValues();

    public Integer getNumOfIndexUsed();

    public Boolean isEmpty();

    public Boolean isIndexFull();

    public void clear();

    public V remove(K key);

    public ListInterface<K> getKeys();

    public ListInterface<V> getValues();

}
