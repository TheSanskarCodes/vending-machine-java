import java.sql.*;
import java.util.*;

public class VendingMachineProject {
	static Connection obj;
	static Scanner sc;
	static ResultSet rs;

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			obj = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendingmachinedatabase", "root", "");
			sc = new Scanner(System.in);
			while(true)
			{
				System.out.println("##### WELCOME TO THE VENDING MACHINE #####\n");
				System.out.println("IF YOU ARE A CUSTOMER PRESS 1");
				System.out.println("IF YOU ARE ADMIN PRESS 2");
				int ch = sc.nextInt();
				switch (ch) {
				case 1:
					customer();
					break;
				case 2:
					adminVerification();
					break;
				default:
					System.out.println("Invalid Input");
				}
				System.out.println("Do you want to Continue : Y/N");
				String c =sc.next();
				if(c.charAt(0)=='y'|| c.charAt(0)=='Y')
					continue;
				else
					break;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void adminVerification() {
		System.out.println("Enter the password");
		int password = sc.nextInt();
		int p = 4321;
		if(password == p)
		{
			admin();
		}
		else
		{
			System.out.println("Password is wrong !!");
			return;
		}
	}

	public static void customer() {
		int C20 = 0, C10 = 0, C5 = 0, C2 = 0, C1 = 0, coin, paid = 0, change;
		try {
			display(); // DISPLAYING ITEMS IN VENDING MACHINE
			System.out.println("Please Select the id of the product which you want to buy");
			int id = sc.nextInt();
			String sql = "SELECT * FROM productdetails WHERE id =?";
			PreparedStatement preparedStatement = obj.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			rs.next();
			int price = rs.getInt(3);
			int quantity = rs.getInt(4);
			if (quantity < 1) {
				System.out.println("Item is not Avalable");
			} else {
				sql = "UPDATE productdetails SET quantity = quantity-1 WHERE id =?";
				PreparedStatement preparedStatement2 = obj.prepareStatement(sql);
				preparedStatement2.setInt(1, id);
				int rowUpdate = preparedStatement2.executeUpdate();
				System.out.println("Please do the payment of : " + price);
				System.out.println("Do the payment in terms of :");
				System.out.println("Re1\tRe2\tRe5\tRe10\tRe20");
				while (paid < price) {
					System.out.println("Enter the required amount using coins: ");
					coin = sc.nextInt();
					if (coin == 1 || coin == 2 || coin == 5 || coin == 10 || coin == 20) {
						paid += coin;
					} else {
						System.out.println("Enter valid coin");
					}
				}
				if (paid > price) // GIVING CHANGE TO THE CUSTOMER
				{
					change = paid - price;
					if (change >= 20) {
						C20 = change / 20;
						change %= 20;
					}
					if (change >= 10) {
						C10 = change / 10;
						change %= 10;
					}
					if (change >= 5) {
						C5 = change / 5;
						change %= 5;
					}
					if (change >= 2) {
						C2 = change / 2;
						change %= 2;
					}
					if (change >= 1) {
						C1 = change;
					}
					System.out.println("Please Take your change :");
					System.out.println(
							"Re20 : " + C20 + "  Re10 : " + C10 + "  Re5 : " + C5 + "  Re2 : " + C2 + "  Re1 : " + C1);
				}
				System.out.println("Do you have any Query : Y/N");
				String c =sc.next();
				if(c.charAt(0)=='y'|| c.charAt(0)=='Y')
				{
					System.out.println("Please Enter your Query and give , in place of space");
					String Querry = sc.next();
					Querry(Querry);
				}
				System.out.println("Thankyou for shopping!!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void Querry(String querry) throws SQLException {
		String sql ="INSERT INTO query(Querry) VALUES(?)";
		PreparedStatement preparedStatement = obj.prepareStatement(sql);
	    preparedStatement.setString(1, querry); 
	    int rowInserted = preparedStatement.executeUpdate();
	    if(rowInserted > 0)
	    {
	    	System.out.println("Querry Submitted ");
	    }
	    else
	    {
	    	System.out.println("Wrong Format");
	    }
	}

	private static void display() {
		try {
			Statement statement = obj.createStatement();
			rs = statement.executeQuery("SELECT * FROM productdetails");
			System.out.println("ID\tNAME\t\tPRICE\tQUANTITY");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + rs.getInt(3) + "\t" + rs.getInt(4));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void admin() {
		try {
			System.out.println("\t##### Welcome Admin #####\n");
			Statement statement = obj.createStatement();
			rs = statement.executeQuery("SELECT * FROM productdetails WHERE quantity=0");
			if (rs.next() == true) {
				System.out.println("\t\tNOTIFICATION !!\n");
				System.out.println("THESE ITEMS ARE EXHAUSTED PLEASE ADD THEM Admin!!\n");
				System.out.println("\t\tId\tName");
				do {
					System.out.println("\t\t" + rs.getInt(1) + "\t" + rs.getString(2));
				} while (rs.next());
				System.out.println();
			}
			System.out.println("1)Update the quantity of a Product");
			System.out.println("2)Add new product");
			System.out.println("3)See Customers Querry");
			int ch = sc.nextInt();
			switch (ch) {
			case 1: {
				additems();
				break;
			}
			case 2: {
				addnewproduct();
				break;
			}
			case 3:
				Statement statement2 = obj.createStatement();
				ResultSet rs = statement2.executeQuery("SELECT * FROM query");
				int i=1;
				while(rs.next())
				{
					System.out.println(i++ + " : "+rs.getString(1));
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void addnewproduct() throws SQLException {
		String sql = "INSERT INTO productdetails(id,name,price,quantity) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = obj.prepareStatement(sql);
		System.out.println("Enter new id , name , price and quantity of the new product :");
		int id = sc.nextInt();
		String name = sc.next();
		int price = sc.nextInt();
		int quantity = sc.nextInt();
		preparedStatement.setInt(1, id);
		preparedStatement.setString(2, name);
		preparedStatement.setInt(3, price);
		preparedStatement.setInt(4, quantity);
		int rowInserted = preparedStatement.executeUpdate();
		if (rowInserted > 0) {
			System.out.println("A new item added successfully");
			display();
		}
	}

	private static void additems() throws SQLException {
		display();
		String sql = "UPDATE productdetails SET quantity =quantity+? WHERE id =?";
		PreparedStatement preparedStatement = obj.prepareStatement(sql);
		System.out.println("Enter the id of the product");
		int id = sc.nextInt();
		System.out.println("Enter the quantity to be added");
		int quantity = sc.nextInt();
		preparedStatement.setInt(1, quantity);
		preparedStatement.setInt(2, id);
		int rowsUpdated = preparedStatement.executeUpdate();
		if (rowsUpdated > 0) {
			System.out.println("\nExisting product quantity updated successfully\n");
			display();
		}
	}
}
