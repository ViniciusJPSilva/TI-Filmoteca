package vjps.filmoteca.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class IgSobre extends JDialog {

	/**
	 * Cria e exibe a GUI.
	 */
	public IgSobre(IgFilmoteca igFilmoteca) {
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel sobrePanel = new JPanel(), creditosPanel = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		tabbedPane.addTab("Sobre", null, sobrePanel, "Versão e copyright do programa");
		tabbedPane.addTab("Créditos", null, creditosPanel, "Autoria do programa" );
		
		sobrePanel.setLayout(new MigLayout("", "[][]", "[][][]"));
		creditosPanel.setLayout(new MigLayout("", "[][]", "[][][]"));
		
		JLabel iconeLabel = new JLabel("");
		sobrePanel.add(iconeLabel, "cell 0 0");
		
		JLabel nomeProgramaLabel = new JLabel("Filmoteca - Linguagem de Programação Visual");
		nomeProgramaLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		sobrePanel.add(nomeProgramaLabel, "cell 1 0");
		
		JLabel versaoLabel = new JLabel("Versão 1.0");
		versaoLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		sobrePanel.add(versaoLabel, "cell 1 1");
		
		JLabel copyrightLabel = new JLabel("Copyright (c) 2022 - Todos os Direitos Reservados");
		copyrightLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		sobrePanel.add(copyrightLabel, "cell 1 2");
		
		JLabel equipeLabel = new JLabel("Desenvolvedor");
		equipeLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		creditosPanel.add(equipeLabel, "cell 0 0");
		
		JLabel autorLabel = new JLabel("Vinícius José Pires Silva");
		autorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		creditosPanel.add(autorLabel, "cell 0 1");
		
		JLabel turmaLabel = new JLabel("Técnico em Informática - 4º Período");
		turmaLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		creditosPanel.add(turmaLabel, "cell 0 2");

		contentPanel.add(tabbedPane);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		buttonPane.add(okButton);
		
		okButton.addActionListener((e) ->  IgSobre.this.dispose());

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Sobre Filmoteca");
		
		// Define a posição da caixa de diálogo em relação a janela principal do programa. 
		setLocationRelativeTo(igFilmoteca);
		setSize(414, 212);
		setResizable(false);
		setVisible(true);
	} // construtor
} // class IgSobre 
