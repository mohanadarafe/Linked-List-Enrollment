package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EnrolmentResults {

    //This method allows us to count the amount of lines in a file.
    public static int courseCounter (String file) throws FileNotFoundException{
        int counter = 0;
        Scanner sc = new Scanner(new FileInputStream(file));

            while(sc.hasNextLine()){
                counter++;
                sc.nextLine();
            }

        sc.close();
        return counter;
    }

    public static void main(String[] args) {

        //Create at least two empty lists from the CourseList class (needed for copy constructor III (e));
        CourseList CL1 = new CourseList();
        CourseList CL2 = new CourseList();

        //Open the Syllabus.txt file and input all information
        FileInputStream syllabusIn = null;
        int courseLines = 0;
        try {
            syllabusIn = new FileInputStream("Syllabus.txt");
            courseLines = courseCounter("Syllabus.txt");
        } catch (FileNotFoundException e) {
            System.out.println("We cannot find this File!");
        }

        Scanner sc = new Scanner(syllabusIn);
        String line = null;
        String courseName = null;
        String courseID = null;
        String preReq = null;
        String coReq = null;
        double courseCredits = 0;

        for (int i = 1; i <= courseLines; i++) {
            line = sc.nextLine();
            //CourseName & Course Credits
            if (line.length() > 10) {
                courseName = line;
                courseName = courseName.replaceAll("_", " ").replaceAll("\t", "").substring(7);

                if (line.contains(".")) {
                    courseName = courseName.substring(0, courseName.length() - 3);
                    courseCredits = Double.parseDouble(line.substring(line.length() - 3));
                } else {
                    courseName = courseName.substring(0, courseName.length() - 1);
                    courseCredits = Double.parseDouble(line.substring(line.length() - 1));
                }
                courseID = line.substring(0, 7);
            } else {
                //coRequisites & preRequisites
                if (line.length() > 3 && line.substring(0, 1).equals("P")) {
                    preReq = line.substring(1).replaceAll("\t", "");
                }
                if (line.length() > 3 && line.substring(0, 1).equals("C")) {
                    coReq = line.substring(1).replaceAll("\t", "");
                }
                //If C or P fields are empty
                if (line.startsWith("C") && line.length() < 5)
                    coReq = null;
                if (line.startsWith("P") && line.length() < 5)
                    preReq = null;
            }
            if(line.startsWith("C") && line.length() < 10 ) {
                Course c = new Course(courseID, courseName, courseCredits, preReq, coReq);
                if(CL1.size() == 0) {
                    CL1.addToStart(c);
                }
                else {
                    CL1.insertAtIndex(c, CL1.size());
                }
            }
        }
        //If two the same courses are in the linked list, this method will delete one.
        CL1.duplicateEntries(CL1);
        sc.close();

        //Prompt user to open a request file.
        Scanner keyIn = new Scanner(System.in);
        FileInputStream reqText = null;
        System.out.println("Please enter the name of the Request file: ");
        String request = keyIn.nextLine();

        int reqlines = 0;
        try {
            reqText = new FileInputStream(request);
            reqlines = courseCounter(request);
        } catch (FileNotFoundException e) {
            System.out.println("The " + request + " file has not been found!");
            System.exit(0);
        }

        Scanner sc1 = new Scanner(reqText);
        ArrayList<String> requestedlist = new ArrayList<String>();
        ArrayList<String> finishedlist = new ArrayList<String>();
        String currentLine = null;
        boolean valid = true;
        int counter = 0;

        //We shall create a requestedList array which contains all courses requested.
        while (valid) {
            currentLine = sc1.nextLine();
            if (!currentLine.equals("Requested"))
                finishedlist.add(currentLine);
            else
                valid = false;
            counter++;
        }
        finishedlist.remove("Finished");

        //We shall created a finishedList array which contains all courses finished.
        for (int i = counter; i < reqlines; i++) {
            requestedlist.add(sc1.nextLine());
        }
        sc1.close();

        //Now we shall start the outcome process.
        CL1.canEnroll(requestedlist, finishedlist, CL1);

    }
}
