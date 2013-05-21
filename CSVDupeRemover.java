import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * @author AKT Developments
 * @version 1.1
 * @since May 21 2013
 */
public class CSVDupeRemover {
    private static String errs = "No" ;
    private static int col = 0;
    private static int rem = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String opath = "",dpath = "";
        if(args.length != 0){
            opath = args[0];
            dpath = opath.substring(0, opath.length() - 4) + "NoDupes.csv";
            col = Integer.parseInt(args[1]);
        }else{
            JFileChooser fc = new JFileChooser();
            fc.setMultiSelectionEnabled(false);
            int rv = fc.showOpenDialog(null);
            if(rv == JFileChooser.APPROVE_OPTION){
                opath = fc.getSelectedFile().getAbsolutePath();
                dpath = opath.substring(0, opath.length() - 4) + "NoDupes.csv";
            }else{
                System.exit(0);
            }  
            col = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Column number to search for duplicates in?"));
        }
        
        
        try{
            CSVReader cr = new CSVReader(new FileReader(opath));
            CSVWriter cw = new CSVWriter(new FileWriter(dpath));
            
            List<String[]> lessDupes = cr.readAll();
            cr.close();
            
            lessDupes = removeDupes(lessDupes);
            if(lessDupes != null){
                cw.writeAll(lessDupes);
                cw.close();
            }
            
            
        }catch(Exception ex){
             msgbox("Unable to find or read file. " + ex.getMessage());
             errs = "1";
        }
        msgbox("CSV Duplicate Remover has finished with: "+rem+" duplicates found and "+errs+" errors.");
    }
    
    private static List<String[]> removeDupes(List<String[]> lns){
        List<String[]> output = null;
        List<String[]> tmp = lns;
        for(int i=0;i<lns.size();i++){
            for(int x=0;x<tmp.size();x++){
                if(lns.get(i)[col].equals(tmp.get(x)[col]) && i!= x){
                    lns.remove(x);
                    rem++;
                }
            }
        }
        output = lns;
        return output;
    }
    
    private static void msgbox(String message){
        javax.swing.JOptionPane.showMessageDialog(null,message);
    }
}
