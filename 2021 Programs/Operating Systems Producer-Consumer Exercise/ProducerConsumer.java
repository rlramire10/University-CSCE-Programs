//Name:  Rudy Ramirez
//UA ID: 010786513
//Date:  4/29/21

/*
The code must use threads, mutexes, and sempphores.
Note: The number of producer and consumer threads does not have to be equal.

Program Description:
The application will create the specified number of producer and consumer threads.
and then sleep letting both type of threads place 100 random integers into a bounded
buffer that has a finite number of slots to hold data.

Each thread will also choose a random amount of time to sleep using Random() and
Thread.sleep() that is less than the given sleep time as it places 100 random 
integers into this one bounded buffer.

Each thread will print the random number that it placed in the bounded buffer or
removed from the bounded buffer to the screen. Set the bounded buffer equal to 5.

Use a mutex variable named "mutex" to protect the buffer.
*/

import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.LinkedList;

public class ProducerConsumer
{
	public static class BoundedBuffer
	{
		//Variables
		Semaphore empty = new Semaphore(5);
		Semaphore full  = new Semaphore(0);
		Semaphore mutex = new Semaphore(1);
		
		LinkedList<Integer> buffer = new LinkedList<>();
		int number = 0;
		
		//Add to Bounded Buffer
		public void Insert(int selected_number) throws InterruptedException
		{
			try
			{
				empty.acquire();
				mutex.acquire();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
			
			//Save selected random number
			number = selected_number;
				
			//Prints produced Number.
			System.out.println("Producer produced " + number);
			
			//Place number in buffer
			buffer.add(number);
				
			mutex.release();
			full.release();
		}
		
		//Remove from Bounded Buffer
		public void Retrieve() throws InterruptedException
		{
			try
			{	
				full.acquire();
				mutex.acquire();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
			
			//Remove number from buffer
			int retrieved_num = buffer.removeFirst();
			
			mutex.release();
			empty.release();
			
			//Prints retrieved number
			System.out.println("	Consumer consumes " + retrieved_num);
		}
	}
	
	public static class Producer implements Runnable
	{
		private final static Random rand1 = new Random();
		private final static Random rand2 = new Random();
		private final static int repetitions = 100;
		private final int number_range = 100000; //0 - 99,999 number range
		int sleep_time_range = 500; 			 //0 - 0.4999 seconds
		
		BoundedBuffer buffer;
		Producer(BoundedBuffer buffer, int sleep_time)
		{
			this.buffer = buffer;
			this.sleep_time_range = sleep_time*1000/40;
		}
		
		@Override
		public void run()
		{
			try
			{	
				for(int i = 0; i < repetitions; i++)
				{
					//The producer will 100 times go through the cycle of sleeping a random amount of time
					Thread.sleep(rand1.nextInt(sleep_time_range));
					//Creating a random integer	
					int random_int = rand2.nextInt(number_range);
					//Inserting the random integer into the bounded buffer of size 5
					buffer.Insert(random_int);
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static class Consumer implements Runnable
	{
		private final static Random rand = new Random();
		private final static int repetitions = 100;
		int sleep_time_range = 500; //0 - 0.4999 seconds
		
		BoundedBuffer buffer;
		Consumer(BoundedBuffer buffer, int sleep_time)
		{
			this.buffer = buffer;
			this.sleep_time_range = sleep_time*1000/40;
		}
		
		@Override
		public void run()
		{
			try
			{
				for(int i = 0; i < repetitions; i++)
				{
					//The consumer will 100 times go through the cycle of sleeping a random amount of time
					Thread.sleep(rand.nextInt(sleep_time_range));
					//Consuming an item from the bounded buffer
					buffer.Retrieve();
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static void main(String[] args)
	{
		//Variables
		int sleep_time = 0; //In Seconds
		int number_P_threads = 0;
		int number_C_threads = 0;

		//Accept command arguments for sleep time and the number of threads.
		if (args.length > 0)
        {
            try
            {
                sleep_time       = Integer.parseInt(args[0]);
                number_P_threads = Integer.parseInt(args[1]);
                number_C_threads = Integer.parseInt(args[2]);
                
                System.out.println("Using arguments from command line");
				System.out.println("Sleep time = " + sleep_time);
				System.out.println("Producer threads = " + number_P_threads);
				System.out.println("Consumer threads = " + number_C_threads);
				System.out.println("");
            } 
            catch (NumberFormatException e)
            {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.err.println("Argument" + args[1] + " must be an integer.");
                System.err.println("Argument" + args[2] + " must be an integer.");
                System.exit(0);
            }
        }
		
		//Create Bounded Buffer
		BoundedBuffer buffer = new BoundedBuffer();
		
		//Create Runnable Variables for Producers and Consumers
		Runnable runner1 = new Producer(buffer, sleep_time);
		Runnable runner2 = new Consumer(buffer, sleep_time);
		
		//Create Array of Threads for Producers and Consumers
		Thread[] p_threads = new Thread[number_P_threads];
		Thread[] c_threads = new Thread[number_C_threads];
		
		//Create and Start Producer Threads
        for(int i1 = 0; i1 < number_P_threads; i1++)
		{
            p_threads[i1] = new Thread(runner1, "Producer");
			p_threads[i1].start();
        }
		
		//Create and Start Consumer Threads
        for(int i2 = 0; i2 < number_C_threads; i2++)
		{
            c_threads[i2] = new Thread(runner2, "Consumer");
			c_threads[i2].start();
        }
	}
}