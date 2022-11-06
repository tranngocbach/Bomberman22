package src.uet.oop.bomberman.entities;
import javafx.util.Pair;

import java.util.*;

public class BFS {

    List<Pair<Integer,Integer> > listMove = new ArrayList<>();
    public int[][] mapAns = new int[50][50];
    public int nextDirection = -1;

    public BFS(char[][] map, int sx,int sy,int dx,int dy) {
        listMove.add(new Pair<>(-1,0));
        listMove.add(new Pair<>(0,-1));
        listMove.add(new Pair<>(1,0));
        listMove.add(new Pair<>(0,1));

        Deque<Integer> dqx = new ArrayDeque<Integer>();
        Deque<Integer> dqy = new ArrayDeque<Integer>();
        dqx.addFirst(sx);
        dqy.addFirst(sy);
        while(!dqx.isEmpty())
        {
            int x = dqx.getLast();
            int y = dqy.getLast();
            //System.out.println(dqx.size());
            /*System.out.print(dx);
            System.out.print(" ");
            System.out.print(dy);
            System.out.print(" ");
            System.out.print(x);
            System.out.print(" ");
            System.out.println(y);*/
            dqx.removeLast();
            dqy.removeLast();
            if(x==dx&&y==dy)
            {
                //System.out.println("fasifhsaofahfsaoihf");
                nextDirection = mapAns[y][x] - 1;
                //System.out.println(nextDirection);
                break;
            }
            for(int i=0;i<4;i++)
            {
                int u = x + listMove.get(i).getKey();
                int v = y + listMove.get(i).getValue();
                if(map[v][u] == ' '
                        || map[v][u] == 'p'
                        || map[v][u] == 'x'
                        || map[v][u] == '1'
                        || map[v][u] == '2'
                        || map[v][u] == '3'
                        || map[v][u] == '4')
                {
                    if(mapAns[v][u] != 0)
                    {
                        continue;
                    }
                    if(mapAns[y][x] == 0)
                    {
                        mapAns[v][u] = i + 1;
                    }
                    else
                    {
                        mapAns[v][u] = mapAns[y][x];
                    }
                    dqx.addFirst(u);
                    dqy.addFirst(v);
                }
            }
        }
    }
}