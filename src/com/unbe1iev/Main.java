package com.unbe1iev;

import com.unbe1iev.Controller.EmployeeController;
import com.unbe1iev.Employee.Director;
import com.unbe1iev.Employee.Tradesman;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;
import static java.lang.System.out;

public class Main {

    public static boolean verifyPesel(String p) {
        return (p.charAt(0) + 3 * p.charAt(1) + 7 * p.charAt(2) + 9 * p.charAt(3) + p.charAt(4) + 3 * p.charAt(5) + 7 * p.charAt(6) + 9 * p.charAt(7) + p.charAt(8) + 3 * p.charAt(9) + p.charAt(10)) % 10 == 0;
    }

    public static void saveEmployee(String argSave, Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(argSave);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(obj);
        oos.close();
        fos.close();
    }

    public static <T> T loadObjectFromFile(int n, String ext) throws IOException {
        T employee = null;

        FileInputStream fis = new FileInputStream("employee" + n + "." + ext);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            employee = (T) ois.readObject();
            fis.close();
            ois.close();
        } catch(IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }

        return employee;
    }

    public static <T> void main(String[] args) throws IOException {
        String operation = null;

        HashMap<String, T> employees = new HashMap<>();
        EmployeeController controller = new EmployeeController();
        Scanner scanner = new Scanner(System.in);

        do {
            out.println("\nMENU");
            out.println("\t1. Employees list");
            out.println("\t2. Add an employee");
            out.println("\t3. Delete an employee");
            out.println("\t4. Backup");
            out.println("\t5. Exit the program");

            out.print("\nOperation> ");
            try {
                operation = scanner.next();

            } catch (InputMismatchException | NumberFormatException e) {
                out.println("Entered incorrectly operation! Try again.");
                continue;
            }

            switch (operation) {
                case "1":
                    out.println("1. Employees list\n");
                    if (employees.size() == 0) { out.println("{ Empty } "); break; }

                    int i = 1;
                    for (Map.Entry<String, T> entry : employees.entrySet()) {
                        controller.setEmployee(entry.getValue());
                        controller.displayAll();
                        out.format("\n%31s[Pozycja: " + i + "/" + employees.size() + "]\n", "");
                        out.println("[Enter] - Next");
                        out.println("[Q] - Quit");

                        out.print("\nOperation> ");
                        char inChar = 'E';
                        try{ inChar = (char) System.in.read();}
                        catch(Exception e){}

                        if (inChar == 'Q' || inChar == 'q') break;

                        i++;
                    }

                    break;
                case "2":
                    do {
                        out.print("Director/Tradesman? [Type D/T]: ");
                        char employeeType = scanner.next().charAt(0);

                        String pesel;
                        if (employeeType == 'D') {
                            Director d = new Director();

                            do {
                                out.print("PESEL: ");
                                pesel = scanner.next();
                                char[] p = new char[pesel.length()];
                                if (verifyPesel(String.valueOf(p))) break;
                                else out.println("Entered pesel incorectly!");
                            } while (true);

                            d.setPesel(pesel);
                            out.print("Name: "); d.setName(scanner.next());
                            out.print("Surname: "); d.setSurname(scanner.next());
                            d.setPosition("Director");
                            out.print("Base salary (zł): "); d.setSalary(scanner.nextBigDecimal());
                            out.print("Service phone number: "); d.setServicePhoneNumber(scanner.next());
                            out.print("Service Bonus (zł): "); d.setServiceBonus(scanner.nextBigDecimal());
                            out.print("Service Card Number: "); d.setServiceCardNumber(scanner.next());
                            out.print("Cost limit/per month (zł): "); d.setCostLimitPerMonth(scanner.nextBigDecimal());
                            out.println("------------------------------------------------------------------");
                            controller.setEmployee(d);
                            controller.displayAll();
                            out.println("------------------------------------------------------------------");
                            out.println("[Enter] - Save");
                            out.println("[Q] - Abort");

                            out.print("\nOperation> ");
                            char inChar = 'E';
                            try{ inChar = (char) System.in.read();}
                            catch(InputMismatchException | NumberFormatException e){
                                out.println("Entered incorrectly operation! Try again.");
                            }

                            if (inChar != '\n' && inChar != 'Q' && inChar != 'q'){ out.println("Operation failed. Entered incorectly!"); break; }
                            if (inChar == 'Q' || inChar == 'q') break;

                            employees.put(d.getPesel(), (T) d);

                            break;
                        } else if (employeeType == 'T') {
                            Tradesman t = new Tradesman();


                            do {
                                out.print("PESEL: ");
                                pesel = scanner.next();
                                char[] p = new char[pesel.length()];
                                if (verifyPesel(String.valueOf(p))) break;
                                else out.println("Entered pesel incorectly!");
                            } while (true);

                            t.setPesel(pesel);
                            out.print("Name: "); t.setName(scanner.next());
                            out.print("Surname: "); t.setSurname(scanner.next());
                            t.setPosition("Tradesman");
                            out.print("Base salary (zł): "); t.setSalary(scanner.nextBigDecimal());
                            out.print("Service phone number: "); t.setServicePhoneNumber(scanner.next());
                            out.print("Commission (%): "); t.setCommissionInPercent(scanner.nextBigDecimal());
                            out.print("Commission limit/per month (zł): "); t.setCommissionLimit(scanner.nextBigDecimal());

                            out.println("------------------------------------------------------------------");
                            controller.setEmployee(t);
                            controller.displayAll();
                            out.println("------------------------------------------------------------------");
                            out.println("[Enter] - Save");
                            out.println("[Q] - Abort");

                            out.print("\nOperation> ");
                            char inChar = 'E';
                            try{ inChar = (char) System.in.read();}
                            catch(InputMismatchException | NumberFormatException e){
                                out.println("Entered incorrectly operation! Try again.");
                            }

                            if (inChar != '\n' && inChar != 'Q' && inChar != 'q'){ out.println("Operation failed. Entered incorectly!"); break; }
                            if (inChar == 'Q' || inChar == 'q') break;

                            employees.put(t.getPesel(), (T) t);
                            break;
                        } else out.println("Wrong argument type entered!");
                    } while (true);
                    break;
                case "3":
                    String toDelete;
                    out.print("Enter PESEL: ");

                    toDelete = scanner.next();

                    out.println("------------------------------------------------------------------");
                    controller.setEmployee(employees.get(toDelete));
                    controller.displayAll();
                    out.println("------------------------------------------------------------------");
                    out.println("[Enter] - Confirm");
                    out.println("[Q] - Abort");

                    out.print("\nOperation> ");
                    char inChar = 'E';
                    try{ inChar = (char) System.in.read();}
                    catch(InputMismatchException | NumberFormatException e){
                        out.println("Entered incorrectly operation! Try again.");
                    }

                    if (inChar != '\n' && inChar != 'Q' && inChar != 'q'){ out.println("Operation failed. Entered incorectly!"); break; }
                    if (inChar == 'Q' || inChar == 'q') break;

                    employees.remove(toDelete);
                    break;
                case "4":
                    out.println("\n4. Backup");
                    out.print("\tSave[S/s]/Load[L/l]: ");
                    char inChar1 = 'E';
                    try{ inChar1 = (char) System.in.read();}
                    catch(InputMismatchException | NumberFormatException e){
                        out.println("Entered incorrectly operation!"); break;
                    }

                    if (inChar1 != 'S' && inChar1 != 's' && inChar1 != 'L' && inChar1 != 'l'){ out.println("Operation failed. Entered incorectly!"); break; }

                    if (inChar1 == 'S' || inChar1 == 's') {
                        out.println("\t------------------------------------------------------------------");
                        File directory = new File("./");

                        // list all files present in the directory
                        File[] files = directory.listFiles();

                        for(File file : files) {
                            String fileName = file.toString();

                            int index = fileName.lastIndexOf('.');
                            if(index > 0) {
                                String prototypeZIP = ".zip";
                                String prototypeGZIP = ".gzip";

                                if (fileName.contains(prototypeGZIP)) {
                                    out.println("\t" + fileName);
                                }

                                if (fileName.contains(prototypeZIP)) {
                                    out.println("\t" + fileName);
                                }
                            }
                        }
                        out.println("\t------------------------------------------------------------------");
                        out.println("[Enter] - Confirm");
                        out.println("[Q] - Abort");

                        System.in.read();
                        out.print("\nOperation> ");
                        char inChar2 = 'E';
                        try{ inChar2 = (char) System.in.read();}
                        catch(InputMismatchException | NumberFormatException e){
                            out.println("Entered incorrectly operation! Try again.");
                        }

                        if (inChar2 != '\n' && inChar2 != 'Q' && inChar2 != 'q'){ out.println("Operation failed. Entered incorectly!"); break; }
                        if (inChar2 == 'Q' || inChar2 == 'q') break;

                        //Serialization of objects:

                        String ext = "";
                        out.print("\tWhich extension [zip/gzip]?: "); ext = scanner.next();

                        ExecutorService executor = Executors.newFixedThreadPool(10);
                        CompletableFuture[] cfTable = new CompletableFuture[employees.size()];

                        int n = 0;
                        for (Map.Entry<String, T> entry : employees.entrySet()) {
                            String finalExt = ext;
                            int finalN = n;
                            cfTable[n] = CompletableFuture.runAsync(() -> {
                                try {
                                    String argSave = "employee" + finalN + "." + finalExt;
                                    saveEmployee(argSave, entry.getValue());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }, executor);
                            n++;
                        }

                        n = 0;
                        for (int l=0; l<cfTable.length; l++) {
                            try {
                                cfTable[n].get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            n++;
                        }

                        out.printf("Data saved.");

                    } else if (inChar1 == 'L' || inChar1 == 'l') {

                        out.println("\t------------------------------------------------------------------");
                        File directory = new File("./");

                        // list all files present in the directory
                        File[] files = directory.listFiles();
                        int numberOfZIPFiles = 0;
                        int numberOfGZIPFiles = 0;

                        for(File file : files) {
                            String fileName = file.toString();

                            int index = fileName.lastIndexOf('.');
                            if(index > 0) {
                                String prototypeZIP = ".zip";
                                String prototypeGZIP = ".gzip";

                                if (fileName.contains(prototypeGZIP)) {
                                    out.println("\t" + fileName);
                                    numberOfGZIPFiles++;
                                }

                                if (fileName.contains(prototypeZIP)) {
                                    out.println("\t" + fileName);
                                    numberOfZIPFiles++;
                                }
                            }
                        }
                        out.println("\t------------------------------------------------------------------");
                        out.println("[Enter] - Confirm");
                        out.println("[Q] - Abort");

                        System.in.read();
                        out.print("\nOperation> ");
                        char inChar2 = 'E';
                        try{ inChar2 = (char) System.in.read();}
                        catch(InputMismatchException | NumberFormatException e){
                            out.println("Entered incorrectly operation! Try again.");
                        }

                        if (inChar2 != '\n' && inChar2 != 'Q' && inChar2 != 'q'){ out.println("Operation failed. Entered incorectly!"); break; }
                        if (inChar2 == 'Q' || inChar2 == 'q') break;

                        //Deserialization of objects:

                        String ext = "";
                        out.print("\tFrom which extension [zip/gzip]?: "); ext = scanner.next();

                        int cfSize = 0;
                        if (ext.equals("gzip")) cfSize = numberOfGZIPFiles;
                        if (ext.equals("zip")) cfSize = numberOfZIPFiles;

                        ExecutorService executor = Executors.newFixedThreadPool(10);
                        List<CompletableFuture<T>> cfList = new ArrayList<>();

                        for (int s =0; s<cfSize; s++) {
                            final T[] employee = (T[]) new Object[]{null};
                            String finalExt = ext;
                            int finalS = s;

                            cfList.add(CompletableFuture.supplyAsync(() -> {
                                try {
                                    employee[0] = loadObjectFromFile(finalS, finalExt);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                return employee[0];
                            }, executor));
                        }

                        for (int s=0; s<cfSize; s++) {
                            try {
                                T employee;
                                employee = (T) cfList.get(s).get();

                                if (employee instanceof Director) {
                                    Director d;
                                    d = (Director) employee;
                                    employees.put(d.getPesel(), (T) d);
                                }
                                if (employee instanceof Tradesman) {
                                    Tradesman t;
                                    t = (Tradesman) employee;
                                    employees.put(t.getPesel(), (T) t);
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }

                        if (ext.equals("gzip") && numberOfGZIPFiles == 0) out.println("There is no file/data in folder!");
                        else if (ext.equals("zip") && numberOfZIPFiles == 0) out.println("There is no file/data in folder!");
                        else out.printf("Data loaded.");

                    } else { out.println("Operation failed. Entered incorectly!"); break; }


                    break;
                case "5":
                    // Leave
                    scanner.close();
                    exit(0);
                    break;
                default:
                    out.println("There is no such an operation!");
            }
        } while (true);
    }
}
