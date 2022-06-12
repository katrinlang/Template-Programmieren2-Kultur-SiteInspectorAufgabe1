package org.htw.prog2.aufgabe2;

import java.io.File;

public class SiteInspector {

    public static void main(String[] args) {

        File[] imageFiles= new File[16];

        for(int i= 1; i<=imageFiles.length; i++){
            try{
                imageFiles[i-1]= new File("data/Blombos/engraving-" + i + ".png");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        ImageSeries imageSeries = new ImageSeries(imageFiles);
        // imageSeries.writeFrames(0, imageFiles.length, true, true, true);
    }
}
