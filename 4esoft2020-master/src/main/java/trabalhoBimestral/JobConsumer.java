package trabalhoBimestral;
import java.util.concurrent.atomic.AtomicBoolean;

public class JobConsumer extends Thread {
    private JobQueue tarefas;
    private Integer assignedJob = null;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public JobConsumer(JobQueue tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            if (assignedJob == null || assignedJob == 0) {
                try {
                    assignedJob = tarefas.pegarProximaTarefa();
                    if (assignedJob == null) {
                        System.out.println("Nenhuma tarefa disponivel :)" + System.currentTimeMillis() + " " + this);
                        //this.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                int workToDo = assignedJob;
                for (int i = assignedJob; i >= 0; i--) {
                    System.out.println("Trabalhando... Tamanho da tarefa " + assignedJob + ", " + System.currentTimeMillis() + ": " + this);                    
                    try {
                        //this.sleep(700);
                    } catch (InterruptedException err) {
                        e.printStackTrace();
                    }
                }
                tarefas.concludeJob()
                assignedJob = null;
            }
            public void stopConsumer() {
                isRunning.set(false);
            }
        }
    }

}
