//Connor Persaud    12/21/2023

import java.util.*;
import java.io.*;
public class AutoCorrect2{
    static int quantity(){
        try {
            File dictionary = new File("Dictionary.txt");
            Scanner line = new Scanner(dictionary); 
            int values = line.nextInt();        //used to initilize dictionary size
            line.close();
            return values;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error in Quantity");
            e.printStackTrace();
            return 0;
        }
    }

    //static String[][] keyGraph= new String[26][];
    static int idfk = 26;
    static long[][] dicGraph= new long[26][quantity()];
    static long[] dicGraphSave =new long[0];
    static int[][] dicGraphCount= new int[26][1];
    static String[][] dicGraphStr= new String[26][quantity()];
    static int[][] dicGraphStrCount= new int[26][1];
    static int[][] dicGraphSorted= new int[26][quantity()];
    static int[][] dicGraphSortedCount= new int[26][1];
    static void initCount(){
        for(int i =0; i<26;i++){
            dicGraphCount[i][0] =0;
            dicGraphStrCount[i][0] =0;          //stores count for each list in graph
            dicGraphSortedCount[i][0] =0;
        }
    }
    static void removeZero(){
        for(int i =0; i<26;i++){
            long[][] dicGraphCopy= new long[26][dicGraphCount[i][0]];
            String[][] dicGraphStrCopy= new String[26][dicGraphStrCount[i][0]];
            //int[][] dicGraphSortedCopy= new int[26][dicGraphSortedCount[i][0]];           //should be correct size before sort
            System.arraycopy(dicGraph[i],0, dicGraphCopy[i],0, dicGraphCount[i][0]);
            dicGraph[i] = dicGraphCopy[i];
            System.arraycopy(dicGraphStr[i],0, dicGraphStrCopy[i],0, dicGraphStrCount[i][0]);       //removes zeros(fillers) from end of list
            dicGraphStr[i] = dicGraphStrCopy[i];

        }
    }
    static String[][] keyboard_Graph(){
        
        String[] q= {"a","w"};
        String[] w= {"q","a","s","d","e"};
        String[] e= {"w","s","d","r"};
        String[] r= {"e","d","f","t"};
        String[] t= {"r","f","g","h","y"};
        String[] y= {"t","g","j","u"};
        String[] u= {"y","h","j","i"};
        String[] i= {"u","j","k","o"};
        String[] o= {"i","k","l","p"};
        String[] p= {"o","l"};
        String[] a= {"q","w","s","z"};
        String[] s= {"w","a","z","x","d","e"};
        String[] d= {"e","s","x","c","f","r"};
        String[] f= {"r","d","c","v","g","t"};
        String[] g= {"t","f","v","b","h","y"};
        String[] h= {"y","g","b","n","j","u"};
        String[] j= {"u","h","n","m","k","i"};
        String[] k= {"i","j","m","l","o"};
        String[] l= {"o","k","p"};
        String[] z= {"a","s","x"};
        String[] x= {"z","s","d","c"};
        String[] c= {"x","d","f","v"};
        String[] v= {"c","f","g","b"};
        String[] b= {"v","g","h","n"};
        String[] n= {"b","h","j","m"};
        String[] m= {"n","j","k"};
 
        String[][]keyGraphF= {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z};        //created map of keyboard
        //for(int ap = 0;ap<keyGraphF.length;ap++){
            //for(int ab = 0;ab<keyGraphF[ap].length;ab++){
            //System.out.print(keyGraphF[ap][ab]);
            //}
            //System.out.println();
        //}
        return keyGraphF;
    }
    static String[][] keyGraph= keyboard_Graph();       //initilize graph of keyboard
    static void createStrDic(String word){            //Dictionary of word as String
        int key = startNum(word);
        //System.out.println(key);
        int count = dicGraphStrCount[key][0];
        while(count <dicGraphStr[key].length){
            if (dicGraphStr[key][count] == null){
                dicGraphStr[key][count] = word;
                break;
            }
            count++;
            dicGraphStrCount[key][0] = count;
        }
        //for(int i =0; i <dicGraphStr.length;i++){
          
            //System.out.println(dicGraphStr[key][i]);
         
        //}
    }
    static void createNumDic(String word){            //Dictionary of word as Numbers
        int key = startNum(word);
        int count = dicGraphCount[key][0];

        long doneAsNum = convertToNum(word);
        while(count <dicGraph[key].length){
            if (dicGraph[key][count]==0){
                dicGraph[key][count] = doneAsNum;
                //System.out.println(dicGraph[key][count]);
                break;
            }
            count++;
            dicGraphCount[key][0] = count;
        }
        //for(int i =0; i <dicGraph.length;i++){
            //System.out.println(dicGraph[key][i]);
        //}
    }
    static int startNum(String value){            //convert first letter into number alphabetically
        char startLetter = value.charAt(0);
        int key = startLetter;
        if (key<97){
            key=key+32;         //gets index of fist letter for dictionary
        }
        key-=97;
        return key;
    }
    static void oddMergeSort(int key, long[]LstPerLetter, int start, int finish){         //Sort Num Dic by size 
        if (start<finish){
            int middle = (start+(finish-1))/2;
            oddMergeSort(key, LstPerLetter,start ,middle);
            oddMergeSort(key, LstPerLetter,middle+1 ,finish);
            dicGraphSave = oddMerge(key, LstPerLetter,start ,finish, middle);
        }

    }
    static long[] oddMerge(int key, long[] LstPerLetter, int start, int finish, int middle){
        int lSize =middle - start+1;
        int rSize =finish - middle;
        long[] left = new long[lSize];
        long[] right = new long[rSize];
        for(int i = 0;i<LstPerLetter.length;i++){
            
        }
        for(int i = 0;i<lSize;i++){
            left[i]=LstPerLetter[start+i];
        }
        for(int q = 0;q<rSize;q++){
            right[q]=LstPerLetter[q+middle+1];
        }
        int x = 0; int y =0; int current = start;
        while(x<lSize && y <rSize){
            if (left[x] <= right[y]){
                LstPerLetter[current] = left[x];
                x++;
            }
            else if (left[x] > right[y]){
                LstPerLetter[current] = right[y];
                y++;
            }
            current+=1;
        }
        while(x<lSize){
            LstPerLetter[current]= left[x];
            x++;
            current+=1;
        }
        while(y<rSize){
            LstPerLetter[current]= right[y];
            y++;
            current+=1;
        }
        return LstPerLetter;
    }
    static String[][] BFS(String word){     //search of keyboard graph, returning list of list of keys x dist from key. each slot in outer list for each letter in word 
        int[] tfList = new int[26];
        int[] npath = new int[26];
        int[] dist = new int[26];
        ArrayList<ArrayList<String>> distSave = new ArrayList<ArrayList<String>>(20);
        String[] letters = word.split("");
        String save;
        Queue<String> queue = new LinkedList<>();
        for(int y = 0;y<letters.length;y++){//for each letter in word
            for (int x = 0; x<keyGraph.length;x++){ //initialize values of visited, path number and distance lists
                tfList[x]=-1;
                npath[x]=0;
                dist[x]=-1;
            }
            ArrayList<String> strLst = new ArrayList<String>();
            distSave.add(strLst);
            //System.out.println(distSave); 
            int distSaveLength = 0;
            int position = startNum(letters[y]);
            int current = 0;
            tfList[position] = 0;
            dist[position]=0;
            queue.add(letters[y]);//pass first letter to queue
            while(!queue.isEmpty()){
                save = queue.poll();
                current =startNum(save);
                for (int i =0; i<keyGraph[current].length;i++){
                    int x = startNum(keyGraph[current][i]);
                    if (tfList[x] == -1){
                        tfList[x]=0;
                        dist[x] = dist[current]+1;
                        npath[x] = npath[current]+1; 
                        if(dist[x]<2){      //only keeps values within 1-2 keys from start
                            distSave.get(y).add(keyGraph[current][i]);
                        }else{break;}
                        queue.add(keyGraph[current][i]);
                        
                    }
                }
            }
        }
        String[][] distSaveArray = new String[distSave.size()][];
        for(int x = 0; x<distSave.size();x++){
            distSaveArray[x] = distSave.get(x).toArray(new String[distSave.get(x).size()]) ;   
        }
        return distSaveArray;
    }
    static String bruteForceSearch(String[] word){
        int index = startNum(word[0]);
        System.out.println("index is "+ index);
        int minDist = 1000;
        int current;
        String best = "All ass";
        String [] dicSplit;
        for (int i= 0; i<dicGraph[index].length;i++){
            dicSplit = dicGraphStr[index][i].split("");
            System.out.println("typed word is "+ word);
            System.out.println("Dic word is "+ dicGraphStr[index][i]);
            current = 0;
            for (int x= 0; x<word.length;x++){
                System.out.println("current is "+ current);
                System.out.println("typed letter "+ word[x]);
                System.out.println("Dictionary letter "+ dicSplit[x]);
                current+=distBFS(word[x], dicSplit[x]);
                System.out.println("current is now "+ current);
            }
            if(minDist > current){
                System.out.println("New best");
                minDist = current;
                best = dicGraphStr[index][i];
            }
        }
        return best;
    }
    static int distBFS(String typedLetter, String dicLetter){     //search of keyboard graph, returning list of list of keys x dist from key. each slot in outer list for each letter in word 
        int[] tfList = new int[26];
        int[] npath = new int[26];
        int[] dist = new int[26];
        ArrayList<ArrayList<String>> distSave = new ArrayList<ArrayList<String>>(20);
        String save;
        Queue<String> queue = new LinkedList<>();
        for (int x = 0; x<keyGraph.length;x++){ //initialize values of visited, path number and distance lists
            tfList[x]=-1;
            npath[x]=0;
            dist[x]=-1;
        }
        ArrayList<String> strLst = new ArrayList<String>();
        distSave.add(strLst);
        //System.out.println(distSave); 
        int distSaveLength = 0;
        int position = startNum(typedLetter);
        int current = 0;
        tfList[position] = 0;
        dist[position]=0;
        queue.add(typedLetter);//pass first letter to queue
        while(!queue.isEmpty()){
            save = queue.poll();
            current =startNum(save);
            for (int i =0; i<keyGraph[current].length;i++){
                int x = startNum(keyGraph[current][i]);
                if (tfList[x] == -1){
                    tfList[x]=0;
                    dist[x] = dist[current]+1;
                    npath[x] = npath[current]+1; 
                    if(dicLetter.equals(keyGraph[current][i])){      //only keeps values within 1-2 keys from start
                        System.out.println("dist[x];");
                        return dist[x];
                    }
                    queue.add(keyGraph[current][i]);
                    
                }
            }
        }
        System.out.println("dist[position] is "+dist[position]);
        return dist[position];
    }
    static String mutationSearch(String[][] keyOptions, String[] letters){
        ArrayList<Integer> tfList = new ArrayList<Integer>();
        ArrayList<Integer> npath = new ArrayList<>();
        ArrayList<Integer> dist = new ArrayList<>();
        for (int x = 0; x<keyOptions.length;x++){ //initialize values of visited, path number and distance lists
          tfList.add(-1);
          npath.add(0);
          dist.add(100);
          }
          Queue<List<String>> queue = new LinkedList<>();
          List<String> tempLst = new ArrayList<>();
          List<String> letterStart = new ArrayList<>();
          for(int i =0;i<letters.length;i++ ){
            letterStart.add(letters[i]);            //passes initial word to queue, letter by letter  [a],[a,b],[a,b,c]
            queue.add(letterStart);
            System.out.println(letterStart);
          }
          
          ArrayList<ArrayList<String>> totalLst = new ArrayList<ArrayList<String>>();
          
          
          int tSize = 0 ;
          int current = 1;      //bc all of first slot is added below 
          boolean first = true;
          for(int i =0;i<keyOptions[0].length;i++ ){
            List <String> startLst = new ArrayList<>();
            if(first == true){
                startLst.add(letters[0]);       //adds first letter and all connections
                first = false;
                i--;
                for(int z = 0;z<startLst.size();z++){
                    System.out.println(startLst.get(z));
                }
            }else{
                startLst.add(keyOptions[0][i]);
                for(int z = 0;z<startLst.size();z++){                  
                    System.out.println(startLst.get(z));
                }
            }
            //System.out.println("StartLst "+startLst);
            //startLst.add(keyOptions[0][i]);
            queue.add(startLst);
          }

          boolean stop = false;
          boolean firstVisit =true;
          //queue.add(startLst);//pass a list to queue
        while(!queue.isEmpty()&&stop == false){
              tempLst = queue.poll();
              current = tempLst.size();
             // if(!totalLst.contains(tempLst)){  //if the path is new, store it to the list of total paths
                if (current==keyOptions.length){
                    totalLst.add(new ArrayList<String>());
                    totalLst.get(tSize).addAll(tempLst);
                    int key = startNum(tempLst.get(0));
                    String newWord = "";
                    long newNum;
                    for(int z = 0;z<tempLst.size();z++){
                        //System.out.println(tempLst.get(z));
                        newWord = newWord+tempLst.get(z);
                    }
                    //System.out.println(newWord);
                    newNum = convertToNum(newWord);
                    stop = oddBinarySearch(dicGraph[key], 0, dicGraph[key].length-1, newNum);
                    if(stop == true){
                        return newWord;
                    }
                    newWord="";
                    //System.out.println(totalLst.get(tSize));
                    //current++;
                    //if (tSize>0 && tSize<300){System.out.println(totalLst.get(tSize));}else if(tSize>300){break;}
                    tSize+=1;
                }
                //if (tempLst.size()<dist.get(current)){  //if new path to node v is smaller than saved path to node v, replace it 
                  //dist.set(current, tempLst.size());
                  //npath.set(current, 1);
                //}else if(tempLst.size()==dist.get(current)){npath.set(current, npath.get(current)+1);}//if its the same size as saved, update number of paths
              //}
              //current++;
              //System.out.println("current == "+current);
              if (keyOptions.length>current){//System.out.println("keyOptions[current].length == "+keyOptions[current].length);
              
              for (int i =0; i<keyOptions[current].length;i++){
                  //if (!tempLst.contains(keyOptions[current][i])){
                    List<String> nextLst = new ArrayList<>(tempLst); //make new list containing the current list
                    if (firstVisit == true){
                    nextLst.add(letters[current]);
                    firstVisit = false;
                    i--;
                    }else{nextLst.add(keyOptions[current][i]);}
                    
                      //nextLst.add(keyOptions[current][i]);  //for every direct connection, add it and pass to queue
                      if(nextLst.size()>keyOptions.length){System.out.println("nextLst.size()>keyOptions.length");stop = true;break;}
                      //if(!queue.contains(nextLst)){queue.add(nextLst);}
                      queue.add(nextLst);
                      
                      //System.out.println(nextLst);
                    //}
                  }
                  firstVisit = true;
                  //current++;
                }
                //firstVisit = true;
              }
              //System.out.println("tSize == "+tSize);
              String[] doneLst = new String[totalLst.size()];
              for(int i =0; i<totalLst.size();i++){
                String done = "";
                for(int x =0; x<totalLst.get(i).size();x++){        
                done = done+totalLst.get(i).get(x);
                }
                doneLst[i] = done;
              }
              return "cant find";
    }
    static boolean oddBinarySearch(long[]LstPerLetter, int left, int right, long word){         //Search dic for word
        if (LstPerLetter.length>0 && word>99){
        if (left<right){
            int middle = (left+(right))/2;
            long current = LstPerLetter[middle];
            long numWord = word;

            if (current  == numWord ){
                return true;
            }
            if (current > numWord ){
                return oddBinarySearch(LstPerLetter,left ,middle,numWord);
            }
            if (current < numWord ){
                return oddBinarySearch(LstPerLetter,middle+1 ,right,numWord);
            }
        }
    }
        return false;
    }
    static long convertToNum(String  word){
        String[] splitStr = word.split("");
        String done = "";
        String letterAsNum;
        for(int i=0;i<splitStr.length;i++){
            char charLetter = word.charAt(i);
            int ascii = charLetter;
            int index = ascii;
            if (index<97){index+=32;}
            index-=97;
            if(index<10){
                letterAsNum = "0"+Integer.toString(index);  
            }else{
                letterAsNum = Integer.toString(index); 
            }
            done = done+letterAsNum;
        }
        long doneAsNum = Long.parseLong(done);
        return doneAsNum;
    }

