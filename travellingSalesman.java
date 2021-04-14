import java.util.Scanner;

/*NOTE ON HOW TO USE THE ALGORITHM:
* STEP 1: If using the same graph as in assignment 3, copy the following string (whole as it is) and paste it in the console where is asks for the very first user input and press enter.
*         Or else you will have to enter each input yourself and this will waste your time. The string is mentioned in the line below.
*         2 1 7 3 0 7 2 4 1 3 5 8 3 2 5 4 2 3 5 4 2 3 4 6 3 5 7 8 4 0 1 8 6 3 2 6 7 4 8 8 3 4 12 11 3 8 1 8 8 7 5 4 5 2 0 7 1 14 5 9 13 9 5 10 5 4 1 14 5 10 13 2 5 2 5 1 3 6 0 8 12 11 8 7 0 1 5 2 1 6 5 7 3
*         The string consists of all the inputs you'll have to enter normally.
* STEP 2: Enter the starting point
*
* Author: Abdullah Ahmed Khan  */

public class travellingSalesman {



    static linkedlist [] arr =new linkedlist[9];
    static int [][] edges = new int [9][4];
    static Scanner sc= new Scanner (System.in);




    public static void main(String[] args) {

        int n,c,h; //n=no. of neighbours a vertex has, c=cost of the edge, h= heuristics of a vertex
        boolean valid=true;

        for(int i = 0 ; i < arr.length;i++) {arr[i] = new linkedlist();}  


        for (int i=0;i<9;i++)
        {
          System.out.println("PLEASE ENTER NUMBER OF NEIGHBOURS OF VERTEX "+i);
          n=sc.nextInt();

          for (int j=0;j<n;j++)
          {
              System.out.println("PLEASE ENTER "+(j+1)+ "th NEIGHBOURS OF VERTEX "+i);
              if(j+1==n){
                  for(int k=j+1;k<4;k++){edges[i][k]=-1;}
              }
              edges[i][j]=sc.nextInt();
          }
        }

        for(int i=0; i<9; i++){
          for (int j=0;j<4;j++){
              if (edges[i][j]!=-1){
                  System.out.println("Enter cost between "+i+ "and "+ edges[i][j]);
                  c=sc.nextInt();
                  System.out.println("Enter heuristic for "+ edges[i][j]);
                  h=sc.nextInt();
                  arr[i].insert(edges[i][j], c, h);
              }
          }
        }



        do{
        valid=true;
        System.out.println("Enter Source:");
        c=sc.nextInt();
        if(c==8){
            System.out.println("Invalid source because source and destination are same. Pleas try again");
            valid=false;
        }
        }while(valid==false);

        FindShortestPath(c,8);  //finds shortest path

    }

    static void FindShortestPath(int source,int destination){
        int [] t_c = new int [9];  //t_c = total cost
        boolean [] visited = new boolean [9];
        int min=100000; int sum; int destination_i=-1; int i=source;
        Node temp;



     //CALCULATES TOTAL COST FOR EACH VERTEX
        t_c[i]=0;visited[i]=true;
        temp= arr[i].head;
        while(i<9){
            if (temp.val==(i-1)){
                temp=temp.next;
            }

            if (temp!=null) {
                sum = t_c[i] + temp.cost;

                if (visited[temp.val] == false) {
                    t_c[temp.val] = sum;
                }

                temp.total_cost = t_c[temp.val];
                visited[temp.val] = true;

                temp = temp.next;
            }
            if(temp==null){i++; if (i<9){temp=arr[i].head;}}
        }



        //CALCULATES A* VALUES AND ALSO FINDS OUT THE INDICES OF DESTINATION
        i=source; temp=arr[i].head; String str="";
        while(i<9){
            if (temp.val==(i-1)){
                temp=temp.next;
            }

            if (temp!=null) {
                temp.A_star_value=temp.total_cost+ temp.h;
                if (temp.val==destination){str=str+""+i;}
                temp = temp.next;
            }
            if(temp==null){i++; if (i<9){temp=arr[i].head;}}
        }



        //CALCULATES WHICH ROUTE HAS LEAST A* VALUE AT DESTINATION VERTEX
        for (i=0;i<str.length();i++){
            sum= Integer.parseInt(String.valueOf(str.charAt(i)));
            temp=arr[sum].head;
            while(temp.val!=destination){temp=temp.next;}
            if (temp.A_star_value<min){min=temp.A_star_value; destination_i=sum;}
        }



        //FIND PATH BY REVERSE TRACKING
           i = source; str = "" + destination;
           temp = arr[i].head;
           boolean skip = false;
           while (destination_i != source) {
               skip = false;

               if (temp.val == destination_i) {
                   str = temp.val + "-->" + str;
                   if (temp.val == 8) {
                       break;
                   }
                   destination_i = i;
                   i = source - 1;
                   skip = true;
               }
               temp = temp.next;
               if (temp == null || skip == true) {
                   i++;
                   if (i < 9) {
                       temp = arr[i].head;
                   }
               }
           }



        str=source+"-->"+str;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Shortest path= "+str); // Prints the shortest path
    }
}



class Node{

    Node next;
    int val, h,cost,total_cost,A_star_value;

    Node(int v, int c, int heuristic){
        val=v;  h=heuristic;  cost=c;
    }
}

class linkedlist{
    Node head;
    Node temp;

    void insert(int v, int c, int heuristic){
        Node n= new Node(v,c,heuristic);
        if (head==null){
            head=n;
        }
        else {
            temp=head;
            while (temp.next!=null){
                temp=temp.next;
            }
            temp.next=n;
        }
    }
}
