package restaurant;

/**
 * Represents a group of people to be seated together at the restaurant.
 */
public class Party {
    
    private boolean isVIP = false;
    private boolean isSeated = false;
    private int size = 0;
    
    public Party( int size, boolean isVIP){
        this.isVIP = isVIP;
        this.size = size;
        
    }

    public boolean isSeated() {
        return isSeated;
    }

    public void setIsSeated(boolean isSeated) {
        this.isSeated = isSeated;
    }
    
    
    public boolean isVIP() {
        
        return isVIP;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
}