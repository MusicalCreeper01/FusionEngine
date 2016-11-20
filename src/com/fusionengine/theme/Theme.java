package com.fusionengine.theme;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Theme {

    public String name = "Unnamed Theme";
    public static Theme[] themes = new Theme[0];

    public String root;

    public class Background {
        public String src;
        public boolean repeat;

        public Background(String s, boolean b){
            this.src = s;
            this.repeat = b;
        }
    }

    public Background background = new Background("../../assets/textures/background.jpg", false);

    public static String themeLocation = "themes";

    public static void LoadThemes(){
        List<Theme> temp = new ArrayList<>();
        File[] themeDirectories = new File(themeLocation).listFiles(File::isDirectory);
        for(File file : themeDirectories) {
            String manifestPath = Paths.get(file.getAbsolutePath(), "theme.json").toString();

            System.out.println("Reading theme " + file.getName() + "...");

            File f = new File(manifestPath);
            if(f.exists() && !f.isDirectory()) {
                System.out.println("    Has manifest!");
                // Manifest exists, so this is a valid theme
                try {
                    System.out.println("    Reading theme.json...");
                    Gson gson = new Gson();
                    JsonReader reader = new JsonReader(new FileReader(manifestPath));
                    Theme theme = gson.fromJson(reader, Theme.class);
                    theme.root = file.getAbsolutePath().toString() + File.separatorChar;
                    System.out.println("    Read theme.json.");
                    temp.add(theme);
                }catch(FileNotFoundException ex){
                    System.out.println("");
                }

            }
        }
        themes = temp.toArray(new Theme[temp.size()]);
    }

}
