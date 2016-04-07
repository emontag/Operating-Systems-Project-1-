import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;


public class Contestant extends Thread{
   private int id_num;
   private int num_rounds;
   public void msg(String m) { 
      System.out.println("["+(System.currentTimeMillis()-Club.time)+"] "+getName()+": "+m);
   }
   public Contestant(int id) { 
      setName("Contestant-" + id); id_num=id;
   }
   public int getIDNum(){return id_num;}
   private LinkedList list_of_dates=new LinkedList();
   public  void run(){
      goToClub();
      msg("Ending Contestant " +id_num);
   }
   public void goToClub(){
      Random r=new Random();
      try {
         sleep(r.nextInt(2000));//allows others to go ahead
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      msg("Enter the club and waiting for SmartPants");
      Club.arrival_queue.add(this);
      while(!Club.arrival_list[id_num]){};
      for(int date_try=0;date_try<Club.num_rounds;date_try++){//set up for num_rounds
         try {//get everyone a date
            if(r.nextBoolean()) sleep(2000);
         } catch (InterruptedException e1) {
            e1.printStackTrace();
         }
         while(noDates()){
            try {
               msg("In no dates");
               sleep(r.nextInt(1000));
               yield();
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            
         }
         
         approach_date();
 
         try {//walking to smartpants
            sleep(r.nextInt(2000));
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         SmartPants.currentThread().interrupt();
         if(SmartPants.interrupted()) {
            msg ("SmartPants gives pat on back");
         }
         
      }
      Club.contestants_finished[id_num]=true;
      addToFinish();
      try {
         sleep(r.nextInt(2000));
      } catch (InterruptedException e) {
         e.printStackTrace();
      }//brag
      int temp=id_num+1;
      if(temp==Club.num_contestants) temp=0;//reset to last
      if(Club.contestants[temp].isAlive() && temp!=0);
         try {//contestants waiting for next one
            if(temp!=0 && Club.contestants[temp-1].isAlive()) Club.contestants[temp-1].interrupt();
            Club.contestants[temp].join();
         } catch (InterruptedException e) {
         }
      printing();
      
   }
   //check if dates are available
   public boolean noDates(){
      for(int num=0;num<Club.dates.length;num++){
         if(Club.date_available[num]=true) return false;
      }
      return true;
   }
   /*
    * approach a date for number
    * takes from queue until has date
    * ensure not go on date with Date twice
    */
   public synchronized void approach_date(){
      Date date=null;
      try {
         date = (Date) Club.date_queue.take();//get next date
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      if(date==null) {//for debug purposes only
         System.out.println("NULL");
         System.exit(1);
      }
      while(Club.list_dates[id_num][date.getIDNum()]){//while dated them already
         try {
            Club.date_queue.put(date);
            date=(Date) Club.date_queue.take();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      msg("Approach Date " + date.getIDNum());
      Club.date_queue.add(date);
      Club.list_dates[id_num][date.getIDNum()]=true;
      if(date.getDecision()) {
         Club.num_of_dates[id_num][date.getIDNum()]=true;
      }
   }
   public synchronized void addToFinish(){//adds to the contestants finished
      Club.contestant_done++;
   }
   public synchronized void printing(){
      msg("Ending. Numbers are for date(s): ");
      for(int num=0;num<Club.num_of_dates[id_num].length;num++){
         if(Club.num_of_dates[id_num][num]) msg(num +" ");
      }
   }

}
