package com.phase3.game;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;


public class HuffmanCode {
    private static String codes[];
    private static int numOfBits=0, serializedTreeBits=0, index=0, beforeSize=0, afterSize=0;
    private static StringBuilder serializedData;

    public static void compress(File file){
        resetData();
        // Calculate frequency of each char in the text
        int freq[] = fillFrequencies(file);
        // Min heap for two minimum frequency
        MinHeap minHeap = constructMinHeap(freq);
        // Construct binary tree from heap
        TNode root = constructTree(minHeap);
        // Construct code from binary tree and store it in a dictionary
        if(root.getLeft()==null && root.getRight()==null) {
            codes[root.getCharacter()] = "1";
            numOfBits=freq[root.getCharacter()];
            serializedData.append('1');
            serializedData.append(getBinaryString(root.getCharacter()));
            serializedTreeBits+=9;
        }
        else
            getCharsCode(root, "", freq, serializedData);
        HuffmanFx huffmanFx = new HuffmanFx(freq, codes);
        // Construct encoded text
        fillFinalBytes(file);
    }
    private static int[] fillFrequencies(File file){
        int freq[] = new int[256];
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8]; // Buffer to hold 8 bytes
            int bytesRead;

            while ((bytesRead=fis.read(buffer)) != -1) {
                // Process the 8 bytes read
                for (int i = 0; i < bytesRead; i++) {
                    freq[buffer[i] & 0xFF]++; // Increase frequency of each char
                    beforeSize+=8;
                }
            }
            return freq;
        } catch (IOException e) {
            return null;
        }
    }
    private static void fillFinalBytes(File file){
        File compressedFile=saveCompressedFile(file);
        if(compressedFile==null) return;
        int numOfFilledBits = 0;
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8]; // Buffer to hold 8 bytes
            int bytesRead;
            byte temp=0;
            try(FileOutputStream fos = new FileOutputStream(compressedFile, true)) {
                while ((bytesRead=fis.read(buffer)) != -1) {
                    // Process the 8 bytes read
                    for (int i = 0; i < bytesRead; i++) {
                        int index = buffer[i] & 0xFF;
                        int count=codes[index].length();
                        for(int j=0;j<count;j++){
                            if(codes[index].charAt(j)=='1')
                                temp = (byte) (temp | (1 << 7-numOfFilledBits%8));
                            else temp = (byte) (temp & ~(1 << 7-numOfFilledBits%8));
                            numOfFilledBits++;
                            if(numOfFilledBits%8==0){
                                fos.write(temp);
                                temp=0;
                            }
                        }
                    }
                }
                if(numOfFilledBits%8!=0){
                    fos.write(temp);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Huffman Compression");
                alert.setHeaderText("Compressed File");
                alert.setContentText("The file is compressed successfully!");
                alert.showAndWait();
            }catch (IOException e) {}
        } catch (IOException e) {
        }
    }
    private static File saveCompressedFile(File file) {
        // Create the compressed file with .huff extension
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if(lastDotIndex!=-1) fileName=fileName.substring(0, lastDotIndex);
        File compressedFile = new File(file.getParent()+File.separator+fileName + ".huff");
        String fileExtension = file.toString().split("\\.")[1];

        try {
            if (compressedFile.createNewFile()) {
                // Open file output stream
                FileOutputStream fos = new FileOutputStream(compressedFile.toString());

                // Write the header
                // The size of the serialized tree (4 bytes, int)
                fos.write(intToBytes(serializedTreeBits));

                // The serialized tree
                byte dataByte=0, counter=7;
                for(int i=0; i<serializedTreeBits; i++) {
                    if(serializedData.charAt(i)=='1')
                        dataByte = (byte) (dataByte | (1<<counter));
                    else if(serializedData.charAt(i)=='0')
                        dataByte = (byte) (dataByte & ~(1<<counter));
                    counter--;
                    if(counter==-1){
                        fos.write(dataByte);
                        counter=7;
                        dataByte=0;
                    }
                }
                if(counter<7) fos.write(dataByte);

                // The length of the file extension (1 byte)
                fos.write(fileExtension.length());

                // The file extension
                fos.write(fileExtension.getBytes());

                // The needed bits from the last encoded byte
                byte neededBits = (byte)(numOfBits%8);
                if(neededBits==0) neededBits=8;
                fos.write(neededBits);

                HeaderFx headerFx = new HeaderFx(fileExtension.length(), fileExtension, serializedTreeBits,
                        serializedData.toString(), neededBits);
                afterSize = ((serializedTreeBits+7)/8)*8+((numOfBits+7)/8)*8+48+fileExtension.length();
                StatFx statFx = new StatFx(beforeSize, afterSize);

                fos.close();
                return compressedFile;
            } else {
                // Display error alert if the file already exists
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("File already exists");
                alert.showAndWait();
                return null;
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong");
            alert.showAndWait();
            return null;
        }
    }

    // Helper method to convert an integer to array of 4 bytes
    private static byte[] intToBytes(int value) {
        return new byte[] {
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }
    private static MinHeap constructMinHeap(int freq[]){
        MinHeap minHeap = new MinHeap();
        for(int i = 0; i < 256; i++){
            if(freq[i] > 0){
                minHeap.insert(new TNode(freq[i], (char)i, null, null));
            }
        }
        return minHeap;
    }
    private static TNode constructTree(MinHeap minHeap){
        while(!minHeap.hasOneElement()){
            TNode min1 = minHeap.pull();
            TNode min2 = minHeap.pull();
            minHeap.insert(new TNode(min1.getValue()+min2.getValue(), min1, min2));
        }
        return minHeap.pull();
    }
    private static void getCharsCode(TNode node, String code, int freq[], StringBuilder serializedTree){
        if(node==null) return;
        if(node.getRight()==null && node.getLeft()==null) {
            codes[node.getCharacter()] = code;
            numOfBits+=code.length()*freq[node.getCharacter()];
            serializedTree.append('1');
            serializedTree.append(getBinaryString(node.getCharacter()));
            serializedTreeBits+=8;
        }
        else
            serializedTree.append('0');
        serializedTreeBits++;
        getCharsCode(node.getLeft(), code+'0', freq, serializedTree);
        getCharsCode(node.getRight(), code+'1', freq, serializedTree);
    }
    private static String getBinaryString(char ch) {
        // Convert the character to an integer and get its binary string
        String binaryString = Integer.toBinaryString(ch);
        // Add leading zeros
        while (binaryString.length() < 8) {
            binaryString = "0" + binaryString;
        }
        return binaryString;
    }

//-----------------------------------------------------------------------------------------------------
//------------------------------------------ DECOMPRESSION --------------------------------------------
//-----------------------------------------------------------------------------------------------------

    public static void decompress(File file){
        resetData();
        readCompressedFile(file);
    }
    public static void readCompressedFile(File compressedFile) {
        try (FileInputStream fis = new FileInputStream(compressedFile)) {
            // Read Tree Size (4 bytes)
            byte[] treeSizeBytes = fis.readNBytes(4);
            int treeSize = bytesToInt(treeSizeBytes);

            // Read Serialized Tree (treeSize bytes)
            String serializedTree = "";
            byte[] buffer = new byte[(treeSize+7)/8]; // Buffer to hold 8 bytes
            int bytesRead=fis.read(buffer);
                // Process the 8 bytes read
            for (int i = 0; i < bytesRead; i++) {
                String binaryString = String.format("%8s", Integer.toBinaryString(buffer[i] & 0xFF)).replace(' ', '0');
                serializedTree+=binaryString;
            }
            serializedTree=serializedTree.substring(0, treeSize);
            int ii=0;
            String tempTree="";
            while(ii<treeSize){
                if(serializedTree.charAt(ii)=='1'){
                    ii++;
                    tempTree+="1";
                    String binaryString=serializedTree.substring(ii,Math.min(ii+8, serializedTree.length()));
                    tempTree += (char) (Integer.parseInt(binaryString, 2) & 0xFF);
                    ii+=8;
                }
                else {
                    tempTree+="0";
                    ii++;
                }
            }
            serializedTree=tempTree;

            // Fill The Binary Tree
            TNode root;
            if(serializedTree.charAt(0)=='1') {
                root = new TNode();
                root.setRight(new TNode(serializedTree.charAt(1)));
            }
            else
                root = fillBinaryTree(serializedTree);

            // Read File Extension Length (1 byte)
            int fileExtLength = fis.read();

            // Read File Extension (fileExtLength bytes)
            byte[] fileExtBytes = fis.readNBytes(fileExtLength);
            String fileExtension = new String(fileExtBytes);

            // Read number of needed bits from the last byte
            byte neededBits = (byte)fis.read();

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files (*." + fileExtension + ")", "*." + fileExtension));
            // Show a save dialog
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if(selectedFile!=null) {
                buffer = new byte[8];
                int nextByte;
                TNode temp = root;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    nextByte = fis.read();  // To see if it's the last byte
                    // Iterate over the bytes read
                    for (int i = 0; i < bytesRead; i++) {
                        int end = 0;
                        if (nextByte == -1 && i == bytesRead - 1) end = 8-neededBits;
                        for (int j = 7; j >= end; j--) {
                            if ((buffer[i] & (1 << j)) == 0) {
                                root = root.getLeft();
                            } else {
                                root = root.getRight();
                            }
                            if (root.getLeft() == null && root.getRight() == null) {
                                try(FileOutputStream fos = new FileOutputStream(selectedFile, true)) {
                                    fos.write(root.getCharacter());
                                }catch (IOException e) {}
                                root = temp;
                            }
                        }
                    }
                    if (nextByte != -1)
                        fis.skip(-1);  // Skip the byte that was peeked
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static TNode fillBinaryTree(String serializedTree){
        if (index >= serializedTree.length())
            return null;

        char c = serializedTree.charAt(index++);

        if (c == '0') {
            // Internal node, recursively construct left and right children
            TNode left = fillBinaryTree(serializedTree);
            TNode right = fillBinaryTree(serializedTree);
            TNode internalNode = new TNode();
            internalNode.setLeft(left);
            internalNode.setRight(right);
            return internalNode;
        }
        // Leaf node, read the next character as the node's value
        char ch = serializedTree.charAt(index++);
        return new TNode(ch);
    }
    // Helper method to convert 4 bytes to an integer
    private static int bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                (bytes[3] & 0xFF);
    }

    private static void resetData(){
        serializedData=new StringBuilder();
        codes = new String[256];
        numOfBits=serializedTreeBits=index=beforeSize=afterSize=0;
    }
}

