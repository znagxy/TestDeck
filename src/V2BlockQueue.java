import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class PlayCardsV2{

    //private volatile int status = 0 ;
    private AtomicInteger status = new AtomicInteger(0);
    private volatile boolean flag = true;
    private Deck cards;


    private AtomicInteger round = new AtomicInteger(1);

    BlockingQueue<Card> blockingQueue;

    public PlayCardsV2(Deck cards, BlockingQueue<Card> blockingQueue) {
        this.cards = cards;
        this.blockingQueue = blockingQueue;
    }

    public void senderCards(int  totalPlayer){
        Card card = null;
        while(flag){
            while (status.get()==0){
                System.out.println("Round " + round);
                for(int i = 1; i <=totalPlayer;i++){
                    card = cards.drawCardsInOrder();
                    System.out.print(card.toString());
                    blockingQueue.offer(card);
                }
                System.out.println();
                round.incrementAndGet();
                status.getAndIncrement();
            }
        }
    }

    public void playerGetCard(int currentPlayer , int totalPlayer){
        Card card = null;
        int sum = 0;
        while(flag){
            while (status.get()==currentPlayer){
                card = blockingQueue.poll();
                sum = sum+ card.getNumber();
                System.out.println("player" + currentPlayer +" get the card: " + card.toString() + ", and total sum is " + sum);
                if(sum>50){
                    flag = false;
                    System.out.println("player" + currentPlayer +" win the game.");
                }
                status.getAndIncrement();
                if(status.get()>totalPlayer){
                    status = new AtomicInteger(0);
                }
            }
        }
    }

}


public class V2BlockQueue {
    public static void main(String[] args) {
        System.out.println("start to play games V2BlockQueue");
        int totalPlayers = 3;
        Deck deck = new Deck();
        deck.shuffle();
        System.out.println(deck.toString());
        PlayCardsV2 playCards = new PlayCardsV2(deck , new ArrayBlockingQueue<>(totalPlayers));

        ExecutorService threadPool = Executors.newFixedThreadPool(totalPlayers +1);
        threadPool.execute(new Thread(()->{
            playCards.senderCards(totalPlayers);
        }));

       for(int i = 1; i <= totalPlayers ; i ++){
           int finalI = i;
           threadPool.execute(new Thread(()->{
               playCards.playerGetCard(finalI,totalPlayers);
           }));
       }

        threadPool.shutdown();

    }
}
