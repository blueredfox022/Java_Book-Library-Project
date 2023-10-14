package com.Perpustakaan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class Model {

    String judulBuku;
    String namaPenulis;
    String tahunTerbit;
    String warning;

    public void SetJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;

    }

    public void SetNamaPenulis(String namaPenulis) {
        this.namaPenulis = namaPenulis;

    }

    public void SettahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public boolean isExist(String dataFile, String line) {
        return dataFile.toLowerCase().contains(line.toLowerCase());
    }

    public String GetWarning() {
        return this.warning;
    }

    private void SetWarning(String warning) {
        this.warning = warning;
    }

    private boolean DeletingFle() throws IOException {
        File file = new File("data/data.txt");
        try {
            if (!file.delete()) {
                System.out.println("Gagal menghapus berkas");
                return false;
            }
        } catch (SecurityException e) {
            System.out.println("Gagal menghapus berkas: Izin tidak mencukupi" +
                    e.getMessage());
        }
        return true;
    }

    public boolean isEmpty() {

        if (judulBuku == null || judulBuku.isEmpty()
                || tahunTerbit == null || tahunTerbit.isEmpty()
                || namaPenulis == null
                || namaPenulis.isEmpty()) {
            SetWarning("terdapat inputan yang kosong");
            return true;
        }

        return false;

    }

    public boolean isValidToAdd() throws IOException {

        FileReader readerFile = new FileReader("data/data.txt");
        BufferedReader bufferFile = new BufferedReader(readerFile);
        String dataFile;
        boolean isFound = false;
        String[] keyword = { judulBuku, tahunTerbit, namaPenulis };

        while ((dataFile = bufferFile.readLine()) != null) {
            for (String pair : keyword) {
                isFound = !isExist(dataFile, pair);
            }

        }

        bufferFile.close();
        readerFile.close();
        if (!isFound) {
            SetWarning("data buku sudah ada");

        }
        return isFound;

    }

    public boolean YearIsVaild() {
        boolean yearValid = false;
        try {
            Year parsedYear = Year.parse(tahunTerbit);
            Year currentYear = Year.now();
            if (parsedYear.isAfter(currentYear)) {
                // throw new IllegalArgumentException("invalid Year");
                SetWarning("Tahun tidak boleh lebih besar dari tahun sekarang.");
                return false;
            }
            yearValid = true;
        } catch (DateTimeParseException e) {
            SetWarning("Tahun harus berupa 4 angka/digit. EX=(YYYY)");
        } catch (IllegalArgumentException e) {
            SetWarning(e.getMessage());
        }
        return yearValid;
    }

    public boolean AddData() throws IOException {
        try {

            File file = new File("data/data.txt");
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(judulBuku + "-" + tahunTerbit + "-" + namaPenulis);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();

            // bufferedWriter.close();
        } catch (Exception e) {
            // TODO: handle exception
            SetWarning("your input is : " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean RenameFile(File oriFile) throws IOException {
        File filetemp = new File("data/temp.txt");
        try {
            if (!filetemp.renameTo(oriFile)) {
                System.out.println("Gagal melakukan rename");
                return false;
            }
        } catch (SecurityException e) {
            System.out.println("Gagal melakukan rename: Izin tidak mencukupi" +
                    e.getMessage());
        }

        return true;

    }

    public boolean CekFile() throws IOException {
        File file = new File("data/data.txt");
        return file.exists();

    }

    public boolean DeleteDataFromFile(int numDel) throws IOException {
        boolean result = false;
        File fileOri = new File("data/data.txt");
        FileReader readerOri = new FileReader(fileOri);
        BufferedReader buffOri = new BufferedReader(readerOri);

        File filetemp = new File("data/temp.txt");
        filetemp.createNewFile();
        FileWriter writerTemp = new FileWriter(filetemp);
        BufferedWriter buffWriter = new BufferedWriter(writerTemp);

        String data;
        int count = 0;

        while ((data = buffOri.readLine()) != null) {
            count++;
            if (count != numDel) {
                buffWriter.write(data);
                buffWriter.newLine();
            }
        }
        buffWriter.flush();

        buffWriter.close();
        buffOri.close();

        if (DeletingFle()) {
            if (RenameFile(fileOri)) {
                result = true;
            }

        }
        return result;
    }

    private boolean InputStr(String numString) {
        return numString.isEmpty();

    }

    public boolean InputStrArr(String[] ArrKey) {
        boolean result = false;
        for (String key : ArrKey) {
            result = !InputStr(key);
        }
        if (!result) {
            SetWarning("Input tidak boleh kosong");
        }
        return result;
    }

    public boolean IsvalidInput(String numStr) {
        File namaFile = new File("data/data.txt");
        try {
            if (InputStr(numStr)) {
                SetWarning("input tidak boleh kosong");
                return false;

            }
            FileReader fileReader = new FileReader(namaFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int dataCount = Integer.parseInt(numStr);

            int jumlahData = 0;
            while (bufferedReader.readLine() != null) {
                // Di sini Anda dapat melakukan pengolahan data sesuai kebutuhan.
                // Misalnya, jika setiap baris adalah satu data, Anda dapat menambahkan 1 ke
                // jumlahData.
                jumlahData++;
            }
            bufferedReader.close();
            if (dataCount > jumlahData || dataCount <= 0) {
                SetWarning("Input tidak boleh kurang dari 0 dan lebih dari jumlah data");
                return false;

            }

        } catch (IOException e) {
            SetWarning("gagal Membuka file");
            return false;
        } catch (NumberFormatException e) {
            // TODO: handle exception
            SetWarning("input harus angka");
            return false;

        }
        return true;

    }

    public boolean EditData(int numData) throws IOException {

        boolean result = false;
        File fileOri = new File("data/data.txt");
        FileReader readerOri = new FileReader(fileOri);
        BufferedReader buffOri = new BufferedReader(readerOri);

        File filetemp = new File("data/temp.txt");
        filetemp.createNewFile();
        FileWriter writerTemp = new FileWriter(filetemp);
        BufferedWriter buffWriter = new BufferedWriter(writerTemp);

        String data;
        int count = 0;

        while ((data = buffOri.readLine()) != null) {
            count++;
            if (count == numData) {
                String newData = new String(judulBuku + "-" + tahunTerbit + "-" + namaPenulis);
                data = data.replace(data, newData);
            }
            buffWriter.write(data);
            buffWriter.newLine();

        }

        buffWriter.flush();

        buffWriter.close();
        buffOri.close();

        if (DeletingFle()) {
            if (RenameFile(fileOri)) {
                result = true;
            }

        }
        return result;

    }

}