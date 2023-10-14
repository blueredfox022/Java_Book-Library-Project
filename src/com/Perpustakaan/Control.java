package com.Perpustakaan;

import java.io.IOException;

public class Control {

    Model model;
    View view;

    public Control(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Jika menjalankan di Windows, gunakan perintah "cls" untuk membersihkan layar
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Jika menjalankan di sistem lain, gunakan perintah "clear" untuk membersihkan
                // layar
                ProcessBuilder pb = new ProcessBuilder("clear");
                pb.inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Tangani pengecualian jika terjadi kesalahan
            System.err.println(e);
        }
    }

    public void Run() throws IOException {
        boolean lanjutkan = true;
        while (lanjutkan) {
            clearScreen();
            String menu = view.MenuMain();
            switch (menu) {
                case "1":
                    Read();
                    break;
                case "2":
                    FindThedataBook();
                    break;
                case "3":
                    Create();
                    break;

                case "4":
                    Update();
                    break;

                case "5":
                    Delete();
                    break;
                case "0":
                    ExitingProgram();
                    break;
                default:
                    view.Message("Pilihan tidak ada");
                    break;

            }
            lanjutkan = view.getYesOrNo("Kembali? ");
        }
        view.ExitingProgram();

    }

    private void ExitingProgram() {
        if (view.getYesOrNo("anda yakin Ingin keluar?")) {
            view.ExitingProgram();
            System.exit(0);
        }
    }

    private void Create() throws IOException {
        view.Message("Tambah buku");

        model.SetJudulBuku(view.GetJudulBuku());
        model.SetNamaPenulis(view.GetNamaPenulis());
        model.SettahunTerbit(view.GetTahunTerbit());

        if (!model.isEmpty() && model.isValidToAdd() && model.YearIsVaild()) {
            if (view.getYesOrNo("Yakin ingin menambahkan?")) {
                if (model.AddData()) {
                    view.Message("Data buku berhasil ditambahkan");
                }
            }
        } else {
            view.Message("Warning!!  " + model.GetWarning());
            view.Message("gagal membuat data baru");
            return;
        }

    }

    private void Read() throws IOException {

        view.Message("lihat semua buku");
        if (!model.CekFile()) {
            FileNotFound();
        }
        view.ShowAllData();
    }

    private void FileNotFound() throws IOException {
        view.Message("Terjari kesalahan membuka File atau File tidak tersedia");
        if (view.getYesOrNo("ingin membuat data baru?")) {
            Create();
        }

    }

    private void Update() throws IOException {
        view.Message("Edit  buku");
        boolean isValid = false;
        if (model.CekFile()) {
            view.ShowAllData();
            String dataSt;
            while (!isValid) {
                dataSt = view.GetNumber("Pilih nomor yang ingin diedit: ");
                if (model.IsvalidInput(dataSt)) {
                    int num = Integer.parseInt(dataSt);
                    view.ShowDataFinded(num);
                    model.SetJudulBuku(view.GetJudulBuku());
                    model.SetNamaPenulis(view.GetNamaPenulis());
                    model.SettahunTerbit(view.GetTahunTerbit());
                    if (!model.isEmpty() && model.isValidToAdd() && model.YearIsVaild()) {
                        if (!view.getYesOrNo("Anda yakin Ingin mengganti?")) {
                            return;
                        }
                        if (!model.EditData(num)) {
                            view.Message("Warning!!  " + model.GetWarning());
                            return;
                        }
                        view.Message("berhasil mengedit data buku");
                        isValid = !view.getYesOrNo("Ingin Edit lagi?");
                    } else {
                        view.Message("Warning!!  " + model.GetWarning());
                        view.Message("gagal membuat data baru");
                        isValid = !view.getYesOrNo("ingin Ulangi?");
                    }
                } else {
                    view.Message("WARNING!!  " + model.GetWarning());
                    isValid = !view.getYesOrNo("ingin Ulangi?");
                }

            }
        } else {
            FileNotFound();

        }

    }

    private void Delete() throws IOException {
        view.Message("Hapus data buku");
        boolean isValid = false;
        if (model.CekFile()) {
            view.ShowAllData();
            while (!isValid) {
                String dataStr = view.GetNumber("pilih nomor yang ingin dihapus: ");
                int dataNum = Integer.parseInt(dataStr);
                view.ShowDataFinded(dataNum);
                if (!model.IsvalidInput(dataStr)) {
                    view.Message("WARNING!!" + model.GetWarning());
                    return;
                }
                if (!view.getYesOrNo("Anda yakin ingin menghapus?")) {
                    return;
                }
                if (!model.DeleteDataFromFile(dataNum)) {
                    view.Message("WARNING!!" + model.GetWarning());
                    return;
                }
                view.Message("berhasil menghapus data");
                isValid = !view.getYesOrNo("Ingin menghapus data lagi?");
            }
        } else {
            FileNotFound();
        }

    }

    private void FindThedataBook() throws IOException {
        view.Message("Telusuri  buku");
        boolean findTheBook = false;
        if (!model.CekFile()) {
            FileNotFound();
        } else {
            while (!findTheBook) {
                String[] keyword = view.GetKeywordToSearch();
                if (model.InputStrArr(keyword)) {
                    view.ShowDataFinded(keyword);
                } else {
                    view.Message("WARNING!!  " + model.GetWarning());
                }
                findTheBook = !view.getYesOrNo("Ingin mencari lagi? ");

            }

        }

    }

}
