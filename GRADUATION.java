import java.util.Arrays;
import java.util.Scanner;
public class GRADUATION {

	static int N, K, M, L;
	static int pre[];
	static int sem[];
	static int INF = 11;
	static int cache[][];
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		int C = sc.nextInt();
		
		for(int i = 0; i < C; i++)
		{
			N = sc.nextInt();
			K = sc.nextInt();
			M = sc.nextInt();
			L = sc.nextInt();
			
			pre = new int[N];
			sem = new int[M];
			cache = new int[M][1<<N];
			
			for(int j = 0; j < M; j++)
				Arrays.fill(cache[j], -1);
			
			for(int j = 0; j < N; j++)
			{
				int c = sc.nextInt();
				for(int k = 0; k < c; k++)
				{
					int tmp = sc.nextInt();	
					pre[j] |= ( 1 << tmp );  
				}
			}
			
			for(int j = 0; j < M; j++)
			{
				int c = sc.nextInt();
				
				for(int k = 0; k < c; k++)
				{
					int tmp = sc.nextInt();
					sem[j] |= ( 1 << tmp );
				}
			}
			int result = graduate(0,0);
			
			if( result == INF) System.out.println("IMPOSSIBLE");
			else System.out.println(result);
		}
		
		sc.close();
	}
	
	public static int graduate(int s, int taken)
	{
		if(Integer.bitCount(taken) >= K) return 0;
		
		if(s == M) return INF;
		
		if(cache[s][taken] != -1) return cache[s][taken];
		
		cache[s][taken] = INF;
		
		int canTake = ( sem[s] & ~taken );
		
		for(int i = 0; i < N; i++)
		{
			if( ( canTake & (1 << i) ) != 0 &&  (taken & pre[i]) != pre[i] )
				canTake &= ~(1 << i);
		}
		
		for(int take = canTake; take > 0; take = ((take - 1) & canTake ))
		{
			if( Integer.bitCount(take) > L ) continue;
			cache[s][taken] = Math.min( cache[s][taken], graduate( s+1, taken | take) + 1);
		}
		
		cache[s][taken] = Math.min( cache[s][taken], graduate( s+1, taken ) );
		
		return cache[s][taken];
	}
}
