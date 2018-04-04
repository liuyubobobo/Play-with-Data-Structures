public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    private int front, tail;
    private int size;   // 有兴趣的同学，在完成这一章后，可以思考一下：
                        // LoopQueue中不声明size，如何完成所有的逻辑？
                        // 这个问题可能会比大家想象的要难一点点：）

    public LoopQueue(int capacity){
        data = (E[])new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public boolean isEmpty(){
        return front == tail;
    }

    @Override
    public int getSize(){
        return size;
    }

    // 下一小节再做具体实现
    @Override
    public void enqueue(E e){

    }

    // 下一小节再做具体实现
    @Override
    public E dequeue(){
        return null;
    }

    // 下一小节再做具体实现
    @Override
    public E getFront(){
        return null;
    }
}
