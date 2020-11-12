package gui.gui_user.graficos;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import db.DataBase;

import java.awt.Dimension;

public class Graficos {
	private DataBase db;
	private JPanel graficoMenu;
	private JPanel grafico1;
	private JPanel grafico2;
	private JPanel grafico3;
	private JPanel grafico4;
	private JPanel grafico5;
	private JComboBox<String> selectGraph;
	private CardLayout clGrafico;
	private JPanel grafico;

	public Graficos() {
		this.db = DataBase.getInstance();
	}
	private void putPanelsGrafico() {
		graficoMenu.add(grafico1, "grafico1");
		graficoMenu.add(grafico2, "grafico2");
		graficoMenu.add(grafico3, "grafico3");
		graficoMenu.add(grafico4, "grafico4");
		graficoMenu.add(grafico5, "grafico5");
		clGrafico.show(graficoMenu, "grafico1");
	}
	private void criaComboBox(JPanel panel) {
		selectGraph = new JComboBox<>();
		DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>(new String[] { "Origem Lotes",
				"Vendas Sub-Categoria", "Media salarios idade", "Vendas por Dia", "Atividade diaria Users" });
		selectGraph.setModel(model3);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(691, Short.MAX_VALUE)
					.addComponent(selectGraph, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addComponent(selectGraph, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
	}
	private void criaGraficos() {
		grafico1 = new JPanel();
		grafico1.setLayout(new BorderLayout(0, 0));

		ChartPanel chartPanel1 = origemLotes();
		grafico1.add(chartPanel1);

		grafico2 = new JPanel();
		grafico2.setLayout(new BorderLayout(0, 0));

		ChartPanel chartPanel2 = vendasSubCategoria();
		grafico2.add(chartPanel2);

		grafico3 = new JPanel();
		grafico3.setLayout(new BorderLayout(0, 0));

		ChartPanel chartPanel3 = mediaSalariosIdade();
		grafico3.add(chartPanel3);

		grafico4 = new JPanel();
		grafico4.setLayout(new BorderLayout(0, 0));

		ChartPanel chartPanel4 = vendasPorDia();
		grafico4.add(chartPanel4);

		grafico5 = new JPanel();
		grafico5.setLayout(new BorderLayout(0, 0));

		ChartPanel chartPanel5 = atividadeUser();
		grafico5.add(chartPanel5);
	}
	public void showGraficos(JFrame frame) {
		grafico = new JPanel();
		frame.getContentPane().add(grafico, "name_1773234573107200");
		grafico.setLayout(new BorderLayout(0, 0));

		graficoMenu = new JPanel();
		grafico.add(graficoMenu, BorderLayout.CENTER);
		clGrafico = new CardLayout(0, 0);
		graficoMenu.setLayout(clGrafico);

		criaGraficos();
		putPanelsGrafico();

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(0, 0));
		panel.setBackground(Color.WHITE);
		grafico.add(panel, BorderLayout.NORTH);

		criaComboBox(panel);
		botoesGrafico();
	}
	private void botoesGrafico() {
		
		selectGraph.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (selectGraph.getSelectedItem().toString().equals("Origem Lotes")) {
					clGrafico.show(graficoMenu, "grafico1");
				} else if (selectGraph.getSelectedItem().toString().equals("Vendas Sub-Categoria")) {
					clGrafico.show(graficoMenu, "grafico2");

				} else if (selectGraph.getSelectedItem().toString().equals("Media salarios idade")) {
					clGrafico.show(graficoMenu, "grafico3");

				} else if (selectGraph.getSelectedItem().toString().equals("Vendas por Dia")) {
					clGrafico.show(graficoMenu, "grafico4");

				} else {
					clGrafico.show(graficoMenu, "grafico5");

				}
			}
		});
	}
	
	public JPanel getGrafico() {
		return grafico;
	}
	public ChartPanel origemLotes() {

		CategoryDataset dataset = dadosOrigemLotes();
		JFreeChart chart = criarGraficoOrigemLotes(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		return chartPanel;
	}
	public CategoryDataset dadosOrigemLotes() {
		String sql = "select origem, count(origem) " + "from lote " + "group by origem "
				+ "ORDER BY count(origem) desc " + "limit 5";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ResultSet rs = db.executeQueryResult(sql);
		try {
			while (rs.next()) {
				dataset.setValue(rs.getInt("count"), "LOTES", rs.getString("origem"));
			}
		} catch (SQLException e) {
		}

		return dataset;
	}
	public JFreeChart criarGraficoOrigemLotes(CategoryDataset dataset) {

		return ChartFactory.createBarChart("Origem dos Lotes", "", "Lotes", dataset, PlotOrientation.VERTICAL, false,
				true, false);

	}

	// Vendas Sub Categoria
	public ChartPanel vendasSubCategoria() {

		CategoryDataset dataset = dadosVendasSubCategoria();
		JFreeChart chart = criarGraficoVendasSubCategoria(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		return chartPanel;
	}

	public CategoryDataset dadosVendasSubCategoria() {
		String sql = "select count(tipo), tipo from(" + "select sku, num_lote, vendido, lote.sub_categoria as tipo "
				+ "from produto " + "join lote " + "on produto.num_lote = lote.numero_lote " + "join sub_categoria "
				+ "on lote.sub_categoria = sub_categoria.sub_categoria " + "where vendido=TRUE) as exemplo "
				+ "group by tipo";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ResultSet rs = db.executeQueryResult(sql);

		try {
			while (rs.next()) {
				dataset.setValue(rs.getInt("count"), "PRODUTOS", rs.getString("tipo"));
			}
		} catch (SQLException e) {
		}

		return dataset;
	}

	public JFreeChart criarGraficoVendasSubCategoria(CategoryDataset dataset) {

		return ChartFactory.createBarChart("Vendas Sub-Categoria", "", "Produtos", dataset, PlotOrientation.VERTICAL,
				false, true, false);

	}

//Média Salários por Idade
	public ChartPanel mediaSalariosIdade() {

		CategoryDataset dataset = dadosMediaSalariosIdade();
		JFreeChart chart = criarGraficoMediaSalario(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		return chartPanel;
	}

	public CategoryDataset dadosMediaSalariosIdade() {
		String sql = "select idade, avg(salario) " + "from funcionario " + "group by idade";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ResultSet rs = db.executeQueryResult(sql);

		try {
			while (rs.next()) {
				dataset.setValue(rs.getInt("avg"), "avg", rs.getString("idade"));
			}
		} catch (SQLException e) {
		}
		return dataset;
	}

	public JFreeChart criarGraficoMediaSalario(CategoryDataset dataset) {
		return ChartFactory.createLineChart("Média Salário por Idades", "idade", "avg", dataset,
				PlotOrientation.VERTICAL, false, true, false);

	}

	// Vendas Por Dia
	public ChartPanel vendasPorDia() {

		CategoryDataset dataset = dadosVendasPorDia();
		JFreeChart chart = criarGraficoVendasPorDia(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		return chartPanel;
	}

	public CategoryDataset dadosVendasPorDia() {
		String sql = "select count(data_saida), data_saida " + "from produto " + "WHERE vendido=true "
				+ "group by data_saida " + "order by data_saida asc";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		ResultSet rs = db.executeQueryResult(sql);

		try {
			while (rs.next()) {
				dataset.setValue(rs.getInt("count"), "PRODUTOS", rs.getDate("data_saida"));
			}
		} catch (SQLException e) {
		}

		return dataset;
	}

	public JFreeChart criarGraficoVendasPorDia(CategoryDataset dataset) {
		return ChartFactory.createLineChart("Vendas por dia", "Data de Saída", "Número de vendas", dataset,
				PlotOrientation.VERTICAL, false, true, false);

	}

//Atividade por user
	public ChartPanel atividadeUser() {

		PieDataset dataset = dadosAtividadeUser();
		JFreeChart chart = criarGraficoAtividadeUser(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		return chartPanel;
	}

	public PieDataset dadosAtividadeUser() {
		String sql = "select count(data), username " + "from logs " + "where cast(data as Date)=CURRENT_DATE "
				+ "group by username";
		DefaultPieDataset dataset = new DefaultPieDataset();
		ResultSet rs = db.executeQueryResult(sql);

		try {
			while (rs.next()) {
				dataset.setValue(rs.getString("username"), rs.getInt("count"));
			}
		} catch (SQLException e) {
		}

		return dataset;
	}

	public JFreeChart criarGraficoAtividadeUser(PieDataset dataset) {
		return ChartFactory.createPieChart("Atividade Diária Utilizadores", dataset, false, true, false);

	}
}
