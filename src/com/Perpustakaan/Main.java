package com.Perpustakaan;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world");
        Model model = new Model();
        View view = new View();
        Control control = new Control(model, view);
        control.Run();
    }

}
