package Apresentacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;
import dados.conexao;

import javax.swing.border.BevelBorder;

import java.awt.Panel;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class Employeeinfo extends JFrame {

	private JPanel contentPane;
	int salvar = 0;
	int usuarioID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employeeinfo frame = new Employeeinfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection conn = null;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField psswd;
	/**
	 * Create the frame.
	 */
	public Employeeinfo() {
		conn = conexao.conecta();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Consulta");
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "select * from usuario";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));//Usando Biblioteca de terceiros
					pst.close();
					rs.close();
				}catch(Exception ex){
					
				}
			}
		});
		btnLoadTable.setBounds(684, 11, 89, 23);
		
		
		contentPane.add(btnLoadTable);
		
		JComboBox nivel = new JComboBox();
		nivel.setModel(new DefaultComboBoxModel(new String[] {"Selecione Nivel", "Gerencia", "Atendimento", "Impressao", "ArteFinalista", "Acabamento", "Produ\u00E7\u00E3o", "Vendas"}));
		nivel.setSelectedIndex(0);
		nivel.setBounds(225, 97, 124, 20);
		contentPane.add(nivel);
		
		JCheckBox Ativo_ = new JCheckBox("Ativo");
		Ativo_.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

			}
			
		});
		Ativo_.setSelected(true);
		Ativo_.setBounds(10, 11, 97, 23);
		contentPane.add(Ativo_);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 160, 763, 260);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//table.getSelectedRow().Column[0].ToString();
				int rows = table.getSelectedRow();
				int colunas  = table.getColumnCount();
			 	JOptionPane.showMessageDialog(null, "Linha ="+rows+" Coluna="+ table.getValueAt(rows, 0).toString());
			 	for(int i=0; i< colunas; i++){
			 		if(i==0){
			 			usuarioID = (int) table.getValueAt(rows, i);
			 		}
			 		if(i==2){
			 			txtNome.setText((String) table.getValueAt(rows, i));
			 		}
			 		if(i==5){
			 			txtLogin.setText((String) table.getValueAt(rows, i));
			 		}
			 		if(i==6){
			 			psswd.setText((String) table.getValueAt(rows, i));
			 		}
			 		if(i==9){
			 			if(table.getValueAt(rows, 9).toString().equals("S")){
			 				Ativo_.setSelected(true);
			 			}
			 			else{
			 				Ativo_.setSelected(false);
			 			}
			 		}
			 		if(i == 7){
			 			JOptionPane.showMessageDialog(null, table.getValueAt(rows, i));
			 			nivel.setSelectedIndex((int) table.getValueAt(rows, i));
			 		}
			 			
			 	}
				salvar = 1;
			}
		});
				
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(10, 44, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setBounds(10, 72, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setBounds(10, 100, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		
		
		txtNome = new JTextField();
		txtNome.setBounds(48, 41, 301, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		txtLogin.setBounds(48, 69, 301, 20);
		contentPane.add(txtLogin);
		
		psswd = new JPasswordField();
		psswd.setBounds(48, 97, 167, 20);
		contentPane.add(psswd);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(salvar == 0){
				try{
					String query = "insert into usuario (`funcionarioID`, `usuarioNome`, `usuariotelefone`, `usuarioEmail`,  `usuarioLogin`, `usuarioSenha`, `usuarioNivel`, `usuarioAtivo`, `empresaID`) values ('1', ?,'','', ?, ?, ?, ?, '1')";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtLogin.getText());
					pst.setString(3, psswd.getText());
					pst.setInt(4, nivel.getSelectedIndex());
					if(Ativo_.isSelected()){
						pst.setString(5, "S");
					}
					else
					{
						pst.setString(5, "N");
					}
					
					//pst.executeQuery();
					if(!pst.execute()){
						JOptionPane.showMessageDialog(null,"Dados Salvos");
						try{
							String query_ = "select * from usuario";
							PreparedStatement pst_ = conn.prepareStatement(query_);
							ResultSet rs = pst_.executeQuery();
							
							table.setModel(DbUtils.resultSetToTableModel(rs));//Usando Biblioteca de terceiros
							table.updateUI();
							pst.close();
							rs.close();
						}catch(Exception ex){
							
						}
						
					}
					//table.setModel(DbUtils.resultSetToTableModel(rs));//Usando Biblioteca de terceiros
					pst.close();
					//rs.close();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}else{
				try{
					String query = "update usuario set usuarioNome=?,  usuarioLogin=?, usuarioSenha=?, usuarioNivel=?, usuarioAtivo=? where usuarioID=?";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtLogin.getText());
					pst.setString(3, psswd.getText());
					pst.setInt(4, nivel.getSelectedIndex());
					if(Ativo_.isSelected()){
						pst.setString(5, "S");
					}
					else
					{
						pst.setString(5, "N");
					}
					pst.setInt(6, usuarioID);
					//pst.executeQuery();
					if(!pst.execute()){
						JOptionPane.showMessageDialog(null,"Dados Atualizados com Sucesso");
						try{
							String query_ = "select * from usuario";
							PreparedStatement pst_ = conn.prepareStatement(query_);
							ResultSet rs = pst_.executeQuery();
							
							table.setModel(DbUtils.resultSetToTableModel(rs));//Usando Biblioteca de terceiros
							table.updateUI();
							pst.close();
							rs.close();
						}catch(Exception ex){
							
						}
						
					}
					//table.setModel(DbUtils.resultSetToTableModel(rs));//Usando Biblioteca de terceiros
					pst.close();
					//rs.close();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
				
		}
	});
		btnSalvar.setBounds(10, 125, 89, 23);
		contentPane.add(btnSalvar);
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvar = 0;
				txtLogin.setText("");
				txtNome.setText("");
				psswd.setText("");
				nivel.setSelectedIndex(0);
			}
		});
		btnNovo.setBounds(260, 11, 89, 23);
		contentPane.add(btnNovo);
	}
}
