/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gisttesting;

import java.io.IOException;

/**
 *
 * @author davidzhou
 */
public class GistTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        ReadPropertyFile.loadFile("/Users/davidzhou/GitHub/SDET/GistTesting/config.properties");
        
        
        CRUDGistUI selenium = new CRUDGistUI();
        selenium.login();
        selenium.addGist("Descripcion gist 1", "Archivo 1 gist", "Nomas una linea", true);
        selenium.editGistByName("Archivo 1 gist", "nueva description", "nuevoarchivo.txt", "nuevocodigo");
    }
    
}
