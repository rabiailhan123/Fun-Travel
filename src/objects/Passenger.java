// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class represents passengers

package objects;

public class Passenger {
    private int numOfPassengers;
    private City destCity;

    public Passenger(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public void setDestination(City destCity) {
        this.destCity = destCity;
    }

    public City getDestination() {
        return destCity;
    }
}
