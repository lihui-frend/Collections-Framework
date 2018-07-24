package com.framework;

/**
 * 数组加链表的结构
 */

public class HashMap implements Map {
    //默认容量16
    private final int DEFALUT_CAPACITY=16;
    //内部存储结构，数组
    Node[] table=new Node[DEFALUT_CAPACITY];
    private int size=0;
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public Object get(Object key) {
        int hashValue=hash(key);
        int i=indexFor(hashValue, table.length);
        for (Node node = table[i]; node!=null; node=node.next) {

            if (node.key.equals(key)&&hashValue==node.hash) {
                return node.value;

            }

        }
        return null;
    }

    public Object put(Object key,Object value) {
        //通过key，求hash值
        int hashValue=hash(key);
        //同过hash值，找到这个key应该放在数组的哪个位置（i）
        int i=indexFor(hashValue,table.length);
        //i位置已经有数据了
        for (Node node = table[i]; node!=null; node=node.next) {

            Object k;
            //且数组中已有这个key的，覆盖其原始的value
            if (node.hash==hashValue&&((k=node.key)==key||key.equals(k))) {

                Object oldValue=node.value;
                node.value=value;
                return oldValue;

            }
        }
        //如果i位置没有数据，或者i位置有数据，但是key是新的key，则新增节点
        addEntry(key, value, hashValue, i);
        return null;

    }

    public void addEntry(Object key, Object value, int hashValue, int i) {
        //如果超过了数组原始的大小，则扩大数组
        if (++size==table.length) {
            Node [] newTable=new Node[table.length*2];
            System.arraycopy(table, 0, newTable, 0, table.length);
            table=newTable;
        }
        //得到i位置的数据
        Node eNode=table[i];
        //新增节点，将该节点的next值指向前一个节点
        table[i]=new Node(hashValue, key, value, eNode);

        /*
         * 上面两句话，需要注意的是，如果i位置原始位置table[i]没有值，
         * eNode为空，就直接将新的node放入table[i],并将该node的next指向空
新的node插入到原始node的前面，所以新的node的next指向原始位置i的node
         */
    }

    public int indexFor(int hashValue, int length) {


        return hashValue%length;
    }

    public int hash(Object key) {

        return key.hashCode();
    }

    static  class Node implements Map.Entry{
        int hash;
        Object key;
        Object value;
        Node next;//指向下一个节点
        Node(int hash, Object key, Object value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }



    }

    public static void main(String[] args) {
        HashMap hashMap=new HashMap();
        hashMap.put("aaa", "1111");
        hashMap.put("bbb", "2222");
        hashMap.put("ccc", "3333");
        hashMap.put("ddd", "4444");
        hashMap.put("eee", "5555");
        hashMap.put("ffff", "9666");
        hashMap.put("ggg", "geg");
        System.out.println(hashMap.get("dd"));
    }

}