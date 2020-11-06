# GRADUATION

https://algospot.com/judge/problem/read/GRADUATION

# 구현 방법
비트 마스크를 이용한다.
```
 i)   각 과목들을 bit로 나타낸다. 예를 들어 1학기에 열리는 과목이 0 1 3 이라면 이것을 1011 로 나타낼 수 있다.
 
 ii)  이렇게 각 과목들을 bit로 나타낼 경우 조건을 체크하거나 재귀 함수를 돌리기 더 편해진다.
      예를 들어 특정 과목의 선수과목을 들었는지 체크할 때 bit로 나타내지 않았다면 반복문을 통해 하나하나 비교해보아야겠지만
      bit로 나타내면 ( 지금까지 들은 과목 ) & ( 선수 과목 ) 이 ( 선수 과목 ) 과 같은 값이 나오는지 한 줄로 체크할 수 있다.
 
 iii) 완전 탐색을 진행하며 ( 각 학기별로 과목을 듣는 모든 경우의 수 테스트, 과목을 듣지 않고 휴학하는 경우 모두 체크 )  cache 배열에 최솟값을 저장해나간다.
      cache 배열에 값을 저장하여 같은 연산을 반복하지 않게 한다.

```

# 구현 코드
```java
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
```
