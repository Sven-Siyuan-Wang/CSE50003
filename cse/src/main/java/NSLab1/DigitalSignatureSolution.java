package NSLab1;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;



public class DigitalSignatureSolution {

    public static void main(String[] args) throws Exception {
//Read the text file and save to String data
        String directory = "/Users/WangSiyuan/AndroidStudioProjects/Term5/cse/src/main/java/NSLab1/";
        String fileName = directory+ "largeSize.txt";
            String data = "";
            String line;
            BufferedReader bufferedReader = new BufferedReader( new FileReader(fileName));
            while((line= bufferedReader.readLine())!=null){
                data = data +"\n" + line;
            }
            //System.out.println("Original content: " + data);

//TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();


//TODO: Calculate message digest, using MD5 hash function
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(data.getBytes());
        System.out.println("Original digest: "+DatatypeConverter.printBase64Binary(digest));

//TODO: print the length of output digest byte[], compare the length of file smallSize.txt and largeSize.txt

        System.out.println("Length of file: "+data.getBytes().length);
        System.out.println("Length of digest byte[]: "+digest.length);


           
//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.
//TODO: encrypt digest message
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(digest);



//TODO: print the encrypted message (in base64format String using DatatypeConverter)
        String base64format = DatatypeConverter.printBase64Binary(encryptedBytes);
        System.out.println("Length of signed digest byte[]: "+encryptedBytes.length);
        System.out.println("encrypted msg in base64format: "+base64format);

//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.           
//TODO: decrypt message
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);


//TODO: print the decrypted message (in base64format String using DatatypeConverter), compare with origin digest 
        String decryptedMsg = DatatypeConverter.printBase64Binary(decryptedBytes);
        System.out.println("decryptedMsg in base64format: "+decryptedMsg);



    }

}
