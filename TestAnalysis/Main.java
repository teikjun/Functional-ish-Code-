import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;

class Main {
    static boolean checkEnd = false;
    static int start = 0; 
    static int end = 10; 
    static boolean shouldPrint = false;
        
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> studentList = new ArrayList<Student>();
    static ArrayList<Mark> markList = new ArrayList<Mark>();
    static ArrayList<Student> absentList = new ArrayList<Student>();

    public static void main(String[] args) {
        while (sc.hasNext()) {
            String nextInput = sc.next();
            if (!nextInput.equals("end")) {
                String plab = nextInput;
                String id = sc.next();
                int group = sc.nextInt();
                Student s = new Student(plab, id, group);
                studentList.add(s);                    
            } else {
                break;
            }
        }

        while (sc.hasNext()) {
            String nextMarkInput = sc.next();
            if (!nextMarkInput.equals("end")) {
                String plab = nextMarkInput;
                int mark = sc.nextInt();
                Mark m = new Mark(plab, mark);
                markList.add(m);                    
            } else {
                break;
            }
        } 

        //print groups
        long numOfGroups = studentList.stream()
            .mapToInt(x -> x.getGroup())
            .distinct()
            .count();

        Stream<Integer> groupStringStream = studentList.stream()
            .map(x -> x.getGroup())
            .sorted()
            .distinct();
        
        String groupString = groupStringStream
            .map(x -> "" + x)
            .reduce((x, y) -> x + ", " + y)
            .get();
        System.out.println("Groups(" + numOfGroups + "):[" + groupString + "]");

        //print students
        studentList.stream()
            .forEach(s ->  { 
                System.out.printf(s + ",");
                Optional<Integer> optMark = markList
                    .stream()
                    .filter(m -> m.getPlab().equals(s.getPlab()))
                    .map(x -> x.getMark())
                    .findFirst();
                int mark = 0;
                if (optMark.isPresent()) {
                    mark = optMark.get();
                } else { 
                    absentList.add(s);
                }
                System.out.println("" + mark);
            });
       //print absentees 
        System.out.println("List of absentees:");
        if (absentList.stream().count() == 0) {
            System.out.println("None");
        } else {
            absentList.stream()
                .forEach(a -> System.out.println(a));
        }
        
        //print mark frequency table 
        IntStream.rangeClosed(0, 10)
            .forEach(x -> {
                long numOfStudents = markList
                    .stream()
                    .filter(m -> m.getMark() == x)
                    .count();
                if (numOfStudents > 0) {
                    if (!checkEnd) {
                        setStart(x);
                        setEnd(x);
                        setCheckEnd(true);
                    } else {
                        setEnd(x);
                    }
                }
            });
        System.out.println("Mark frequency from " + start + " to " + end);
        IntStream.rangeClosed(start, end) 
            .forEach(x -> {
                System.out.printf(x + " : ");
                long numOfStudents = markList
                    .stream()
                    .filter(m -> m.getMark() == x)
                    .count();
                System.out.println(numOfStudents);
        });

        //print mark frequency for individual groups 
         
         List<Integer> groupList = studentList.stream()
             .map(y -> y.getGroup()) 
             .sorted() 
             .distinct()
             .collect(Collectors.toList());
         
         for (Integer i : groupList) {
            //List of students who are in group i and took exam
            List<Student> groupExamStudentList = studentList.stream()
                .filter(s -> s.getGroup() == (int) i)
                .filter(s -> markList.stream().anyMatch(m -> m.getPlab().equals(s.getPlab()))) 
                .collect(Collectors.toList());
              
            if (groupExamStudentList.stream().count() > 0) { 
                //groupStudentList.stream().forEach(s -> System.out.println(s));
                System.out.println("Group #" + i + "...Mark frequency from " + start + " to " + end);
                IntStream.rangeClosed(start, end) 
                    .forEach(x -> {
                        System.out.printf(x + " : ");
                        //number of students who have mark x and belong in the list of students in group i and took exam
                        long numOfStudents = markList
                            .stream()
                            .filter(m -> m.getMark() == x)
                            .filter(m -> groupExamStudentList.stream().anyMatch(z -> z.getPlab().equals(m.getPlab())))
                            .count();
                        System.out.println(numOfStudents);
                });
            }
        }
    }
    //setter methods
    static void setStart(int x) {
        start = x;
    } 
    static void setEnd(int y) {
        end = y;
    } 
    static void setCheckEnd(boolean z) {
        checkEnd = z;
    }
}
