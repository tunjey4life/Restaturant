/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

/**
 *
 * @author tunje
 */
public class Main {

    public static void main(String args[]) {

        Restaurant r = new Restaurant();
        try {
            Table table1 = r.addTable(1);
            Table table2 = r.addTable(3);
            Table table3 = r.addTable(1);
            Table table4 = r.addTable(4);
            Table table5 = r.addTable(1);

            System.out.println("========Free Tables=============");
            for (Table t : r.getEmptyTables()) {
                System.out.println("Table Capacity:" + " " + t.getCapacity());
            }
            System.out.println("\n\nAvailable Seat:" + " " + r.getEmptyTables().size());
            System.out.println("=================================");

            Party party1 = r.bookParty(3, true);
            Party party2 = r.bookParty(1, false);
            Party party3 = r.bookParty(4, true);
            Party party4 = r.bookParty(5, false);

            System.out.println("========Waiting Queue=============");
            for (Party p : r.getUnseatedParties()) {
                System.out.println("Party:" + " Size: " + p.getSize() + " VIP: " + p.isVIP());
            }

            System.out.println("========Seating Parties=============");
            r.seatParty();

            Thread.sleep(3000);

            System.out.println("\n");

            System.out.println("========Occupied Tables=============");
            for (Table t : r.getFilledTables()) {
                System.out.println("Table Capacity:" + " " + t.getCapacity());
            }
            
//            System.out.println("========Removed Party=============");
//            Party pt = r.emptyTable(table1);
//            System.out.println("Party Removed:"+" "+pt.getSize());
            
//            r.removeParty(pt);

            System.out.println("========Waiting Queue=============");
            for (Party p : r.getUnseatedParties()) {
                System.out.println("Party:" + " Size: " + p.getSize() + " VIP: " + p.isVIP());
            }
            
            System.out.println("\nAvailable Seat:" + " " + r.getEmptyTables().size());
            System.out.println("=================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
