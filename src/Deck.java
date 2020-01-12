import java.util.Collections;
import java.util.LinkedList;

public class Deck {

    //private Card[] cards;
    private LinkedList<Card> cards;

    public Deck(){
        this(true);
    }

    public Deck(boolean inculdeJoker) {
        cards = new LinkedList<>();
        Card card = null;
        for(int suit = Constanst.CARD_SUIT_DIAMOND; suit<=Constanst.CARD_SUIT_SPADE;suit++){
            for(int var = 1; var<=13; var++){
                card = new Card(var , suit);
                cards.add(card);
            }
        }
        if(inculdeJoker){
            cards.add(new Card(Constanst.CARD_BLACK_JOKER_VALUE,Constanst.CARD_BLACK_JOKER_SUIT));
            cards.add(new Card(Constanst.CARD_RED_JOKER_VALUE,Constanst.CARD_RED_JOKER_SUIT));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public int remainingCardsSum(){
        int sum = 0;
        for(Card card: cards){
            sum = sum + card.getNumber();
        }
        return sum;
    }

    public Card drawCardsInOrder(){
        if (cards.size() == 0)
            throw new IllegalStateException("No cards are left in the deck.");
        Card card = cards.element();
        cards.remove();
        return card;
    }


    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}' + ", total cards=" + cards.size() +", total remaining sum=" + remainingCardsSum();
    }


    public static void main(String[] args) {
        Deck cards = new Deck();
        System.out.println(cards);
        cards.shuffle();
        System.out.println(cards);
        System.out.println(cards.drawCardsInOrder());
        System.out.println(cards.drawCardsInOrder());
        System.out.println(cards.drawCardsInOrder());
        System.out.println("after draw 3 cards");
        System.out.println(cards);
    }

}
