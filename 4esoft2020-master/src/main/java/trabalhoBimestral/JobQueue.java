package trabalhoBimestral; 

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

 public class JobQueue {
    private LinkedList<Integer> tarefas = new LinkedList<>();
    private AtomicInteger processando = new AtomicInteger(0);
    private JobQueueListener listener;
    private JobQueueCompletedListener completedListener;

    public JobQueue() {super()}


 public synchronized void queueJob(int job) {
        synchronized (this) {
            this.tarefas.add(job);
            if (this.listener != null) {
                this.listener.jobQueueChanged(this.tarefas.size());
            }

        }
    }
	
    public void addJobQueueListener(JobQueueListener listener, JobQueueCompletedListener completedListener) {
        this.listener = listener;
        this.completedListener = completedListener;
    }

    public interface JobQueueListener {
        void jobQueueChanged(int newSize);
    }

    public interface JobQueueCompletedListener {
        void jobQueueCompletedChanged(int newSize);
    }

    public synchronized void associarTarefa() {
        synchronized (this) {
            this.processando.incrementAndGet();
            if (this.listener != null) {
                this.completedListener.jobQueueCompletedChanged(this.processando.get());
            }
        }
    }

    public synchronized Integer pegarProximaTrafefa() {
        synchronized (this) {
            if (this.tarefas.isEmpty()) {
                return null;
            }

            Integer job = this.tarefas.getFirst();
            System.out.println("pegando outro job!");
            return job;
        }
    }
	
    public synchronized void concludeJob() {
        synchronized (this) {
            this.processando.decrementAndGet();
            if (!this.tarefas.isEmpty()) {
                this.tarefas.removeFirst();
            }
            System.out.println("Tarefa Concluida !! :DD");
            if (this.listener != null) {
                this.listener.jobQueueChanged(this.tarefas.size());
            }
            if (this.listener != null) {
                this.completedListener.jobQueueCompletedChanged(this.processando.get());
            }
        }
    }

}