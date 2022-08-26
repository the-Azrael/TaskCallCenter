import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static final int MAX_CALLS = 60;
    private static final int MAX_OPERATORS = 10;
    private static final String FIRST_CODE_RUSSIA = "+7";
    private static final String SECOND_CODE_RUSSIA = "9";
    private static final int RESOLVE_TIME = 5_000;
    private static final int CALL_GENERATE_TIME = 1_000;
    private static final int PHONE_NUMBER_LENGTH = 11;
    private static final int NUMBER_LENGTH = PHONE_NUMBER_LENGTH - 2;
    private static Queue<String> calls = new ArrayBlockingQueue<>(MAX_CALLS);
    private static AtomicBoolean isCompleted = new AtomicBoolean();

    private static Thread ats = new Thread(() -> {
        isCompleted.set(false);
        for (int i = 0; i < MAX_CALLS; i++) {
            String call = generateNumber();
            try {
                Thread.sleep(CALL_GENERATE_TIME);
                calls.offer(call);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Поступил звонок " + call);
        }
        isCompleted.set(true);
    });
    
    private static Thread operatorsControl = new Thread(() -> {
        System.out.println(isCompleted.get());
        while (!isCompleted.get()) {
            for (int i = 0; i < MAX_OPERATORS; i++) {
                String operatorName = "Оператор" + i;
                Thread op = new Thread(() -> {
                    String call = calls.poll();
                    try {
                        if (call != null) {
                            System.out.println(operatorName + ": Работает над звонком: " + call);
                            System.out.println("Не распределенных звонков " + calls.size());
                            Thread.sleep(RESOLVE_TIME);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                op.setName(operatorName);
                op.start();
            }
        }
    });

    private static String generateNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append(FIRST_CODE_RUSSIA);
        sb.append(SECOND_CODE_RUSSIA);
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ats.start();
        operatorsControl.start();
    }
}
