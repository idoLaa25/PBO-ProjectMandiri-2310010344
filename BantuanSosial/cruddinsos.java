 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BantuanSosial;

/*import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;*/

import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USER
 */
public class cruddinsos {
    
    private String database="pbo_2310010344";
    private String username="root";
    private String password="";
    private String url="jdbc:mysql://localhost/"+database;
    private Connection koneksidb;
    public String var_nama, var_jabatan = null;
    public boolean validasi = false;
    
    public cruddinsos (){
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
                model.addColumn("NIP"); 
                model.addColumn("Nama");
                model.addColumn("Jabatan");
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
    
public void simpanDinsosSTS(String nip, String nm, String jbt) {
    try {
        String checkPrimary = "SELECT * FROM dinsos WHERE NIP='" + nip + "'";
        Statement checkData = koneksidb.createStatement();
        ResultSet data = checkData.executeQuery(checkPrimary);

        if (data.next()) {
            String isi = "\nNama: " + data.getString("Nama")
                       + "\nJabatan: " + data.getString("Jabatan");
            JOptionPane.showMessageDialog(null, "NIP sudah terdaftar" + isi);

            this.validasi = true;
            this.var_nama = data.getString("Nama");
            this.var_jabatan = data.getString("Jabatan");
        } else {
            String sql = "INSERT INTO dinsos VALUES ('" + nip + "', '" + nm + "', '" + jbt + "')";
            Statement perintah = koneksidb.createStatement();
            perintah.execute(sql);
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");

            this.validasi = false;
            this.var_nama = null;
            this.var_jabatan = null;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

public void simpanDinsosPST(String nip, String nm, String jbt) {
    try {
        String checkPrimary = "SELECT * FROM dinsos WHERE NIP='" + nip + "'";
        Statement checkData = koneksidb.createStatement();
        ResultSet data = checkData.executeQuery(checkPrimary);

        if (data.next()) {
            String isi = "\nNama: " + data.getString("Nama")
                       + "\nJabatan: " + data.getString("Jabatan");
            JOptionPane.showMessageDialog(null, "NIP sudah terdaftar" + isi);

            this.validasi = true;
            this.var_nama = data.getString("Nama");
            this.var_jabatan = data.getString("Jabatan");
        } else {
            String sql = "INSERT INTO dinsos (NIP, Nama, Jabatan) VALUES (?, ?, ?)";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, nip);
            perintah.setString(2, nm);
            perintah.setString(3, jbt);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");

            this.validasi = false;
            this.var_nama = null;
            this.var_jabatan = null;
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}
        
     public void ubahDinsosSTS(String nip, String nm, String jbt){
         try {
             String sqlubah = "Update dinsos set Nama = '"+nm+
                     "', Jabatan = '"+jbt+"' where NIP='"+nip+"'";
             
             Statement ubah = koneksidb.createStatement();
             ubah.execute(sqlubah);
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     }
          public void ubahDinsosPST(String nip, String nm, String jbt){
         try {
             String sqlubah = "Update dinsos set Nama = ?, Jabatan = ? where NIP = ?";
             
             PreparedStatement ubah = koneksidb.prepareStatement(sqlubah);
             ubah.setString(1, nm);
             ubah.setString(2, jbt);
             ubah.setString(3, nip);
             
             ubah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data berhasil diubah");
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     }
          public void hapusDinsosSTS(String nip){
              try {
                  String sqlhapus = "Delete from dinsos where NIP = '"+nip+"'";
                  Statement hapus = koneksidb.createStatement();
                  hapus.execute(sqlhapus);
                  
                  JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, e.getMessage());
              }
}
           public void hapusDinsos(String nip){
              try {
                  String sqlhapus = "Delete from dinsos where NIP = ?";
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
