package restaurant;


import restaurant.exceptions.EmptyTableException;
import restaurant.exceptions.MissingPartyException;
import restaurant.exceptions.MissingTableException;
import restaurant.exceptions.NoTablesAvailableException;
import restaurant.exceptions.NonPositiveArgumentException;
import restaurant.exceptions.OccupiedTableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Restaurant {
    
    private final List<Table> tables = new ArrayList<>();
    private final Queue<Party> reqularQueue = new LinkedList();
    private final Queue<Party> VIPQueue = new LinkedList();

    /**
     * Creates a table with the given capacity and adds it to the restaurant. If
     * the capacity is less than or equal to zero, this method throws an
     * instance of {@link NonPositiveArgumentException} with the capacity filled
     * in.
     *
     * @param capacity The capacity of the table.
     * @return A reference to the new table.
     * @throws NonPositiveArgumentException
     */
    public Table addTable(final int capacity) throws NonPositiveArgumentException {
        
        if (capacity <= 0) {
            throw new NonPositiveArgumentException(capacity);
        }
        Table table = new Table(capacity);
        tables.add(table);
        return table;
    }

    /**
     * Removes the specified table from the restaurant.If the table is occupied,
     * this method throws an instance of {@link OccupiedTableException} with the
     * specified table and occupying party filled in. If the table is null or
     * does not exist, this method throws an instance of
     * {@link MissingTableException} with the specified table filled in.
     *
     * @param table The table to remove from the restaurant.
     * @throws OccupiedTableException
     * @throws restaurant.exceptions.MissingTableException
     */
    public void removeTable(final Table table) throws OccupiedTableException, MissingTableException {
        
        if (table == null) {
            throw new MissingTableException(table);
        }
        if (table.getParty() != null) {
            throw new OccupiedTableException(table, table.getParty());
        }
        tables.remove(table);
    }

    /**
     * Creates a party with the given size and VIP status and adds it to the
     * queue of parties waiting for a table. If the size is less than or equal
     * to zero, this method throws an instance of
     * {@link NonPositiveArgumentException} with the size filled in.
     *
     * @param size The size of the party.
     * @param isVIP Whether the party is a VIP.
     * @return A reference to the new party.
     * @throws NonPositiveArgumentException
     */
    public Party bookParty(final int size, final boolean isVIP) throws NonPositiveArgumentException {
        
        if (size <= 0) {
            throw new NonPositiveArgumentException(size);
        }
        Party party = new Party(size, isVIP);
        if (isVIP) {
            VIPQueue.add(party);
            return party;
        }
        reqularQueue.add(party);
        return party;
    }

    /**
     * Removes the specified party from the restaurant.If the party is already
     * seated, this method should empty the table that the party is currently
     * seated at.If the party does not exist, this method throws an instance of
     * {@link MissingPartyException} with the specified party filled in.
     *
     * @param party The party to remove from the restaurant.
     * @throws restaurant.exceptions.MissingPartyException
     * @throws restaurant.exceptions.EmptyTableException
     */
    public void removeParty(final Party party) throws MissingPartyException, EmptyTableException {
        
        if (party == null) {
            throw new MissingPartyException(party);
        }
        
        Iterator itr = this.getFilledTables().iterator();
        while (itr.hasNext()) {
            Table table = (Table) itr.next();
            
            if (table.getParty().equals(party)) {
                if (party.isSeated()) {
                    
                    this.emptyTable(table);
                }
                itr.remove();
            }
        }

    }

    /**
     * Seats the next eligible party to an empty table.Priority should first be
 given to the earliest unseated VIP party. If no VIP party can be seated,
 then priority should be given to the earliest unseated non-VIP party. The
 table chosen should be any table with the smallest capacity that can seat
 the party. If there is no table with a capacity large enough to seat the
 party, the next party should be checked. If there is no table available
 that can seat any party in the queue, this method throws an instance of
 {@link NoTablesAvailableException} with the first party in the queue
     * filled in. If no parties are currently waiting, this method does nothing.
     *
     * @throws NoTablesAvailableException
     * @throws java.lang.InterruptedException
     */
    public void seatParty() throws NoTablesAvailableException, InterruptedException {
        
        if (getUnseatedParties().isEmpty()) {
            return;
        }
        
        boolean isAnyPartySeated = false;
        for (Party party : getUnseatedParties()) {
            for (Table table : getEmptyTables()) {
  
                if (party.getSize() <= table.getCapacity()) {
                    party.setIsSeated(true);
                    table.setParty(party);
                    if (party.isVIP()) {
                        VIPQueue.remove(party);
                    } else {
                        reqularQueue.remove(party);
                    }
                    isAnyPartySeated = true;
                    break;
                    
                }                
            }
             Thread.sleep(1000);
            
        }
        if (!isAnyPartySeated) {
            throw new NoTablesAvailableException(getUnseatedParties().get(0));
        }

    }

    /**
     * Removes a party from an occupied tableIf the table is not currently
     * occupied, this method throws an instance of {@link EmptyTableException}
     * with the specified table filled in...
     *
     * @param table The table to empty
     * @return .
     * @throws EmptyTableException
     */
    public Party emptyTable(final Table table) throws EmptyTableException {
        
        if (!table.isOccupied()) {
            throw new EmptyTableException(table);
        }
        Party party = table.getParty();
        table.setParty(null);
        return party;
    }

    /**
     * @return The list of tables currently occupied by a party.
     */
    public List<Table> getFilledTables() {
        List<Table> occupiedTables = new ArrayList<>();
        for (Table table : tables) {
            if (table.isOccupied()) {
                occupiedTables.add(table);
            }
        }
        return occupiedTables;
    }

    /**
     * @return The list of tables not occupied by a party.
     */
    public List<Table> getEmptyTables() {
        
        List<Table> emptyTables = new ArrayList<>();
        for (Table table : tables) {
            if (!table.isOccupied()) {
                emptyTables.add(table);
            }
        }
        Collections.sort(emptyTables);
        return emptyTables;
    }

    /**
     * @return The list of parties waiting for a table, with the earliest booked
     * parties at the beginning of the list.
     */
    public List<Party> getUnseatedParties() {
        
        List<Party> waitingList = new ArrayList<>();
        waitingList.addAll(VIPQueue);
        waitingList.addAll(reqularQueue);
        return waitingList;
    }
}
