import java.io.*;
import java.util.*;

class Data{
  String TaskName;
  String TaskStatus;

  @Override
  public String toString(){
    return "TASK NAME: " + TaskName +"\n" +"TASK STATUS: " + TaskStatus;
  }
}

public class ToDoList {
  public static void main(String[] args) throws IOException {
  Scanner sc=new Scanner(System.in);

  File fileObj = new File("To-Do-Text.txt");
  if(fileObj.createNewFile()){
      System.out.println('\n'+"NEW FILE CREATED");
    }
  else{
      System.out.println("FILE ALREADY CREATED");
    }
  
  boolean ToContinue=true;
  do { 
    System.out.println('\n' +"1.INSERT");
    System.out.println("2.VIEW ALL TASK");
    System.out.println("3.MARK AS COMPLETED");
    System.out.println("4.DELETE A TASK");
    System.out.println("5.EXIT" +'\n' );

    System.out.print("ENTER YOUR OPTION: ");
    int Option =sc.nextInt();
    sc.nextLine();

    switch (Option) {
        //Write in Text File 
        case 1:

            //Object for SubClass
            Data DataObj = new Data();

            System.out.print('\n' + "ENTER TASK NAME: ");
            DataObj.TaskName=sc.nextLine();

            System.out.print("ENTER TASK STATUS: ");
            DataObj.TaskStatus=sc.nextLine();

            //Writing and Append Operation
            BufferedWriter wr =new BufferedWriter(new FileWriter(fileObj,true));

            wr.write(DataObj.toString() +"\n" +"-----------" + "\n");
            wr.newLine();
            wr.close();
            break;

        //Read In Text File
        case 2:
            System.out.println();
            BufferedReader br =new BufferedReader(new FileReader(fileObj));
            String ReadByLine;
            while ((ReadByLine = br.readLine()) !=null) {
                System.out.println(ReadByLine);             
            }
            br.close();
            break;

        //Updation In Text File (1.Read the file,2.Update the file,3.Write the file)
        case 3:
            //ArrayList created to read file
            ArrayList<String>ToDoArrayList=new ArrayList<>();

            boolean TaskFound= false;

            BufferedReader UpdateReader=new BufferedReader(new FileReader("To-Do-Text.txt"));
            String UpdateString;

            while ((UpdateString=UpdateReader.readLine())!=null) { 
                ToDoArrayList.add(UpdateString);
            }
            UpdateReader.close();

            System.out.print("ENTER THE TASK NAME TO BE MARK AS COMPLETED (AS YOU ENTERED IN THE TASK LIST): ");
            String ToUpdateTaskName=sc.nextLine().trim();

            //update value in ArrayList First
            for(int i=0;i<ToDoArrayList.size();i++){
              if(ToDoArrayList.get(i).startsWith("TASK NAME: ") && ToDoArrayList.get(i).substring(10).trim().equalsIgnoreCase(ToUpdateTaskName)){
                  if((i + 1)<ToDoArrayList.size() && ToDoArrayList.get(i+1).startsWith("TASK STATUS: ")){
                    ToDoArrayList.set(i+1, "TASK STATUS: COMPLETED");
                    System.out.println('\n'+"UPDATION TASK COMPLETED SUCCESSFULLY");
                    TaskFound=true;
                    break;
                  }
              }
            }
            if(!TaskFound){
                System.out.println("TASK NOT FOUND,CHECK YOUR INPUT DATA ");
            }

            if(TaskFound){
                //update the updated ArrayList value to the text file
                BufferedWriter UpdateFile=new BufferedWriter(new FileWriter("To-Do-Text.txt"));
                for(String index: ToDoArrayList){
                  UpdateFile.write(index);
                  UpdateFile.newLine();
                }
                UpdateFile.close();
                break;
            }
            break;
        case 4:
            System.out.println("ENTER 1 TO DELETE SINGLE TASK,ENTER 0 TO DELETE ALL TASK");
            System.out.print("ENTER YOUR OPTION: ");
            int DeleteOption=sc.nextInt();
            sc.nextLine();
            if (DeleteOption == 0) {
              fileObj.delete();
              if(fileObj.createNewFile()){
                System.out.println("\n" +" EXISTED FILE DELETED AND NEW FILE CREATED" + "\n");
              }
              System.out.println("ALL TASK DELETED SUCCESSFULLY");
              break;
            }

            if (DeleteOption == 1) {
                ArrayList<String>ToDeleteArrayList=new ArrayList<>();

                boolean DeleteTask=false;
    
                BufferedReader DeleteReader=new BufferedReader(new FileReader(fileObj.getPath()));
                String DeleteString;
  
                  while ((DeleteString=DeleteReader.readLine())!=null) { 
                      ToDeleteArrayList.add(DeleteString);
                  }
                  DeleteReader.close();
      
                  System.out.print("ENTER THE TASK NAME TO BE DELETED (AS YOU ENTERED IN THE TASK LIST): ");
                  String ToDeleteTaskName=sc.nextLine().trim();
      
                  //delete value in ArrayList First
                  for(int i=0;i<ToDeleteArrayList.size();i++){
                      if(ToDeleteArrayList.get(i).startsWith("TASK NAME: ") && ToDeleteArrayList.get(i).substring(11).trim().equals(ToDeleteTaskName)){
                          ToDeleteArrayList.remove(i); //Delete Task Name

                          if(i<ToDeleteArrayList.size()&& ToDeleteArrayList.get(i).startsWith("TASK STATUS: ")){
                              ToDeleteArrayList.remove(i); //Delete Task Status
                          }
                          if(i<ToDeleteArrayList.size() && ToDeleteArrayList.get(i).startsWith("-----------")){
                              ToDeleteArrayList.remove(i);
                          }
                          DeleteTask=true;
                          break;
                      }

                  }
      
                  if(!DeleteTask){
                      System.out.println("TASK NOT FOUBD TO DELETE,CHECK YOUR INPUT DATA ");
                  }
      
                  if(DeleteTask){
                      //update the updated ArrayList value to the text file
                      BufferedWriter DeleteFile=new BufferedWriter(new FileWriter("To-Do-Text.txt"));
                      for(String index: ToDeleteArrayList){
                          DeleteFile.write(index);
                          DeleteFile.newLine();
                      }
                      DeleteFile.close();
                  }
            }   

            break;
        case 5:    
              System.out.println("BYE, WELCOME......");
            ToContinue = false;
            break;
    }                
  } while (ToContinue);
  sc.close();
  }  
}
