


public class SmartPants extends Thread{
   private String name;
   public void msg(String m) { System.out.println("["+(System.currentTimeMillis()-Club.time)+"] "+getName()+": "+m); }
   public SmartPants(int id) { 
      setName("Smartpants-" + id);
      available_to_meet=true;
      }
   private volatile boolean available_to_meet;
   public  boolean meetSmartpants() { return available_to_meet; }
   public void run(){
      while(Club.moreArrivals()){//meet Contestants
         try {
            sleep(200);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         if(Club.arrival_queue.isEmpty()) continue;//wait for more
         
         talkToArrival();
      }
      while(show_not_end()){ //keep waiting
         try {
            sleep(5000);
         } catch (InterruptedException e) {
         
           e.printStackTrace();
         }
      }
      Club.showEnds=true;
      Date.go_home=true;
      msg("Show Over");
      for(int i=0;i<Club.date_available.length;i++)//tell dates go home
         Club.date_available[i]=false;
      //if(Club.showEnds==true) System.exit(0);
     
      
   }
   public void talkToArrival(){
      Contestant c=(Contestant) Club.arrival_queue.peek();
      Club.arrival_queue.remove();
      msg("Smartpants has spoken to Contestant " + c.getIDNum());
      Club.arrival_list[c.getIDNum()]=true;
   }
   public boolean show_not_end(){
      if(Club.contestant_done==Club.num_contestants) {
         Club.showEnds=true;
         return false;
      }
      else return true;
     
            
   }
   

}
