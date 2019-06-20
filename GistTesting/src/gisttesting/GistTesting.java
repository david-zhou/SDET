/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gisttesting;

/**
 *
 * @author davidzhou
 */
public class GistTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        ReadPropertyFile.loadFile("/Users/davidzhou/GitHub/SDET/GistTesting/config.properties");
//        CRUDGistUI selenium = new CRUDGistUI();
//        selenium.login();
//        selenium.addGist("Descripcion gist 1", "Archivo 1 gist", "Nomas una linea", true);
//        selenium.addGist("Description gist 2", "Archivo 2 gist", "Helloworld", true);
//        selenium.editGistByName("Archivo 1 gist", "nueva description", "nuevoarchivo.txt", "nuevocodigo");
//        selenium.deleteGistByName("Archivo 2 gist");
        
        ReadPropertyFile.loadTokenFile("/Users/davidzhou/GitHub/SDET/GistTesting/tokenfile.properties");
        CRUDGistAPI api = new CRUDGistAPI();
        api.addGistAPI("Descripcion gist api 1", true, "archivo1api.txt", "Codigo \\nSegunda linea");
        
    }
    
}
