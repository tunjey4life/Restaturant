package restaurant;

import java.util.Objects;

public class Table implements Comparable<Table>{
    
    private Party party = null;
    private int capacity = 0;
 

    protected Table(int capacity) {
        
        this.capacity = capacity;
        
    }

    public int getCapacity() {
        return capacity;
    }


    public void setParty(Party party) {
        this.party = party;
    }

    public boolean isOccupied() {
        return party != null;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.party);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Table other = (Table) obj;
        if (!Objects.equals(this.party, other.party)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Table t) {
         int compareCapcity = ((Table)t).getCapacity();
      
        return this.capacity - compareCapcity;
    }
    
      

}