    public static void main(String[] args){
        keyboard_Graph(); 
        try {
            File dictionary = new File("Dictionary.txt");
            Scanner line = new Scanner(dictionary); 
            line.nextLine();
            initCount();
            while (line.hasNextLine()){
                String word = line.nextLine();
                createStrDic(word);
                createNumDic(word);
            }
            line.close();
            removeZero();
            for(int key=0;key<dicGraph.length;key++){
                oddMergeSort(key, dicGraph[key], 0, dicGraph[key].length-1);
                if (dicGraphSave.length!=0){dicGraph[key] = dicGraphSave;}
                dicGraphSave = new long[0];
            }
            //for(int i = 0;i<dicGraph.length; i++){
                //System.out.println("The start of new letter");
                //for(int z = 0;z<dicGraph[i].length; z++){
                    //System.out.printf("%d%s",dicGraph[i][z]," , ");
                //}
                //System.out.println();
                //System.out.println();
            //}
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error");
            e.printStackTrace();
        }
        System.out.println("Type"); 
        Scanner input = new Scanner(System.in);
            String[] in = input.nextLine().split(" ");
            int size = in.length;
            long[] numbers = new long [size];
            boolean realWord;
            String[] out = new String [size];
            String [] [] keyOptions;
            String newWord = "";
            long newNum;
            int count = 0;
            boolean NewRealWord = false;
            for (int i = 0; i<in.length;i++){
                numbers[i] = convertToNum(in[i]);
                //System.out.println(in[i]+" is now "+numbers[i]);
            }
            for (int i = 0; i<in.length;i++){
                //System.out.println("Start of for loop");
                int key = startNum(in[i]);
                realWord = oddBinarySearch(dicGraph[key], 0, dicGraph[key].length-1, numbers[i]);
                if (realWord == true){
                    System.out.println(in[i]+" is a real word");
                    out[i] = in[i];
                }
                if (realWord == false){
                    String[] letters = in[i].split("");
                    keyOptions = BFS(in[i]);
                    String option = bruteForceSearch(letters);     //mutationSearch should binary search for each possibility, break and return word if real word. idk how that would track distance tho
                    out[i] = option;
                    }
                    //count = 0;
                }
            for (int i = 0; i<in.length;i++){
                System.out.print(out[i]+" , ");
            }
            }
            
            //System.out.println(size); 
            //String[][] letters= new String[size][];
            //for(int i =0; i<size;i++){
                //for(int z =0; z<in.length;z++){
                    //letters[z] = in[z].split(""); 
                //}
                //for(int z =0; z<letters[i].length;z++){
                    //System.out.println(letters[i][z]); 
                //}
                //System.out.println();
            //}
            //System.out.println();
            //for(int i=0; i<letters.length;i++){
                //for(int z=0; z<letters[i].length;z++){                
                //BFS(letters[i][z]);
                //}
            //}
        //input.close();
    }

