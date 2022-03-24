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