/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.checker;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 *
 * @author nguye
 */
public class CheckWellformed {
    
    
//    public void testWellformed(String urlString) throws IOException {
//        URL url = new URL(urlString);
//        URLConnection connection = url.openConnection();
//        connection.setReadTimeout(8*1000);
//        connection.setConnectTimeout(8*1000);
//        
//        String textContent = getString(connection.getInputStream());
//        
//        textContent = TextUtils.refineHtml(textContent);
//                
//        if(checkWellformedXml(textContent)) {
//            System.out.println(urlString + "wellformed");
//        }
//    }
   
    public String documentWellformed(String document) {
        document = TextUtils.refineHtml(document);
        checkWellformedXml(document);
        return document;
    }
    
//    private String getString(InputStream stream) {
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        
//        try(BufferedReader bufferReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
//            while ((line = bufferReader.readLine()) != null) {                
//                stringBuilder.append(line);
//            }
//        } catch (IOException ignore) {
//        }
//        
//        return stringBuilder.toString();
//    }
    
    private boolean checkWellformedXml(String src) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
       
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }
        });
        
        try {
            builder.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));
            return true;
        } catch (SAXException | IOException e) {
            return false;
        }
    }
    
}
