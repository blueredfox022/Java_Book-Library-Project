package com.Perpustakaan;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class View {

    public String MenuMain() {

        System.out.println("1. Lihat semua buku");
        System.out.println("2. Telusuri buku");
        System.out.println("3. Tambah buku");
        System.out.println("4. Edit data buku");
        System.out.println("5. Hapus buku");
        System.out.println("0. Keluar");

        System.out.print("\nPilih : ");
        return System.console().readLine();

    }

    //// VOID PESAN
    public void Message(String message) {
        System.out.println(message);
    }

    // VOID GET JUDUL BUKU
    public String GetJudulBuku() {
        System.out.print("Masukkan nama buku:  ");
        return System.console().readLine();
    }

    public String GetJudulBuku(String message) {
        System.out.print(message);
        return System.console().readLine();
    }

    // VOID GET NAMA PENULIS
    public String GetNamaPenulis() {
        System.out.print("Masukkan nama penulis: ");
        return System.console().readLine();
    }

    public String GetNamaPenulis(String message) {
        System.out.print(message);
        return System.console().readLine();
    }

    // VOID GET TAHUN TERBIT

    public String GetTahunTerbit() {
        System.out.print("Masukkan tahun terbit: ");
        return System.console().readLine();
    }

    public String GetTahunTerbit(String message) {
        System.out.print(message);
        return System.console().readLine();
    }

    // VOID GETNUMBER
    public String GetNumber(String message) {
        System.out.print(message);
        return System.console().readLine();
    }

    public void ShowAllData() throws IOException {
        try {

            FileReader reader = new FileReader("data/data.txt");
            BufferedReader buffer = new BufferedReader(reader);
            String data;
            int count = 0;
            System.out.println("|=======================================================================|");
            System.out.println("| No |\t\tNama Buku\t|  Tahun  |\t    Penerbit\t        |");
            System.out.println("|=======================================================================|");
            while ((data = buffer.readLine()) != null) {
                count++;

                StringTokenizer strToken = new StringTokenizer(data, "-");
                String no = String.format("%-2d", count);
                String namaBuku = String.format("%-24s", strToken.nextToken());
                String tahun = String.format("%-6s", strToken.nextToken());
                String penerbit = String.format("%-27s", strToken.nextToken());

                System.out.println("| " + no + " | " + namaBuku + " |  " + tahun + " | " + penerbit + " |");
                System.out.println("|-----------------------------------------------------------------------|");

            }

            System.out.println("|=======================================================================|");
            buffer.close();
            reader.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public boolean getYesOrNo(String message) {
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("Tidak dapat mengakses konsol.");
        }
        String input = console.readLine("%s (y/n): ", message);
        while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
            console.format("Pilihan tidak valid.%n");
            input = console.readLine("%s (y/n): ", message);
        }
        return input.equalsIgnoreCase("y");
    }

    public void ExitingProgram() {
        System.out.println("program Selesai.....");
    }

    public String[] GetKeywordToSearch() {
        // mengambil keyword dari user
        Console console = System.console();
        System.out.print("Masukkan kata kunci untuk mencari Buku : ");
        String bookSearch = console.readLine();
        String[] keywords = bookSearch.split("\\s+");
        return keywords;

    }

    public void ShowDataFinded(String[] keyword) throws IOException {

        FileReader readerFile = new FileReader("data/data.txt");
        BufferedReader bufferFile = new BufferedReader(readerFile);
        Model model = new Model();

        String dataFile;
        int count = 0;
        boolean isExist = false;
        boolean isFound = false;
        System.out.println("|=======================================================================|");
        System.out.println("| No |\t\tNama Buku\t|  Tahun  |\t    Penerbit\t        |");
        System.out.println("|=======================================================================|");

        while ((dataFile = bufferFile.readLine()) != null) {
            isExist = true;
            for (String line : keyword) {
                isExist = isExist && model.isExist(dataFile, line);
            }
            if (isExist) {
                count++;
                StringTokenizer tokenStr = new StringTokenizer(dataFile, "-");
                String no = String.format("%-2d", count);
                String namaBuku = String.format("%-24s", tokenStr.nextToken());
                String tahun = String.format("%-6s", tokenStr.nextToken());
                String penerbit = String.format("%-27s", tokenStr.nextToken());
                System.out.println("| " + no + " | " + namaBuku + " |  " + tahun + " | " + penerbit + " |");
                isFound = true;
            }

        }

        if (!isFound) {
            System.out.println("|\t\t\t\tNOT FOUND\t\t\t\t|");
        }
        System.out.println("|=======================================================================|");

        bufferFile.close();
        readerFile.close();

    }

    public void ShowDataFinded(int num) throws IOException {

        FileReader readerFile = new FileReader("data/data.txt");
        BufferedReader bufferFile = new BufferedReader(readerFile);

        String dataFile;
        int count = 0;
        boolean isFound = false;
        System.out.println("|=======================================================================|");
        System.out.println("| No |\t\tNama Buku\t|  Tahun  |\t    Penerbit\t        |");
        System.out.println("|=======================================================================|");

        while ((dataFile = bufferFile.readLine()) != null) {
            count++;

            if (num == count) {
                StringTokenizer tokenStr = new StringTokenizer(dataFile, "-");
                String no = String.format("%-2d", count);
                String namaBuku = String.format("%-24s", tokenStr.nextToken());
                String tahun = String.format("%-6s", tokenStr.nextToken());
                String penerbit = String.format("%-27s", tokenStr.nextToken());
                System.out.println("| " + no + " | " + namaBuku + " |  " + tahun + " | " + penerbit + " |");
                isFound = true;
                break;

            }

        }

        if (!isFound) {
            System.out.println("|\t\t\t\tNOT FOUND\t\t\t\t|");
        }
        System.out.println("|=======================================================================|");

        bufferFile.close();
        readerFile.close();

    }

}
