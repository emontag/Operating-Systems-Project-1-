import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;




public class Club {
   /*
    * Needed variables
    */
   
   public static long time = System.currentTimeMillis();
   public static SmartPants smartpants;
   public static Contestant [] contestants;
   public static Date[] dates;
   public static Queue arrival_queue=new LinkedList(); //to Club queue
   public static volatile boolean []  date_available; //keep track if dates are available to meet
   public static volatile boolean[] arrival_list;//meet smartpants first time
   public static volatile BlockingQueue date_queue=new LinkedBlockingQueue();
   public static volatile boolean [][] list_dates;
   public static volatile boolean [][] num_of_dates;
   public static volatile boolean showEnds;
   public static boolean [] contestants_finished;
   public static int num_rounds;
   public static volatile int contestant_done;
   public static int num_contestants;
   /*
    * Initializes variables and starts all threads
    */
         
   public static void main(String[] args) {
     try{
        showEnds=false;
        num_contestants=Integer.parseInt(args[0]);
        int num_dates=Integer.parseInt(args[1]);
        num_rounds=Integer.parseInt(args[2]);
        smartpants=new SmartPants(1);
        dates=new Date[num_dates];
        contestant_done=0;
        contestants=new Contestant[num_contestants];
        contestants_finished=new boolean[num_contestants];
        date_available=new boolean[num_dates];
        list_dates=new boolean[num_contestants][num_dates];
        num_of_dates=new boolean[num_contestants][num_dates];
        initilize(dates,contestants,num_dates,num_contestants);
        smartpants.start();
        for(int num=0;num<num_dates;num++){
            dates[num].start();
        }
        for(int num=0;num<num_contestants;num++){
           contestants[num].start(); 
           
        }
     }catch(Exception e){
        e.printStackTrace();
     }
     

   }
   /*
    * Initializes variables and arrays to proper defaults
    */
   private static void initilize(Date[] dates, Contestant[] contestants,
      int num_dates, int num_contestants) {
      for(int number=0;number<num_dates;number++){
         date_available[number]=true;
         dates[number]=new Date(number);
      }
         
      arrival_list=new boolean[num_contestants];
      for(int number=0;number<num_contestants;number++){
         arrival_list[number]=false;
         contestants_finished[number]=false;
         contestants[number]=new Contestant(number);
      }
      for(int num=0;num<num_contestants;num++){
         for(int number=0;number<num_dates;number++){
            list_dates[num][number]=false;
            num_of_dates[num][number]=false;
         }
      }    
       
   }
   //says whether more contestants need to arrive
   public static boolean moreArrivals(){
      for(int num=0;num<arrival_list.length;num++){
         if(arrival_list[num]==false) return true;
      }
      return false;
   }

}
