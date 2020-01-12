import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class PlayCardsV1{

    private volatile int status = 0 ;
    private volatile boolean flag = true;
    private Deck cards;


    private AtomicInteger round = new AtomicInteger(1);

    BlockingQueue<Card> blockingQueue;

    public PlayCardsV1(Deck cards, BlockingQueue<Card> blockingQueue) {
        this.cards = cards;
        this.blockingQueue = blockingQueue;
    }

    public void senderCards(){
        Card card = null;
        while(flag){
            while (status==0){
                System.out.println("Round " + round);
                card = cards.drawCardsInOrder();
                System.out.print(card.toString());
                blockingQueue.offer(card);
                card = cards.drawCardsInOrder();
                System.out.print(card.toString());
                blockingQueue.offer(card);
                card = cards.drawCardsInOrder();
                System.out.println(card.toString());
                blockingQueue.offer(card);
                round.incrementAndGet();
                status =1;
            }
        }
    }

    public void play1GetCard(){
        Card card = null;
        int sum = 0;
        while(flag){
            while (status==1){
                card = blockingQueue.poll();
                sum = sum+ card.getNumber();
                System.out.println("player1 get the card: " + card.toString() + ", and total sum is " + sum);
                if(sum>50){
                    flag = false;
                    System.out.println("player1 win the game.");
                }
                status = 2;
            }
        }
    }


    public void play2GetCard(){
        Card card = null;
        int sum = 0;
        while(flag){
            while (status==2){
                card = blockingQueue.poll();
                sum = sum+ card.getNumber();
                System.out.println("player2 get the card: " + card.toString() + ", and total sum is " + sum);
                if(sum>50){
                    flag = false;
                    System.out.println("player2 win the game.");
                }
                status = 3;
            }
        }
    }



    public void play3GetCard(){
        Card card = null;
        int sum = 0;
        while(flag){
            while (status==3){
                card = blockingQueue.poll();
                sum = sum+ card.getNumber();
                System.out.println("player3 get the card: " + card.toString() + ", and total sum is " + sum);
                if(sum>50){
                    flag = false;
                    System.out.println("player3 win the game.");
                }
                status = 0;
            }
        }
    }
}


public class V1BlockQueue {
    public static void main(String[] args) {
        System.out.println("start to play the games V1BlockQueue");
        Deck deck = new Deck();
        deck.shuffle();
        System.out.println(deck.toString());
        PlayCardsV1 playCards = new PlayCardsV1(deck , new ArrayBlockingQueue<>(3));

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        threadPool.execute(new Thread(()->{
            playCards.senderCards();
        }));
        threadPool.execute(new Thread(()->{
            playCards.play1GetCard();
        }));
        threadPool.execute(new Thread(()->{
            playCards.play2GetCard();
        }));
        threadPool.execute(new Thread(()->{
            playCards.play3GetCard();
        }));

        threadPool.shutdown();

    }
}
