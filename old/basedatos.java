import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.sql.*;
import java.awt.Dimension;

import java.io.*;
import java.text.*;
import javax.naming.NamingException;


public class basedatos extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton jButton = null;
//	 Paso de mensajes
	Connections conexionTopic;
	Hashtable properties;
	Context context;
	ConnectionFactory factory;
	javax.jms.Connection connection;
	Session session;
	Destination destination;
	int i=0;

	private JButton jButton1 = null;

	private JButton jButton11 = null;

	public class Connections{
		private void StartConnection(){
			//	Context client;
			try{
				properties = new Hashtable();
			    properties.put(Context.INITIAL_CONTEXT_FACTORY, 
			                   "org.exolab.jms.jndi.InitialContextFactory");
			    properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");

			    context = new InitialContext(properties);
			    
			    factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			    
			    connection = factory.createConnection();
			    
			    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			    
			    destination = (Destination) context.lookup("topic1");
			    
			    connection.start();
            
				} catch(Exception ex){
					ex.printStackTrace();
				}
		}
		
		private void SendMessage(int mapa, int x, int y){
			try{
				//connection.start();
			    MessageProducer sender = session.createProducer(destination);
			    TextMessage message = session.createTextMessage("Posicion: "+x+" "+y);
			    sender.send(message);
			    
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		private void SendMessage(String mensaje){
			try{
				//connection.start();
			    MessageProducer sender = session.createProducer(destination);
			    TextMessage message = session.createTextMessage(mensaje);
			    sender.send(message);
			    
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	void EscribeFichero(String mensaje)
	{
	        try
	        {
	            FileWriter fichero = new FileWriter("./mensajes.txt",true);
	            PrintWriter pw = new PrintWriter(fichero);
	            /*for (int i = 0; i < 10; i++)
	                pw.println("Linea " + i);*/
	            pw.println(mensaje);
	            pw.close();
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    
	}
	
	class actualizar extends Thread {
		public void run() {
			try {
                    //start the connection to enable message delivery
					
					MessageConsumer receiver = session.createConsumer(destination);
				    receiver.setMessageListener(new MessageListener() {
				       
				       public void onMessage(Message message) {
				            try{
				            	TextMessage text = (TextMessage) message;
								String textoRecibido=text.getText();
								
								int indice = textoRecibido.indexOf("desconectar");
								
								if(indice != -1){
									
									//conectar con la base de datos
									String db = "./juegorol.mdb";
									String url = "jdbc:odbc:MS Access Database;DBQ=" + db;
								
					                //registrar el driver JDBC usando el cargador de clases Class.forName
									Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
									
									//System.out.println("\nEstableciendo conexion..."); access
									Connection con = DriverManager.getConnection (url, "", "");
									     
									//Statement select = con.createStatement();
																		     
									String Nombre1= textoRecibido.substring(textoRecibido.indexOf("-")+1,textoRecibido.indexOf("*"));
								    String Mundo = textoRecibido.substring(textoRecibido.indexOf("*")+1,textoRecibido.indexOf("*")+3);
								    String X = textoRecibido.substring(textoRecibido.indexOf("*")+3,textoRecibido.indexOf("*")+5);
								    String Y = textoRecibido.substring(textoRecibido.indexOf("*")+5,textoRecibido.indexOf("*")+7);
									String Bando= textoRecibido.substring(textoRecibido.indexOf("*")+7,textoRecibido.indexOf("*")+8);
									String Vida= textoRecibido.substring(textoRecibido.indexOf("*")+8,textoRecibido.indexOf("*")+12);
									String Nivel= textoRecibido.substring(textoRecibido.indexOf("*")+12,textoRecibido.indexOf("*")+14);
									String Objeto1= textoRecibido.substring(textoRecibido.indexOf("*")+14,textoRecibido.indexOf("*")+15);
									String Objeto2= textoRecibido.substring(textoRecibido.indexOf("*")+15,textoRecibido.indexOf("*")+16);
									String Objeto3= textoRecibido.substring(textoRecibido.indexOf("*")+16,textoRecibido.indexOf("*")+17);
									String Objeto4= textoRecibido.substring(textoRecibido.indexOf("*")+17,textoRecibido.indexOf("*")+18);
									String Exp= textoRecibido.substring(textoRecibido.indexOf("*")+18,textoRecibido.indexOf("*")+19);
									
								   
									char[] mundoc= new char[2];
									mundoc= Mundo.toCharArray();
									char[] Xc= new char[2];
									Xc= X.toCharArray();
									char[] Yc= new char[2];
									Yc= Y.toCharArray();
									char[] bandoc= new char[1];
									bandoc= Bando.toCharArray();
									char[] vidac= new char[4];
									vidac= Vida.toCharArray();
									char[] nivelc= new char[2];
									nivelc= Nivel.toCharArray();
									char[] objeto1c= new char[1];
									objeto1c= Objeto1.toCharArray();
									char[] objeto2c= new char[1];
									objeto2c= Objeto2.toCharArray();
									char[] objeto3c= new char[1];
									objeto3c= Objeto3.toCharArray();
									char[] objeto4c= new char[1];
									objeto4c= Objeto4.toCharArray();
									char[] expc= new char[1];
									expc= Exp.toCharArray();
									
									int world = (int)mundoc[0]*10+(int)mundoc[1]-528;
									int xs = (int)Xc[0]*10+(int)Xc[1]-528;
									int ys = (int)Yc[0]*10+(int)Yc[1]-528;
									int bando = (int)bandoc[0]-48;
									int health = (int)vidac[0]*1000+(int)vidac[1]*100+(int)vidac[2]*10+(int)vidac[3]-53328;
									int level = (int)nivelc[0]*10+(int)nivelc[1]-528;
								    int object1= (int)objeto1c[0]-48;;
								    int object2= (int)objeto2c[0]-48;;
								    int object3= (int)objeto3c[0]-48;;
								    int object4= (int)objeto4c[0]-48;;
								    int expi = (int)expc[0]-48;;
								   
								    ResultSet consulta; 
									Statement sentencia=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
									consulta=sentencia.executeQuery("SELECT * FROM Usuarios where Nombre='"+Nombre1+"'"); 
									boolean seguir = consulta.next();
									if(seguir){
										consulta.updateInt(2,expi); 
										consulta.updateInt(4,world); 
										consulta.updateInt(5,xs); 
										consulta.updateInt(6,ys); 
										consulta.updateInt(9,bando); 
										consulta.updateInt(10,health); 
										consulta.updateInt(11,level); 
										consulta.updateInt(12,object1); 
										consulta.updateInt(13,object2); 
										consulta.updateInt(14,object3); 
										consulta.updateInt(15,object4); 
										consulta.updateRow();
									}
									consulta.close();
									con.close();
									System.out.println("\nConexion con: \"" + db + "\" cerrada.\n\n");
								}
								
				            }
				            catch (Exception pollo)
							{
								//System.out.println("\nError al conectar con la BD: " + pollo.getMessage() + "\n");
								System.out.println("\nError al realizar alguna accion del programa.\n\n");
								      
							}
				       }
				       
				    });
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
							
	
	class servidor extends Thread {
		public void run() {
			try {
                    //start the connection to enable message delivery
					
					MessageConsumer receiver = session.createConsumer(destination);
				    receiver.setMessageListener(new MessageListener() {
				       
				       public void onMessage(Message message) {
				            try{
				            	TextMessage text = (TextMessage) message;
								String textoRecibido=text.getText();
								
								// Obtenemos la hora actual de recepción del mensaje
								long time=System.currentTimeMillis();
								Date fecha=new Date(time);
								SimpleDateFormat sdf;
						        sdf = new SimpleDateFormat("HH:mm:ss");
						        String horaActual = new String(sdf.format(fecha));
						        // Guardamos en el fichero la hora actual y el mensaje recibido en ese momento
								EscribeFichero("  > "+horaActual+" >> "+textoRecibido);
								
								int indice = textoRecibido.indexOf("okbd");
								
								if(indice == -1){
									
									indice = textoRecibido.indexOf("newbd");
									if(indice!=-1){
										try{
											//conectar con la base de datos
										   String db = "./juegorol.mdb";
										   String url = "jdbc:odbc:MS Access Database;DBQ=" + db;
								
					                       //registrar el driver JDBC usando el cargador de clases Class.forName
									       Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
									      
										   //System.out.println("\nEstableciendo conexion..."); access
									       Connection con = DriverManager.getConnection (url, "", "");
									     
									       //Statement select = con.createStatement();
									     
									       PreparedStatement stmt = con.prepareStatement("INSERT INTO Usuarios VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
									     
									       String Nombre= textoRecibido.substring(textoRecibido.indexOf("-")+1,textoRecibido.indexOf("*"));
									       String Sexo= textoRecibido.substring(textoRecibido.indexOf("*")+1, textoRecibido.indexOf("*")+2);
									       String Bando= textoRecibido.substring(textoRecibido.indexOf("*")+2,textoRecibido.indexOf("*")+3);
									       String Casa= textoRecibido.substring(textoRecibido.indexOf("*")+3,textoRecibido.indexOf("*")+4);
									       String Imagen= textoRecibido.substring(textoRecibido.indexOf("*")+4,textoRecibido.indexOf("*")+5);
								    	   
									       char[] sexoc= new char[1];
									       sexoc= Sexo.toCharArray();
									       char[] bandoc= new char[1];
									       bandoc= Bando.toCharArray();
									       char[] casac= new char[1];
									       casac= Casa.toCharArray();
									       char[] imagenc= new char[1];
									       imagenc= Imagen.toCharArray();
									       int sexo = (int)sexoc[0]-48;
									       int bando = (int)bandoc[0]-48;
									       int casa = (int)casac[0]-48;
									       int imagen = (int)imagenc[0]-48;
								    	   int exp = 0;
								    	   int mapa;
								    	   if (bando == 0) mapa = 3;
								    	   else mapa= 5;
								    	   int X= 1;
								    	   int Y= 1;
								    	   int vida = 100;
								    	   int nivel = 1;
								    	   int objeto1= 0;
								    	   int objeto2= 1;
								    	   int objeto3= 2;
								    	   int objeto4= 3;
								    	   
								    	   stmt.setString(1,Nombre);
								    	   stmt.setInt(2,exp);
								    	   stmt.setString(3, "");
								    	   stmt.setInt(4,mapa);
								    	   stmt.setInt(5,X);
								    	   stmt.setInt(6,Y);
								    	   stmt.setInt(7,casa);
								    	   stmt.setInt(8,sexo);
								    	   stmt.setInt(9,bando);
								    	   stmt.setInt(10,vida);
								    	   stmt.setInt(11,nivel);
								    	   stmt.setInt(12,objeto1);
								    	   stmt.setInt(13,objeto2);
								    	   stmt.setInt(14,objeto3);
								    	   stmt.setInt(15,objeto4);
								    	   stmt.setInt(16,imagen);
								    	   
								    	   stmt.executeUpdate();     
									       con.close();
									       System.out.println("\nConexion con: \"" + db + "\" cerrada.\n\n");
										}
										catch (Exception pollo)
									    {
									       //System.out.println("\nError al conectar con la BD: " + pollo.getMessage() + "\n");
									       System.out.println("\nError al realizar alguna accion del programa.\n\n");
									       
									    }
									}
									else{
										indice = textoRecibido.indexOf("bd");
									
										if(indice != -1){
											indice = textoRecibido.indexOf("-");
									
											String Nombre=textoRecibido.substring(indice+1);
											try{					
										
												//conectar con la base de datos
												String db = "./juegorol.mdb";
												String url = "jdbc:odbc:MS Access Database;DBQ=" + db;

												//registrar el driver JDBC usando el cargador de clases Class.forName
												Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
												
												//System.out.println("\nEstableciendo conexion..."); access
												Connection con = DriverManager.getConnection (url, "", "");
								       
												//busqueda
												Statement select = con.createStatement();
												ResultSet nombres = select.executeQuery("SELECT * FROM Usuarios where Nombre = '"+Nombre+"'");
												boolean seguir = nombres.next();
												
												//si ya esta insetado recuperamos sus datos
												if(seguir)
												{
													String jugnombre = nombres.getString(1);
													int jugexp = nombres.getInt(2);
													String contraseña = nombres.getString(3);
													int mapa = nombres.getInt(4);
													int x = nombres.getInt(5);
													int y = nombres.getInt(6);
													int casa = nombres.getInt(7);
													int sexo =nombres.getInt(8);
													int bando= nombres.getInt(9);
													int vida = nombres.getInt(10);
													int nivel = nombres.getInt(11);
													int objeto1 = nombres.getInt(12);
													int objeto2 = nombres.getInt(13);
													int objeto3 = nombres.getInt(14);
													int objeto4 = nombres.getInt(15);
													
													int img = nombres.getInt(16);
								    	   
													String charX="0";
													String charY="0";
													String charMapa="0";
													String charimg="0";
													String charnivel = "0";
													String charvida1 = "0";
													String charvida2 = "0";
													String charvida3 = "0";
													
													if(mapa>9)charMapa="";
													if(x>9)charX="";
													if(y>9)charY="";
													if(img>9)charimg="";
													if(nivel>9)charnivel="";
													if(vida>9)charvida1="";
													if(vida>99)charvida2="";
													if(vida>999)charvida3="";
								    	   
													conexionTopic.SendMessage("okbd-"+jugnombre+"*"+charMapa+mapa+charX+x+charY+y+charimg+img+casa+sexo+bando+charvida3+charvida2+charvida1+vida+charnivel+nivel+objeto1+objeto2+objeto3+objeto4+jugexp);
												} 
										
										
												//si no le creamos el nuevo usuario
												else
												{
								    	   
													conexionTopic.SendMessage("Error");
												}					   
												//cerramos la bd
												nombres.close();
												select.close();       
												con.close();
												System.out.println("\nConexion con: \"" + db + "\" cerrada.\n\n");   
										
											}
											catch (Exception pollo)
											{
												//System.out.println("\nError al conectar con la BD: " + pollo.getMessage() + "\n");
												System.out.println("\nError al realizar alguna accion del programa.\n\n");
											}
										}
									}	
								}
				            }
							catch (JMSException e) {
								e.printStackTrace();
							}
				        }
				    });
			  
		    }
			catch(JMSException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Lanzar servidor");
			jButton.setBounds(new Rectangle(203, 95, 143, 30));
			jButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//	Metodo que nos da la hora del sistema
					long time=System.currentTimeMillis();
					Date fecha=new Date(time);
					SimpleDateFormat sdf;
			        sdf = new SimpleDateFormat("HH:mm:ss");
			        String horaActual = new String(sdf.format(fecha));
			        
					EscribeFichero("[ ON - Servidor Harry Potter Iniciado ]");
					EscribeFichero("[ Fecha: "+fecha.toString()+" ]");
			        EscribeFichero("[ Hora: "+horaActual+" ]");

					conexionTopic = new Connections();
					conexionTopic.StartConnection();
					servidor server = new servidor();
					server.start();
					actualizar actualiza = new actualizar();
					actualiza.start();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */

	/**
	 * This method initializes jButton11	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setBounds(new Rectangle(182, 151, 180, 30));
			jButton11.setText("Desconectar servidor");
			jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					long time=System.currentTimeMillis();
					Date fecha=new Date(time);
					SimpleDateFormat sdf;
			        sdf = new SimpleDateFormat("HH:mm:ss");
			        String horaActual = new String(sdf.format(fecha));
			        
					EscribeFichero("[ OFF - Servidor Harry Potter Desconectado ]");
					EscribeFichero("[ Fecha: "+fecha.toString()+" ]");
			        EscribeFichero("[ Hora: "+horaActual+" ]");
			        EscribeFichero("\n");
					try {
	                    context.close();
	                } catch (NamingException exception) {
	                    exception.printStackTrace();
	                }
	                try {
	                    connection.close();
	                } catch (JMSException exception) {
	                    exception.printStackTrace();
	                }
				}
			});
		}
		return jButton11;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				basedatos thisClass = new basedatos();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public basedatos() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(538, 257);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton11(), null);
		
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
