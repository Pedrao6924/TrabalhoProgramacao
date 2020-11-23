package trabalhoBimestral;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class JobProducer extends Thread {
	
	
    private final JobQueue tarefas;
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    public JobProducer(JobQueue tarefas) {this.tarefas = tarefas;}
	
	
	
    @Override
    public void run() {
            try {
                System.out.println("Adicionando uma tarefas, " + this );
                this.tarefas.queueJob(1);
            } 	catch (Exception err) {
                System.out.println("Thread interompido, " + this);
            }
			System.out.println("Parando thread =>" + this);
        this.interrupt();
    }

}
