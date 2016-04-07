import java.util.Random;


public class Date extends Thread{
   private String name;
   private int id_num;
   public void msg(String m) { 
      System.out.println("["+(System.currentTimeMillis()-Club.time)+"] "+getName()+": "+m); 
   }
   public Date(int id) { setName("Date-" + id); id_num=id;}
   public void setThreadName(String n){name=n;}
   public String getThreadName(){ return name;}
   public int getIDNum(){return id_num;}
   static boolean go_home;
   /*
    * adds to date_queue for use for the contestants. Runs the "Date"
    */
   public  void run(){
      setPriority(Thread.MAX_PRIORITY);
      go_home=false;
      try {
         Club.date_queue.put(this);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      setPriority(Thread.NORM_PRIORITY);
      while( !Club.showEnds && !go_home){ //checks if can leave
         while(Club.date_available[id_num]){};
         setPriority(Thread.MAX_PRIORITY);
         Random r=new Random();
         try {
            sleep(r.nextInt(1000));
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         setPriority(Thread.NORM_PRIORITY);
         
      }
      msg("Going home. ");
    
   }
   /*
    * gives decision whether to give phone number
    */
   public boolean getDecision(){
      Random r=new Random();
      if(r.nextBoolean()==true) {
         return true;
      }
      else return false;
   }
}
