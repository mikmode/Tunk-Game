public class Inserter
{
    String myDriver = "org.gjt.mm.mysql.Driver";
    String myUrl = "jdbc:mysql://localhost/test";
  Class.forName(myDriver);
    Connection conn = DriverManager.getConnection(myUrl, "root", "");
}
