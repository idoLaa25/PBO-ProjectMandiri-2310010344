 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BantuanSosial;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USER
 */
public class cruddonasi {
    
    private String database="pbo_2310010344";
    private String username="root";
    private String password="";
    private String url="jdbc:mysql://localhost/"+database;
    private Connection koneksidb;
    public String var_uang, var_barang, var_sembako = null;
    public boolean validasi = false;
    
    public cruddonasi (){
        try{
            Driver driverkoneksi = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driverkoneksi);
            koneksidb=DriverManager.getConnection(url, username, password);
            System.out.print("Berhasil koneksi");
    }catch (Exception e) {
        JOptionPane.showMessageDialog(null, "terjadi error:\n"+e.getMessage());
    } 
    }
    
        public void loadData(JTable tabel, String sql){
        try {
                Statement perintah=koneksidb.createStatement();
                ResultSet ds=perintah.executeQuery(sql);
                ResultSetMetaData data = ds.getMetaData();
                int kolom = data.getColumnCount();
                DefaultTableModel model=new DefaultTableModel();
                
//                for(int i = 1; i < kolom; i++){
//                    model.addColumn(data.getColumnName(i));
//                }
                model.addColumn("Id"); 
                model.addColumn("Uang");
                model.addColumn("Barang");
                model.addColumn("Sembako");
                model.getDataVector().clear();
                model.fireTableDataChanged();
                while (ds.next()){
                    Object[] baris = new Object[kolom];
                    for(int j = 1; j<= kolom; j++){
                        baris[j-1] = ds.getObject(j);
                    
                    }
                    model.addRow(baris);
                    tabel.setModel(model);
                }
            } catch (Exception e) {
            }
    }
    
public void simpanDonasiSTS(String id, String uang, String brg, String sbk) {
    try {
        String checkPrimary = "SELECT * FROM donasi WHERE Id='" + id + "'";
        Statement checkData = koneksidb.createStatement();
        ResultSet data = checkData.executeQuery(checkPrimary);

        if (data.next()) {
            String isi = "\nUang: " + data.getString("Uang")
                       + "\nBarang: " + data.getString("Barang")
                       + "\nSembako: " + data.getString("Sembako");
            JOptionPane.showMessageDialog(null, "ID sudah terdaftar" + isi);

            this.validasi = true;
            this.var_uang = data.getString("Uang");
            this.var_barang = data.getString("Barang");
            this.var_sembako = data.getString("Sembako");
        } else {
            String sql = "INSERT INTO donasi VALUES ('" + id + "', '" + uang + "', '" + brg + "', '" + sbk + "')";
            Statement perintah = koneksidb.createStatement();
            perintah.execute(sql);
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");

            this.validasi = false;
            this.var_uang = null;
            this.var_barang = null;
            this.var_sembako = null;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

public void simpanDonasiPST(String id, String uang, String brg, String sbk) {
    try {
        String checkPrimary = "SELECT * FROM donasi WHERE Id='" + id + "'";
        Statement checkData = koneksidb.createStatement();
        ResultSet data = checkData.executeQuery(checkPrimary);

        if (data.next()) {
            String isi = "\nUang: " + data.getString("Uang")
                       + "\nBarang: " + data.getString("Barang")
                       + "\nSembako: " + data.getString("Sembako");
            JOptionPane.showMessageDialog(null, "ID sudah terdaftar" + isi);

            this.validasi = true;
            this.var_uang = data.getString("Uang");
            this.var_barang = data.getString("Barang");
            this.var_sembako = data.getString("Sembako");
        } else {
            String sql = "INSERT INTO donasi (Id, Donasi_Uang, Donasi_Barang, Donasi_Sembako) VALUES (?, ?, ?, ?)";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, id);
            perintah.setString(2, uang);
            perintah.setString(3, brg);
            perintah.setString(4, sbk);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");

            this.validasi = false;
            this.var_uang = null;
            this.var_barang = null;
            this.var_sembako = null;
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}
        
     public void ubahDonasiSTS(String id, String uang, String brg, String sbk){
         try {
             String sqlubah = "Update donasi set Donasi_Uang = '"+uang+
                     "', Donasi_Barang = '"+brg+"', Donasi_Sembako = '"+sbk+"' where Id='"+id+"'";
             
             Statement ubah = koneksidb.createStatement();
             ubah.execute(sqlubah);
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     }
          public void ubahDonasiPST(String id, String uang, String brg, String sbk){
         try {
             String sqlubah = "Update donasi set Donasi_Uang = ?, Donasi_Barang = ?, Donasi_Sembako = ? where Id = ?";
             
             PreparedStatement ubah = koneksidb.prepareStatement(sqlubah);
             ubah.setString(1, uang);
             ubah.setString(2, brg);
             ubah.setString(3, sbk);
             ubah.setString(4, id);
             
             ubah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data berhasil diubah");
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     }
          public void hapusDonasiSTS(String id){
              try {
                  String sqlhapus = "Delete from donasi where Id = '"+id+"'";
                  Statement hapus = koneksidb.createStatement();
                  hapus.execute(sqlhapus);
                  
                  JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, e.getMessage());
              }
}
           public void hapusDonasi(String id){
              try {
                  String sqlhapus = "Delete from donasi where Id = ?";
                  PreparedStatement hapus = koneksidb.prepareStatement(sqlhapus);
                  hapus.executeUpdate();
                  
                  JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, e.getMessage());
              }
           }
           public void cetaklaporan(String filelaporan, String SQL) {
                try {
                    File laporan = new File(filelaporan);
                    JasperDesign designlaporan = JRXmlLoader.load(laporan);
                    JRDesignQuery querylaporan = new JRDesignQuery();
                    querylaporan.setText(SQL);
                    designlaporan.setQuery(querylaporan);
                    JasperReport objeklaporan = JasperCompileManager.compileReport(designlaporan);
                    JasperPrint objJasperPrint = JasperFillManager.fillReport(
                    objeklaporan, null, this.koneksidb);
                    JasperViewer.viewReport(objJasperPrint, false);
               } catch (Exception e) {
                e.printStackTrace();
    }
              
}
}
