package com.unbe1iev;

import com.unbe1iev.Controller.EmployeeController;
import com.unbe1iev.Employee.Director;
import com.unbe1iev.Employee.Tradesman;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import static com.unbe1iev.Main.loadObjectFromFile;
import static com.unbe1iev.Main.saveEmployee;
import static java.lang.System.out;

class MainTest<T> {
    HashMap<String, T> employees = new HashMap<>();
    EmployeeController controller = new EmployeeController();

    /*@Test
    void saveEmployee() {
    }

    @Test
    void loadObjectFromFile() {
    }*/

    //1.1
    @Test
    public void addTradesmanToEmptyHM() {
        Tradesman t = new Tradesman("00331514216", "Marta", "Bykowska", "Tradesman", BigDecimal.valueOf(123), "222333111",
                BigDecimal.valueOf(3709), BigDecimal.valueOf(2000));
        employees.put(t.getPesel(), (T) t);
    }

    //1.2
    @Test
    public void addDirectorToEmptyHM() {
        Director d = new Director("00331514217", "Przemek", "Byk", "Director", BigDecimal.valueOf(123), "333444555",
                BigDecimal.valueOf(345), "555", BigDecimal.valueOf(234));
        employees.put(d.getPesel(), (T) d);
    }

    //1.3
    @Test
    public void addAnotherTradesmanToHM() {
        addTradesmanToEmptyHM();
        Tradesman t = new Tradesman("00331514776", "Jola", "Mak", "Tradesman", BigDecimal.valueOf(666), "555768435",
                BigDecimal.valueOf(8854), BigDecimal.valueOf(2213));
        employees.put(t.getPesel(), (T) t);
    }

    //1.4
    @Test
    public void addAnotherDirectorToHM() {
        addDirectorToEmptyHM();
        Director d = new Director("00331514998", "Oskar", "Fasola", "Director", BigDecimal.valueOf(545), "667888929",
                BigDecimal.valueOf(444), "216", BigDecimal.valueOf(995));
        employees.put(d.getPesel(), (T) d);
    }

    //1.5
    @Test
    public void add10RandomTypesToHM() {
        Random random = new Random();
        Boolean choice = random.nextBoolean();

        double min = 10331514998.0;
        double max = 90331514998.0;
        int randNumber;
        String randPesel;

        for (int i=0; i<10; i++) {
            randNumber = (int) (random.nextInt((int) (max - min)) + min);
            randPesel = String.valueOf(randNumber);

            if (choice) employees.put(randPesel, (T) new Director());
            else employees.put(randPesel, (T) new Tradesman());
        }
    }

    //1.6
    @Test
    public void deleteOneTradesmanOfAll() {
        addAnotherTradesmanToHM();
        controller.setEmployee(employees.get("00331514216"));
        controller.displayAll(); //info about employee that we delete
    }

    //1.7
    @Test
    public void deleteOneDirectorOfAll() {
        addAnotherDirectorToHM();
        controller.setEmployee(employees.get("00331514217"));
        controller.displayAll(); //info about employee deletion that we delete
    }

    //1.8a
    @ParameterizedTest
    @ValueSource(strings =  {"00221804212", "63030772376", "00224973216", "68081241219", "00212338751", "00223333213", "00225573217", "64083172854", "79041156977", "57051637297"})
    void verifyGoodPesels(String p) {
        Assertions.assertEquals(0, (p.charAt(0) + 3 * p.charAt(1) + 7 * p.charAt(2) + 9 * p.charAt(3) + p.charAt(4) + 3 * p.charAt(5) + 7 * p.charAt(6) + 9 * p.charAt(7) + p.charAt(8) + 3 * p.charAt(9) + p.charAt(10)) % 10);
    }

    //1.8b
    @ParameterizedTest
    @ValueSource(strings =  {"00261804212", "00291764215", "00244973216", "00214973244", "00214933216", "00203333213", "00255573217", "00254999219", "00235571211", "00334983555"})
    void verifyBadPesels(String p) {
        Assertions.assertNotEquals(0, (p.charAt(0) + 3 * p.charAt(1) + 7 * p.charAt(2) + 9 * p.charAt(3) + p.charAt(4) + 3 * p.charAt(5) + 7 * p.charAt(6) + 9 * p.charAt(7) + p.charAt(8) + 3 * p.charAt(9) + p.charAt(10)) % 10);
    }

    static boolean isValid(final File file) {
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(file);
            return true;
        } catch (ZipException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (zipfile != null) {
                    zipfile.close();
                    zipfile = null;
                }
            } catch (IOException e) {
            }
        }
    }

    //1.9
    @Test
    void serializationValid(){
        addDirectorToEmptyHM();

        String ext = "zip";
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
    }

    //1.10
    @Test
    void deserializationValid(){
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

                if (fileName.contains(prototypeGZIP)) numberOfGZIPFiles++;
                if (fileName.contains(prototypeZIP)) numberOfZIPFiles++;

            }
        }

        String ext = "";
        ext = "zip";

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
        out.println(employees);
    }

    //1.11
    @Test
    void checkIfZipIsDamaged(){
        File file = new File("./employee0.zip");
        Assertions.assertFalse(isValid(file));
    }

    //1.12
    @Test
    public void shouldHaveProperErrorMessage_FindingAFile() { // it will generate error message with test failure
        try {
            String ext = "zip";
            int n = -1;
            FileInputStream fis = new FileInputStream("employee" + n + "." + ext);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Assertions.fail("Exception wasn't thrown!");
        } catch (Exception ex) {
            Assertions.assertEquals("Ooppss... File not found!", ex.getMessage());
        }
    }
}