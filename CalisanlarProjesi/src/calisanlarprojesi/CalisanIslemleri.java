package calisanlarprojesi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;     
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Ferzan
 */
public class CalisanIslemleri {
    private Connection con = null;
    private Statement statemnet = null;
    private PreparedStatement preparedStatments = null;
    
    public ArrayList<Calisan> calisaniGetir(){
        ArrayList<Calisan> cikti = new ArrayList<Calisan>();
        try {
             statemnet = con.createStatement();
             String sorgu = "Select * From calisanlar";
             ResultSet rs = statemnet.executeQuery(sorgu);
             
             while(rs.next()){
                 int id = rs.getInt("id");
                 String ad = rs.getString("ad");
                 String soyad = rs.getString("soyad");
                 String dept = rs.getString("departman");
                 String maas = rs.getString("maas");
                 
                   cikti.add(new Calisan(id, ad, soyad, dept, maas));
             }
                  return cikti;   
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
     
    }
         public void calisanGuncelle(int id, String yeni_ad, String yeni_soyad,String yeni_departman, String yeni_maas){
             String sorgu = "Update calisanlar set ad = ? , soyad = ? , departman = ? , maas = ? where id = ?";
        try {
            preparedStatments = con.prepareStatement(sorgu);
            
            preparedStatments.setString(1, yeni_ad);
            preparedStatments.setString(2, yeni_soyad);
            preparedStatments.setString(3, yeni_departman);
            preparedStatments.setString(4, yeni_maas);
            
            preparedStatments.setObject(5, id);
            preparedStatments.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
         public void calisanSil(int id){
             String sorgu = "Delete from calisanlar where id = ?";
            
        try {
            preparedStatments = con.prepareStatement(sorgu);
            preparedStatments.setInt(1, id);
            preparedStatments.executeUpdate();
        } catch (SQLException ex) {
            
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
             
            
         }
         public void calisanEkle(String ad , String soyad, String departman, String maas){
            
            String sorgu = "Insert Into calisanlar (ad,soyad,departman,maas) VALUES(?,?,?,?)";
        try {
            preparedStatments = con.prepareStatement(sorgu);
            
            preparedStatments.setString(1, ad);
            preparedStatments.setString(2, soyad);
            preparedStatments.setString(3, departman);
            preparedStatments.setString(4, maas);
            
            preparedStatments.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }
       public boolean girisYap(String kullanici_adi, String parola){
        String sorgu = "Select * From adminler where username = ? and password = ?";
        try {
            preparedStatments = con.prepareStatement(sorgu);
            preparedStatments.setString(1, kullanici_adi);
            preparedStatments.setString(2, parola);
            
            ResultSet rs = preparedStatments.executeQuery();
            
           return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        return false;
        }
        
    }
    public CalisanIslemleri (){
        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_ismi+ "?useUnicode=true&characterEncoding=utf8";
        
         try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }
        
        
        try {
            con = DriverManager.getConnection(url, Database.kullanici_adi, Database.parola);
            System.out.println("Bağlantı Başarılı...");
            
            
        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
        }
       
        
        
        
    }
   
   
}
