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
public class crudlayanan {
    
    private String database="pbo_2310010344";
    private String username="root";
    private String password="";
    private String url="jdbc:mysql://localhost/"+database;
    private Connection koneksidb;
    public String var_nama, var_ket, var_kom = null;
    public boolean validasi = false;
    
    public crudlayanan (){
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
                model.addColumn("Nama");
                model.addColumn("Keterangan");
                model.addColumn("Komentar");
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
    
public void simpanLayananSTS(String id, String nm, String ket, String kom) {
    try {
        String checkPrimary = "SELECT * FROM layanan_publik WHERE Id='" + id + "'";
        Statement checkData = koneksidb.createStatement();
        ResultSet data = checkData.executeQuery(checkPrimary);

        if (data.next()) {
            String isi = "\nNama: " + data.getString("Nama")
                       + "\nKeterangan: " + data.getString("Keterangan")
                       + "\nKomentar: " + data.getString("Komentar");
            JOptionPane.showMessageDialog(null, "ID sudah terdaftar" + isi);

            this.validasi = true;
            this.var_nama = data.getString("Nama");
            this.var_ket = data.getString("Keterangan");
            this.var_kom = data.getString("Komentar");
        } else {
            String sql = "INSERT INTO layanan_publik VALUES ('" + id + "', '" + nm + "', '" + ket + "', '" + kom + "')";
            Statement perintah = koneksidb.createStatement();
            perintah.execute(sql);
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");

            this.validasi = false;
            this.var_nama = null;
            this.var_ket = null;
            this.var_kom = null;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

public void simpanLayananPST(String id, String nm, String ket, String kom) {
    try {
        String checkPrimary = "SELECT * FROM layanan_publik WHERE Id='" + id + "'";
        Statement checkData = koneksidb.createStatement();
        ResultSet data = checkData.executeQuery(checkPrimary);

        if (data.next()) {
            String isi = "\nNama: " + data.getString("Nama")
                       + "\nKeterangan: " + data.getString("Keterangan")
                       + "\nKomentar: " + data.getString("Komentar");
            JOptionPane.showMessageDialog(null, "ID sudah terdaftar" + isi);

            this.validasi = true;
            this.var_nama = data.getString("Nama");
            this.var_ket = data.getString("Keterangan");
            this.var_kom = data.getString("Komentar");
        } else {
            String sql = "INSERT INTO layanan_publik (Id, Nama, Keterangan, Komentar) VALUES (?, ?, ?, ?)";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, id);
            perintah.setString(2, nm);
            perintah.setString(3, ket);
            perintah.setString(4, kom);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");

            this.validasi = false;
            this.var_nama = null;
            this.var_ket = null;
            this.var_kom = null;
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}
        
     public void ubahLayananSTS(String id, String nm, String ket, String kom){
         try {
             String sqlubah = "Update layanan_publik set Nama = '"+nm+
                     "', Email = '"+ket+"', No_Hp = '"+kom+"' where Id='"+id+"'";
             
             Statement ubah = koneksidb.createStatement();
             ubah.execute(sqlubah);
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     }
          public void ubahLayananPST(String id, String nm, String ket, String kom){
         try {
             String sqlubah = "Update layanan_publik set Nama = ?, Keterangan = ?, Komentar = ? where Id = ?";
             
             PreparedStatement ubah = koneksidb.prepareStatement(sqlubah);
             ubah.setString(1, nm);
             ubah.setString(2, ket);
             ubah.setString(3, kom);
             ubah.setString(4, id);
             
             ubah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data berhasil diubah");
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     }
          public void hapusLayananSTS(String id){
              try {
                  String sqlhapus = "Delete from layanan_publik where Id = '"+id+"'";
                  Statement hapus = koneksidb.createStatement();
                  hapus.execute(sqlhapus);
                  
                  JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, e.getMessage());
              }
}
           public void hapusLayanan(String id){
              try {
                  String sqlhapus = "Delete from layanan_publik where Id = ?";
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
