package Login;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import Apresentacao.Employeeinfo;
import dados.conexao;
public class frmLogin {

	private JFrame frmLogin;
	private JTextField txtUsr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmLogin window = new frmLogin();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection conn = null;
	private JPasswordField txtPasswd;
	private JLabel lblNewLabel;
	
	/**
	 * Create the application.
	 */
	public frmLogin() {//Construtor da classe
		initialize();
		conn = conexao.conecta();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setResizable(false);
		frmLogin.setBounds(100, 100, 502, 197);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		txtUsr = new JTextField();
		txtUsr.setBounds(215, 18, 262, 27);
		frmLogin.getContentPane().add(txtUsr);
		txtUsr.setColumns(10);
		
		JButton btnLogin = new JButton("");
		Image img_ = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		btnLogin.setIcon(new ImageIcon(img_));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "select * from usuario where usuarioLogin=? and usuarioSenha=? ";
					PreparedStatement pst= conn.prepareStatement(query);
					pst.setString(1, txtUsr.getText());
					pst.setString(2, txtPasswd.getText());
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while(rs.next())
					{
						count++;
					}
					if(count == 1)
					{
						//JOptionPane.showMessageDialog(null, "User Name or Passwd is correct");
						frmLogin.dispose();
						Employeeinfo emplinfo = new Employeeinfo();
						emplinfo.setVisible(true);
						
					}
					else if(count >1)
					{
						JOptionPane.showMessageDialog(null, "Duplicated User and Passwd");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "User Name or Passwd is not correct, Try Again!");
					}
						
					rs.close();
					pst.close();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
					
				}
				
			}
		});
		btnLogin.setBounds(159, 118, 24, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setBounds(159, 24, 46, 14);
		frmLogin.getContentPane().add(lblUsurio);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(159, 55, 46, 14);
		frmLogin.getContentPane().add(lblSenha);
		
		txtPasswd = new JPasswordField();
		txtPasswd.setEchoChar('*');
		txtPasswd.setBounds(215, 49, 262, 27);
		frmLogin.getContentPane().add(txtPasswd);
		
		lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/Login.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(21, 17, 128, 124);
		frmLogin.getContentPane().add(lblNewLabel);
	}
}
