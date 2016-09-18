package NSLab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;


public class DesSolution {
    public static void main(String[] args) throws Exception {
        String directory = "/Users/WangSiyuan/AndroidStudioProjects/Term5/cse/src/main/java/NSLab1/";
        String fileName = "smallSize.txt";
        String data = "";
        String line;
        BufferedReader bufferedReader = new BufferedReader( new FileReader(directory+fileName));
        while((line= bufferedReader.readLine())!=null){
            data = data +"\n" + line;
        }
        //System.out.println("Original content: "+ data);

//TODO: generate secret key using DES algorithm
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecretKey desKey = keyGen.generateKey();
        
//TODO: create cipher object, initialize the ciphers with the given key, choose encryption mode as DES
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, desKey);
//TODO: do encryption, by calling method Cipher.doFinal().
        //Q1
        System.out.println("Q1: Orginal text in byte[] format:"+data.getBytes());
        byte[] encryptedBytes =  desCipher.doFinal(data.getBytes());
        //Q2
        System.out.println("Q2: encryptedBytes: "+encryptedBytes);//garbage
        System.out.println("Q3: newString(cipherBytes): "+new String(encryptedBytes));//not printable
        System.out.println("");

//TODO: print the length of output encrypted byte[], compare the length of file smallSize.txt and largeSize.txt
        System.out.println("Length of original data: "+data.getBytes().length);
        System.out.println("Length of encryptedBytes: "+encryptedBytes.length);
//TODO: do format conversion. Turn the encrypted byte[] format into base64format String using DatatypeConverter
        String base64format = DatatypeConverter.printBase64Binary(encryptedBytes);

//TODO: print the encrypted message (in base64format String format)

        //Q4
        System.out.println("Q4: Cipher text: " + base64format);
//TODO: create cipher object, initialize the ciphers with the given key, choose decryption mode as DES

        desCipher.init(Cipher.DECRYPT_MODE, desKey);  //Is it ok to reuse?


//TODO: do decryption, by calling method Cipher.doFinal().

        byte[] decryptedBytes = desCipher.doFinal(encryptedBytes);
        //Q5
        System.out.println("Q5: decryptedBytes"+decryptedBytes);

//TODO: do format conversion. Convert the decrypted byte[] to String, using "String a = new String(byte_array);"

        String decrptedText = new String(decryptedBytes);
//TODO: print the decrypted String text and compare it with original text

        //System.out.println("Decrypted text: "+decrptedText);


        String fileName2 = "largeSize.txt";
        String data2 = "";
        String line2;
        BufferedReader bufferedReader2 = new BufferedReader( new FileReader(directory+fileName2));
        while((line2= bufferedReader2.readLine())!=null){
            data2 = data2 +"\n" + line2;
        }

        desCipher.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] encryptedBytes2 =  desCipher.doFinal(data2.getBytes());
        System.out.println("Length of original bytes[]: "+data2.getBytes().length);
        System.out.println("Q6 length of encrpted byte[] for largeSize.txt: "+encryptedBytes2.length);

    }
}